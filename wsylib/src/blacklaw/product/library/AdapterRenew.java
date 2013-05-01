package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


class AdapterRenew extends BaseAdapter{
	private JSONArray data=new JSONArray();
	private LayoutInflater inflater;
	boolean checked[]=null;
	//boolean chooseAll;
	//private String str[]={"a","b","c","d","e"};
	public AdapterRenew(Activity caller){
		inflater=LayoutInflater.from(caller);
	}
	public void setData(String str){
		
			try {
				data=new JSONArray(str);
				checked=new boolean[data.length()];
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.notifyDataSetChanged();
			//Log.v("RenewAdapter.data",data.toString());
	}
	public boolean toggleAll(){
		boolean chooseAll=true;
		for(int i=0;i<checked.length;i++){
			chooseAll=chooseAll&&checked[i];
			if(!chooseAll) break;
		}
		if(chooseAll)
			for(int i=0;i<checked.length;i++){
				checked[i]=false;
			}
		else
			for(int i=0;i<checked.length;i++){
				checked[i]=true;
			}
		this.notifyDataSetChanged();
		//chooseAll=!chooseAll;
		return chooseAll;
	}
	public void click(int position){
		checked[position]=!checked[position];
		this.notifyDataSetChanged();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return data.length();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		Object obj=null;
		try {
			obj= data.get(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class MylibLayout{
		View view;
		CheckBox check;
		TextView title;
		TextView back;
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		//if(mylibLayouts==null){
		/*	Log.v("MylibLayout Create", "Start");
			mylibLayouts=new MylibLayouts();
			//mylibLayout.view =inflater.inflate(R.layout.list_renew, null);
			//view=mylibLayout.view;
			mylibLayouts.check=(CheckBox)view.findViewById(R.id.renew_check);
			mylibLayouts.title=(TextView)view.findViewById(R.id.renew_title);
			mylibLayouts.back=(TextView)view.findViewById(R.id.renew_back);*/
		//}
			
		//Log.v("getView", "Start");
		//view=mylibLayout.view;
		MylibLayout mylibLayout=null;
		String sTitle="title",sBack="back_date",sBorrow="borrow_date";
		if(convertView==null){
		mylibLayout=new MylibLayout();
		convertView=inflater.inflate(R.layout.list_renew, null);
		mylibLayout.check=(CheckBox)convertView.findViewById(R.id.renew_check);
		mylibLayout.title=(TextView)convertView.findViewById(R.id.renew_title);
		mylibLayout.back=(TextView)convertView.findViewById(R.id.renew_back);
		convertView.setTag(mylibLayout);
		}else{
			mylibLayout=(MylibLayout)convertView.getTag();
			
		}
		//Log.v("getView_view",view.toString());
		/*
		view =inflater.inflate(R.layout.list_renew, null);*/
		CheckBox check=mylibLayout.check;
		TextView title=mylibLayout.title;
		TextView back=mylibLayout.back;
		
		
		//Log.v("MyLibRenew.Adapter.getView().position",String.valueOf(position));
		//Log.v("MyLibRenew.Adapter.getView().checked.length",String.valueOf(checked.length));
		check.setChecked(checked[position]);
		JSONObject obj=null;
		try {
			obj=data.getJSONObject(position);
			sTitle=(String)obj.get("title");
			sBack=(String)obj.get("back");
			sBorrow=(String)obj.get("borrow");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		title.setText(sTitle);
		back.setText(sBorrow+" - "+sBack);
		return convertView;
	}
	public boolean renew(){
		
		return true;
	}
}


