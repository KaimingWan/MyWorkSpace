package annotations;

import java.lang.annotation.*;

/**
 * @author Wan Kaiming on 2016/11/16
 * @version 1.0
 */

//注解作用目标是方法

@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StartMonitor {
    //注解有个默认成员name，默认值为类名称
    //除了name，还可以定义自己所需要的注解属性，都可以在运行时通过反射取得


    //定义注解的成员

}
