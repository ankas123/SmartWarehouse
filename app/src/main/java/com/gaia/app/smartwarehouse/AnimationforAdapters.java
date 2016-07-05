package com.gaia.app.smartwarehouse;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by praveen_gadi on 7/2/2016.
 */
public class AnimationforAdapters {


    public static void animate(RecyclerView.ViewHolder holder, boolean direction)
    {
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator animator_Y=ObjectAnimator.ofFloat(holder.itemView,"translationY",direction==true?40f:-40f,0);
        animator_Y.setDuration(2000);

        ObjectAnimator animator_X=ObjectAnimator.ofFloat(holder.itemView,"translationX",-30f,30f,-20f,20f,-10f,10f,0);
        animator_X.setDuration(2000);
        //animatorSet.playSequentially(animator_X,animator_Y);
        animatorSet.playTogether(animator_X,animator_Y);
        animatorSet.start();

        Log.e("animation","animation running");
    }
}
