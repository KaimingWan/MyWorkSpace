import org.junit.Test;
import design.PerformanceHandlerJDK;
import vo.Person;
import vo.Student;

import java.lang.reflect.Proxy;

/**
 * Created by Kaiming Wan on 2017/1/13.
 */
public class PerformanceHandlerJDKTest {

    @Test
    public void testJDKProxy(){
        //JDK只能代理接口对象,这里先准备一个将要被代理的对象
        Person person = new Student();
        //Handler中定义了横切逻辑
        PerformanceHandlerJDK handler = new PerformanceHandlerJDK(person);
        //利用被代理对象、横切逻辑handler生成一个代理对象proxy
        Person personProxy = (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                handler);


        //通过代理来调用方法
        personProxy.sayHello();
    }
}
