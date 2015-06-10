package com.github.glomadrian.switchinboxpinned.view.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.glomadrian.switchinboxpinned.R;

/**
 * @author Adrián García Lomas
 */
public abstract class IconPainter implements SwitchInboxPinnedPainter {

  protected Bitmap iconBitmap;
  protected Context context;
  protected Paint paint;
  protected int width;
  protected int height;
  protected int imageHeight;
  protected int imageWidth;
  protected boolean isVisible = false;
  protected int lastBallPosition;
  protected int iconXPosition;
  protected int iconYPosition;

  public IconPainter(Context context) {
    this.context = context;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStrokeWidth(5);
    initBitmap();
  }

  protected void initBitmap() {
    //TODO must be from attrs
    iconBitmap =
        BitmapFactory.decodeResource(context.getResources(), R.drawable.tack_save_button_32_blue);
    iconBitmap = Bitmap.createScaledBitmap(iconBitmap, 35, 35, false);

    imageHeight = iconBitmap.getHeight();
    imageWidth = iconBitmap.getWidth();
  }

  @Override public void draw(Canvas canvas) {
    if (isVisible) {
      canvas.drawBitmap(iconBitmap, iconXPosition, iconYPosition, paint);
    }
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
  }
}
