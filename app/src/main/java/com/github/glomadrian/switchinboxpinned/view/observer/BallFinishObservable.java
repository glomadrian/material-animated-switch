package com.github.glomadrian.switchinboxpinned.view.observer;

import java.util.Observable;

/**
 * @author Adrián García Lomas
 */
public class BallFinishObservable extends Observable {

  private static BallFinishObservable ballFinishObservable;
  private BallState state;

  public static BallFinishObservable getInstance() {
    if (ballFinishObservable == null) {
      ballFinishObservable = new BallFinishObservable();
    }
    return ballFinishObservable;
  }

  public void setBallState(BallState state) {
    this.state = state;
    setChanged();
    notifyObservers();
  }

  public BallState getState() {
    return state;
  }

  public static enum BallState {
    RELEASE, PRESS, MOVE
  }
}
