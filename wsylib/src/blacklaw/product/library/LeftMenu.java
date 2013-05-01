package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
/*
 * LeftMenu 类 左侧栏,用在Books Find 中,以后可能经常用,单独列出来
 */
public class LeftMenu {
	private ScrollView menu;
	private Activity caller;
	private boolean state;
	private int menuWidth;
	private WindowManager wm=null;
	private int BlankWidth;
	public LeftMenu(Activity iCaller, int menuID,int iBlankWidth){
		//@add 增加对menuID的判断,是否是ScrollView控件
		menuWidth=iBlankWidth;
		caller=iCaller;
		menu=(ScrollView)iCaller.findViewById(menuID);
		wm=(WindowManager)caller.getSystemService(Context.WINDOW_SERVICE);
		BlankWidth=iBlankWidth;
		state=false;
		hidden();
	}
	public boolean getState()
	{
		return state;
	}
	public void toggle()
	{
		if(state==true) 
			hidden();
		else
			show();
	}
	@SuppressWarnings("deprecation")
	public void show()
	{
		menu.setVisibility(1);
		//初始化  
		/*Animation scaleAnimation = new ScaleAnimation(0.0f, 0.8f,1.0f,1.0f);  
		//设置动画时间  
		scaleAnimation.setDuration(500);  
		menu.startAnimation(scaleAnimation);*/
		menuWidth=wm.getDefaultDisplay().getWidth()-BlankWidth;
		setWidth(menuWidth);
		state=true;
	}
	public void hidden()
	{
		menu.setVisibility(0);
		setWidth(0);
		state=false;
	}
	private void setWidth(int width)
	{
		ViewGroup.LayoutParams lParams=menu.getLayoutParams();
		lParams.width=width;
		menu.setLayoutParams(lParams);
	}
	
}
