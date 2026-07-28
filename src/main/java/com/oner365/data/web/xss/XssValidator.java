package com.oner365.data.web.xss;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oner365.data.commons.util.DataUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 自定义xss校验注解实现
 *
 * @author zhaoyong
 */
public class XssValidator implements ConstraintValidator<Xss, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(XssValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !containsHtml(value);
    }

    /**
     * 检测是否包含 HTML（使用 Jsoup）
     * @param value HTML标签
     * @return 是否包含
     */
    public static boolean containsHtml(String value) {
        if (DataUtils.isEmpty(value)) {
            return true;
        }
        try {
            String cleaned = Jsoup.clean(value, Safelist.none());
            return !cleaned.equals(value);
        }
        catch (Exception e) {
            LOGGER.error("containsHtml error", e);
            return value.contains("<") && value.contains(">");
        }
    }

    /**
     * 移除 HTML 标签
     * @param value HTML标签
     * @return String
     */
    public static String removeHtml(String value) {
        if (DataUtils.isEmpty(value)) {
            return value;
        }

        try {
            return Jsoup.clean(value, Safelist.none());
        }
        catch (Exception e) {
            LOGGER.error("removeHtml error", e);
            return value.replaceAll("<[^>]*>", "");
        }
    }

}
