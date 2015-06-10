package com.github.glomadrian.switchinboxpinned.view.painter;

import android.graphics.Canvas;
import android.view.View;
import com.github.glomadrian.switchinboxpinned.Utils;

/**
 * @author Adrián García Lomas
 */
public class BallShadowPainter extends BallPainter {

  int shadowMovement;

  public BallShadowPainter(int bgColor, int toBgColor, View view, int pading, int shadowColor) {
    super(bgColor, toBgColor, view, pading);
    shadowMovement = Utils.dpToPx(2, view.getResources());
    paint.setColor(shadowColor);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX + 2, (height / 2) + 2, radius, paint);
  }
}
