package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class MylibLogin extends Activity{
	private MainMylib parent;
	private Button butLogin;
	private CheckBox checkReserved;	
	private EditText editName;
	private EditText editPassword;
	private String session;
	private String username;
	private String password;

	private int		loadPro=0;
	private ProgressDialog progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylib_login);
		
		parent=((MainMylib)this.getParent());
		//butClear=(Button)findViewById(R.id.butClear);
		butLogin=(Button)findViewById(R.id.butLogin);
		checkReserved=(CheckBox)findViewById(R.id.checkRemember);
		//*****checkReserved=(CheckBox)findViewById(R.id.checkReserved);
		editName=(EditText)findViewById(R.id.editName);
		editPassword=(EditText)findViewById(R.id.editPassword);
		editName.setText(Common.getShare(this,Re.name.share_file, Re.name.username_share_block));
		editPassword.setText(Common.getShare(this,Re.name.share_file, Re.name.password_share_block));

		butLogin.setOnClickListener(onLoginClick);

	}
	OnClickListener onLoginClick= new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			username=editName.getText().toString();
			password=editPassword.getText().toString();
			if(!checkInput(username,password)){
				warnInputIllegal();
				return;
			}
			progress=ProgressDialog.show(parent,Re.leading.login_loading_progress[0],Re.leading.login_loading_progress[1],true,true);
			LoginRunnable checkRunnable=new LoginRunnable();
			checkRunnable.init(Re.url.USER_CONTENT+"?ACTION=test&NAME="+username+"&PASSWORD="+password, 4);
			new Thread(checkRunnable).start();
			
			/*if(checkAccount(editName.getText().toString(),editPassword.getText().toString()))
			{	Toast.makeText(MylibLogin.this, "Login in success", Toast.LENGTH_SHORT).show();

			
			}else
				Toast.makeText(MylibLogin.this, "Login in failure", Toast.LENGTH_SHORT).show();
		*/	
		}
		
	} ;
	private void warnInputIllegal(){
		Toast.makeText(this,Re.warning.login_input_error, Toast.LENGTH_SHORT).show();
	}
	private boolean checkInput(String lUsername,String lPassword){
		
		return !(lUsername.length()==0 || lPassword.length()==0);
	}
	private void codeLoginData(String str){
		Log.v("onLoginEnd()","str"+str);
		if(isLoginSuccess(str)){
			parent.notifySonDataChanged(5);//Login Success Message
			if(checkReserved.isChecked()){
				Common.putShare(this,Re.name.share_file,Re.name.username_share_block, username);
				Common.putShare(this,Re.name.share_file, Re.name.password_share_block, password);
			}
			Toast.makeText(MylibLogin.this,Re.leading.login_success, Toast.LENGTH_SHORT).show();
			LoginRunnable countRunnable=new LoginRunnable();
			LoginRunnable renewRunnable=new LoginRunnable();
			LoginRunnable historyRunnable=new LoginRunnable();
			renewRunnable.init(Re.url.USER_CONTENT+"?&ACTION=renew&SESSION="+session, 1);
			historyRunnable.init(Re.url.USER_CONTENT+"?&ACTION=history&SESSION="+session, 2);
			countRunnable.init(Re.url.USER_CONTENT+"?&ACTION=count&SESSION="+session, 3);
			loadPro=0;
			
			new Thread(renewRunnable).start();
			new Thread(historyRunnable).start();
			new Thread(countRunnable).start();
			}else{
				warnWrongInputInfo();
				loadPro=0;
				progress.dismiss();
			}
		
	}
	private void warnWrongInputInfo(){
		Toast.makeText(MylibLogin.this, Re.leading.login_failure, Toast.LENGTH_SHORT).show();
		
	}
	class LoginRunnable implements Runnable{
		private int handlerMsg;
		private RemoteReader reader=new RemoteReader();
		private String url;
		public void init(String iUrl,int iHandlerMsg){
			handlerMsg=iHandlerMsg;
			url=iUrl;
			Log.v("mRunnable_url",url);
		}
		public void run() {
			// TODO Auto-generated method stub
			String rValue="";
		
				rValue=reader.loadData(url);

			mHandler.obtainMessage(handlerMsg,rValue).sendToTarget();
			
		}
		
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler=new Handler(){

		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what)
			{
			case 1:parent.strRenew=(String)msg.obj;progressSay(Re.leading.block_login_success.renew);break;
			case 2:parent.strHistory=(String)msg.obj;progressSay(Re.leading.block_login_success.history);break;
			case 3:parent.strFinancial=(String)msg.obj;progressSay(Re.leading.block_login_success.financial);break;
			case 4:progressSay(Re.leading.login_success);codeLoginData((String)msg.obj);break;
			}
			if(msg.what<=3 && msg.what>=1)
				loadPro+=msg.what;
	
			if(loadPro==6){
				loadPro=0;
				progress.dismiss();
			
			}
			parent.notifySonDataChanged(msg.what);

			super.handleMessage(msg);
		}
	};
	private void progressSay(String msg){
		progress.setMessage(msg);
		//Toast mToast=null;
		//mToast=Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		//mToast.show();
		
	}
	private boolean isLoginSuccess(String str){
		//RemoteReader reader=new RemoteReader();
			JSONObject	rObj=new JSONObject();
			String rValue="";

			try{
			rObj=new JSONObject(str);	
			session=(String)rObj.get("session");
			rValue=(String)rObj.get("state");
			//Toast.makeText(ItemLogin.this, str, Toast.LENGTH_SHORT).show();
			
			}catch(Exception e){
				Log.v("checkAccount_error",e.toString());
				
			}
			if(rValue.indexOf("FAILURE")>=0)
				Toast.makeText(this,Re.warning.no_register , Toast.LENGTH_SHORT).show();
			if(rValue.length()==0)
				Toast.makeText(this, Re.warning.no_net_server, Toast.LENGTH_SHORT).show();
			Log.v("checkAccount_rValue",rValue);
			return(rValue.indexOf("SUCCESS")>=0);
				
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "this is BooksFavorite", Toast.LENGTH_SHORT).show();
		/*Activity lParent=getParent();
		if(lParent!=null)
			lParent.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);*/
		return false;
	}

}
