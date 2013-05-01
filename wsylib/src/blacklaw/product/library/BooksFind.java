package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.net.URLEncoder;
import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class BooksFind extends Activity implements OnCheckedChangeListener{
	private boolean 		isLoading=false,isEnd=false;
	private Button 			findBut;
	private EditText 		text;
	private ExpandableListView 		lView;
	private int 			pageIndex,indexPageNum,indexSortType,indexSort,indexType;
	private LeftMenu 		menu;
	private AdapterFind 	adapter;
	private ProgressDialog 	progress;
	private RadioGroup 		group_pageNum,group_sortType,group_sort,group_type;
	private Toast 			lastToast;
	
	final private String strpageNum[]={"20","50","100"};
	final private String strSortType[]={"","pubyear","publish"};
	final private String strSort[]={"desc","asc"};
	final private String strType[]={"text","name","author","classno","isbn","callno","publish"};

	//private RemoteReader 	remote = new RemoteReader();
	
	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.books_find);

		lView=(ExpandableListView)findViewById(R.id.listView1);
		findBut=(Button)findViewById(R.id.button2);
		text=(EditText)findViewById(R.id.editText1);
		//Toast.makeText(this, String.valueOf(iNow), Toast.LENGTH_SHORT).show();
		group_pageNum=(RadioGroup)findViewById(R.id.group_pageNum);
		group_sortType=(RadioGroup)findViewById(R.id.group_sortType);
		group_sort=(RadioGroup)findViewById(R.id.group_sort);
		group_type=(RadioGroup)findViewById(R.id.group_type);
		lastToast=Toast.makeText(BooksFind.this,"Is End", Toast.LENGTH_SHORT);
		menu=new LeftMenu(this,R.id.leftMenu,50);
		adapter=new AdapterFind(this);
		lView.setAdapter(adapter);
		group_pageNum.setOnCheckedChangeListener(this);
		group_sortType.setOnCheckedChangeListener(this);
		group_sort.setOnCheckedChangeListener(this);
		group_type.setOnCheckedChangeListener(this);
		Intent intent=this.getIntent();
		int action=intent.getIntExtra("action",0);
		switch(action){
			//Log.v("BooksFind_action",action);
		case 2:text.setText(intent.getStringExtra("keywords"));break;
		default:text.requestFocus();
		}
		
		lView.setOnScrollListener(onFindListScroll);
		lView.setOnGroupClickListener(new ExOnGroupClickListener());
		findBut.setOnClickListener(onFindButClick);
		findViewById(R.id.button1).setOnClickListener(onSettingButClick);
		//Common.showInputMethod(text);
	}
	OnScrollListener onFindListScroll=new OnScrollListener(){
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			
			if(isEnd)
				lastToast.show();	
			else if(totalItemCount - firstVisibleItem - visibleItemCount < 1&&pageIndex>=1&&!isLoading)
				addNext();
		}

		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	OnClickListener onFindButClick = new OnClickListener(){
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(text.getText().length()==0){
				Toast.makeText(v.getContext(),Re.warning.no_find_input, Toast.LENGTH_SHORT).show();
				return;
			}
			Common.hiddenInputMethod(text);
			clearList();
			pageIndex = 0;
			isEnd=false;
			addNext();
		}
	};
	private void clearList(){
		adapter.clear();
	}
	OnClickListener onSettingButClick =new OnClickListener(){

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Common.hiddenInputMethod(text);
			menu.toggle();
			//addNext();
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_MENU){
			Common.hiddenInputMethod(text);
			menu.toggle();
		}
		return super.onKeyDown(keyCode, event);
	}
	private void addNext()
	{
		//Toast.makeText(this, String.valueOf(iNow), Toast.LENGTH_SHORT).show();
			isLoading=true;
			Context proContext=this.getParent();
			if(proContext==null)
				proContext=this;
			progress=ProgressDialog.show(proContext,Re.leading.find_loading_progress[0],Re.leading.find_loading_progress[1],true,true);
			pageIndex++;
			Thread thread=new Thread(runnable);
			thread.start();
			

	}
	Runnable runnable=new Runnable(){
		@SuppressWarnings("deprecation")
		public void run() {
			// TODO Auto-generated method stub
			RemoteReader remote=new RemoteReader();
			String receiveMsg="";
			Log.v("Run_up","blacklaw.wsylib");
	
				//receiveMsg=remote.loadData("http://townke.com/getLibs.php?action=find&keyValue="+URLEncoder.encode(text.getText().toString()) +"&pageNum=30&page="+String.valueOf(iNow));
				String url="";
				url=Re.url.BOOKS_FIND;
				url+="?action=findEx";
				url+="&keyValue="+URLEncoder.encode(text.getText().toString());
				url+="&page="+String.valueOf(pageIndex);
				url+="&pageNum="+strpageNum[indexPageNum];
				url+="&type="+strType[indexType];
				url+="&sortType="+strSortType[indexSortType];
				url+="&sort="+strSort[indexSort];
				//http://townke.com/app/wsylib/BOOKS_FIND.php?action=findEx&keyValue=~keyValue
				receiveMsg=remote.loadDataWithWarning(url);

			Log.v("runable_handler_obtain",String.valueOf(receiveMsg.length()));
			mHandler.obtainMessage(1,receiveMsg).sendToTarget();
		}
		
	};
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch(msg.what)
			{
			case 1:
				Log.v("MSG_UP","blacklaw.wsylib");
				String receive=(String)msg.obj;
				if(receive.equals(Re.string.remoteConnectTimeout))
					Toast.makeText(BooksFind.this, Re.warning.connect_timeout, Toast.LENGTH_SHORT).show();
					else
				isEnd=!codeJson(receive);
				break;		
			}
			progress.dismiss();
			isLoading=false;
			
			super.handleMessage(msg);
		}
		
		
	};
	private void addOneLine(String title,String author,String publisher,String date,String isbn,String callno,String classno,String pages,String price)
	{
		//Map<String,Object> map=new HashMap<String,Object>();
		//map.put("img",R.drawable.ic_launcher);
		BookInfo in=new BookInfo(title,author,publisher,date,isbn,callno,classno,pages,price);
		adapter.addBook(in);
		/*	in.author=author;
		in.title=title;
		in.publisher=publisher;
		in.date=date;
		in.isbn=isbn;
		in.callno=callNO;
		in.classno=classNO;
		in.pages=pages;
		in.price=price;*/
		/*map.put("author", author);
		map.put("title", title);
		map.put("publisher", publisher);
		map.put("date",date);
		map.put("isbn",isbn);
		map.put("callNO",callNO);
		map.put("classNO",classNO);
		map.put("pages",pages);
		map.put("price",price);*/
		
	}
	//Return false means nowPage is the last
	private boolean codeJson(String str){
		/*if(str.equals(Re.string.remoteConnectTimeout))
		{
			Common.globalToast(Re.warning.connect_timeout);
			return false;
		}*/
		JSONArray jA=null,ja=null;
			Log.v("BooksFind.codeJSON()","Start");
			try {
				jA=new JSONArray(str);
			
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				Log.v("BooksFind.codeJSON()","jA_error");
				e2.printStackTrace();
				Toast.makeText(this,Re.warning.no_net_server, Toast.LENGTH_SHORT).show();
				//Internet Connect Error;
				return false;
				
			}
			if(jA.length()==0)
			{
				Toast.makeText(this, Re.warning.no_this_keyword, Toast.LENGTH_SHORT).show();
				return false;
			}
		//Log.v("jA_Length","String.valueOf(jA.length())2");
		try{
			for(int i=0;i<jA.length();i++)
			{	
				ja=jA.getJSONArray(i);
				addOneLine(ja.getString(1),ja.getString(2),ja.getString(3),ja.getString(4),ja.getString(5),ja.getString(6),ja.getString(7),ja.getString(8),ja.getString(9));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			//Log.v("jA_Length","String.valueOf(jA.length())3");	
				//Toast.makeText(ItemActivity.this,strpageNum[indexPageNum] , Toast.LENGTH_SHORT).show();	
			if(jA.length()<Integer.parseInt(strpageNum[indexPageNum]))
			{
				//Log.v("json_num_error","list_last");
				return false;			
			}
			return true;
	}
	/*private ArrayList<Map<String,Object>> getData(){
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("img", R.drawable.ic_launcher);
		map.put("title", "tittle");
		map.put("content","conttent");
		list.add(map);
		 
	
		for(int i=0;i<=100;i++)
			{map=new HashMap<String,Object>();
		map.put("img", R.drawable.ic_launcher);
		map.put("title", "tittle"+Integer.toString(i));
		map.put("content","conttent");
			list.add(map);
			}
		return list;
		
	}*/
/*	class mSimpleAdapter extends BaseExpandableListAdapter{
		

		}
		public boolean clear() {
			// TODO Auto-generated method stub
			data.clear();
			this.notifyDataSetChanged();
			return true;
		}
		public boolean addObject(Map<String, Object> son) {
			// TODO Auto-generated method stub
			data.add(son);
			this.notifyDataSetChanged();
			return true;
		}
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=inflater.inflate(R.layout.list_find_son, null);
			TextView author=(TextView)view.findViewById(R.id.dauthor);
			TextView publisher=(TextView)view.findViewById(R.id.dpublisher);
			TextView date=(TextView)view.findViewById(R.id.ddate);
			TextView isbn=(TextView)view.findViewById(R.id.disbn);
			TextView callno=(TextView)view.findViewById(R.id.dcallno);
			TextView pages=(TextView)view.findViewById(R.id.dpages);
			TextView price=(TextView)view.findViewById(R.id.dprice);
			TextView classno=(TextView)view.findViewById(R.id.dclassno);	
			Button shareBut=(Button)view.findViewById(R.id.but_share);
			Button collectBut=(Button)view.findViewById(R.id.but_collect);	
					
			author.setText((String)data.get(groupPosition).get("author"));
			publisher.setText((String)data.get(groupPosition).get("publisher"));
			date.setText((String)data.get(groupPosition).get("date"));
			isbn.setText((String)data.get(groupPosition).get("isbn"));
			callno.setText((String)data.get(groupPosition).get("callNO"));
			classno.setText((String)data.get(groupPosition).get("classNO"));
			pages.setText((String)data.get(groupPosition).get("pages"));
			price.setText((String)data.get(groupPosition).get("price"));
				
			shareBut.setOnClickListener(new OnClickListener(){

				public void onClick(View arg0) {
					// TODO Auto-generated method stub

	                Intent intent=new Intent(Intent.ACTION_SEND);   
	                intent.setType("image/*");   
	                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");   
	                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app (分享自city丽人馆)");   
	                
	                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
	                startActivity(Intent.createChooser(intent, getTitle()));   
	                 
	    
				}
				
			});
			
			return view;
		}
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return 1;
		}
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return data.size();
		}
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view=inflater.inflate(R.layout.list_find, null);
			ImageView img=(ImageView)view.findViewById(R.id.img);
			TextView author=(TextView)view.findViewById(R.id.author);
			TextView title=(TextView)view.findViewById(R.id.title);
			img.setImageResource(R.drawable.ic_launcher);
			author.setText((String)data.get(groupPosition).get("author"));
			title.setText((String)data.get(groupPosition).get("title"));
			
			return view;
		}
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

		
		
	}
*/
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int pos=Integer.parseInt(findViewById(checkedId).getContentDescription().toString());
		switch(group.getId())
		{
		case R.id.group_pageNum:indexPageNum=pos;break;
		case R.id.group_sort:indexSort=pos;break;
		case R.id.group_sortType:indexSortType=pos;break;
		case R.id.group_type:indexType=pos;break;
		}
	}

}
