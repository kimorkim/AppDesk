package net.usbsync.appdesk.jobs;

public class AppDatas {
	int Id;
	String AppName;
	String PackageName;
	int AppIcon;
	String Option;

	public AppDatas() {

	}

	public AppDatas(int id, String appName, String packageName, int appIcon,
			String Option) {

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getAppName() {
		return AppName;
	}

	public void setAppName(String appName) {
		AppName = appName;
	}

	public String getPackageName() {
		return PackageName;
	}

	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public int getAppIcon() {
		return AppIcon;
	}

	public void setAppIcon(int appIcon) {
		AppIcon = appIcon;
	}

	public String getOption() {
		return Option;
	}

	public void setOption(String option) {
		Option = option;
	}

	public void ClearData() {
		Id = 0;
		AppName = null;
		PackageName = null;
		AppIcon = 0;
		Option = null;
	}
}
