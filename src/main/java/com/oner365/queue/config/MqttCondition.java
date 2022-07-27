package com.oner365.queue.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

/**
 * MQTT Condition
 * 
 * @author zhaoyong
 *
 */
public class MqttCondition implements Condition {
  
  @Override
  public boolean matches(ConditionContext conditionContext, @NonNull AnnotatedTypeMetadata metadata) {
    Environment environment = conditionContext.getEnvironment();
    String type = environment.getProperty("mqtt.enabled");
    // 是否开启
    return type != null && type.equalsIgnoreCase(Boolean.TRUE.toString());
  }

}
