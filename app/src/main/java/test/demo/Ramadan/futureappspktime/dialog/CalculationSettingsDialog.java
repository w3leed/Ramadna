package test.demo.Ramadan.futureappspktime.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.demo.musilmguide.R;
import test.demo.Ramadan.futureappspktime.CONSTANT;
import test.demo.Ramadan.futureappspktime.GPSTracker;
import test.demo.Ramadan.futureappspktime.VARIABLE;

import test.demo.Ramadan.prayertimings.PrayerTimingActivity;
import test.demo.Ramadan.MainActivity;

import test.demo.Ramadan.prayertimings.SettingsActivity;

public class CalculationSettingsDialog extends Dialog {


	GPSTracker currentLocation = new GPSTracker(getContext());
//    Location currentLocation = VARIABLE.getCurrentLocation(getContext());

	PrayerTimingActivity fragment2PrayerTimes = new PrayerTimingActivity();
    MainActivity mainActivity = new MainActivity();

	public CalculationSettingsDialog(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_calculation);
		setTitle(R.string.calculation);
		((EditText)findViewById(R.id.latitude)).setText(""+MainActivity.lati);
		((EditText)findViewById(R.id.longitude)).setText(""+MainActivity.longi);
//        if(currentLocation != null) {
//            ((EditText)findViewById(R.id.latitude)).setText(Double.toString(currentLocation.getLatitude()));
//            ((EditText)findViewById(R.id.longitude)).setText(Double.toString(currentLocation.getLongitude()));
//        } else {
//            ((EditText)findViewById(R.id.latitude)).setText("");
//            ((EditText)findViewById(R.id.longitude)).setText("");
//        }

//		float latitude = VARIABLE.settings.getFloat("latitude", -999f);
//		float longitude = VARIABLE.settings.getFloat("longitude", -999f);
//		((EditText)findViewById(R.id.latitude)).setText(latitude == -999f ? "" : Float.toString(latitude));
//		((EditText)findViewById(R.id.longitude)).setText(longitude == -999f ? "" : Float.toString(longitude));

		Spinner calculation_methods = (Spinner)findViewById(R.id.calculation_methods);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.calculation_methods, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		calculation_methods.setAdapter(adapter);
		calculation_methods.setSelection(VARIABLE.settings.getInt("calculationMethodsIndex", CONSTANT.DEFAULT_CALCULATION_METHOD));

//		((Button)findViewById(R.id.lookup_gps)).setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
////				Location currentLocation = VARIABLE.getCurrentLocation(getContext());
//				if(currentLocation != null) {
//					((EditText)findViewById(R.id.latitude)).setText(Double.toString(currentLocation.getLatitude()));
//					((EditText)findViewById(R.id.longitude)).setText(Double.toString(currentLocation.getLongitude()));
//				} else {
//					((EditText)findViewById(R.id.latitude)).setText("");
//					((EditText)findViewById(R.id.longitude)).setText("");
//				}
//			}
//		});
		
		((Button)findViewById(R.id.save_settings)).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = VARIABLE.settings.edit();
				try {
					editor.putFloat("latitude", Float.parseFloat(((EditText)findViewById(R.id.latitude)).getText().toString()));
				} catch(Exception ex) {
					// Invalid latitude
				}
				try {
					editor.putFloat("longitude", Float.parseFloat(((EditText)findViewById(R.id.longitude)).getText().toString()));
				} catch(Exception ex) {
					// Invalid longitude
				}
				editor.putInt("calculationMethodsIndex", ((Spinner)findViewById(R.id.calculation_methods)).getSelectedItemPosition());
                editor.commit();
				SettingsActivity.context.finish();
			}
		});
		((Button)findViewById(R.id.reset_settings)).setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v) {
				((Spinner)findViewById(R.id.calculation_methods)).setSelection(CONSTANT.DEFAULT_CALCULATION_METHOD);
			}
		});
	}
}