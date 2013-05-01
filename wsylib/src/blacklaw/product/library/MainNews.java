package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainNews extends Activity{
	private WebView web;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_news);
		web=(WebView)findViewById(R.id.webView1);
		configWebview();
		//loadNewsUrl();
		//writeNewsCache("<center><h1 style=\"color:#ff0\">Net Server Error...</h1></center>");
		//Toast.makeText(this, Common.readAssetStr(this, "wsylibkey"), Toast.LENGTH_SHORT).show();
		loadNewsFromCache();
		loadNewsFromWeb();
		/*web.getSettings().setJavaScriptEnabled(true);
		web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);		
		web.setOnLongClickListener(new OnLongClickListener(){
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		web.setWebViewClient(new MyWebViewClient());
		web.loadUrl(Re.url.SCHOOL_NEWS);*/
		//web.loadUrl("http://townke.com/program/library/school/school_whswgcxy.html");
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
	private void loadNewsUrl(){
		web.loadUrl(Re.url.SCHOOL_NEWS);
		
	}
	private void loadNewsFromCache(){
		String newsCache="";
		newsCache=readNewsCache();
		if(newsCache.length()==0)
			loadNewsUrl();
		else
			setWebviewData(newsCache);
	}
	
	private void loadNewsFromWeb(){
		MRunnable runnable=new MRunnable();
		runnable.url=Re.url.SCHOOL_NEWS;
		new Thread(runnable).start();
	}
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if(msg.what==1){
				//Log.v("MainNews.mHandler.msg.obj",(String)msg.obj);
				String receive=(String)msg.obj;
				if(receive.length()!=0)
				{
					writeNewsCache(receive);
					setWebviewData((String)msg.obj);
					Common.globalToast(Re.leading.news_refush_success);
				}
			}
			super.handleMessage(msg);
		}
		
		
	};
	class MRunnable implements Runnable{
		public String url;
		@SuppressLint({ "HandlerLeak", "HandlerLeak" })
		public void run() {
			// TODO Auto-generated method stub
			RemoteReader reader=new RemoteReader();
			String receive=reader.loadData(url);
			mHandler.obtainMessage(1,receive).sendToTarget();
		}
		
		
	}
	private boolean setWebviewData(String data){
		//Log.v("MainNews.setWebviewData().newsCache",data);
		//Common.writeSDCard("/wsylib.schoolnews.txt", data.getBytes());
		//web.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
		web.loadDataWithBaseURL("",data,"text/html", "utf-8",Re.url.SCHOOL_NEWS);
		return true;
	}
	private String readNewsCache(){
		String cache="";
		//cache="<center><h1 style=\"color:#0f0\">Net Server Error...</h1></center>";
		cache=Common.readRawStr(this, Re.name.news_cache);
		return cache;
		
	}
	private boolean writeNewsCache(String cache){
		Common.writeRawStr(this, Re.name.news_cache, cache);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "this is BooksFavorite", Toast.LENGTH_SHORT).show();
		/*Activity lParent=getParent();
		if(lParent!=null)
			lParent.onKeyDown(keyCode, event);
		return super.onKeyDown(keyCode, event);*/
		return false;
	}
	}
