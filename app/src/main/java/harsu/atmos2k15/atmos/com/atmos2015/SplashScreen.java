package harsu.atmos2k15.atmos.com.atmos2015;

import android.animation.AnimatorSet;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import Svg.AnimatedSvgView;
import app.AppConfig;
import harsu.atmos2k15.atmos.com.atmos2015.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import Svg.AnimatedSvgView;
import harsu.atmos2k15.atmos.com.atmos2015.services.MyInstanceIDListenerService;
import harsu.atmos2k15.atmos.com.atmos2015.services.RegistrationIntentService;
import harsu.atmos2k15.atmos.com.atmos2015.services.ScheduleUpdateService;

public class SplashScreen extends AppCompatActivity {
    private LinearLayout splashLinearLayout;
    private float mInitialLogoOffset;
    private AnimatedSvgView mAnimatedSvgView;
    private ImageView mSubtitleView;
    private AnimatorSet set;
    private Runnable mOnFillStartedCallback;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashLinearLayout = (LinearLayout) findViewById(R.id.linearLayoutSplash);
        mInitialLogoOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,
                getResources().getDisplayMetrics());

        mAnimatedSvgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);
        mSubtitleView = (ImageView) findViewById(R.id.wt_logo_bottom);

        mAnimatedSvgView.setGlyphStrings(LogoPaths.LOGO_GLYPHS);
        mAnimatedSvgView.setFillPaints(
                new int[]{255, 0, 0, 255, 255, 255, 255, 255, 255},
                new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255},
                new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255},
                new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255});
        final int traceColor = Color.argb(255, 51, 172, 224);
        int[] traceColors = new int[9]; // 4 glyphs
        int residueColor = Color.argb(55, 51, 172, 224);
        int[] residueColors = new int[9]; // 4 glyphs

        // Every glyph will have the same trace/residue
        for (int i = 0; i < traceColors.length; i++) {
            traceColors[i] = traceColor;
            residueColors[i] = residueColor;
        }
        mAnimatedSvgView.setTraceColors(traceColors);
        mAnimatedSvgView.setTraceResidueColors(residueColors);

        mAnimatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {


            @Override
            public void onStateChange(int state) {
                if (state == AnimatedSvgView.STATE_FILL_STARTED) {
                    mSubtitleView.setAlpha(0.0f);

                    //mSubtitleView.setVisibility(View.VISIBLE);


                    // mSubtitleView.setTranslationY(-mSubtitleView.getHeight());


                    set = new AnimatorSet();
                    Interpolator interpolator = new OvershootInterpolator();
                    ObjectAnimator a1 = ObjectAnimator.ofFloat(mAnimatedSvgView, View.TRANSLATION_Y, 0);
                    ObjectAnimator a2 = ObjectAnimator.ofFloat(mSubtitleView, View.TRANSLATION_Y, 0);
                    ObjectAnimator a3 = ObjectAnimator.ofFloat(mSubtitleView, View.ALPHA, 1);
                    a1.setInterpolator(interpolator);
                    a2.setInterpolator(interpolator);
                    set.setDuration(500).playTogether(a1, a2, a3);
                    set.start();


                    if (mOnFillStartedCallback != null) {
                        mOnFillStartedCallback.run();

                    }

                } else if (state == AnimatedSvgView.STATE_FINISHED) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



        reset();

        createToken();
    }

    public void reset() {
        mAnimatedSvgView.reset();
        mAnimatedSvgView.setTranslationY(mInitialLogoOffset);
        mSubtitleView.setVisibility(View.INVISIBLE);
    }

    private void animateLogo() {
        mAnimatedSvgView.start();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                animateLogo();
            }
        }, 1000);
    }
    private void createToken() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!sharedPreferences.getBoolean(AppConfig.SENT_TOKEN_TO_SERVER, false)) {
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        } else {
            Intent intent = new Intent(this, MyInstanceIDListenerService.class);
            startService(intent);
        }
    }



}
