package com.oner365.api.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.oner365.api.dto.Message;
import com.oner365.api.service.SseService;
import com.oner365.util.DateUtil;

import jakarta.annotation.Resource;

/**
 * Message Task
 * 
 * @author zhaoyong
 * 
 */
@Configuration
public class SendMessageTask {

  @Resource
  private SseService sseService;

  /**
   * 定时执行 秒 分 时 日 月 周
   */
  @Scheduled(cron = "*/5 * * * * *")
  public void sendMessageTask() {
    Message message = new Message();
    message.setData(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
    sseService.sendMessage(message);
  }
}
