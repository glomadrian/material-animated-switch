package com.github.glomadrian.materialanimatedswitch.painter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.github.glomadrian.materialanimatedswitch.Utils;

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

  public IconPainter(Context context, Bitmap bitmap) {
    this.context = context;
    this.iconBitmap = bitmap;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setAntiAlias(true);
    paint.setStrokeWidth(5);
    initBitmap();
  }

  protected void initBitmap() {
    int iconSize = Utils.dpToPx(11, context.getResources());
    iconBitmap = Bitmap.createScaledBitmap(iconBitmap, iconSize, iconSize, false);
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
