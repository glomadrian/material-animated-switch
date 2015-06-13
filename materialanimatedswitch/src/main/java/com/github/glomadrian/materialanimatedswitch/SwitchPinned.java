package com.github.glomadrian.materialanimatedswitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.github.glomadrian.materialanimatedswitch.observer.BallFinishObservable;
import com.github.glomadrian.materialanimatedswitch.observer.BallMoveObservable;
import com.github.glomadrian.materialanimatedswitch.painter.BallPainter;
import com.github.glomadrian.materialanimatedswitch.painter.BallShadowPainter;
import com.github.glomadrian.materialanimatedswitch.painter.BasePainter;
import com.github.glomadrian.materialanimatedswitch.painter.IconPressPainter;
import com.github.glomadrian.materialanimatedswitch.painter.IconReleasePainter;

/**
 * @author Adrián García Lomas
 */
public class SwitchPinned extends View {

  private int pading;
  private BasePainter basePainter;
  private BallPainter ballPainter;
  private BallShadowPainter ballShadowPainter;
  private SwitchInboxPinedState actualState;
  private IconPressPainter iconPressPainter;
  private IconReleasePainter iconReleasePainter;
  private int baseColorRelease = Color.parseColor("#3061BE");
  private int baseColorPress = Color.parseColor("#D7E7FF");
  private int ballColorRelease = Color.parseColor("#5992FB");
  private int ballColorPress = Color.parseColor("#FFFFFF");
  private int ballShadowColor = Color.parseColor("#99000000");
  private Bitmap releaseIcon;
  private Bitmap pressIcon;
  private BallFinishObservable ballFinishObservable;
  private BallMoveObservable ballMoveObservable;

  public SwitchPinned(Context context) {
    super(context);
    init();
  }

  public SwitchPinned(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public SwitchPinned(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  private void init() {
    pading = Utils.dpToPx(11, getResources());
    initObservables();
    initPainters();
    actualState = SwitchInboxPinedState.INIT;
    setState(actualState);
    setLayerType(View.LAYER_TYPE_SOFTWARE, null); // cheese
  }

  private void initPainters() {
    basePainter = new BasePainter(baseColorRelease, baseColorPress, pading, ballMoveObservable);
    ballPainter =
        new BallPainter(ballColorRelease, ballColorPress, this, pading, ballFinishObservable,
            ballMoveObservable);
    ballShadowPainter =
        new BallShadowPainter(ballShadowColor, ballShadowColor, this, pading, ballShadowColor,
            ballFinishObservable, ballMoveObservable);
    iconPressPainter =
        new IconPressPainter(getContext(), pressIcon, ballFinishObservable, ballMoveObservable);
    iconReleasePainter = new IconReleasePainter(getContext(), releaseIcon, ballFinishObservable);
  }

  private void init(AttributeSet attrs) {
    TypedArray attributes =
        getContext().obtainStyledAttributes(attrs, R.styleable.materialAnimatedSwitch);
    initAttributes(attributes);
    init();
  }

  private void initAttributes(TypedArray attributes) {
    baseColorRelease = attributes.getColor(R.styleable.materialAnimatedSwitch_base_release_color,
        baseColorRelease);
    baseColorPress =
        attributes.getColor(R.styleable.materialAnimatedSwitch_base_press_color, baseColorPress);
    ballColorRelease = attributes.getColor(R.styleable.materialAnimatedSwitch_ball_release_color,
        ballColorRelease);
    ballColorPress =
        attributes.getColor(R.styleable.materialAnimatedSwitch_ball_press_color, ballColorPress);
    pressIcon = BitmapFactory.decodeResource(getResources(),
        attributes.getResourceId(R.styleable.materialAnimatedSwitch_icon_press,
            R.drawable.tack_save_button_32_blue));
    releaseIcon = BitmapFactory.decodeResource(getResources(),
        attributes.getResourceId(R.styleable.materialAnimatedSwitch_icon_release,
            R.drawable.tack_save_button_32_white));
  }

  private void initObservables() {
    ballFinishObservable = new BallFinishObservable();
    ballMoveObservable = new BallMoveObservable();
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
