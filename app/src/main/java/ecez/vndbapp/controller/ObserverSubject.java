package ecez.vndbapp.controller;

/**
 * Created by johnteng on 2017-09-26.
 */

public interface ObserverSubject {
    void registerObserver(CustomObserver customObserver);
    void removeObserver(CustomObserver customObserver);
    void notifyObservers();
}

