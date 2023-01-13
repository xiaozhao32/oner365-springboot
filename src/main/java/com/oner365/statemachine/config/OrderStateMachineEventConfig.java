package com.oner365.statemachine.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.OnTransitionEnd;
import org.springframework.statemachine.annotation.OnTransitionStart;
import org.springframework.statemachine.annotation.WithStateMachine;

import com.oner365.statemachine.entity.Order;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;

/**
 * 事件监听器
 * 
 * @author zhaoyong
 *
 */
@Configuration
@WithStateMachine
public class OrderStateMachineEventConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderStateMachineEventConfig.class);
  
  public static final String HEADER_NAME = "order";

  @OnTransition(target = "UNPAY")
  public void create() {
    LOGGER.info("订单创建");
  }

  @OnTransition(source = "UNPAY", target = "WAIT_RECEIVE")
  public void pay(Message<OrderEventEnum> message) {
    Order order = (Order) message.getHeaders().get(HEADER_NAME);
    order.setOrderState(OrderStateEnum.WAIT_RECEIVE);
    LOGGER.info("用户支付完成: {}", message.getHeaders().toString());
  }

  @OnTransitionStart(source = "UNPAY", target = "WAIT_RECEIVE")
  public void payStart() {
    LOGGER.info("用户支付开始");
  }

  @OnTransitionEnd(source = "UNPAY", target = "WAIT_RECEIVE")
  public void payEnd() {
    LOGGER.info("用户支付结束");
  }

  @OnTransition(source = "WAIT_RECEIVE", target = "FINISHED")
  public void receive(Message<OrderEventEnum> message) {
    Order order = (Order) message.getHeaders().get(HEADER_NAME);
    order.setOrderState(OrderStateEnum.FINISHED);
    LOGGER.info("用户已收货: {}", message.getHeaders().toString());
  }
}
