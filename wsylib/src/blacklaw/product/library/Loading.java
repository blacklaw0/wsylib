package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
/*
 * Loading 类,启动时的缓冲界面,经常用到,单独列出
 * 
 */
public class Loading extends Activity {
	//private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setContentView(R.layout.loading);
		new Thread(new mRunnable()).start();

	}
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				toMainPage();	
				
			}
			
			super.handleMessage(msg);
			
			
		}
		
	};
	private void toMainPage(){
		//this.finish();
		Intent intent=new Intent(Loading.this,Main.class);
		//Bundle mBundle=new Bundle();
		//mBundle.putSerializable("url",Re.url.lib_open_time );
		//intent.putExtras(mBundle);
		this.startActivity(intent);
		this.finish();
		
	}
	private class mRunnable implements Runnable {
	    public void run() {
	        // TODO Auto-generated method stub
	        try {
				Thread.sleep(Re.integer.loading_time);// 线程暂停4秒，单位毫秒
				mHandler.obtainMessage(1).sendToTarget();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	}

}



