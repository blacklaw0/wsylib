package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
/*
 * Common 类,常用代码的集合,很乱,磁盘读写,状态检查的都有,没有整理
 * 
 */
public class Common {
	static String sdDir;
	static boolean isExist;
	static boolean isDebug(String testName){
		Log.v("wsylib.common.isDebug()","start");
		sdDir=Environment.getExternalStorageDirectory().toString();
		File file=new File(sdDir+"/"+testName+"_is_debug");
		Log.v("wsylib.common.isDebug().sdDirdirect",sdDir+"/"+testName+"_is_debug");
		Log.v("wsylib.common.isDebug()","end "+String.valueOf(file.exists()));
		isExist=file.exists();
		return isExist;
	}
	static boolean isDebug(){
		
		return isExist;
	}
	static Activity activity;
	static public void setGlobalActivity(Activity lActivity){
		activity=lActivity;
	}
	static public void globalToast(String msg){
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
		
	}
    static public boolean writeLibData(Activity activity,String filename,byte[] bits,boolean showDialog){
    	if(!Common.isSDCardExist())
    		Toast.makeText(activity,Re.warning.no_sdcard, Toast.LENGTH_SHORT).show();
    	if(!Common.isFileExist("/BlackLaw"))
    		Common.createDir("/BlackLaw");
    	if(!Common.isFileExist("/BlackLaw/wsylib"))
    		Common.createDir("/BlackLaw/wsylib");
    	boolean rValue= Common.writeSDCard("/BlackLaw/wsylib/"+filename, bits);
    	if(rValue && showDialog)
    		Toast.makeText(activity, Re.leading.sd_data_write.replace("~filename~", "SDCard/BlackLaw/wsylib/"+filename), Toast.LENGTH_SHORT).show();
    	return rValue;

    }
	static boolean isDebugAnd(boolean pra0){
		return isExist && pra0;
	
	}
    static public String getShare(Activity caller,String filename,String key){
    	SharedPreferences share=caller.getSharedPreferences(filename,Context.MODE_PRIVATE);
    	return share.getString(key, null);
    }
    static public boolean putShare(Activity caller,String filename,String key,String content){
    	SharedPreferences share=caller.getSharedPreferences(filename,Context.MODE_PRIVATE);
    	Editor shareEdit=share.edit();
    	shareEdit.putString(key, content);
    	shareEdit.commit();
    	return true;
    }
   static public String readRawStr(Activity caller,String filename) {
    	String data="";
    	byte[] str=null;
    	try{
    		
	    	InputStream is=caller.openFileInput(filename);
	    	if(is.available()==0)
	    		return "";
	    	str=new byte[is.available()];
	    	while(is.read(str)!=-1)
	    	{
	    		data+=new String(str);
	    		//Log.v("readRawStr","*");
	    	}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return data;
    }
    static public boolean isRawFileExist(String filename){
    	return readRawStr(activity,filename).length()!=0;
    }
    static public boolean writeRawStr(Activity caller,String filename,String str){
		try{
			OutputStream os=caller.openFileOutput(filename,Context.MODE_PRIVATE);
			os.write(str.getBytes());    	
			os.close();
	}catch(Exception e){	
			e.printStackTrace();
		return false;
		}
		return true;
    }
    static public boolean hiddenInputMethod(View v){
    	InputMethodManager inputManager=(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    	return inputManager.hideSoftInputFromWindow(v.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
    	
    }
    static public boolean showInputMethod(View v){
    	InputMethodManager inputManager=(InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    	Log.v("Common.showInputMethod","Start");
    	return inputManager.showSoftInput(v,InputMethodManager.RESULT_UNCHANGED_SHOWN);
    	
    }
    static public String getDateStr(){
		SimpleDateFormat dFormat=new SimpleDateFormat("yy-MM-dd HH:mm:ss",Locale.CHINESE);
		Date date=new Date();
		return dFormat.format(date.getTime());
    }
    static private long pressTime;
    static public boolean twoBackExit(Activity activity,long grpTime){
    	boolean rValue;
		long lTime=new Date().getTime();
		Log.v("Common.onExitPressed()","Start");
		Log.v("Common.onExitPressed().lTime",String.valueOf(lTime-pressTime));
		rValue=lTime-pressTime<grpTime;
		pressTime=lTime;
		return rValue;
	}
    static public void shareWords(Activity activity,String str){
        Intent intent=new Intent(Intent.ACTION_SEND);   
        intent.setType("text/plain");   
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share"); 
        Log.v("Common.shareWords.str",str);
        intent.putExtra(Intent.EXTRA_TEXT, str+"http://www.townke.com\r\n(分享自 武生院图书馆 townke网)");   
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
        activity.startActivity(Intent.createChooser(intent, activity.getTitle()));   
   
    }
    //RemoteReaderRunnable RemoteReaderRunnable;
	public static class RemoteReaderRunnable implements Runnable{
		private String url;
		private Handler handler;
		private int msg;
		public RemoteReaderRunnable(String lUrl,Handler lHandler,int lMsg){
			url=lUrl;
			handler=lHandler;
			msg=lMsg;
		}
		@SuppressLint({ "HandlerLeak", "HandlerLeak" })
		public void run() {
			// TODO Auto-generated method stub
			RemoteReader reader=new RemoteReader();
			String receive="";
			if(url!="" && url!=null) receive=reader.loadData(url);
			handler.obtainMessage(msg,receive).sendToTarget();
		}
		
		
	}


       static public boolean isConnectingToInternet(Activity activity){
            ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
              if (connectivity != null)
              {
                  NetworkInfo[] info = connectivity.getAllNetworkInfo();
                  if (info != null)
                      for (int i = 0; i < info.length; i++)
                          if (info[i].getState() == NetworkInfo.State.CONNECTED)
                          {
                              return true;
                          }
     
              }
              return false;
        }
    
    static public void sharePhoto(Activity activity,String photoUri,String text) { 
        Intent shareIntent = new Intent(Intent.ACTION_SEND); 
        shareIntent.setType("image/png"); 
        File file = new File(photoUri); 
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file)); 
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        shareIntent.putExtra(Intent.EXTRA_TEXT,text);
        //shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(shareIntent, activity.getTitle())); 
    }
	static public boolean writeSDCard(String filename,byte[] bits){
		Log.v("writeSDCard().SDCardDir",Environment.getExternalStorageDirectory().toString());
		String address=Environment.getExternalStorageDirectory()+filename;
		File file=new File(address);
		try {
			FileOutputStream is=new FileOutputStream(file);
			is.write(bits);
			is.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	static public boolean isFileExist(String filename){
		File file=new File(Environment.getExternalStorageDirectory()+filename);
		return file.exists();
	}
	static public boolean isSDCardExist(){
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	static public boolean  createDir(String dirname){
		
		File dir=new File(Environment.getExternalStorageDirectory()+dirname);
		if(dir.exists())
			return false;
		else
			return dir.mkdir();
	}
	static public String readAssetStr(Activity activity,String filename){
		String str="";
		AssetManager am=activity.getAssets();
		try {

			InputStream in=am.open(filename);
			byte[] bits=new byte[in.available()];  
			while(in.read(bits)!=-1){
				str+=new String(bits);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return str;
	}
	static public boolean str2bmp(ArrayList<String> str,String filename){
		try{
		int x=10,y=30;
		Bitmap bitmap = Bitmap.createBitmap(700,str.size()*30+50, Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		Paint paint = new Paint();
		paint.setTextSize(20);
		canvas.drawColor(Color.WHITE);
		paint.setColor(0xff49b580);
			for(int i=0;i<str.size();i++){
			canvas.drawText(str.get(i), x, y, paint);
			y=y+30;
			}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		String path = Environment.getExternalStorageDirectory() +filename;
	    System.out.println(path);
			FileOutputStream os = new FileOutputStream(new File(path));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
			os.flush();
			os.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

    	return true;
    }
}
