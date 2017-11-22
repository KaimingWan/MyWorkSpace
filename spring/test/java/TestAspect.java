import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vo.Student;

/**
 * 测试下kami-tools-spring这个构件里面的@StartMonitor注解是否生效
 * @author Wan Kaiming on 2016/12/5
 * @version 1.0
 */
public class TestAspect {

    @Test
    public void MethodTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        Student student = (Student) context.getBean("student");
        student.sayHello();
    }
}
