package anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.DecelerateInterpolator;


/**
 * Created by harsu on 6/23/2015.
 */
public class AnimationUtils {

    static int counter = 0;

    public static void animate(RecyclerView.ViewHolder holder, boolean goingDown) {

        //TODO Fly in
        /*AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator animTranslateY= ObjectAnimator.ofFloat(holder.itemView, "translationY", goingDown?600:-600, 0);
        ObjectAnimator scaleX=ObjectAnimator.ofFloat(holder.itemView,"scaleX",0.5F,1.2F,0.7F,1.1F,0.8F,1.1F,1.0F);
        animTranslateY.setDuration(700);
        scaleX.setDuration(1000);
        animatorSet.playTogether(animTranslateY,scaleX);

        animatorSet.start();*/

        //SunBlind
        int holderHeight = holder.itemView.getHeight();
        holder.itemView.setPivotY(goingDown == true ? 0 : holderHeight);
        holder.itemView.setPivotX(holder.itemView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goingDown == true ? 300 : -300, 0);
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(holder.itemView, "rotationX", goingDown == true ? -90f : 90, 0f);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 1f);
        animatorSet.playTogether(animatorTranslateY, animatorRotation, animatorScaleX);
        animatorSet.setInterpolator(new DecelerateInterpolator(1.1f));
        animatorSet.setDuration(800);
        animatorSet.start();

        //scatter view

        /*counter=++counter%4;
        int holderHeight = holder.itemView.getHeight();
        int holderWidth= holder.itemView.getWidth();
        View holderItemView=holder.itemView;
        holderItemView.setPivotY(goingDown == true ? 0 : holderHeight);
        holderItemView.setPivotX(holderWidth/2);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holderItemView, "translationY", goingDown == true ? 300 : -300, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holderItemView,"translationX",counter==1||counter==3?holderWidth:-holderWidth,0);
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holderItemView,"scaleX",counter==1||counter==2?0:2,1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holderItemView,"scaleY",counter==1||counter==2?0:2,1f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(holderItemView,"alpha",0f,1f);
        animatorAlpha.setInterpolator(new AccelerateInterpolator(1.5f));
        animatorSet.playTogether(animatorAlpha, animatorScaleX, animatorScaleY, animatorTranslateX, animatorTranslateY);
        animatorSet.setDuration(1000).setInterpolator(new DecelerateInterpolator(1.1f));
        animatorSet.start();*/
    }
}
