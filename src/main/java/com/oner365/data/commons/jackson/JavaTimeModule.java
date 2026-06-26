package com.oner365.data.commons.jackson;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.oner365.data.commons.util.DataUtils;
import com.oner365.data.commons.util.DateUtil;

import tools.jackson.core.JsonGenerator;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.ValueSerializer;
import tools.jackson.databind.cfg.PackageVersion;
import tools.jackson.databind.ext.javatime.deser.LocalDateDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalDateTimeDeserializer;
import tools.jackson.databind.ext.javatime.deser.LocalTimeDeserializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalDateTimeSerializer;
import tools.jackson.databind.ext.javatime.ser.LocalTimeSerializer;
import tools.jackson.databind.module.SimpleModule;

/**
 * JavaTimeModule
 *
 * @author zhaoyong
 *
 */
public class JavaTimeModule extends SimpleModule {

    private static final long serialVersionUID = 1L;

    public JavaTimeModule() {
        super(PackageVersion.VERSION);

        addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
        addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
        addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
        addSerializer(Instant.class,
                new InstantCustomSerializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));

        addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.FULL_TIME_FORMAT)));
        addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateUtil.FULL_DATE_FORMAT)));
        addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateUtil.LONG_TIME_FORMAT)));
        addDeserializer(Instant.class, new InstantCustomDeserializer());
    }

    static class InstantCustomSerializer extends ValueSerializer<Instant> {

        private final DateTimeFormatter format;

        private InstantCustomSerializer(DateTimeFormatter formatter) {
            this.format = formatter;
        }

        @Override
        public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializationContext serializerProvider) {
            if (instant != null) {
                String jsonValue = format.format(instant.atZone(ZoneId.systemDefault()));
                jsonGenerator.writeString(jsonValue);
            }
        }

    }

    static class InstantCustomDeserializer extends ValueDeserializer<Instant> {

        @Override
        public Instant deserialize(JsonParser parser, DeserializationContext context) {
            String dateString = parser.getString().trim();
            if (DataUtils.isEmpty(dateString)) {
                Date pareDate = DateUtil.stringToDate(dateString, DateUtil.FULL_TIME_FORMAT);
                if (pareDate != null) {
                    return pareDate.toInstant();
                }
            }
            return null;
        }

    }

}
