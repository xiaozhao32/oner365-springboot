
package com.oner365.test.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import com.oner365.data.commons.enums.BaseEnum;
import com.oner365.data.commons.util.ClassesUtil;

/**
 * 工具类测试
 *
 * @author liutao
 *
 */
class ClassUtilsTest extends BaseUtilsTest {

  @Test
  void test() {
//       BaseEnum.class.;
    Reflections ref = new Reflections("com.oner365.data.commons.enums");
    List<Class<? extends BaseEnum>> childList = new ArrayList<>(ref.getSubTypesOf(BaseEnum.class));
    Assertions.assertNotEquals(0, childList.size());
    childList.forEach(clazz -> {
      try {
        List<Field> fieldList = Arrays.asList(clazz.getDeclaredFields());
        fieldList = fieldList.stream().filter(field -> !field.getGenericType().getTypeName().contains("[")).collect(Collectors.toList());
        fieldList.forEach(field -> {
          if(ClassesUtil.isEnum(field.getGenericType().getTypeName())) {
            logger.info("Enum name:{}", field.getName());
          }
        });
      } catch (SecurityException e) {
        logger.error("SecurityException error", e);
      }
    });
  }

}
