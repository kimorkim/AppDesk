package net.usbsync.appdesk;

import java.util.List;

import net.usbsync.appdesk.util.MainUtil;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

public class MainDesk extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_desk);

		// ��Ű�� ���� ���
		PackageManager pm = this.getPackageManager();

		// ��ġ�� ���ø����̼� ����Ʈ ���
		List<ApplicationInfo> packs = pm
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_DISABLED_COMPONENTS);

		for (ApplicationInfo app : packs) {
			// App Icon
			// info.mIcon = app.loadIcon(pm);
			// App Name
			// info.mAppNaem = app.loadLabel(pm).toString();
			// App Package Name
			// info.mAppPackge = app.packageName;
			// mListData.add(info);
			Log.d(MainUtil.DEBUG_TAG, app.loadLabel(pm).toString());
		}
	}
}
