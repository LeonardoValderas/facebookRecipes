package com.valderas.facebookrecipes.libs.base;

/**
 * Created by LEO on 21/6/2016.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}

