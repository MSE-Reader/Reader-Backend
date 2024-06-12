package com.Capstone.Project.global.config.jackson;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class JacksonDateTimeFormatter implements JacksonFormatConfigurer {

    public static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String TIME_FORMAT_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    public static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN);
    public static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN);
    @Override
    public void configure(Jackson2ObjectMapperBuilder builder) {
        builder.simpleDateFormat(DATE_TIME_FORMAT_PATTERN);

        builder.serializers(new LocalDateSerializer(DATE_FORMAT));
        builder.serializers(new LocalTimeSerializer(TIME_FORMAT));
        builder.serializers(new LocalDateTimeSerializer(DATE_TIME_FORMAT));

        builder.deserializers(new LocalDateDeserializer(DATE_FORMAT));
        builder.deserializers(new LocalTimeDeserializer(TIME_FORMAT));
        builder.deserializers(new LocalDateTimeDeserializer(DATE_TIME_FORMAT));
    }
}
