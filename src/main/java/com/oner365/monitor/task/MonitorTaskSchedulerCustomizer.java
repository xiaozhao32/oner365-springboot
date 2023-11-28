package com.oner365.monitor.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.ThreadPoolTaskSchedulerCustomizer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * 定时任务 异常处理
 * 
 * @author zhaoyong
 * 
 */
@Component
public class MonitorTaskSchedulerCustomizer implements ThreadPoolTaskSchedulerCustomizer {
    
    private final Logger logger = LoggerFactory.getLogger(MonitorTaskSchedulerCustomizer.class);

    @Override
    public void customize(ThreadPoolTaskScheduler taskScheduler) {
        taskScheduler.setErrorHandler(new MonitorErrorHandler());
    }
    
    private class MonitorErrorHandler implements ErrorHandler {
        @Override
        public void handleError(Throwable throwable) {
            logger.error("ErrorHandler", throwable);
        }
    }

}
