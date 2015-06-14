package com.github.glomadrian.materialanimatedswitch.painter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.github.glomadrian.materialanimatedswitch.SwitchInboxPinedState;
import com.github.glomadrian.materialanimatedswitch.observer.BallFinishObservable;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
public class IconReleasePainter extends IconPainter {

  private ValueAnimator enterXAnimatior;
  private ValueAnimator enterYAnimatior;
  private ValueAnimator exitXAnimatior;
  private ValueAnimator exitYAnimatior;
  private ValueAnimator alphaAnimator;
  private int initY = -100;
  private int initX;
  private BallFinishObservable ballFinishObservable;

  public IconReleasePainter(Context context, Bitmap bitmap,
      BallFinishObservable ballFinishObservable, int margin) {
    super(context, bitmap, margin);
    initValueAnimator();
    this.ballFinishObservable = ballFinishObservable;
    initObserver();
  }

  private void initValueAnimator() {
    enterXAnimatior = ValueAnimator.ofInt(0, initX);
    enterXAnimatior.setDuration(400);
    enterXAnimatior.setInterpolator(new AccelerateDecelerateInterpolator());
    enterXAnimatior.addUpdateListener(new EnterXAnimationListener());

    exitXAnimatior = ValueAnimator.ofInt(-50, initX);
    exitXAnimatior.setDuration(400);
    exitXAnimatior.setInterpolator(new AccelerateDecelerateInterpolator());
    exitXAnimatior.addUpdateListener(new EnterXAnimationListener());

    enterYAnimatior = ValueAnimator.ofInt(initX, 0);
    enterYAnimatior.setDuration(400);
    enterYAnimatior.setInterpolator(new AccelerateDecelerateInterpolator());
    enterYAnimatior.addUpdateListener(new EnterYAnimationListener());

    exitYAnimatior = ValueAnimator.ofInt(0, 100);
    exitYAnimatior.setDuration(400);
    exitYAnimatior.setInterpolator(new AccelerateDecelerateInterpolator());
    exitYAnimatior.addUpdateListener(new EnterYAnimationListener());

    alphaAnimator = ValueAnimator.ofInt(0, 255);
    alphaAnimator.setDuration(200);
    alphaAnimator.addUpdateListener(new AlphaAnimatorListener());
  }

  @Override public void setColor(int color) {

  }

  @Override public int getColor() {
    return 0;
  }

  @Override public void setState(SwitchInboxPinedState state) {
    switch (state) {
      case INIT:
        isVisible = true;
        break;
      case PRESS:
        exitYAnimatior.start();
        exitXAnimatior.reverse();
        alphaAnimator.reverse();

        break;
      case RELEASE:
        isVisible = true;
        alphaAnimator.start();
        enterXAnimatior.reverse();
        enterYAnimatior.start();
        break;
    }
  }

  private void initObserver() {
    ballFinishObservable.addObserver(new BallFinishListener());
  }

  private class BallFinishListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallFinishObservable.BallState ballState = ((BallFinishObservable) observable).getState();
      switch (ballState) {
        case PRESS:
          isVisible = false;
          break;
      }
    }
  }

  @Override public void onSizeChanged(int height, int width) {
    super.onSizeChanged(height, width);
    initX = width;
    iconYPosition = height / 2 - (imageHeight / 2);
    iconXPosition = margin - imageWidth / 2;
    enterXAnimatior.setIntValues(0, initX);
    enterYAnimatior.setIntValues(initY, height / 2);
    exitYAnimatior.setIntValues(height / 2, 100);
  }

  private class EnterXAnimationListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconXPosition = ((int) animation.getAnimatedValue()) - imageWidth / 2 + margin;
    }
  }

  private class EnterYAnimationListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = ((int) animation.getAnimatedValue() - imageWidth / 2);
    }
  }

  private class AlphaAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      paint.setAlpha((Integer) animation.getAnimatedValue());
    }
  }
}
