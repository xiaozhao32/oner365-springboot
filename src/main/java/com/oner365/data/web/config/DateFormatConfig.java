package com.oner365.data.web.config;

import java.text.DateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

import com.oner365.data.commons.jackson.JavaTimeModule;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper.Builder;

/**
 * 日期格式化
 *
 * @author zhaoyong
 *
 */
@Configuration
@ConditionalOnClass({ ObjectMapper.class })
@AutoConfigureBefore({ JacksonAutoConfiguration.class })
public class DateFormatConfig implements JsonMapperBuilderCustomizer {

    @Override
    public void customize(@NonNull Builder builder) {
        builder.defaultLocale(Locale.CHINA);
        builder.defaultTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
        builder.defaultDateFormat(DateFormat.getDateTimeInstance());
        builder.addModule(new JavaTimeModule());
    }

}
