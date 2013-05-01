package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class BooksFavorite extends Activity {
	private AdapterFavorite adapter;
	private ExpandableListView list;
	private ExOnGroupClickListener onClickListener=new ExOnGroupClickListener();
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return false;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.books_favroite);
		adapter=new AdapterFavorite(this);
		list=(ExpandableListView)findViewById(R.id.list_favorite);
		
		list.setOnGroupClickListener(onClickListener);
		list.setAdapter(adapter);

		adapter.initDataFromCache();

		registerFavoriteAdapter();

		
	}
	private void registerFavoriteAdapter(){
		((MyApp)this.getApplication()).register.favoriteAdapter=adapter;
		
		
	}
	public boolean collapseAll(){
		return onClickListener.collapseAll();
	}



}
