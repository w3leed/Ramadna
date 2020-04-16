package test.demo.Ramadan;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.musilmguide.R;
import test.demo.Ramadan.duas.Duas;
import test.demo.Ramadan.futureappspktime.VARIABLE;
import test.demo.Ramadan.futureappspktime.util.LocaleManager;
import test.demo.Ramadan.kalma.KalmasOfIslam;
import test.demo.Ramadan.nearmosque.MosqueFinderActivity;
import test.demo.Ramadan.prayertimings.PrayerTimingActivity;
import test.demo.Ramadan.qibladirection.QiblaCompass;
import test.demo.Ramadan.ramadantimings.RamadanTimingActivity;
import test.demo.Ramadan.surahrecitation.SurahRecitationActivity;
import test.demo.Ramadan.tasbeeh.Tasbeeh;
import test.demo.Ramadan.universal.GPSTracker;
import test.demo.Ramadan.universal.ThemeManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //    private static String[] titles = null;
    Context context;
    TextView currentDate, currentTime;
    GPSTracker gps;
    public static double lati, longi;
    private static ThemeManager themeManager;
    private static LocaleManager localeManager;
    private ImageView setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar ().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar ().setCustomView(R.layout.customactionbar);
        context = this;

        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);

        VARIABLE.context = this;
        if (VARIABLE.settings == null)
            VARIABLE.settings = getSharedPreferences("settingsFile", MODE_PRIVATE);
        getlocation();
        localeManager = new LocaleManager();
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        String date = df.format(Calendar.getInstance().getTime());
        DateFormat tf = new SimpleDateFormat("h:mm a");
        String time = tf.format(Calendar.getInstance().getTime());


        currentTime = (TextView) findViewById(R.id.idTvTimeHome);
        currentTime.setText(time);
        currentDate = (TextView) findViewById(R.id.idTvDateHome);
        currentDate.setText(date);

        setting = (ImageView) getSupportActionBar ().getCustomView().findViewById(R.id.ivsetting);
        setting.setVisibility(View.VISIBLE);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting =new Intent(MainActivity.this,Settings.class);
                startActivity(setting);
//                new SettingsDialog(context, localeManager, themeManager).show();
            }

        });

    }

    private void getlocation() {
        // TODO Auto-generated method stub
        gps = new GPSTracker(this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            lati = gps.getLatitude();
            longi = gps.getLongitude();


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }

    public void Clicker(View view) {
        switch (view.getId()) {
            case R.id.iv6kalma:
                Intent kalmas = new Intent(context, KalmasOfIslam.class);
                startActivity(kalmas);
//                Toast.makeText(context,"kalma",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivramadantiming:
                Intent ramadan = new Intent(context, RamadanTimingActivity.class);
                startActivity(ramadan);
//                Toast.makeText(context,"ramadantiming",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivtasbeeh:
                Intent tasbeeh = new Intent(context, Tasbeeh.class);
                startActivity(tasbeeh);

//                Toast.makeText(context,"tasbeeh",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivduas:
                Intent duas = new Intent(context, Duas.class);
                startActivity(duas);
//                Toast.makeText(context,"duas",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivqibladirction:
                Intent qiblad = new Intent(context, QiblaCompass.class);
                startActivity(qiblad);
//                Toast.makeText(context,"qibladirction",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivprayertiming:

                Intent prayertime = new Intent(context, PrayerTimingActivity.class);
                startActivity(prayertime);
//                Toast.makeText(context,"prayertiming",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivlearnquran:
                Intent learnquran = new Intent(context, SurahRecitationActivity.class);
                startActivity(learnquran);
//                Toast.makeText(context,"learnquran",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ivmosquenearby:
                Intent mosque = new Intent(context, MosqueFinderActivity.class);
                startActivity(mosque);
//                Toast.makeText(context, "mosquenearby", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
