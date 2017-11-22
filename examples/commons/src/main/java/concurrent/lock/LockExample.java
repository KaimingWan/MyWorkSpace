package concurrent.lock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Lock及其子类（一般都称之为显示锁或者可重入锁）相比内置锁有如下优点：
 * 1.  能保证进入访问等待的程序的先后顺序
 * 2. 能控制锁的超时时间的超时锁
 * 3. 加锁和解锁方法的调用可以放在不同的方法里
 * 4. 提供可中断的锁
 * 5. 轮询锁
 *
 * 缺点：使用起来复杂点，而且不会像内置锁一样自动的去管理锁的释放
 *
 * PS： 现在内置锁和Lock实现上都采用了分级锁的思想，竞争不激烈就不加锁，竞争激烈就变为自旋锁再激烈就变
 * 重量级锁。网上很多博客的说法都是针对老版本的JDK。现在的JDK中，Lock和内置锁性能都比较不错，无论
 * 竞争激烈与否。如果需要用到Lock的功能就用Lock，否则用内置锁即可。
 *
 *
 * 可重入锁定义：同一个线程对部分代码块上了锁A。后来有些方法又要对已经被A锁上的代码块再上锁B。
 * 如果是可重入的锁，那么锁B是能够成功加上（替换了原来的锁A）并且访问之前已经被A锁上的所有代码块的。
 *
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class LockExample {
    public static void main(String[] args) {

    }


    /**
     *  简单的非可重入的自旋锁类实现，并发包里面的原子类都是这么实现的。
     *  竞争大情况下慎用基于CAS实现的自旋锁。
     *
     *  对于非可重入自旋锁来说：
     * 1、若有同一线程两调用lock() ，会导致第二次调用lock位置进行自旋，产生了死锁
     *　说明这个锁并不是可重入的。（在lock函数内，应验证线程是否为已经获得锁的线程）
     * 2、若1问题已经解决，当unlock（）第一次调用时，就已经将锁释放了。实际上不应释放锁。
     *（采用计数次进行统计）
     */

    static class SpinLock {

        private AtomicReference<Thread> sign =new AtomicReference<>();

        //如果同一个线程两次调用lock会导致第二次调用lock进行自旋
        public void lock(){
            Thread current = Thread.currentThread();
            while(!sign .compareAndSet(null, current)){
            }
        }

        public void unlock (){
            Thread current = Thread.currentThread();
            sign .compareAndSet(current, null);
        }
    }


    /**
     * 自定义的可重入的自旋锁.
     */
    static class SpinReentrantLock{
        private AtomicReference<Thread> sign =new AtomicReference<>();
        private int count =0;
        public void lock(){
            Thread current = Thread.currentThread();
            if(current== sign.get()) {
                count++;
                return ;
            }

            while(!sign.compareAndSet(null, current)){

            }
        }
        public void unlock (){
            Thread current = Thread.currentThread();
            if(current== sign.get()){
                if(count!=0){
                    count--;
                }else{
                    sign.compareAndSet(current, null);
                }

            }

        }

    }

}
