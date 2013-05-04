package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
/*
 * ExOnGroupListener 类,主要实现全部折叠和打开功能,单独列出
 */
public class ExOnGroupClickListener implements OnGroupClickListener{
	int sign=-1;
	private ExpandableListView parent;
	public boolean onGroupClick(ExpandableListView iParent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		parent=iParent;
		if(sign==-1){
			parent.expandGroup(groupPosition);
             sign= groupPosition;
		}else if(sign==groupPosition){
				parent.collapseGroup(groupPosition);
				sign=-1;	
		}else{
			
			parent.collapseGroup(sign);
			parent.expandGroup(groupPosition);
			sign=groupPosition;
			}
		return true;

	}
	public boolean collapseAll(){
		parent.collapseGroup(sign);		
		sign=-1;

		return true;
	}
}
