package x_wolves.ais.SlpashScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;
import x_wolves.ais.Login;
import x_wolves.ais.MainActivity;
import x_wolves.ais.R;

import java.io.File;

public class SplashScreen extends Activity {
    private static final String FILENAME = "jsondata.json";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        getWindow().setStatusBarColor(getResources().getColor(R.color.mainColor2));
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 7500) {
                        sleep(100);
                        waited += 100;
                    }

                    File extFileDir = getExternalFilesDir(null);
                    String path = extFileDir.getAbsolutePath();
                    File file = new File(extFileDir,FILENAME);

                    if (file.exists()){

                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    } else {

//                        Intent intent = new Intent(SplashScreen.this, SignIn.class);
//                        startActivity(intent);
//                        SplashScreen.this.finish();
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        SplashScreen.this.finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }



                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }

}
