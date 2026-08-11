package com.oner365.elasticsearch.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jspecify.annotations.NonNull;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import com.oner365.data.commons.util.DateUtil;

/**
 * 从 ES 读取时：String → LocalDateTime
 *
 * @author zhaoyong
 */
@ReadingConverter
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DateUtil.LOCAL_DATE_TIME_FORMAT);

    @Override
    public @NonNull LocalDateTime convert(@NonNull String source) {
        return source == null ? null : LocalDateTime.parse(source, FORMATTER);
    }

}
