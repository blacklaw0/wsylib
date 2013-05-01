package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class BooksHot extends Activity{
	private ListView list;
	private SimpleAdapter adapter;
	//private listData 
	List<Map<String,Object>> listData=new ArrayList<Map<String,Object>>();
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "this is BooksFavorite", Toast.LENGTH_SHORT).show();
		/*Activity lParent=getParent();
		if(lParent!=null)
			lParent.onKeyDown(keyCode, event);*/
		return false;//super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.books_hot);
		list=(ListView)findViewById(R.id.listHot);
		//listData=getData();
		
		setListData(Common.readRawStr(this, "blacklaw_wsylib_bookshot"));

		list.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BooksHot.this,BooksFind.class);
				intent.putExtra("keywords",(String)(listData.get(arg2).get("title")));
				intent.putExtra("action", 2);
				//intent.putExtra("action", "search keywords");
				startActivity(intent);
				Log.v("BooksHot_onItemSelected()","Start");
			}
			
			
		});
		Thread thread=new Thread(new mRunnable());
		thread.start();
	}
	private boolean setListData(String str){  //List<Map<String,Object>> data){
		//hots=str;
	
		listData=getListData(str);
		adapter=new SimpleAdapter(this,listData, R.layout.list_hots, new String[]{"title"}, new int[]{R.id.textHot});
		list.setAdapter(adapter);
		return true;
	}
	class mRunnable implements Runnable{

		public void run() {
			// TODO Auto-generated method stub
			RemoteReader reader;
			reader=new RemoteReader();
			String str=reader.loadData(Re.url.HOT_BOOKS);
			mHandler.obtainMessage(2,str).sendToTarget();
			
		}
		
	}
	private List<Map<String,Object>> getListData(String str) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		JSONArray array=new JSONArray();
		try {
			array=new JSONArray(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(array.length()==0){
			return listData;
		}
		Common.writeRawStr(this, "blacklaw_wsylib_bookshot", str);
		for(int i=0;i<array.length();i++){
		Map<String,Object> map=new HashMap<String,Object>();
		String value="";
		try {
			value=array.getString(i);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("title", value);
		list.add(map);
		}
		//list.add("asdf");

		return list;
	}

	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 2:
				String data=(String)msg.obj;
				if(data.length()!=0 && data!=null){
					setListData(data);
					Common.globalToast(Re.leading.hots_refush_success);
				}
				break;
			}
		}
		
		
	};
/*	private void setData(String str){
		hots=str;
		Log.v("BooksHot.setData().hots",hots);
		//listData.clear();
		//listData=getData();
		listData=getData();
		setListData(listData);
		//adapter.notifyDataSetChanged();
	}*/
}
