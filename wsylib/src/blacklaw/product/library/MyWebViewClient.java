package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
/*
 * webview 的client,常用,单独列出
 * 
 */
public class MyWebViewClient extends WebViewClient{
    
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
              //Log.i(TAG, "-MyWebViewClient->shouldOverrideUrlLoading()--");
             view.loadUrl(url);
            return true;
    }
    
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
             // Log.i(TAG, "-MyWebViewClient->onPageStarted()--");
            super.onPageStarted(view, url, favicon);
    }
    
    @Override
    public void onPageFinished(WebView view, String url) {
             // Log.i(TAG, "-MyWebViewClient->onPageFinished()--");
            super.onPageFinished(view, url);
    }
    
    
    @Override
    public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Log.v("MyWebViewClient.onReceivedError.errorCode",String.valueOf(errorCode));
            Log.v("MyWebViewClient.onReceivedError.description",description);
            Log.v("MyWebViewClient.onReceivedError.failingUrl",failingUrl);
            view.loadUrl(Re.url.web_error);
             // Log.i(TAG, "-MyWebViewClient->onReceivedError()--\n errorCode="+errorCode+" \ndescription="+description+" \nfailingUrl="+failingUrl);
             //这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
                      //view.loadData("file:///android_asset/weberror.html", "text/html", "UTF-8");
             
             
    }
}
