package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainMylib extends TabActivity implements OnCheckedChangeListener{
	private TabHost host;
	public String getStr="";
	public String strFinancial="";
	public String strHistory="";
	public String strRenew="";
	private String lastDataDate="";
	public MylibRenew sonRenew;
	public MylibHistory sonHistory;
	public MylibFinancial sonFinancial;
	public boolean 		isLoginSuccess=false;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;//super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_mylib);
		bindUI();
		initData();
	}
	
	private void bindUI(){
		host=this.getTabHost();
		Intent intentLogin=new Intent(MainMylib.this,MylibLogin.class);
		host.addTab(host.newTabSpec("Login").setIndicator("账户").setContent(intentLogin));
		Intent intentRenew=new Intent(MainMylib.this,MylibRenew.class);
		host.addTab(host.newTabSpec("Renew").setIndicator("续借").setContent(intentRenew));
		Intent intentHistory=new Intent(MainMylib.this,MylibHistory.class);
		host.addTab(host.newTabSpec("History").setIndicator("历史").setContent(intentHistory));
		Intent intentFinancial=new Intent(MainMylib.this,MylibFinancial.class);
		host.addTab(host.newTabSpec("Financial").setIndicator("财经").setContent(intentFinancial));
		((RadioGroup)findViewById(R.id.mylibGroup)).setOnCheckedChangeListener(this);
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(group.getId()){
		case R.id.mylibGroup:onGroupChanged(checkedId);
		
		}
	}
	public void onGroupChanged(int checkedId){
		int id=Integer.valueOf(findViewById(checkedId).getContentDescription().toString());
		setSpecByCheckedId(id);
		switch(id){
			case 0:onLoginClick();break;
			default:onOtherButtonClick();break;
		}
	}
	private void initData(){
		for(int i=0;i<4;i++)
			host.setCurrentTab(i);
		host.setCurrentTab(0);
		lastDataDate=Common.getShare(this,Re.name.share_file,Re.name.date_share_block);
		readMylibCache();
		
		
		
	}
	private void readMylibCache(){
		
		strRenew=Common.readRawStr(this, Re.name.renew_cache);
		strHistory=Common.readRawStr(this, Re.name.history_cache);
		strFinancial=Common.readRawStr(this, Re.name.financial_cache);
		for(int i=1;i<=3;i++)
			notifySonDataChanged(i);
		
	}
	private void setSpecByCheckedId(int i){
		String spec="";
		switch(i){
		case 0:spec="Login";break;
		case 1:spec="Renew";break;
		case 2:spec="History";break;
		case 3:spec="Financial";break;
		}
		host.setCurrentTabByTag(spec);
	}
	private void onLoginClick(){
		
		
	}
	private boolean onOtherButtonClick(){
		if(lastDataDate==null)
		{
			Toast.makeText(this,Re.warning.first_login , Toast.LENGTH_SHORT).show();
			RadioButton radio=(RadioButton)findViewById(R.id.radioLogin);
			radio.setChecked(true);
			host.setCurrentTabByTag("Login");
			return false;
		}
		
		if(!isLoginSuccess){
			Toast.makeText(this,Re.warning.old_data.replaceAll("~lastDataDate~",lastDataDate) , Toast.LENGTH_SHORT).show();
		}
		return true;
	}
	
	public void notifySonDataChanged(int son){
		switch(son)
		{
		case 1:sonRenew.setData(strRenew);Common.writeRawStr(this,Re.name.renew_cache , strRenew);break;
		case 2:sonHistory.setData(strHistory);Common.writeRawStr(this,Re.name.history_cache , strHistory);break;
		case 3:sonFinancial.setData(strFinancial);Common.writeRawStr(this,Re.name.financial_cache , strFinancial);break;
		case 5:isLoginSuccess=true;lastDataDate=Common.getDateStr();Common.putShare(this, Re.name.share_file, Re.name.date_share_block, lastDataDate);
		;break;
		}

	}

	

}
