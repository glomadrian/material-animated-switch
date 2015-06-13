package com.github.glomadrian.materialanimatedswitch.painter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import com.github.glomadrian.materialanimatedswitch.SwitchInboxPinedState;
import com.github.glomadrian.materialanimatedswitch.Utils;
import com.github.glomadrian.materialanimatedswitch.observer.BallFinishObservable;
import com.github.glomadrian.materialanimatedswitch.observer.BallMoveObservable;

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
  private BallFinishObservable ballFinishObservable;
  private BallMoveObservable ballMoveObservable;

  public BallPainter(int bgColor, int toBgColor, View view, int pading,
      BallFinishObservable ballFinishObservable, BallMoveObservable ballMoveObservable) {
    this.bgColor = bgColor;
    this.view = view;
    this.toBgColor = toBgColor;
    this.pading = pading;
    this.ballFinishObservable = ballFinishObservable;
    this.ballMoveObservable = ballMoveObservable;
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
    colorAnimator.setDuration(100);
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
      ballFinishObservable.setBallState(BallFinishObservable.BallState.MOVE);
    }

    @Override public void onAnimationEnd(Animator animation) {
      if (actualState.equals(SwitchInboxPinedState.PRESS)) {
        ballFinishObservable.setBallState(BallFinishObservable.BallState.PRESS);
      } else {
        ballFinishObservable.setBallState(BallFinishObservable.BallState.RELEASE);
      }
    }

    @Override public void onAnimationCancel(Animator animation) {

    }

    @Override public void onAnimationRepeat(Animator animation) {

    }
  }

  private class BallAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      //TODO
      // La animacion va desde el 0 hasta el ancho
      // para que esto sea generico es necesario hacer una regla de 3 donde se convierta el rango
      // que se esta usando en cada vista a otro rango (0 - 100) que sera el que se use en las
      // animaciones de todas las vistas,

      int value = (int) animation.getAnimatedValue();
      int rangeValue = getAnimatedRange(value);

      ballPositionX = (int) animation.getAnimatedValue();
      ballMoveObservable.setBallPosition(rangeValue);
      colorAnimator.setCurrentPlayTime(rangeValue);
    }

    private int getAnimatedRange(int value) {
      int to = width - pading;
      return ((value * 100) / to);
    }
  }
}
