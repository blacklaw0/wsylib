

package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class Main extends TabActivity implements OnCheckedChangeListener{
	private TabHost tabHost;
	private RadioGroup rGroup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.loading);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Toast.makeText(this, this.getCacheDir().toString(), Toast.LENGTH_LONG).show();
        Common.setGlobalActivity(this);
        setContentView(R.layout.main);
       // toLoadingPage();
        netCheck();
        bindUI();
        debugTest();
        traverseAllSon();
        
  
    }
    private void toLoadingPage(){
		Intent intent=new Intent(Main.this,Loading.class);
		//Bundle mBundle=new Bundle();
		//mBundle.putSerializable("url",Re.url.lib_open_time );
		//intent.putExtras(mBundle);
		this.startActivity(intent);
    }
    private void netCheck(){
    	
    	if(!Common.isConnectingToInternet(this)){
    		Toast.makeText(this, Re.warning.no_internet, Toast.LENGTH_LONG).show();
    	}
    }
    private void traverseAllSon(){
    	for(int i=0;i<4;i++)
    		tabHost.setCurrentTab(i);
    	tabHost.setCurrentTab(0);
    	
    	
    }
    private void bindUI(){
        rGroup=(RadioGroup)findViewById(R.id.radioGroup1);
        rGroup.setOnCheckedChangeListener(this);
        tabHost=this.getTabHost();
        Intent intent=new Intent(this,MainBooks.class);
        tabHost.addTab(tabHost.newTabSpec("Item01").setIndicator("label").setContent(intent));
        Intent intent2=new Intent(this,MainMylib.class);        
        tabHost.addTab(tabHost.newTabSpec("Item02").setIndicator("label2").setContent(intent2));
        Intent intent3=new Intent(this,MainNews.class);        
        tabHost.addTab(tabHost.newTabSpec("Item03").setIndicator("label3").setContent(intent3));
        Intent intent4=new Intent(this,MainAbout.class);        
        tabHost.addTab(tabHost.newTabSpec("Item04").setIndicator("label4").setContent(intent4));
    	
    }
    private void debugTest(){
        if(Common.isDebug("wsylib"))
        	Toast.makeText(this, Re.warning.debug, Toast.LENGTH_SHORT).show();
    }


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch(keyCode)
		{//Toast.makeText(this, "This is MainActivity!", Toast.LENGTH_SHORT).show();
		case KeyEvent.KEYCODE_BACK:onExitPressed();break;
		
		}
		return false;//super.onKeyDown(keyCode, event);
	}

	private void onExitPressed(){
		/*long lTime=new Date().getTime();
		Log.v("Main.onExitPressed()","Start");
		Log.v("Main.onExitPressed().lTime",String.valueOf(lTime-pressTime));*/
		if(Common.twoBackExit(this,Re.integer.exit_last_time))
			exit();
		else
			exitGuide();
	
	}
	private void exit(){
		System.exit(0);
	}
	private void exitGuide(){
		Toast.makeText(this, Re.leading.exit, Toast.LENGTH_SHORT).show();
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		setCurrentSpec(checkedId);
		
	}
	public void setCurrentSpec(int id){
		switch(id)
		{
		case R.id.radio0:tabHost.setCurrentTabByTag("Item01");break;
		case R.id.radio1:tabHost.setCurrentTabByTag("Item02");break;
		case R.id.radio2:tabHost.setCurrentTabByTag("Item03");break;
		case R.id.radio3:tabHost.setCurrentTabByTag("Item04");break;
		}
	}
}
