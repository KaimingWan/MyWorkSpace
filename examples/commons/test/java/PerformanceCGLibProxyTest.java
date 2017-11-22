import org.junit.Test;
import design.PerformanceCGLibProxy;
import vo.Student;

/**
 * @author Wan Kaiming on 2016/12/5
 * @version 1.0
 */
public class PerformanceCGLibProxyTest {

    /**
     * 测试performanceCGLibProxy这个动态代理类是否生效
     */
    @Test
    public void testPerformanceCGLibProxy(){
        PerformanceCGLibProxy performanceCGLibProxy = new PerformanceCGLibProxy();
        Student student = (Student) performanceCGLibProxy.getProxy(Student.class);
        student.sayHello();

    }
}
