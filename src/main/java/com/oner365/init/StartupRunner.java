package com.oner365.init;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.oner365.common.constants.PublicConstants;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ExistsEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.enums.StatusEnum;
import com.oner365.common.enums.StorageEnum;
import com.oner365.monitor.enums.MisfirePolicyEnum;
import com.oner365.monitor.enums.RabbitmqTypeEnum;
import com.oner365.monitor.enums.TaskStatusEnum;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.enums.MessageTypeEnum;
import com.oner365.sys.enums.SysUserSexEnum;
import com.oner365.sys.enums.SysUserTypeEnum;

/**
 * 初始化应用配置
 * 
 * @author zhaoyong
 *
 */
@Component
public class StartupRunner implements ApplicationRunner {
  
  private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

  public static Map<String, String> initEnumMap = new HashMap<>();

  @Override
  public void run(ApplicationArguments args) {
    initEnum();
  }
  
  /**
   * 初始化枚举
   */
  private void initEnum() {
    /* common */
    initEnumMap.put(PublicConstants.PARAM_STATUS, StatusEnum.class.getName());
    initEnumMap.put(PublicConstants.PARAM_FILE_STORAGE, StorageEnum.class.getName());
    initEnumMap.put("errorInfo", ErrorInfoEnum.class.getName());
    initEnumMap.put("exists", ExistsEnum.class.getName());
    initEnumMap.put("result", ResultEnum.class.getName());
    
    /* system */
    initEnumMap.put("messageStatus", MessageStatusEnum.class.getName());
    initEnumMap.put("messageType", MessageTypeEnum.class.getName());
    initEnumMap.put("sex", SysUserSexEnum.class.getName());
    initEnumMap.put("sysUserType", SysUserTypeEnum.class.getName());
    
    /* monitor */
    initEnumMap.put("misfirePolicy", MisfirePolicyEnum.class.getName());
    initEnumMap.put("rabbitmqType", RabbitmqTypeEnum.class.getName());
    initEnumMap.put("taskStatus", TaskStatusEnum.class.getName());
    
    logger.info("Initializing Oner365 Enum map.");
  }

}
