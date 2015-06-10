package com.github.glomadrian.switchinboxpinned;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.github.glomadrian.switchinboxpinned.view.SwitchInboxPinedState;
import com.github.glomadrian.switchinboxpinned.view.painter.BallPainter;
import com.github.glomadrian.switchinboxpinned.view.painter.BallShadowPainter;
import com.github.glomadrian.switchinboxpinned.view.painter.BasePainter;
import com.github.glomadrian.switchinboxpinned.view.painter.IconPressPainter;
import com.github.glomadrian.switchinboxpinned.view.painter.IconReleasePainter;

/**
 * @author Adrián García Lomas
 */
public class SwitchPinned extends View {

  private int pading = 35;
  private BasePainter basePainter;
  private BallPainter ballPainter;
  private BallShadowPainter ballShadowPainter;
  private SwitchInboxPinedState actualState;
  private IconPressPainter iconPressPainter;
  private IconReleasePainter iconReleasePainter;

  public SwitchPinned(Context context) {
    super(context);
    init();
  }

  public SwitchPinned(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SwitchPinned(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    basePainter = new BasePainter(Color.parseColor("#3061BE"), Color.parseColor("#D7E7FF"), pading);
    ballPainter =
        new BallPainter(Color.parseColor("#5992FB"), Color.parseColor("#FFFFFF"), this, pading);
    ballShadowPainter =
        new BallShadowPainter(Color.parseColor("#000000"), Color.parseColor("#000000"), this,
            pading, Color.parseColor("#99000000"));
    iconPressPainter = new IconPressPainter(getContext());
    iconReleasePainter = new IconReleasePainter(getContext());
    actualState = SwitchInboxPinedState.INIT;
    setState(actualState);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = Utils.dpToPx(45, getResources());
    int height = Utils.dpToPx(28, getResources());
    setMeasuredDimension(width, height);
    basePainter.onSizeChanged(height, width);
    ballShadowPainter.onSizeChanged(height, width);
    ballPainter.onSizeChanged(height, width);
    iconPressPainter.onSizeChanged(height, width);
    iconReleasePainter.onSizeChanged(height, width);

  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    basePainter.draw(canvas);
    ballShadowPainter.draw(canvas);
    ballPainter.draw(canvas);
    iconPressPainter.draw(canvas);
    iconReleasePainter.draw(canvas);
    invalidate();
  }

  private void setState(SwitchInboxPinedState switchInboxPinedState) {
    basePainter.setState(switchInboxPinedState);
    ballPainter.setState(switchInboxPinedState);
    ballShadowPainter.setState(switchInboxPinedState);
    iconPressPainter.setState(switchInboxPinedState);
    iconReleasePainter.setState(switchInboxPinedState);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    super.onTouchEvent(event);

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        doActionDown();
        return true;
      default:
        return false;
    }
  }

  private void doActionDown() {
    if (actualState.equals(SwitchInboxPinedState.RELEASE) || actualState.equals(
        SwitchInboxPinedState.INIT)) {
      actualState = SwitchInboxPinedState.PRESS;
      setState(actualState);
    } else {
      actualState = SwitchInboxPinedState.RELEASE;
      setState(actualState);
    }
  }
}
