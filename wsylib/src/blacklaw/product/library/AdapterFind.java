package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

class AdapterFind extends AdapterBooksBase{

	public AdapterFind(Activity itemActivity) {
		super(itemActivity);
		// TODO Auto-generated constructor stub
	}
	public int getChildViewLayoutID(){
		return R.layout.list_find_son;
	}
	public boolean setButAction(View view,int groupPosition,BookInfo in){
		
		Button collect=(Button)view.findViewById(R.id.but_collect);
		collect.setOnClickListener(new OnCollectButClick(in));
		return super.setButAction(view, groupPosition, in);
	}
	class OnCollectButClick implements OnClickListener{
		int groupPosition;
		BookInfo in;
		public OnCollectButClick(BookInfo iIn){
			in=iIn;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("collectBut.onClick()","caller.getParent().toString()");
			//Map<String, Object> map=data.get(groupPosition);
			//String title=(String)map.get("title");
			if(addFavorite(in))
				toastFavoriteSuccess(in.title);
			else
				toastFavoriteFailure(in.title);
		}
	}
	public boolean addFavorite(BookInfo in){
		AdapterFavorite adapter=getFavoriteAdapter();

		if(!adapter.addBook(in))
			return false;
		//adapter.saveFavorite();
		Log.v("addFavorite()","add map");
		return true;
	}
	private AdapterFavorite getFavoriteAdapter(){
		return ((MyApp)caller.getApplication()).register.favoriteAdapter;
	}
	public void toastFavoriteSuccess(String title){
		Toast.makeText(caller,  Re.leading.bookmark_add_success.replace("~title~", title), Toast.LENGTH_SHORT).show();
	}
	public void toastFavoriteFailure(String title){
		Toast.makeText(caller, Re.warning.bookmark_readd.replace("~title~",title), Toast.LENGTH_SHORT).show();
	}
}
	//String shareStr="";
/*	LayoutInflater inflater;
	Activity caller;
	ArrayList<Map<String,Object>> data;
	int layout;
	String[] strs;
	int[] ints;
	public AdapterFind(BooksFind itemActivity) {
		// TODO Auto-generated constructor stub
		caller=itemActivity;
		inflater=LayoutInflater.from(caller);
		data=new ArrayList<Map<String,Object>>();

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
	class FindLayout{
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
		public boolean setDataAndAction(BooksInfo in){
			//Set Data
			author.setText("作者:    "+in.author);
			publisher.setText("出版社: "+in.publisher);
			date.setText("出版年: "+in.date);
			isbn.setText("ISBN:    "+in.isbn);
			callno.setText("索取号: "+in.callno);
			classno.setText("分类号: "+in.classno);
			pages.setText("页数:    "+in.pages);
			price.setText("价格:    "+in.price);
			//Set Action
			collectBut.setOnClickListener(new OnCollectButClick(in));
			shareBut.setOnClickListener(new OnShareButClick(createShareStr(in)));
			return true;
		}
	}

	public View getChildView(final int groupPosition, int childPosition,
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
	}

	private AdapterFavorite getFavoriteAdapter(){
		return ((MyApp)caller.getApplication()).register.favoriteAdapter;
	}
	class BooksInfo extends BookInfo{
		public BooksInfo(int ad) {
			super(ad);
			// TODO Auto-generated constructor stub
		}

		protected String getAttributeValue(int groupPosition,
				String attributeName) {
			// TODO Auto-generated method stub
			return (String)data.get(groupPosition).get(attributeName);
		}

		
	}
	private String createShareStr(BooksInfo i){
		//String value="";
		String shareStr="亲们,推荐一本学校图书馆的书,\n";
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
	class OnCollectButClick implements OnClickListener{
		int groupPosition;
		BooksInfo in;
		public OnCollectButClick(BooksInfo iIn){
			in=iIn;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			Log.v("collectBut.onClick()","caller.getParent().toString()");
			//Map<String, Object> map=data.get(groupPosition);
			//String title=(String)map.get("title");
			if(addFavorite(in))
				toastFavoriteSuccess(in.title);
			else
				toastFavoriteFailure(in.title);
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
	public void toastFavoriteSuccess(String title){
		Toast.makeText(caller,  Re.leading.bookmark_add_success.replace("~title~", title), Toast.LENGTH_SHORT).show();
	}
	public void toastFavoriteFailure(String title){
		Toast.makeText(caller, Re.warning.bookmark_readd.replace("~title~",title), Toast.LENGTH_SHORT).show();
	}
	public boolean addFavorite(BooksInfo in){
		AdapterFavorite adapter=getFavoriteAdapter();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("img",R.drawable.ic_launcher);
		map.put("author", in.author);
		map.put("title", in.title);
		map.put("publisher", in.publisher);
		map.put("date",in.date);
		map.put("isbn",in.isbn);
		map.put("callNO",in.callno);
		map.put("classNO",in.classno);
		map.put("pages",in.pages);
		map.put("price",in.price);

		if(!adapter.addObject(map))
			return false;
		adapter.saveFavorite();
		Log.v("addFavorite()","add map");
		return true;
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
	
	class GroupLayout{
		ImageView img;
		TextView author;
		TextView title;
		public boolean setData(BooksInfo in,boolean isExpanded){
			if(isExpanded)
				img.setImageResource(R.drawable.swap_up);
			else
				img.setImageResource(R.drawable.swap_down);
			author.setText(in.author);
			title.setText(in.title);
			return true;
		}
	}

	
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

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
		BooksInfo in=new BooksInfo(groupPosition);
		groupLayout.setData(in,isExpanded);
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



	
	
}*/