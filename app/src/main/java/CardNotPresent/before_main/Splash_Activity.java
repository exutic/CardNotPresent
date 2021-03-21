package CardNotPresent.before_main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import CardNotPresent.Main.MainActivity;
import CardNotPresent.R;

public class Splash_Activity extends AppCompatActivity {

    ImageView imgLogo;
    public static String WEB_API= "";

    //    public static AlertDialog alertDialogWaiting;
//    TextView txtMessage;
//    AVLoadingIndicatorView avLoadingIndicatorView;
//    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        WEB_API = "https://bcard.totanservice.com:8096/api/WebApi/";

        findViews();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Splash_Activity.this, MainActivity.class));
                finish();
            }
        }, 3000);

//        createAlert();
    }

    public void findViews()
    {
        imgLogo = findViewById(R.id.actv_splash_img_logo_spin);
        RotateAnimation rotate = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2500);
        rotate.setInterpolator(new LinearInterpolator());
        imgLogo.startAnimation(rotate);
    }

    @Override
    public void onBackPressed() {

    }
}
