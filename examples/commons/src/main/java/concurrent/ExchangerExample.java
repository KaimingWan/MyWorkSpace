package concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class ExchangerExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //Exchanger作为一个在两个线程之间共享的容器，来完成对象的交换。PS：只能用在2个线程之间，不能有更多的线程。
        Exchanger exchanger = new Exchanger();
        executorService.submit(new Worker(exchanger, "Data-A"));
        executorService.submit(new Worker(exchanger, "Data-B"));

        executorService.shutdown();

    }
}


//用于交换数据的工作线程
class Worker implements Runnable{

    //定义交换器
    Exchanger exchanger;
    //定义要被交换的对象
    Object object;

    public Worker(Exchanger exchanger,Object object){
        this.exchanger = exchanger;
        this.object = object;
    }

    @Override
    public void run() {
        //记录下原来的旧值
        Object oldData = this.object;
        //当前实例中的object替换成Exchanger里面的新值
        try {
            //一个线程调用了exchange方法后会进入阻塞状态，直到另外一个参与交换的线程也调用exchange
            this.object = this.exchanger.exchange(this.object);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+" exchanged "+oldData+" for "+this.object);
    }
}
