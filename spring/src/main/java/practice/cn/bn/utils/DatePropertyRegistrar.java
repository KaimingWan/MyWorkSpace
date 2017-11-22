package practice.cn.bn.utils;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @version: 1.0
 * 2016/10/26
 */
public class DatePropertyRegistrar implements PropertyEditorRegistrar {
    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void registerCustomEditors(PropertyEditorRegistry registry) {
//        1.自定义
//        DatePropertyEditor datePropertyEditor = new DatePropertyEditor();
//        datePropertyEditor.setPattern(pattern);
//        registry.registerCustomEditor(Date.class, datePropertyEditor);
//        2.使用spring自带的
        registry.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), false));
    }
}
