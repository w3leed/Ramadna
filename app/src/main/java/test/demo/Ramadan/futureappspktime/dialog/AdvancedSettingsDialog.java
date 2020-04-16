package test.demo.Ramadan.futureappspktime.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.demo.musilmguide.R;
import test.demo.Ramadan.futureappspktime.CONSTANT;
import test.demo.Ramadan.futureappspktime.VARIABLE;


public class AdvancedSettingsDialog extends Dialog {

	public AdvancedSettingsDialog(Context context) {
		super(context);
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.settings_advanced);
		setTitle(R.string.advanced);

		Spinner time_format = (Spinner)findViewById(R.id.time_format);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.time_format, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		time_format.setAdapter(adapter);
		time_format.setSelection(VARIABLE.settings.getInt("timeFormatIndex", CONSTANT.DEFAULT_TIME_FORMAT));

		((EditText)findViewById(R.id.pressure)).setText(Float.toString(VARIABLE.settings.getFloat("pressure", 1010)));
		((EditText)findViewById(R.id.temperature)).setText(Float.toString(VARIABLE.settings.getFloat("temperature", 10)));
		((EditText)findViewById(R.id.altitude)).setText(Float.toString(VARIABLE.settings.getFloat("altitude", 0)));

		Spinner rounding_types = (Spinner)findViewById(R.id.rounding_types);
		adapter = ArrayAdapter.createFromResource(getContext(), R.array.rounding_types, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		rounding_types.setAdapter(adapter);
		rounding_types.setSelection(VARIABLE.settings.getInt("roundingTypesIndex", CONSTANT.DEFAULT_ROUNDING_TYPE));

		((EditText)findViewById(R.id.offset_minutes)).setText(Integer.toString(VARIABLE.settings.getInt("offsetMinutes", 0)));

		((Button)findViewById(R.id.save_settings)).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				SharedPreferences.Editor editor = VARIABLE.settings.edit();
				editor.putInt("timeFormatIndex", ((Spinner)findViewById(R.id.time_format)).getSelectedItemPosition());
				try {
					editor.putFloat("altitude", Float.parseFloat(((EditText)findViewById(R.id.altitude)).getText().toString()));
				} catch(Exception ex) {
					editor.putFloat("altitude", 0);
				}
				try {
					editor.putFloat("pressure", Float.parseFloat(((EditText)findViewById(R.id.pressure)).getText().toString()));
				} catch(Exception ex) {
					editor.putFloat("pressure", 1010);
				}
				try {
					editor.putFloat("temperature", Float.parseFloat(((EditText)findViewById(R.id.temperature)).getText().toString()));
				} catch(Exception ex) {
					editor.putFloat("temperature", 10);
				}
				editor.putInt("roundingTypesIndex", ((Spinner)findViewById(R.id.rounding_types)).getSelectedItemPosition());
				try {
					editor.putInt("offsetMinutes", Integer.parseInt(((EditText)findViewById(R.id.offset_minutes)).getText().toString()));
				} catch(Exception ex) {
					editor.putInt("offsetMinutes", 0);
				}
				editor.commit();
				dismiss();
			}
		});
		((Button)findViewById(R.id.reset_settings)).setOnClickListener(new Button.OnClickListener() {  
			public void onClick(View v) {
				((Spinner)findViewById(R.id.time_format)).setSelection(CONSTANT.DEFAULT_TIME_FORMAT);
				((EditText)findViewById(R.id.pressure)).setText("1010.0");
				((EditText)findViewById(R.id.temperature)).setText("10.0");
				((EditText)findViewById(R.id.altitude)).setText("0.0");
				((Spinner)findViewById(R.id.rounding_types)).setSelection(CONSTANT.DEFAULT_ROUNDING_TYPE);
				((EditText)findViewById(R.id.offset_minutes)).setText("0");
			}
		});
	}
}