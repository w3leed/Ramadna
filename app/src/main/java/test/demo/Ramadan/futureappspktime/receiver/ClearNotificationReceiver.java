package test.demo.Ramadan.futureappspktime.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import test.demo.Ramadan.futureappspktime.Notifier;

public class ClearNotificationReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Notifier.stop();
	}
}