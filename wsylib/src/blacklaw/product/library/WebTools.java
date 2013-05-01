package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class WebTools extends Activity {
	//private String url;
	private String toolName;
	private WebView webTools;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		toolName=(String)this.getIntent().getExtras().get("toolName");
		//url=(String)this.getIntent().getExtras().get("url");
		//Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
		setContentView(R.layout.web_tools);
		webTools=(WebView)findViewById(R.id.tools_web);
		webTools.setWebViewClient(new MyWebViewClient());
		webTools.loadDataWithBaseURL(Re.url.TOOLS_UPDATA, Common.readRawStr(this, toolName), "text/html","utf-8", "");
		//webTools.loadUrl(url);
		

	}

}



