package com.oner365.elasticsearch.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Nonnull;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import com.oner365.data.commons.util.DateUtil;

/**
 * 写入 ES 时：LocalDateTime → String
 * 
 * @author zhaoyong
 */
@WritingConverter
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateUtil.LOCAL_DATE_TIME_FORMAT);

    @Override
    public @Nonnull String convert(@Nonnull LocalDateTime source) {
        return source == null ? null : source.format(FORMATTER);
    }
}
