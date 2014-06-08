package net.usbsync.appdesk;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

import net.usbsync.appdesk.jobs.AppDatas;
import net.usbsync.appdesk.jobs.DBAppDataHelper;
import net.usbsync.appdesk.jobs.TaskPackMgr;
import net.usbsync.appdesk.util.MainUtil;
import net.usbsync.appdesk.util.PagerAdapterClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;

@SuppressLint("NewApi")
public class MainDesk extends Activity implements
		SearchView.OnQueryTextListener {

	public static Context nContext;
	private SearchView mSearchView = null;
	private ViewPager mPager;
	public static Handler MainContHandler = null;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main_desk);

		nContext = this;

		TaskPackMgr tpm = new TaskPackMgr();

		tpm.execute();

		MainContHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == MainUtil.MAKE_APP_LISTS) {
					makeAppDeskPaper();
				}
				
				DBAppDataHelper da = new DBAppDataHelper(nContext);
				try {
					da.dbOpen();
					ArrayList<AppDatas> a = da.getAppDatasForText("¤»¤©¤·");
					a.toString();
					ArrayList<AppDatas> b = da.getAllAppDatas();
					b.toString();
				} finally {
					da.dbClose();
				}
			}
		};
		
	}

	public void makeAppDeskPaper() {
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(new PagerAdapterClass(getApplicationContext()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_action_bar, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);

		return true;

	}

	private void setupSearchView(MenuItem searchItem) {

		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		} else {
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
					| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchables = searchManager
					.getSearchablesInGlobalSearch();

			// Try to use the "applications" global search provider
			SearchableInfo info = searchManager
					.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchables) {
				if (inf.getSuggestAuthority() != null
						&& inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			mSearchView.setSearchableInfo(info);
		}

		mSearchView.setOnQueryTextListener(this);
	}

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		// mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}
}
