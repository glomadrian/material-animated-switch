package com.github.glomadrian.materialanimatedswitch.observer;

import java.util.Observable;

/**
 * @author Adrián García Lomas
 */
public class BallMoveObservable extends Observable {

  private int ballPosition;

  public void setBallPosition(int val) {
    this.ballPosition = val;
    setChanged();
    notifyObservers();
  }

  public int getBallPosition() {
    return ballPosition;
  }
}
