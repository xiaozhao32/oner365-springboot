package com.oner365.statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;
import com.oner365.statemachine.config.OrderStateMachineEventConfig;
import com.oner365.statemachine.entity.Order;
import com.oner365.statemachine.enums.OrderEventEnum;
import com.oner365.statemachine.enums.OrderStateEnum;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 订单状态机
 * 
 * @author zhaoyong
 *
 */
@RestController
@Api(tags = "订单状态机")
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Autowired
  private StateMachine<OrderStateEnum, OrderEventEnum> stateMachine;

  /**
   * 订单状态机测试
   * 
   * @return String
   */
  @ApiOperation("1.测试")
  @ApiOperationSupport(order = 1)
  @GetMapping("/test")
  public String index(Integer orderId) {
    if (orderId == null) {
      return "订单不能为空";
    }
    logger.info("--- 开始订单流程 ---");
    stateMachine.start();

    // 创建订单对象
    Order order = new Order();
    order.setOrderState(OrderStateEnum.UNPAY);
    order.setId(orderId);

    logger.info("--- 发送支付事件 ---");
    Message<OrderEventEnum> message = MessageBuilder.withPayload(OrderEventEnum.PAY)
        .setHeader(OrderStateMachineEventConfig.HEADER_NAME, order).build();
    stateMachine.sendEvent(message);

    logger.info("--- 发送收货事件 ---");
    message = MessageBuilder.withPayload(OrderEventEnum.RECEIVE)
        .setHeader(OrderStateMachineEventConfig.HEADER_NAME, order).build();
    stateMachine.sendEvent(message);

    return ResultEnum.SUCCESS.getName();
  }
}
