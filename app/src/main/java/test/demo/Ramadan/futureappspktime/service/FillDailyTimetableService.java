package test.demo.Ramadan.futureappspktime.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.demo.musilmguide.R;
import test.demo.Ramadan.futureappspktime.CONSTANT;
import test.demo.Ramadan.futureappspktime.Schedule;
import test.demo.Ramadan.futureappspktime.VARIABLE;
import test.demo.Ramadan.futureappspktime.jitl.Jitl;
import test.demo.Ramadan.futureappspktime.jitl.astro.Direction;
import test.demo.Ramadan.futureappspktime.jitl.astro.Dms;
import test.demo.Ramadan.futureappspktime.jitl.astro.Location;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class FillDailyTimetableService extends Service {

	private static Activity parent;

	private static Schedule day;
	private static ArrayList<HashMap<String, String>> timetable;
	private static SimpleAdapter timetableView;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		try {
			GregorianCalendar[] schedule = day.getTimes();
			SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
			if(VARIABLE.settings.getInt("timeFormatIndex", CONSTANT.DEFAULT_TIME_FORMAT) != CONSTANT.DEFAULT_TIME_FORMAT) {
				timeFormat = new SimpleDateFormat("HH:mm ");
			}

			for(short i = CONSTANT.FAJR; i <= CONSTANT.NEXT_FAJR; i++) {
				String fullTime = timeFormat.format(schedule[i].getTime());
				timetable.get(i).put("mark", ""); // Clear all existing markers since we're going to set the next one
				timetable.get(i).put("time", fullTime.substring(0, fullTime.lastIndexOf(" ")));
				if(VARIABLE.settings.getInt("timeFormatIndex", CONSTANT.DEFAULT_TIME_FORMAT) == CONSTANT.DEFAULT_TIME_FORMAT) {
					timetable.get(i).put("time_am_pm", fullTime.substring(fullTime.lastIndexOf(" ") + 1, fullTime.length()) + (day.isExtreme(i) ? "*" : ""));
				} else {
					timetable.get(i).put("time_am_pm", day.isExtreme(i) ? "*" : "");
				}
				if(day.isExtreme(i)) ((TextView)parent.findViewById(R.id.notes)).setText("* " + getString(R.string.extreme));
			}
            timetable.get(day.nextTimeIndex()).put("mark1", getString(R.string.next_time_marker));
			timetable.get(day.nextTimeIndex()).put("mark", getString(R.string.next_time_marker));

			timetableView.notifyDataSetChanged();

			// Add Latitude, Longitude and Qibla DMS location
			Location location = new Location(VARIABLE.settings.getFloat("latitude", 43.67f), VARIABLE.settings.getFloat("longitude", -79.417f), Schedule.getGMTOffset(), 0);
			location.setSeaLevel(VARIABLE.settings.getFloat("altitude", 0) < 0 ? 0 : VARIABLE.settings.getFloat("altitude", 0));
			location.setPressure(VARIABLE.settings.getFloat("pressure", 1010));
			location.setTemperature(VARIABLE.settings.getFloat("temperature", 10));

			DecimalFormat df = new DecimalFormat("#.###");
			Dms latitude = new Dms(location.getDegreeLat());
			Dms longitude = new Dms(location.getDegreeLong());
			Dms qibla = Jitl.getNorthQibla(location);
			VARIABLE.qiblaDirection = (float)qibla.getDecimalValue(Direction.NORTH);
			((TextView)parent.findViewById(R.id.current_latitude_deg)).setText(String.valueOf(latitude.getDegree()));
			((TextView)parent.findViewById(R.id.current_latitude_min)).setText(String.valueOf(latitude.getMinute()));
			((TextView)parent.findViewById(R.id.current_latitude_sec)).setText(df.format(latitude.getSecond()));
			((TextView)parent.findViewById(R.id.current_longitude_deg)).setText(String.valueOf(longitude.getDegree()));
			((TextView)parent.findViewById(R.id.current_longitude_min)).setText(String.valueOf(longitude.getMinute()));
			((TextView)parent.findViewById(R.id.current_longitude_sec)).setText(df.format(longitude.getSecond()));
			((TextView)parent.findViewById(R.id.current_qibla_deg)).setText(String.valueOf(qibla.getDegree()));
			((TextView)parent.findViewById(R.id.current_qibla_min)).setText(String.valueOf(qibla.getMinute()));
			((TextView)parent.findViewById(R.id.current_qibla_sec)).setText(df.format(qibla.getSecond()));
		} catch(Exception ex) {
			try {
				java.io.StringWriter sw = new java.io.StringWriter();
				java.io.PrintWriter pw = new java.io.PrintWriter(sw, true);
				ex.printStackTrace(pw);
				pw.flush(); sw.flush();
//				((TextView)parent.findViewById(R.id.notes)).setText(sw.toString());
			} catch(Exception ex2) {
				// App must not be open (ex. killed from app history), prevent a force close
			}
		}
	}

	/**
	 * We use this class in a static way by using the following set function.
	 * In the future we may want to be able to display other dates than just today.
	 *  **/
	public static void set(Activity _parent, Schedule _day, ArrayList<HashMap<String, String>> _timetable, SimpleAdapter _timetableView) {
		parent = _parent;

		day = _day;
		timetable = _timetable;
		timetableView = _timetableView;

		parent.startService(new Intent(parent, FillDailyTimetableService.class));
	}
}