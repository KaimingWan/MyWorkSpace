package practice.cn.bn.utils;

import org.joda.time.format.DateTimeFormat;

import java.beans.PropertyEditorSupport;

/**
 * @description:
 * @version: 1.0
 * 2016/10/26
 */
public class DatePropertyEditor extends PropertyEditorSupport {
    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        this.setValue(DateTimeFormat.forPattern(pattern).parseDateTime(text).toDate());
    }
}
