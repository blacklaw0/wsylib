package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class MylibRenew extends Activity implements  OnItemClickListener, OnClickListener{
	private ListView renewList;
	private AdapterRenew adapter;
//	private String strRenew="";
	private MainMylib parent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylib_renew);
		parent=((MainMylib)this.getParent());
		parent.sonRenew=this;
		/*if(Common.isDebugAnd(strRenew==""))
			strRenew=loadAsset("renew.txt");*/
		renewList=(ListView)findViewById(R.id.renewList);
		((Button)findViewById(R.id.renew_choose_all)).setOnClickListener(this);
		((Button)findViewById(R.id.renew_renew)).setOnClickListener(this);
		
		adapter=new AdapterRenew(this);
		
		renewList.setAdapter(adapter);
		renewList.setOnItemClickListener(this);
	//	Toast.makeText(this, "MylibRenew Builded", Toast.LENGTH_SHORT).show();
	}

	public void setData(String strData){
		adapter.setData(strData);
		//return true;
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
/*	private boolean changeContent(){
		
		
	}
	*/
	/*private String loadAsset(String url){
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Log.v("MylibRenew.onItemClick()","Start");
		adapter.click(arg2);
		//Toast.makeText(this, String.valueOf(arg2), Toast.LENGTH_SHORT).show();
	}
	public void renew(){
		adapter.renew();
	}
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.renew_choose_all:toggleAll();break;
		case R.id.renew_renew:renew();break;
		}
	}
	private void toggleAll(){
		adapter.toggleAll();
		
	}

}
