package com.oner365.test.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import com.oner365.common.enums.BaseEnum;
import com.oner365.util.ClassesUtil;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class ClassUtils extends BaseUtilsTest {

  @Test
  void test() {
//       BaseEnum.class.;
    Reflections ref = new Reflections("com.oner365.common.enums");
    List<Class<? extends BaseEnum>> childList = ref.getSubTypesOf(BaseEnum.class).stream().collect(Collectors.toList());
    childList.stream().forEach(clazz -> {
      try {
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        fieldList = fieldList.stream().filter(field -> !field.getGenericType().getTypeName().contains("[")).collect(Collectors.toList());
        fieldList.stream().forEach(field -> {
          if(ClassesUtil.isEnum(field.getGenericType().getTypeName())) {
            System.out.println(field.getName());
          }
        });
      } catch (SecurityException e) {
        logger.error("SecurityException error", e);
      }
    });
  }

}
