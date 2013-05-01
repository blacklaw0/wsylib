package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AdapterHistory extends BaseExpandableListAdapter{
	private LayoutInflater inflater;
	private JSONArray fathers=new JSONArray();
	private String sons[]={"son1"};
	public AdapterHistory(Activity caller){
		inflater=LayoutInflater.from(caller);
	}
	public boolean setData(String str){
		//Map<String,Object> obj=new HashMap();
	
			try {
				fathers=new JSONArray(str);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.notifyDataSetChanged();
		return true;
	}
	private int getReversedAddress(int address){
		return fathers.length()-address-1;
	}
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return sons[childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}
	class HistoryLayout{
		TextView bar;
		TextView callno;
	}
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		HistoryLayout historyLayout;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_history_son, null);
			historyLayout=new HistoryLayout();
			historyLayout.bar=(TextView)convertView.findViewById(R.id.history_bar);
			historyLayout.callno=(TextView)convertView.findViewById(R.id.history_callno);
			convertView.setTag(historyLayout);
		}else{
			historyLayout=(HistoryLayout)convertView.getTag();
		}
		// TODO Auto-generated method stub
		groupPosition=getReversedAddress(groupPosition);
		//View view=inflater.inflate(R.layout.list_history_son, null);
		TextView bar=historyLayout.bar;
		TextView callno=historyLayout.callno;
		//bar=(TextView)view.findViewById(R.id.history_bar);
		//callno=(TextView)view.findViewById(R.id.history_callno);
		String sBar="",sCallno="";
		try{
		JSONObject obj=fathers.getJSONObject(groupPosition);
		sBar="条码号: "+(String)obj.get("bar");
		sCallno="索取号: "+(String)obj.get("callno");
		}catch(Exception e){
			e.printStackTrace();
		}
		bar.setText(sBar);
		callno.setText(sCallno);
		return convertView;
	}
	public ArrayList<String> getPrintList(){
		ArrayList<String> list=new ArrayList<String>();
		
		String date="",title="",callno="",line="",order="";
		list.add("            时间               索取号                          书名");
		try{
		for(int i=0;i<fathers.length();i++){
			JSONObject obj=fathers.getJSONObject(i);
			if(!((String)obj.get("type")).equals("1")) continue;
			order=String.valueOf(list.size());
			date=(String)obj.get("date");
			callno=(String)obj.get("callno");
			line=order+".  "+date+"  "+callno+"   "+title;
			/*line=String.valueOf(list.size())+" .  ";
			line+=(String)obj.get("date");
			line+="  "+;
			line+="  "+(String)obj.get("title");*/
			list.add(line);
			Log.v("AdapterHistory.getPrintList().line",line);
		}}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return sons.length;
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		try {
			return fathers.get(groupPosition);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return fathers.length();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}
class GroupLayout{
	TextView title;
	TextView time;
	TextView action;
	
	
}
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stu
		GroupLayout groupLayout;
		if(convertView==null){
			convertView=inflater.inflate(R.layout.list_history, null);
			groupLayout=new GroupLayout();
			groupLayout.title=(TextView)convertView.findViewById(R.id.history_title);
			groupLayout.time=(TextView)convertView.findViewById(R.id.history_time);
			groupLayout.action=(TextView)convertView.findViewById(R.id.history_action);			
			convertView.setTag(groupLayout);
		}else{
			groupLayout=(GroupLayout)convertView.getTag();
		}
		JSONObject obj=new JSONObject();
		groupPosition=getReversedAddress(groupPosition);
		try {
			obj=fathers.getJSONObject(groupPosition);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.v("getGroupView_obj",obj.toString());
		TextView hTitle=groupLayout.title;
		TextView hTime=groupLayout.time;
		TextView hAction=groupLayout.action;
		String title="title",date="date",type="type";

		try {	//Log.v("getGroupView_obj","try_start");
			title=(String)obj.get("title");
			
		date=(String)obj.get("date");
		//Log.v("getGroupView_obj","try_end2");
		type=(String)obj.get("type");
//Log.v("getGroupView_obj","try_end3");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int receiveType=0;
		try{
		receiveType=Integer.parseInt(type);
		}catch(Exception e){
			e.printStackTrace();
		}
		switch(receiveType)
		{
		case 1:type=" 借出 ";break;
		case 2:type=" 还回 ";break;
		case 3:type=" 续借 ";break;
		}
		hTitle.setText(title);
		hTime.setText(date);
		hAction.setText(type);	
		setActionSign(hAction,receiveType);
		return convertView;
	}
	public boolean setActionSign(TextView text,int style){
		switch(style){
		case 1:text.setBackgroundResource(R.drawable.history_action_selector_jiecu);break;
		case 2:text.setBackgroundResource(R.drawable.history_action_selector_huanhui);break;
		case 3:text.setBackgroundResource(R.drawable.history_action_selector_xujie);break;
		
		}
		return true;
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
