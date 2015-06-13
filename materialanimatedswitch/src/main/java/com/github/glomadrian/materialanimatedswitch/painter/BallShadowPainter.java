package com.github.glomadrian.materialanimatedswitch.painter;

import android.graphics.Canvas;
import android.view.View;
import com.github.glomadrian.materialanimatedswitch.Utils;
import com.github.glomadrian.materialanimatedswitch.observer.BallFinishObservable;
import com.github.glomadrian.materialanimatedswitch.observer.BallMoveObservable;

/**
 * @author Adrián García Lomas
 */
public class BallShadowPainter extends BallPainter {

  int shadowMovement;

  public BallShadowPainter(int bgColor, int toBgColor, View view, int pading, int shadowColor,
      BallFinishObservable ballFinishObservable, BallMoveObservable ballMoveObservable) {
    super(bgColor, toBgColor, view, pading, ballFinishObservable, ballMoveObservable);
    shadowMovement = Utils.dpToPx(2, view.getResources());
    paint.setColor(shadowColor);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX + 2, (height / 2) + 2, radius, paint);
  }
}
