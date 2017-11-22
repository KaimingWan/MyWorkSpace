package practice.cn.bn.utils;


import org.joda.time.format.DateTimeFormat;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

/**
 * @description:
 * @version: 1.0
 * 2016/10/26
 */
public class DateConverter implements Converter<String, Date> {
    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Date convert(String source) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(source).toDate();
    }
}
