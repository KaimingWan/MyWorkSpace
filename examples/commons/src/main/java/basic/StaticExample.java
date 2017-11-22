package basic;

/**
 * Created by Kaiming Wan on 2017/1/20.
 */
public class StaticExample {
    public static int k =0 ;
    public static StaticExample t1 = new StaticExample("t1") ;
    public static StaticExample t2 = new StaticExample("t2") ;
    public static int i = print("i") ;
    public static int n =99 ;
    public int j = print("j") ;
    {
        print("构造块");
    }
    static {
        print("静态块");
    }
    public StaticExample(String str){
        System.out.println((++k)+":"+str+"   i="+i+"    n="+n) ;
        ++i;++n ;
    }
    public static int print(String str){
        System.out.println((++k)+":"+str+"   i="+i+"    n="+n) ;
        ++n;
        return ++i ;
    }
    public static void main (String args[]){
        StaticExample t = new StaticExample("init") ;
    }
}
