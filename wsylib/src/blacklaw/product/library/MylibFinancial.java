package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MylibFinancial extends Activity implements OnClickListener{
	//	private String strCount;
	private TextView countCW;
	private TextView countCZ;
	private TextView countFW;
	private TextView countFZ;
	private TextView countYY;
	private MainMylib parent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylib_financial);
		parent=(MainMylib)this.getParent();
		parent.sonFinancial=this;
		//if(common.isDebugAnd(strCount==""))
		
		countCW=(TextView)findViewById(R.id.countCW);
		countCZ=(TextView)findViewById(R.id.countCZ);
		countFW=(TextView)findViewById(R.id.countFW);
		countFZ=(TextView)findViewById(R.id.countFZ);
		countYY=(TextView)findViewById(R.id.countYY);
		((Button)findViewById(R.id.financial_save)).setOnClickListener(this);
		((Button)findViewById(R.id.financial_share)).setOnClickListener(this);
		//	Log.v("loadJSONData",String.valueOf(strCount.length()));	


		/*	try {
				Log.v("TXT",loadAsset("history.txt"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
//Toast.makeText(this, Item2Activity.getStr, Toast.LENGTH_SHORT).show();
		
	}
	public void setData(String strData){
		
				loadJSONData(strData);
	}
/*	private String loadAsset(String url) throws Exception{
		String rValue="";
		InputStream in=this.getAssets().open(url);
		//FileInputStream in= new FileInputStream(url);
		byte[] buf=new byte[in.available()];
		in.read(buf);
		in.close();
		rValue=EncodingUtils.getString(buf, "utf-8");
		//InputStreamReader inr=new InputStreamReader(in);
		//Reader buf=new BufferedReader(inr);
		
		return rValue;
		
	}*/
	private boolean loadJSONData(String data){
		Log.v("loadJSONData",String.valueOf(data.length()));
		if(data.length()==0)
			return false;
		try {
			JSONArray array=new JSONArray(data);
			countCW.setText((String)array.get(0));
			countCZ.setText((String)array.get(1));
			countYY.setText((String)array.get(2));
			countFW.setText((String)array.get(3));
			countFZ.setText((String)array.get(4));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.financial_save:save();break;
		case R.id.financial_share:share();break;
		}
	}
	public boolean save(){
		String filename="";
		filename=Re.name.financial_save.replace("~time~", String.valueOf(new Date().getTime()));
		Common.writeLibData(parent,filename, getShareStr().getBytes(),true);
		return false;
	}
	public String getShareStr(){
		String shareStr="";
		shareStr=Re.string.financial_share_body_temple.replace("CW", countCW.getText().toString())
				.replace("CZ",countCZ.getText().toString())
				.replace("YY",countYY.getText().toString())
				.replace("FW",countFW.getText().toString())
				.replace("FZ",countFZ.getText().toString());
		
		
		/*shareStr="财经状况";
		shareStr+="\r\n普通借阅押金: "+countCW.getText().toString();
		shareStr+="\r\n专项借阅押金: "+countCZ.getText().toString();
		shareStr+="\r\n现预付款余额: "+countYY.getText().toString();
		shareStr+="\r\n普通外借余款: "+countFW.getText().toString();
		shareStr+="\r\n专项外借余款: "+countFZ.getText().toString()+"\r\n";*/
		return shareStr;
	}
	public boolean share(){

		Common.shareWords(this,Re.string.financial_share_head+getShareStr());
		return true;
	}

}
