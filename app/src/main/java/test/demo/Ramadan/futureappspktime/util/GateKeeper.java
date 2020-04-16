package test.demo.Ramadan.futureappspktime.util;

import android.content.pm.PackageManager;

import test.demo.Ramadan.futureappspktime.VARIABLE;


public class GateKeeper {
	public static String getVersionName() {
		String versionName = "undefined";
		try {
			versionName = VARIABLE.context.getPackageManager().getPackageInfo(VARIABLE.context.getPackageName(), PackageManager.GET_META_DATA).versionName;
		} catch(Exception ex) { }
		return versionName;
	}
}