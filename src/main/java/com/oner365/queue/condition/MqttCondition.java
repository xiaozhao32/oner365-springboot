package com.oner365.queue.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.QueueEnum;

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
    String type = environment.getProperty(PublicConstants.QUEUE_TYPE);
    // 是否开启
    return type != null && type.equalsIgnoreCase(QueueEnum.MQTT.name());
  }

}
