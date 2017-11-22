package client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import vo.Student;

/**
 * @author Wan Kaiming on 2016/11/21
 * @version 1.0
 */


public class AnnotationClient {


    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:beans.xml");
        Student student = (Student) context.getBean("student");
        student.sayHello();
    }

}
