package net.usbsync.appdesk.util;

import net.usbsync.appdesk.MainDesk;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class MainUtil {
	public final static String DEBUG_TAG = "MY_DEBUG";

	public static class Util {

	}

	public static Drawable loadIcon(PackageManager pm, int icon,
			String packageName) {
		if (icon != 0) {
			Drawable dr = pm.getDrawable(packageName, icon,
					MainDesk.nContext.getApplicationInfo());
			if (dr != null) {
				return dr;
			}
		}
		return pm.getDefaultActivityIcon();
	}
}
