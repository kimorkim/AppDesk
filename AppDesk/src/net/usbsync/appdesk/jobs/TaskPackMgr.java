package net.usbsync.appdesk.jobs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import net.usbsync.appdesk.MainDesk;
import net.usbsync.appdesk.util.MainUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class TaskPackMgr extends AsyncTask<Void, String, Boolean> {
	private ProgressDialog PackMgrDlg;
	private int PackSize = 0;
	private DBAppDataHelper AppDB = new DBAppDataHelper(MainDesk.nContext);
	Context MainContext = MainDesk.nContext;
	SharedPreferences pref;
	SharedPreferences.Editor editor;

	@Override
	protected void onPreExecute() {

		Log.d(MainUtil.DEBUG_TAG,
				"onPreExecute ����=================================");
		PackMgrDlg = new ProgressDialog(MainDesk.nContext);
		PackMgrDlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		PackMgrDlg.setMessage("��ġ�� ���� ��ȸ�� �Դϴ�.");

		pref = MainContext.getSharedPreferences("AppDesk", 0);
		editor = pref.edit();

		super.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		Boolean returnValue = true;
		AppDatas data = new AppDatas();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		AppDB.dbOpen();
		try {

			Log.d(MainUtil.DEBUG_TAG,
					"doInBackground ����=================================");

			if (pref.getBoolean("CheckApp", false)) {
				AppDB.onUpgrade(AppDB.getDataBase(), 0, 0);
				// ��Ű�� ���� ���
				PackageManager pm = MainDesk.nContext.getPackageManager();

				// ��ġ�� ���ø����̼� ����Ʈ ���
				List<ApplicationInfo> packs = pm
						.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
								| PackageManager.GET_DISABLED_COMPONENTS);

				PackSize = packs.size();
				publishProgress("Show");
				publishProgress("Max", String.valueOf(PackSize));

				int appCnt = 1;
				for (ApplicationInfo app : packs) {
					data.setAppName(app.loadLabel(pm).toString());
					data.setPackageName(app.packageName);
					data.setAppIcon(app.icon);
					data.setOption("");
					AppDB.addAppDatas(data);
					Log.d(MainUtil.DEBUG_TAG, app.loadLabel(pm).toString());
					publishProgress("Prog", String.valueOf(appCnt++));
				}
			} else {

			}

		} catch (Exception e) {
			e.printStackTrace();
			returnValue = false;
		} finally {
			AppDB.dbClose();
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return returnValue;
	}

	@Override
	protected void onProgressUpdate(String... values) {
		if (values[0].equals("Prog")) {
			PackMgrDlg.setProgress(Integer.valueOf(values[1]));
		} else if (values[0].equals("Max")) {
			PackMgrDlg.setMax(Integer.valueOf(values[1]));
		} else {
			PackMgrDlg.show();
		}

		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		PackMgrDlg.dismiss();
		if (result) {
			Log.d(MainUtil.DEBUG_TAG,
					"onPostExecute ����=================================");
			Toast.makeText(MainDesk.nContext, "�۾��� �Ϸ�Ǿ����ϴ�.",
					Toast.LENGTH_SHORT).show();		
		} else {
			Toast.makeText(MainDesk.nContext, "�۾��� �����Ͽ����ϴ�.",
					Toast.LENGTH_SHORT).show();
		}
		
		editor.putBoolean("CheckApp", result);
		editor.commit();
		
		MainDesk.MainContHandler.sendEmptyMessage(MainUtil.MAKE_APP_LISTS);
		
		super.onPostExecute(result);
	}

}
