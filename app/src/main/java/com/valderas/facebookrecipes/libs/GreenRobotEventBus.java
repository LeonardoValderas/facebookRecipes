package com.valderas.facebookrecipes.libs;

import com.valderas.facebookrecipes.libs.base.EventBus;

/**
 * Created by LEO on 21/6/2016.
 */
public class GreenRobotEventBus implements EventBus {
    org.greenrobot.eventbus.EventBus eventBus;

    public GreenRobotEventBus() {
        eventBus = org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Override
    public void register(Object subscriber) {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    @Override
    public void post(Object event) {
        eventBus.post(event);
    }
}
