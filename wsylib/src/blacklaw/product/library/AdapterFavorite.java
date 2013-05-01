
package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


class AdapterFavorite extends AdapterBooksBase{

	public AdapterFavorite(Activity itemActivity) {
		super(itemActivity);
		// TODO Auto-generated constructor stub
	}
	private boolean saveFavorite(){
		return Common.writeRawStr(caller, Re.name.favorite_cache, getJSONData());
		
	}
	private String getJSONData(){
		String jObj="";
		JSONArray array=new JSONArray();
		for(int i=0;i<data.size();i++){
			
			
			JSONObject obj=new JSONObject(books2map(data.get(i)));
			array.put(obj);
		}
		jObj=array.toString();
		return jObj;
	}

/*		in.title=(String)map.get("title");
		in.author=(String)map.get("author");
		in.publisher=(String)map.get("publisher");
		in.date=(String)map.get("date");
		in.isbn=(String)map.get("isbn");
		in.pages=(String)map.get("pages");
		in.publisher=(String)map.get("publisher");
		in.publisher=(String)map.get("publisher");
		in.publisher=(String)map.get("publisher");
*/		
	public void initDataFromCache(){
		setJSONData(Common.readRawStr(caller,Re.name.favorite_cache));
	}
	@SuppressWarnings("unchecked")
	public boolean setJSONData(String str){
		//Log.v("setJSONData",str);

		try{
		JSONArray array=new JSONArray(str);
		JSONObject jObj=null;
		
		String key="";
		Object obj=null;
		
		data.clear();
		for(int i=0;i<array.length();i++){
			jObj=array.getJSONObject(i);
			Iterator<String> keys=jObj.keys();
			Map<String,Object> map=new HashMap<String,Object>();
			while(keys.hasNext()){
				key=keys.next();
				obj=jObj.get(key);
				
				map.put(key, obj);
				
			}
			data.add(map2books(map));
			
		}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		this.notifyDataSetChanged();
		return true;
	}
	public int getChildViewLayoutID(){
		return R.layout.list_favorite_son;
	}
	public boolean addBook(BookInfo son) {
		// TODO Auto-generated method stub
		for(Iterator<BookInfo> iter=data.iterator();iter.hasNext();){
			//if(((String)iter.next().get("callNO")).equals(son.get("callNO")))
			//String next=;
			if(iter.next().title.equals(son.title))
			{//Log.v("AdapterFavorite.addObject():son.title,next.title",son.title+","+next);
			return false;
			}
		}
		data.add(0,son);
		this.notifyDataSetChanged();
		saveFavorite();
		return true;
	}
/*	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent){
		View view=super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
		Button uncollect=(Button)view.findViewById(R.id.but_collect);
		uncollect.setOnClickListener(new OnRemoveCollectButClick(groupPosition));
		return view;
	}*/
	public boolean setButAction(View view,int groupPosition,BookInfo in){
		
		Button uncollect=(Button)view.findViewById(R.id.but_collect);
		uncollect.setOnClickListener(new OnRemoveCollectButClick(groupPosition));
		return super.setButAction(view, groupPosition, in);
	}
	class OnRemoveCollectButClick implements OnClickListener{
		int dataAddress;
		public OnRemoveCollectButClick(int iGroupPosition){
			dataAddress=iGroupPosition;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			removeItem(dataAddress);

			saveFavorite();
		}
	};
	private void removeItem(int id){
		((BooksFavorite)caller).collapseAll();
		data.remove(id);
		notifyDataSetChanged();
	}
	public Map<String,Object> books2map(BookInfo in){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", in.title);
		map.put("author", in.author);
		map.put("publisher", in.publisher);
		map.put("date", in.date);
		map.put("isbn", in.isbn);
		map.put("pages", in.pages);
		map.put("price", in.price);
		map.put("callno", in.callno);
		map.put("classno", in.classno);
		return map;
	}
	public BookInfo map2books(Map<String,Object> map){
		BookInfo in=new BookInfo(
				(String)map.get("title"),
				(String)map.get("author"),
				(String)map.get("publisher"),
				(String)map.get("date"),
				(String)map.get("isbn"),
				(String)map.get("callno"), 
				(String)map.get("classno"),
				(String)map.get("pages"), 
				(String)map.get("price")
				);
		
		return in;
	}
/*	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FindLayout findLayout=null;
		if(convertView==null){
			findLayout=new FindLayout();
			convertView=inflater.inflate(R.layout.list_find_son, null);
			//view=findLayout.view;
			findLayout.author=(TextView)convertView.findViewById(R.id.dauthor);
			findLayout.publisher=(TextView)convertView.findViewById(R.id.dpublisher);
			findLayout.date=(TextView)convertView.findViewById(R.id.ddate);
			findLayout.isbn=(TextView)convertView.findViewById(R.id.disbn);
			findLayout.callno=(TextView)convertView.findViewById(R.id.dcallno);
			findLayout.pages=(TextView)convertView.findViewById(R.id.dpages);
			findLayout.price=(TextView)convertView.findViewById(R.id.dprice);
			findLayout.classno=(TextView)convertView.findViewById(R.id.dclassno);	
			findLayout.shareBut=(Button)convertView.findViewById(R.id.but_share);
			findLayout.collectBut=(Button)convertView.findViewById(R.id.but_collect);
			convertView.setTag(findLayout);
		}else{
			findLayout=(FindLayout)convertView.getTag();
		}
		BooksInfo in=new BooksInfo(groupPosition);
		findLayout.setDataAndAction(in);
		return convertView;
	}*/
}
/*	String shareStr;
	LayoutInflater inflater;
	Activity caller;
	ArrayList<Map<String,Object>> data;
	int layout;
	String[] strs;
	int[] ints;
	public AdapterFavorite(Activity itemActivity) {
		// TODO Auto-generated constructor stub
		caller=itemActivity;
		inflater=LayoutInflater.from(caller);
		//if(iData==null)
			data=new ArrayList<Map<String,Object>>();

	}
	public boolean clearFavorite(){
		
		return Common.writeRawStr(caller, Re.name.favorite_cache,"");
	}
	public boolean saveFavorite(){
		return Common.writeRawStr(caller, Re.name.favorite_cache, getJSONData());
		
	}
	private String getJSONData(){
		String jObj="";
		JSONArray array=new JSONArray();
		for(int i=0;i<data.size();i++){
			JSONObject obj=new JSONObject(data.get(i));
			array.put(obj);
		}
		jObj=array.toString();
		return jObj;
	}
	public boolean setJSONData(String str){
		//Log.v("setJSONData",str);

		try{
		JSONArray array=new JSONArray(str);
		JSONObject jObj=null;
		
		String key="";
		Object obj=null;
		
		data.clear();
		for(int i=0;i<array.length();i++){
			jObj=array.getJSONObject(i);
			Iterator<String> keys=jObj.keys();
			Map<String,Object> map=new HashMap<String,Object>();
			while(keys.hasNext()){
				key=keys.next();
				obj=jObj.get(key);
				
				map.put(key, obj);
				
			}
			data.add(map);
			
		}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		this.notifyDataSetChanged();
		return true;
	}
	public boolean clear() {
		// TODO Auto-generated method stub
		data.clear();
		this.notifyDataSetChanged();
		return true;
	}
	
	public boolean addObject(Map<String, Object> son) {
		// TODO Auto-generated method stub
		for(Iterator<Map<String, Object>> iter=data.iterator();iter.hasNext();){
			if(((String)iter.next().get("callNO")).equals(son.get("callNO")))
			return false;
		}
		data.add(son);
		this.notifyDataSetChanged();
		return true;
	}
	public int getDataAddress(int choose){
		return data.size()-choose-1;
	}
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	class FavoriteLayout{
		TextView author;
		TextView publisher;
		TextView date;
		TextView isbn;
		TextView callno;
		TextView pages;
		TextView price;
		TextView classno;
		Button shareBut;
		Button collectBut;
		
	}
	class BooksInfo{
		String author;
		String publisher;
		String date;
		String isbn;
		String callno;
		String classno;
		String pages;
		String price;
		String title;
		public BooksInfo(int ad){
			title=getAttributeValue(ad,"title");
			author=getAttributeValue(ad,"author");
			publisher=getAttributeValue(ad,"publisher");//(String)data.get(groupPosition).get("publisher");
			date=getAttributeValue(ad,"date");//(String)data.get(groupPosition).get("date");
			isbn=getAttributeValue(ad,"isbn");//+(String)data.get(groupPosition).get("isbn"));
			callno=getAttributeValue(ad,"callNO");
			classno=getAttributeValue(ad,"classNO");
			pages=getAttributeValue(ad,"pages");
			price=getAttributeValue(ad,"price");
		}
		private String getAttributeValue(int groupPosition,String attributeName){
			return (String)data.get(groupPosition).get(attributeName);
		}
		
	}
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		shareStr="";
		FavoriteLayout favoriteLayout;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_favorite_son, null);
			favoriteLayout=new FavoriteLayout();
			favoriteLayout.author=(TextView)convertView.findViewById(R.id.dauthor);
			favoriteLayout.publisher=(TextView)convertView.findViewById(R.id.dpublisher);
			favoriteLayout.date=(TextView)convertView.findViewById(R.id.ddate);
			favoriteLayout.isbn=(TextView)convertView.findViewById(R.id.disbn);
			favoriteLayout.callno=(TextView)convertView.findViewById(R.id.dcallno);
			favoriteLayout.pages=(TextView)convertView.findViewById(R.id.dpages);
			favoriteLayout.price=(TextView)convertView.findViewById(R.id.dprice);
			favoriteLayout.classno=(TextView)convertView.findViewById(R.id.dclassno);	
			favoriteLayout.shareBut=(Button)convertView.findViewById(R.id.but_share);
			favoriteLayout.collectBut=(Button)convertView.findViewById(R.id.but_collect);
			convertView.setTag(favoriteLayout);
		}else{
			favoriteLayout=(FavoriteLayout)convertView.getTag();
		}
		TextView author=favoriteLayout.author;
		TextView publisher=favoriteLayout.publisher;
		TextView date=favoriteLayout.date;
		TextView isbn=favoriteLayout.isbn;
		TextView callno=favoriteLayout.callno;
		TextView pages=favoriteLayout.pages;
		TextView price=favoriteLayout.price;
		TextView classno=favoriteLayout.classno;	
		Button shareBut=favoriteLayout.shareBut;
		Button collectBut=favoriteLayout.collectBut;	
		int dataAddress=getDataAddress(groupPosition);
		BooksInfo in=new BooksInfo(dataAddress);
		
		
		
		author.setText("浣滆€?    "+in.author);
		publisher.setText("鍑虹増绀? "+in.publisher);
		date.setText("鍑虹増骞? "+in.date);
		isbn.setText("ISBN:    "+in.isbn);
		callno.setText("绱㈠彇鍙? "+in.callno);
		classno.setText("鍒嗙被鍙? "+in.classno);
		pages.setText("椤垫暟:    "+in.pages);
		price.setText("浠锋牸:    "+in.price);
		
		collectBut.setOnClickListener(new OnRemoveCollectButClick(dataAddress));
		shareBut.setOnClickListener(new OnShareButClick(createShareStr(in)));
		return convertView;
	}

	private String createShareStr(BooksInfo i){
		//String value="";
		String shareStr="浜蹭滑,鎺ㄨ崘涓€鏈鏍″浘涔﹂鐨勪功,\n";
		shareStr+="<<"+i.title+">>"+"\r\n";
 		shareStr+=i.author+"\r\n";
		shareStr+=i.publisher+"\r\n";
		shareStr+=i.date+"\r\n";
		shareStr+=i.isbn+"\r\n";
		shareStr+=i.callno+"\r\n";
		shareStr+=i.classno+"\r\n";
		shareStr+=i.pages+"\r\n";
		shareStr+=i.price+"\r\n";
		return shareStr;
	}

	class OnRemoveCollectButClick implements OnClickListener{
		int dataAddress;
		public OnRemoveCollectButClick(int iGroupPosition){
			dataAddress=iGroupPosition;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			((BooksFavorite)caller).collapseAll();
			data.remove(dataAddress);
			notifyDataSetChanged();
			saveFavorite();
		}
	};
	 class OnShareButClick implements OnClickListener{
		 String shareStr;
		 public OnShareButClick(String iShareStr){
			 shareStr=iShareStr;
		 }
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.v("AdapterFind.OnShareButClick.shareStr",shareStr);
			Common.shareWords(caller, shareStr);
		}
		
	};
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
	class GroupLayout{
		ImageView img;
		TextView author;
		TextView title;
	}
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupLayout groupLayout;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_favorite, null);
			groupLayout=new GroupLayout();
			groupLayout.img=(ImageView)convertView.findViewById(R.id.img);
			groupLayout.author=(TextView)convertView.findViewById(R.id.author);
			groupLayout.title=(TextView)convertView.findViewById(R.id.title);
			convertView.setTag(groupLayout);
		}else{
			groupLayout=(GroupLayout)convertView.getTag();
		}
		ImageView img=groupLayout.img;
		TextView author=groupLayout.author;
		TextView title=groupLayout.title;
		if(isExpanded)
			img.setImageResource(R.drawable.swap_up);
		else
			img.setImageResource(R.drawable.swap_down);
		author.setText((String)data.get(getDataAddress(groupPosition)).get("callNO"));
		title.setText((String)data.get(getDataAddress(groupPosition)).get("title"));
		
		return convertView;
	}
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

/*		public int getCount() {
		// TODO Auto-generated method stub
		
		return data.size();
	}
	public boolean clear(){
		data.clear();
		this.notifyDataSetChanged();
		return true;
	}

	public boolean addObject(Map<String,Object> son){
		data.add(son);
		this.notifyDataSetChanged();
		return true;
	}
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(layout,null);
		ImageView img=(ImageView)view.findViewById(R.id.img);
		Button but=(Button)view.findViewById(R.id.button1);
		TextView text1=(TextView)view.findViewById(R.id.title);
		TextView text2=(TextView)view.findViewById(R.id.info);
		img.setImageResource((Integer)data.get(position).get("img"));
		text1.setText((String)data.get(position).get("title"));
		text2.setText((String)data.get(position).get("content"));
		but.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(caller, (String)data.get(position).get("title"), Toast.LENGTH_LONG).show();
			}
		});
		return view;
	}

	
	
}*/