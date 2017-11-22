package vo;

//import annotations.StartMonitor;

/**
 * @author Wan Kaiming on 2016/11/16
 * @version 1.0
 */
public class Student implements Person{

    private String name;
    private int age;

    public Student() {
        this.name = "deafult";
        this.age = 18;
    }


    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//    @StartMonitor
    public void sayHello(){
        System.out.println(this.getClass()+": I am saying hello.");
    }
}
