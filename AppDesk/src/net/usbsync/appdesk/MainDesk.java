package net.usbsync.appdesk;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.usbsync.appdesk.jobs.TaskPackMgr;
import net.usbsync.appdesk.util.MainUtil;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainDesk extends Activity {

	public static Context nContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_desk);

		nContext = this;
		
		
		TaskPackMgr tpm = new TaskPackMgr();
		
		tpm.execute();
	}
}
