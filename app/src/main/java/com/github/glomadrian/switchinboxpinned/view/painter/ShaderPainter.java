package com.github.glomadrian.switchinboxpinned.view.painter;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import com.github.glomadrian.switchinboxpinned.view.SwitchInboxPinedState;

/**
 * @author Adrián García Lomas
 */
public class ShaderPainter implements SwitchInboxPinnedPainter {

  private Bitmap transparentBitmap;
  private Shader shader;
  private Paint paint;
  private int height;
  private int width;

  public ShaderPainter() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStrokeWidth(20);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(0, height / 2, width, height / 2, paint);
  }

  @Override public void setColor(int color) {

  }

  @Override public int getColor() {
    return 0;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;

    transparentBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(transparentBitmap);
    canvas.drawColor(Color.parseColor("#000000"));
    shader = new BitmapShader(transparentBitmap, BitmapShader.TileMode.CLAMP,
        BitmapShader.TileMode.CLAMP);
    paint.setShader(shader);
  }

  @Override public void setState(SwitchInboxPinedState state) {

  }
}
