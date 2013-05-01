package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Application;
import android.widget.Toast;

public class MyApp extends Application{  
    //private String globalVariable;  
    //private Activity activity;
   /* public String getGlobalVariable() {  
        return globalVariable;  
    }  
    public void setGlobalVariable(String globalVariable) {  
        this.globalVariable = globalVariable;  
    }  */
    /*public void setActivity(Activity iActivity){
    	activity=iActivity;
    }
    public Activity getActivity(){
    	
    	return activity;
    }*/
    Register register=new Register();
    class Register{
    	AdapterFavorite favoriteAdapter;
    	
    }
 /*   public boolean writeLibData(String filename,byte[] bits,boolean showDialog){
    	if(!Common.isSDCardExist())
    		Toast.makeText(this,Re.warning.no_sdcard, Toast.LENGTH_SHORT).show();
    	if(!Common.isFileExist("/BlackLaw"))
    		Common.createDir("/BlackLaw");
    	if(!Common.isFileExist("/BlackLaw/wsylib"))
    		Common.createDir("/BlackLaw/wsylib");
    	boolean rValue= Common.writeSDCard("/BlackLaw/wsylib/"+filename, bits);
    	if(rValue && showDialog)
    		Toast.makeText(this, Re.leading.sd_data_write.replace("~filename~", "SDCard/BlackLaw/wsylib/"+filename), Toast.LENGTH_SHORT).show();
    	return rValue;

    }*/
}