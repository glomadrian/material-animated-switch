package com.github.glomadrian.materialanimatedswitch.painter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.github.glomadrian.materialanimatedswitch.R;
import com.github.glomadrian.materialanimatedswitch.SwitchInboxPinedState;
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
  protected int padding;
  protected int height;
  protected int width;
  protected int radius;
  protected int ballPositionX;
  protected int ballMovementRange;
  private View view;
  private ValueAnimator moveAnimator;
  private ValueAnimator colorAnimator;
  private SwitchInboxPinedState actualState;
  private BallFinishObservable ballFinishObservable;
  private BallMoveObservable ballMoveObservable;
  private Context context;
  private int middle;

  public BallPainter(int bgColor, int toBgColor, View view, int pading,
      BallFinishObservable ballFinishObservable, BallMoveObservable ballMoveObservable,
      Context context) {
    this.bgColor = bgColor;
    this.view = view;
    this.toBgColor = toBgColor;
    this.padding = pading;
    this.ballFinishObservable = ballFinishObservable;
    this.ballMoveObservable = ballMoveObservable;
    this.context = context;
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
    radius = (int) context.getResources().getDimension(R.dimen.ball_radius);
    ballPositionX = radius;

  }

  private void initAnimator() {
    int from = padding;
    int to = width - padding;
    ballMovementRange = to - from;
    moveAnimator = ValueAnimator.ofInt(from, to);
    moveAnimator.addUpdateListener(new BallAnimatorListener());
    moveAnimator.addListener(new BallAnimatorFinishListener());
  }

  private void initColorAnimator() {
    colorAnimator = ValueAnimator.ofInt(0, 255);
    colorAnimator.setDuration(ballMovementRange);
    colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        toBgPainter.setAlpha((Integer) animation.getAnimatedValue());
      }
    });
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX, middle, radius, paint);
    canvas.drawCircle(ballPositionX, middle, radius, toBgPainter);
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
    middle = height / 2;
    initAnimator();
    initColorAnimator();
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
      //Empty
    }

    @Override public void onAnimationRepeat(Animator animation) {
      //Empty
    }
  }

  private class BallAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      int value = (int) animation.getAnimatedValue();
      //Move the ball
      ballPositionX = value;
      //1- Get pixel of movement from 0 to movementRange
      int pixelMove = value - padding;
      //Transform the range movement to a 0 - 100 range
      int rangeValue = getAnimatedRange(pixelMove);
      //Change the color animation to the actual range value (duration is 100)
      colorAnimator.setCurrentPlayTime(rangeValue);
      //Set ball position to
      ballMoveObservable.setBallPosition(ballPositionX);
      //Put this value on a observable the listeners know the state of the movement in a range of 0
      //to 100
      ballMoveObservable.setBallAnimationValue(rangeValue);
    }

    private int getAnimatedRange(int value) {
      return ((value * 100) / ballMovementRange);
    }
  }
}
