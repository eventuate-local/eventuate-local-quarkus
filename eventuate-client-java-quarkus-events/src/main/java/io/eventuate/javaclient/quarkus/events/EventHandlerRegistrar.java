package io.eventuate.javaclient.quarkus.events;

import io.eventuate.Subscriber;
import io.eventuate.javaclient.eventdispatcher.EventDispatcherInitializer;
import io.quarkus.runtime.Startup;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;

@Startup
@Singleton
public class EventHandlerRegistrar {

  @Inject
  EventDispatcherInitializer eventDispatcherInitializer;

  @Inject
  Instance<Subscriber> subscribers;

  @PostConstruct
  public void registerEventHandlers() throws BeansException {
    subscribers.stream().forEach(subscriber -> {
      Class<?> actualClass = AopUtils.getTargetClass(subscriber);
      String name = subscriber.getClass().getSimpleName();
      name = String.valueOf(name.charAt(0)).toLowerCase() + name.substring(1);
      eventDispatcherInitializer.registerEventHandler(subscriber, name, actualClass);
    });
  }
}
