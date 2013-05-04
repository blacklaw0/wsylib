package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
/*
 * RemoteReader 类,获取远程数据,Get调用获取,经常用,为方便调试,单列
 */
public class RemoteReader {
	int errorCode=0;
	public String loadDataWithWarning(String urlStr){
		String value=loadData(urlStr);
		if(errorCode==1)//网络超时
			return Re.string.remoteConnectTimeout;
			//Toast.makeText(activity, Re.warning.connect_timeout, Toast.LENGTH_LONG).show();
		return value;
		
	}
	public String loadData(String urlStr) 
	{
		errorCode=0;
		String data ="",str;
		try{
		URL url=null;
		HttpURLConnection urlConn=null;
		Log.v("BufferedReader",urlStr);
			url=new URL(urlStr);
		
			urlConn=(HttpURLConnection)url.openConnection();
			Log.v("BufferedReader","p1");
			urlConn.setConnectTimeout(Re.integer.timeout_millis);
			urlConn.setReadTimeout(Re.integer.timeout_millis);
			Log.v("BufferedReader","p2");
			BufferedReader buffer = null;
			
			try {
				buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			} catch (java.net.SocketTimeoutException e) {
				// TODO Auto-generated catch block
				//Toast.makeText(this, text, duration)
				//if(e.equals(java.net.SocketTimeoutException))
				Log.v("RemoteReader.loadData().timeout","true");
				errorCode=1;
				//Common.globalToast(Re.warning.connect_timeout);
				return "";
				
				
			}
			Log.v("BufferedReader","p3");
			while((str=buffer.readLine())!=null)
			{	data+=str;
				Log.v(str,"LIB_DEBUG");
			}
			if(data.length()==0){
				return "";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
}
