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
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BooksTools extends Activity implements OnClickListener{
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "this is BooksFavorite", Toast.LENGTH_SHORT).show();
		/*Activity lParent=getParent();
		if(lParent!=null)
			lParent.onKeyDown(keyCode, event);*/
		return false;//super.onKeyDown(keyCode, event);
	}
	ListView toolsList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.books_tools);
		toolsList=(ListView)findViewById(R.id.toolsList);
		toolsList.setOnItemClickListener(onToolsListClick);
		((Button)findViewById(R.id.butLibWhen)).setOnClickListener(this);
		((Button)findViewById(R.id.butLibWhere)).setOnClickListener(this);
		String index=loadRawIndex();
		
		refushUI(index);
		getUpdataIndex();
	}
	OnItemClickListener onToolsListClick=new OnItemClickListener(){

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			//Toast.makeText(BooksTools.this, pageName[arg2], Toast.LENGTH_SHORT).show();
			openTool(pageName[arg2]);
		}
		
		
	};
	private boolean openTool(String toolName){
		Intent intent=new Intent(BooksTools.this,WebTools.class);
		Bundle mBundle=new Bundle();
		mBundle.putSerializable("toolName",toolName );//Re.url.lib_apartment_local);
		intent.putExtras(mBundle);
		this.startActivity(intent);
		return true;
	}
	String pageName[]={""};
	private boolean refushUI(String index){
		Log.v("BooksTools.refushUI.index",index);
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		try{
			if(index.length()==0) return false;
			JSONArray array=new JSONArray(index);
			pageName=new String[array.length()];
			for(int i=0;i<array.length();i++){
				JSONObject obj=array.getJSONObject(i);
				pageName[i]=obj.get("id")+"#"+obj.get("version");
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("title", (String)obj.get("name"));
				data.add(map);
				Log.v("BooksTools.refushUI.obj.name",(String)obj.get("name"));
			}
			toolsList.setAdapter(new SimpleAdapter(this,data, R.layout.list_tools, new String[]{"title"}, new int[]{R.id.textHot}));
			//(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data));
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	private String loadRawIndex(){

		String value=Common.getShare(this, Re.name.share_file,Re.name.tool_index_share_block);
		if(value==null || value.length()==0){
			value=readDefaultIndex();
			
		}
		return value; 
	}
	private String readDefaultIndex(){
		String value=Common.readAssetStr(this, "defaultToolsIndex");
		for(int i=1;i<=Re.integer.default_tools_count;i++){
			String toolName="defaultTool"+String.valueOf(i)+"#0";
			String tool=Common.readAssetStr(this, toolName);
			Common.writeRawStr(this,toolName, tool);
		}
		
	
	return value; 
		
	}
	private boolean saveRawIndex(String index){
		if(index.length()!=0)
		return Common.putShare(this, Re.name.share_file,Re.name.tool_index_share_block,index);
		else return false;
		//return true;
	}
	/*private boolean saveToolsData(){
		
		return true;
	}*/
	PageHandler pageHandler=null;
	private boolean getToolsPage(String index) {
		if(index.length()==0)
			return false;
		if(index.equals(loadRawIndex()))
			return false;
		//jsonData=getUpdataData();
		try{
			JSONArray array=new JSONArray(index);
			JSONObject obj=null;
			String filename="";
			/*Log.v("BooksTools.getToolsPage().array.length",String.valueOf(array.length()));
			Log.v("BooksTools.getToolsPage().sum",String.valueOf(sum(array.length())));
			*/
			//增加更新标识del,"true"不更新
			for(int i=0;i<array.length();i++){
				obj=array.getJSONObject(i);

				//new JSONArray(collection);
				filename=obj.get("id")+"#"+obj.get("version");
				if(Common.isRawFileExist(filename)){
					obj.put("del", "true");
				
					
				}else{
					obj.put("del", "false");
					
				}
					array.put(i,obj);
			}
			pageHandler=new PageHandler(sum(array.length()-1),index);
			filenames=new String[array.length()];
			for(int i=0;i<array.length();i++){
				obj=array.getJSONObject(i);
				//filename=obj.get("id")+"#"+obj.get("version");
				//if(Common.isRawFileExist(filename))
					
				filenames[i]=obj.get("id")+"#"+obj.get("version");
				//Log.v("BooksTools.getToolsPage().filenames",filenames[i]);
				JSONArray urlArray=obj.getJSONArray("url");
				//Log.v("BooksTools.getToolsPage().urls",obj.get("url").toString());
				//Log.v("BooksTools.getToolsPage().urlArrayLength",String.valueOf(urlArray.length()));
				if(urlArray.length()>=1)
				{
					String url=(String)urlArray.get(0);
					Log.v("BooksTools.getToolsPage().url",url);
					if(obj.get("del")=="true") url="";
					new Thread(new Common.RemoteReaderRunnable(url,pageHandler,i)).start();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		//Log.v("BooksTools.updataTools().data",data);
		Common.globalToast(Re.leading.tools_refush_success);
		return true;
	}
	private int sum(int last){
		int sum=0;
		
		for(int i=0;i<=last;i++){
			sum+=i;
		}
		return sum;
	}
	String filenames[]={""};
	//List<String> filenames=new ArrayList<String>()
	class PageHandler extends Handler{
		public int sum=0;
		public String index="";
		@SuppressLint("HandlerLeak")
		public PageHandler(int lSum,String lIndex){
			sum=lSum;
			index=lIndex;
		}
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			saveToolPage((String)msg.obj,filenames[msg.what]);
			sum-=msg.what;
			Log.v("BooksTools.handleMessage().sum",String.valueOf(sum));
			if(sum==0){
				updataTools(index);
			}
			super.handleMessage(msg);
		}
		
	};
	private boolean updataTools(String index){
		Log.v("BooksTools.updatTools()","start");
		saveRawIndex(index);
		refushUI(index);
		return true;
	}
	private boolean saveToolPage(String pageData,String filename){
		if(pageData.length()==0) return false;
		return Common.writeRawStr(this, filename, pageData);
		//return true;
	}
	
	private boolean getUpdataIndex(){
		(new Thread(new Common.RemoteReaderRunnable(Re.url.TOOLS_UPDATA,mHandler,1))).start();
		return true;
	}

	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch(msg.what){
			case 1:getToolsPage((String)msg.obj);break;
			
			}
			super.handleMessage(msg);
		}
		
		
	};
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.butLibWhen:onClickWhen();break;
		case R.id.butLibWhere:onClickWhere();break;
		
		}
	}
	public void onClickWhen(){
		Intent intent=new Intent(BooksTools.this,WebTools.class);
		Bundle mBundle=new Bundle();
		mBundle.putSerializable("url",Re.url.lib_open_time );
		intent.putExtras(mBundle);
		this.startActivity(intent);
		
	}
	public void onClickWhere(){
		Intent intent=new Intent(BooksTools.this,WebTools.class);
		Bundle mBundle=new Bundle();
		mBundle.putSerializable("url", Re.url.lib_apartment_local);
		intent.putExtras(mBundle);
		this.startActivity(intent);
		
	}


}
