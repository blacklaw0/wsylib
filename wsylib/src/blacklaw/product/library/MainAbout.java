package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebView;

public class MainAbout extends Activity{
	private WebView web;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "this is BooksFavorite", Toast.LENGTH_SHORT).show();
		/*Activity lParent=getParent();
		if(lParent!=null)
			lParent.onKeyDown(keyCode, event);*/
		return false;//super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_about);
		web=(WebView)findViewById(R.id.webView1);
		configWebview();
		loadAboutUrl();
		


	}
	@SuppressLint("SetJavaScriptEnabled")
	private void configWebview(){
		web.getSettings().setJavaScriptEnabled(true);
		web.setOnLongClickListener(new OnLongClickListener(){
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		web.setBackgroundColor(0);
		web.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		web.setWebViewClient(new MyWebViewClient());
	}
	private void loadAboutUrl(){
		web.loadUrl(Re.url.about);
	}

}
