package sigit.pertama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{
    protected boolean _active = true;
    protected int _splashTime = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        StartAnimations();//Menjalankan Method Start Animasi
        Thread splashThread = new Thread() {
            //Timer Splash
            public void run() {
                try{
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    Intent newIntent=new Intent(SplashActivity.this, MainActivity.class);//pindah Activity Login
                    startActivity(newIntent);
                    finish();
                }
            }
        };
        splashThread.start();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.imageSplash);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Toast.makeText(getBaseContext(), "Animation Stopped!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
