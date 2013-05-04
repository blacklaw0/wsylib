package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;

public class MylibHistory extends Activity implements OnClickListener{
	private MainMylib parent;
	private AdapterHistory historyAdapter;
	private ExpandableListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylib_history);
		parent=(MainMylib)this.getParent();
		parent.sonHistory=this;
		/*if(Common.isDebugAnd(strHistory==""))
			strHistory=loadAsset("history.txt");
		*/
		listView=(ExpandableListView)findViewById(R.id.expandableListView1);
		historyAdapter=new AdapterHistory(this);
		listView.setAdapter(historyAdapter);
		listView.setOnGroupClickListener(new ExOnGroupClickListener());
		findViewById(R.id.history_save).setOnClickListener(this);
		findViewById(R.id.history_share).setOnClickListener(this);
		//historyAdapter.setObj(strHistory);
		/*listView.setOnGroupClickListener(new OnGroupClickListener(){
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				int head=listView.getFirstVisiblePosition();
				if(sign==-1){
					listView.expandGroup(groupPosition);
		            // listView.setSelectedGroup(head);  
	                 sign= groupPosition;
				}else if(sign==groupPosition){
						listView.collapseGroup(groupPosition);
						sign=-1;	
				}else{
					
					listView.collapseGroup(sign);
					listView.expandGroup(groupPosition);
					//listView.setSelectedGroup(head);
					sign=groupPosition;
					}
				return true;
			}
			
			
		});*/

	}
	public void setData(String strData){
		historyAdapter.setData(strData);
		
	}
	/*
	private String loadAsset(String url){
		String rValue="";
		InputStream in=null;
		try {
			in = this.getAssets().open(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//FileInputStream in= new FileInputStream(url);
		byte[] buf=null;
		try {
			buf = new byte[in.available()];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.read(buf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rValue=EncodingUtils.getString(buf, "utf-8");
		//InputStreamReader inr=new InputStreamReader(in);
		//Reader buf=new BufferedReader(inr);
		
		return rValue;
		
	}
	*/
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
	String num="";
	ProgressDialog progress;
	boolean fromShare=false;
	public boolean share(){
		save();
		fromShare=true;
		return true;
	}
	public void shareEnd(String filename){
		String content="自进入武生院 http://www.whsw.net以来,我已经读过 "+num+" 本书!正所谓 读书破万卷,下笔如有神 ,看来,读过这么多书的我已经超\"神\"了啊!";
		content+="(分享自 武生院图书馆 "+Re.url.DOWNLOAD_ADDR+" ) 堂客网http://townke.com";
		fromShare=false;
		Log.v("MylibHistory.shareEnd().filename",filename);
		Common.sharePhoto(this,Environment.getExternalStorageDirectory()+filename,content);
	}
	public boolean save(){
		ArrayList<String> list=historyAdapter.getPrintList();
		num=String.valueOf(list.size()-1);
		list.add("原来我已经借过 "+num+" 本书啊!再接再厉!");
		list.add("你也来晒晒书单吧!");
		list.add("(分享自 武生院图书馆 )");
		list.add("http://www.townke.com");
		list.add("由堂客网str2png工具转换输出而成");	
		SaveRunnable mRunnable=new SaveRunnable();
		mRunnable.list=list;
		String filename="借阅史"+String.valueOf(new Date().getTime())+".png";
		if(!Common.writeLibData(parent,filename,"".getBytes(),false))
		return false;
		Log.v("MylibHistory.save()","new file success");
		mRunnable.filename="/BlackLaw/wsylib/"+filename;
		
		progress=ProgressDialog.show(parent,Re.leading.history_pic_save_progress[0],Re.leading.history_pic_save_progress[1].replace("~filename~",filename),true,true);
		new Thread(mRunnable).start();
		return true;
	}
	class SaveRunnable implements Runnable{
		public ArrayList<String> list=new ArrayList<String>();
		public String filename="";
		public void run() {
			// TODO Auto-generated method stub
			Common.str2bmp(list, filename);
			mHandle.obtainMessage(1,filename).sendToTarget();
			//Toast.makeText(this, "共借出"++"本书!", Toast.LENGTH_SHORT).show();
			}
		
		
	}
	Handler mHandle=new Handler(){

		@SuppressLint({ "HandlerLeak", "HandlerLeak" })
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				progress.dismiss();
				if(fromShare)
					shareEnd((String)msg.obj);
			
					
			}
			super.handleMessage(msg);
		}
		
	};
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.history_save:save();break;
		case R.id.history_share:share();break;
		}
	}

}
