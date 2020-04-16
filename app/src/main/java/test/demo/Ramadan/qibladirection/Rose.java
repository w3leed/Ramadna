package test.demo.Ramadan.qibladirection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.demo.musilmguide.R;

import java.text.DecimalFormat;

/**
 * Created by BuAli_bluehorn on 12-May-15.
 */
public class Rose extends View {

    QiblaCompass fragment1QiblaCompass = new QiblaCompass();

    private float directionNorth = 0;
    private float directionQibla = 0;
    private TextView bearingNorth;
    private String bearingNorthString;
    private TextView bearingQibla;
    private String bearingQiblaString;
    private DecimalFormat df = new DecimalFormat("0.000");
    private Bitmap compassBackground;
    private Bitmap compassNeedle;
    private Matrix rotateNeedle = new Matrix();
    private int width = 240;
    private int height = 240;
    private float centre_x = width * 0.5f;
    private float centre_y = height * 0.5f;

    public Rose(Context context) {
        super(context);
        initCompassView();
    }
    public Rose(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCompassView();
    }
    public Rose(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }
    private void initCompassView() {
        compassNeedle = BitmapFactory.decodeResource(getResources(), R.drawable.qibla_needle);
        compassBackground = BitmapFactory.decodeResource(getResources(), R.drawable.compass_view);
        width = compassBackground.getWidth()*2;
        height = compassBackground.getHeight()*2;

        centre_x = width  * 0.5f;
        centre_y = height * 0.5f;
        rotateNeedle.postTranslate(centre_x - compassNeedle.getWidth()/2, centre_y - compassNeedle.getHeight()/2);
        invalidate();
    }
    public void setConstants(TextView bearingNorth, CharSequence bearingNorthString, TextView bearingQibla, CharSequence bearingQiblaString) {
        this.bearingNorth = bearingNorth;
        this.bearingNorthString = bearingNorthString.toString();
        this.bearingQibla = bearingQibla;
        this.bearingQiblaString = bearingQiblaString.toString();
//        compassBackground = BitmapFactory.decodeResource(getResources(), themeManager.getCompassBackground());
//        compassNeedle = BitmapFactory.decodeResource(getResources(), themeManager.getCompassNeedle());
    }
//    public void setConstants(TextView bearingNorth, CharSequence bearingNorthString, TextView bearingQibla, CharSequence bearingQiblaString, ThemeManager themeManager) {
//        compassBackground = BitmapFactory.decodeResource(getResources(), themeManager.getCompassBackground());
//    }
    public void setDirections(float directionsNorth, float directionsQibla) {
        this.directionNorth = directionsNorth;
        this.directionQibla = directionsQibla;
        rotateNeedle = new Matrix();
        float degree=(float)QiblaCompass.degree;

        rotateNeedle.postRotate(degree, compassNeedle.getWidth()/2, compassNeedle.getHeight()/2);
        rotateNeedle.postTranslate(centre_x - compassNeedle.getWidth()/2, centre_y - compassNeedle.getHeight()/2);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        bearingNorth.setText(bearingNorthString.replace("(1)", df.format(directionNorth)));
//        bearingQibla.setText(bearingQiblaString.replace("(+/-)", directionQibla >= 0 ? " +" : " -").replace("(2)", df.format(Math.abs(directionQibla))).replace("(3)",  df.format(directionNorth + directionQibla)));

        Paint p = new Paint();
        canvas.rotate(-directionNorth, centre_x, centre_y);
        canvas.drawBitmap(compassBackground, compassBackground.getWidth()/2, compassBackground.getHeight()/2, p);
        canvas.drawBitmap(compassNeedle, rotateNeedle, p);
    }
}
