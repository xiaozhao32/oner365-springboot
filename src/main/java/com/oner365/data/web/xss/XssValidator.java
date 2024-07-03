package com.oner365.data.web.xss;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oner365.data.commons.util.DataUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义xss校验注解实现
 *
 * @author zhaoyong
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

  private static final String HTML_PATTERN = "<(\\S*?)[^>]*>.*?|<.*? />";

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (DataUtils.isEmpty(value)) {
      return true;
    }
    return !containsHtml(value);
  }

  /**
   * containsHtml
   *
   * @param value html value
   * @return 是否包含
   */
  public static boolean containsHtml(String value) {
    Pattern pattern = Pattern.compile(HTML_PATTERN);
    Matcher matcher = pattern.matcher(value);
    return matcher.matches();
  }
}
