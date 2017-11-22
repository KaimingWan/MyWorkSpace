package concurrent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，
 * 其中的对象只能在其到期时才能从队列中取走。
 * 这种队列是有序的，即队头对象的延迟到期时间最长。
 * 注意：不能将null元素放置到这种队列中。
 * Created by Kaiming Wan on 2017/1/11.
 */
public class DelayQueueExample {

    /**
     * 缓存过期可以考虑使用DelayQueue,当然实际使用可以考虑guava的Cache
     */




    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        int cacheNumber = 10;
        int liveTime = 0;
        Cache<String, Integer> cache = new Cache<String, Integer>();


        //初始化Cache里面的内容
        for (int i = 0; i < cacheNumber; i++) {
            liveTime = random.nextInt(3000);
            System.out.println(i+"  "+liveTime);
            cache.put(i+"", i, random.nextInt(liveTime));

            //20%概率再往Cache里面加入一个对象
            if (random.nextInt(cacheNumber) > 7) {
                liveTime = random.nextInt(3000);
                System.out.println(i+"  "+liveTime);
                cache.put(i+"", i, random.nextInt(liveTime));
            }
        }

        Thread.sleep(3000);
        System.out.println();
    }

    }



//这里定义一个Cache class

class Cache<K,V>{
    public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
    public DelayQueue<DelayedItem<K>> queue = new DelayQueue<DelayedItem<K>>();


    public void put(K k,V v,long liveTime){
        //put in ConcurrentHashMap
        V v2 = map.put(k, v);

        DelayedItem<K> tmpItem = new DelayedItem<K>(k, liveTime);
        if (v2 != null) {   //说明要加入的对象已经存在

            queue.remove(tmpItem);
        }

        //加入对象
        queue.put(tmpItem);
    }

    //初始化之后，成为一个守护线程
    public Cache(){
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
                    dameonCheckOverdueKey();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.setDaemon(true);
        t.start();
    }


    //用于从ConcurrentHashMap中删除达到指定时间的Delayed对象
    public void dameonCheckOverdueKey() throws InterruptedException {
        while (true) {
            //达到指定时间的才能take出来
            DelayedItem<K> delayedItem = queue.take();
            if (delayedItem != null) {      //取到达到时间的元素
                map.remove(delayedItem.getT()); //从map中删除
                System.out.println(System.nanoTime()+" remove "+delayedItem.getT() +" from cache");
            }
            try {
                Thread.sleep(300);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}



class DelayedItem<T> implements Delayed {

    private T t;
    private long liveTime ;
    private long removeTime;

    public DelayedItem(T t,long liveTime){
        this.setT(t);
        this.liveTime = liveTime;   //指定的存活时间
        this.removeTime = TimeUnit.NANOSECONDS.convert(liveTime, TimeUnit.NANOSECONDS) + System.nanoTime(); //计算得到被删除时候的时间
    }

    //本质上是个优先队列，用Delayed time作为比较优先级的依据
    @Override
    public int compareTo(Delayed o) {
        if (o == null) return 1;
        if (o == this) return  0;
        if (o instanceof DelayedItem){
            DelayedItem<T> tmpDelayedItem = (DelayedItem<T>)o;
            if (removeTime > tmpDelayedItem.removeTime ) {      //用removeTime来比较，不要用liveTime。删除一些元素，再加入一些元素，live time可以是相同的
                return 1;
            }else if (removeTime == tmpDelayedItem.removeTime) {
                return 0;
            }else {
                return -1;
            }
        }

        //其他Delayed的子类的处理方式
        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1:diff == 0? 0:-1;
    }


    //重写该方法，表明在该值满足之后，才会释放元素
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(removeTime - System.nanoTime(), unit);
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }



}