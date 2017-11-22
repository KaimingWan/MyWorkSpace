package jdk8;

import vo.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wan Kaiming on 2016/12/15
 * @version 1.0
 */
public class G1Test {

    public static void main(String[] args) throws InterruptedException {

        List<Student> list = new ArrayList<>();



        while(true){
            Thread.currentThread().sleep(1);
            for(int i=0;i<1000;i++){
                list.add(new Student());
            }
        }
    }
}



