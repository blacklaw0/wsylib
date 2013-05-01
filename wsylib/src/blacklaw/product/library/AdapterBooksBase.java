package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class AdapterBooksBase extends BaseExpandableListAdapter{
	//String shareStr="";
	LayoutInflater inflater;
	Activity caller;
	ArrayList<BookInfo> data;
	int layout;
	String[] strs;
	int[] ints;
	public AdapterBooksBase(Activity itemActivity) {
		// TODO Auto-generated constructor stub
		caller=itemActivity;
		inflater=LayoutInflater.from(caller);
		data=new ArrayList<BookInfo>();

	}
	public boolean clear() {
		// TODO Auto-generated method stub
		data.clear();
		this.notifyDataSetChanged();
		return true;
	}
	public boolean addBook(BookInfo son) {
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

		public boolean setData(BookInfo in){
			//Set Data
			author.setText("作者:    "+in.author);
			publisher.setText("出版社: "+in.publisher);
			date.setText("出版年: "+in.date);
			isbn.setText("ISBN:    "+in.isbn);
			callno.setText("索取号: "+in.callno);
			classno.setText("分类号: "+in.classno);
			pages.setText("页数:    "+in.pages);
			price.setText("价格:    "+in.price);
			return true;
		}
	}
	public boolean setButAction(View view,int groupPosition,BookInfo in){
		Button shareBut=(Button)view.findViewById(R.id.but_share);
		shareBut.setOnClickListener(new OnShareButClick(createShareStr(in)));
		return true;
	}
	public int getChildViewLayoutID(){
		return R.layout.list_find_son;
	}
	public View getChildView(final int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FindLayout findLayout=null;
		if(convertView==null){
			findLayout=new FindLayout();
			convertView=inflater.inflate(getChildViewLayoutID(), null);

			findLayout.author=(TextView)convertView.findViewById(R.id.dauthor);
			findLayout.publisher=(TextView)convertView.findViewById(R.id.dpublisher);
			findLayout.date=(TextView)convertView.findViewById(R.id.ddate);
			findLayout.isbn=(TextView)convertView.findViewById(R.id.disbn);
			findLayout.callno=(TextView)convertView.findViewById(R.id.dcallno);
			findLayout.pages=(TextView)convertView.findViewById(R.id.dpages);
			findLayout.price=(TextView)convertView.findViewById(R.id.dprice);
			findLayout.classno=(TextView)convertView.findViewById(R.id.dclassno);	

			convertView.setTag(findLayout);
		}else{
			findLayout=(FindLayout)convertView.getTag();
		}
		BookInfo in=data.get(groupPosition);
		findLayout.setData(in);
		setButAction(convertView,groupPosition,in);
		return convertView;
	}



	
	private String createShareStr(BookInfo i){
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
;
	 class OnShareButClick implements OnClickListener{
		 String shareStr;
		 public OnShareButClick(String iShareStr){
			 shareStr=iShareStr;
		 }
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Log.v("AdapterFind.OnShareButClick.shareStr",shareStr);
			share(shareStr);
		}
		
	};
	private void share(String shareStr){
		Common.shareWords(caller, shareStr);
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
		public boolean setData(BookInfo in,boolean isExpanded){
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
		BookInfo in=data.get(groupPosition);
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



	
	
}