package com.github.glomadrian.switchinboxpinned.view.observer;

import java.util.Observable;

/**
 * @author Adrián García Lomas
 */
public class BallMoveObservable extends Observable {

  private static BallMoveObservable ballMoveObservable;
  private int ballPosition;

  public static BallMoveObservable getInstance() {
    if (ballMoveObservable == null) {
      ballMoveObservable = new BallMoveObservable();
    }
    return ballMoveObservable;
  }

  public void setBallPosition(int val) {
    this.ballPosition = val;
    setChanged();
    notifyObservers();
  }

  public int getBallPosition() {
    return ballPosition;
  }
}
