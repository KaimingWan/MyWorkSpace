package JVM;

/**
 * 考察class loader机制的题目
 * 来源：http://www.cnblogs.com/javaee6/p/3714716.html
 * Created by Kaiming Wan on 2017/1/15.
 */
public class ClassLoadExample {
    public static void main(String[] args) {
//
//        过程：
//        1:SingleTon singleTon = SingleTon.getInstance();调用了类的SingleTon调用了类的静态方法，触发类的初始化
//        2:类加载的时候在准备过程中为类的静态变量分配内存并初始化默认值 singleton=null count1=0,count2=0
//        3:类初始化化，为类的静态变量赋值和执行静态代码快。singleton赋值为new SingleTon()调用类的构造方法
//        4:调用类的构造方法后count=1;count2=1
//        5:继续为count1与count2赋值,此时count1没有赋值操作,所以count1为1,但是count2执行赋值操作就变为0
        SingleTon singleTon = SingleTon.getInstance();
        System.out.println("count1=" + singleTon.count1);   //为1
        System.out.println("count2=" + singleTon.count2);   //为0
    }
}


class SingleTon {
    private static SingleTon singleTon = new SingleTon();
    public static int count1;
    public static int count2 = 0;   //这个赋值操作在初始化之后执行，所以本题的答案是1,0

    private SingleTon() {
        count1++;
        count2++;
    }

    public static SingleTon getInstance() {
        return singleTon;
    }
}