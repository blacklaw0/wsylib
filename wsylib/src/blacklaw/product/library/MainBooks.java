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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainBooks extends TabActivity implements OnCheckedChangeListener,OnClickListener{
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		/*Activity parent=getParent();
		if(parent!=null)
		return	parent.onKeyDown(keyCode, event);*/
		return false;//super.onKeyDown(keyCode, event);
	}
	private TabHost host;
	public AdapterFavorite adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main_books);
		//TODO writed in a better way
		//((MyApp)(this.getApplication())).setActivity(this);
		bindUI();
		traverseSons();
		
	}
	private void traverseSons(){
	for(int i=0;i<3;i++)	
		host.setCurrentTab(i);
	host.setCurrentTab(0);
	}
	public void bindUI(){
		((RadioGroup)findViewById(R.id.cho_books_group)).setOnCheckedChangeListener(this);
		findViewById(R.id.choFind).setOnClickListener(this);
		host=this.getTabHost();
//		Toast.makeText(this, "MainBooks Builded", Toast.LENGTH_SHORT).show();
		Intent intentFavorite=new Intent(MainBooks.this,BooksFavorite.class);
		host.addTab(host.newTabSpec("Favorite").setIndicator("Favorite").setContent(intentFavorite));
		Intent intentHot=new Intent(MainBooks.this,BooksHot.class);
		host.addTab(host.newTabSpec("Hot").setIndicator("Hot").setContent(intentHot));
		Intent intentTools=new Intent(MainBooks.this,BooksTools.class);
		host.addTab(host.newTabSpec("Tools").setIndicator("Tools").setContent(intentTools));
		Intent intentFind=new Intent(MainBooks.this,BooksFind.class);
		host.addTab(host.newTabSpec("Find").setIndicator("Find").setContent(intentFind));	
		
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(group.getId()==R.id.cho_books_group)
		{
			//Toast.makeText(this, String.valueOf(checkedId), Toast.LENGTH_SHORT).show();
			setCurrentSpec(checkedId);
		}
	}
	public void setCurrentSpec(int id){
		switch(id)
		{
		case R.id.choFavorite:host.setCurrentTabByTag("Favorite");break;
		case R.id.choHot:host.setCurrentTabByTag("Hot");break;
		case R.id.choTools:host.setCurrentTabByTag("Tools");break;
//		case R.id.choFind:host.setCurrentTabByTag("Find");break;
		
		}
	}
/*	public boolean addFavorite(Map<String,Object> map){
		if(!adapter.addObject(in))
			return false;
		adapter.saveFavorite();
		Log.v("addFavorite()","add map");
		return true;
	}*/
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.choFind){
			onFindClick();
		}
			
	}
	public void onFindClick(){
		jumpToFindPage();
		
	}
	public void jumpToFindPage(){
		Intent intent=new Intent(this,BooksFind.class);
		this.startActivity(intent);
	}

}
