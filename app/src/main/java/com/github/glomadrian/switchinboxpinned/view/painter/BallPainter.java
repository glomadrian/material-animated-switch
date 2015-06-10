package com.github.glomadrian.switchinboxpinned.view.painter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.github.glomadrian.switchinboxpinned.Utils;
import com.github.glomadrian.switchinboxpinned.view.SwitchInboxPinedState;
import com.github.glomadrian.switchinboxpinned.view.observer.BallFinishObservable;
import com.github.glomadrian.switchinboxpinned.view.observer.BallMoveObservable;

/**
 * @author Adrián García Lomas
 */
public class BallPainter implements SwitchInboxPinnedPainter {

  protected Paint paint;
  protected Paint toBgPainter;
  private int bgColor;
  private int toBgColor;
  protected int pading;
  protected int height;
  protected int width;
  protected int radius;
  protected int ballPositionX;
  private View view;
  private ValueAnimator moveAnimator;
  private ValueAnimator colorAnimator;
  private SwitchInboxPinedState actualState;

  public BallPainter(int bgColor, int toBgColor, View view, int pading) {
    this.bgColor = bgColor;
    this.view = view;
    this.toBgColor = toBgColor;
    this.pading = pading;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setColor(bgColor);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    toBgPainter = new Paint();
    toBgPainter.setColor(toBgColor);
    toBgPainter.setStyle(Paint.Style.FILL);
    toBgPainter.setAntiAlias(true);
    toBgPainter.setAlpha(0);
    radius = Utils.dpToPx(11, view.getResources());
    ballPositionX = radius;
    initColorAnimator();
  }

  private void initAnimator() {
    int from = pading;
    int to = width - pading;
    moveAnimator = ValueAnimator.ofInt(from, to);
    moveAnimator.addUpdateListener(new BallAnimatorListener());
    moveAnimator.addListener(new BallAnimatorFinishListener());
  }

  private void initColorAnimator() {
    colorAnimator = ValueAnimator.ofInt(0, 255);
    colorAnimator.setDuration(100 - 35);
    colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        toBgPainter.setAlpha((Integer) animation.getAnimatedValue());
      }
    });
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX, height / 2, radius, paint);
    canvas.drawCircle(ballPositionX, height / 2, radius, toBgPainter);
  }

  @Override public void setColor(int color) {
    this.bgColor = color;
  }

  @Override public int getColor() {
    return bgColor;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    initAnimator();
  }

  @Override public void setState(SwitchInboxPinedState state) {
    switch (state) {
      case PRESS:
        actualState = SwitchInboxPinedState.PRESS;
        moveAnimator.start();
        break;
      case RELEASE:
        actualState = SwitchInboxPinedState.RELEASE;
        moveAnimator.reverse();
    }
  }

  private class BallAnimatorFinishListener implements ValueAnimator.AnimatorListener {

    @Override public void onAnimationStart(Animator animation) {
      BallFinishObservable.getInstance().setBallState(BallFinishObservable.BallState.MOVE);
    }

    @Override public void onAnimationEnd(Animator animation) {
      if (actualState.equals(SwitchInboxPinedState.PRESS)) {
        BallFinishObservable.getInstance().setBallState(BallFinishObservable.BallState.PRESS);

      } else {
        BallFinishObservable.getInstance().setBallState(BallFinishObservable.BallState.RELEASE);
      }
    }

    @Override public void onAnimationCancel(Animator animation) {

    }

    @Override public void onAnimationRepeat(Animator animation) {

    }
  }

  private class BallAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      int value = (int) animation.getAnimatedValue();
      ballPositionX = (int) animation.getAnimatedValue();
      BallMoveObservable.getInstance().setBallPosition(value);

      //TODO better pls
      int finalValue = value - 35;
      colorAnimator.setCurrentPlayTime(finalValue);
    }
  }
}
