package concurrent;

import sun.nio.ch.Interruptible;

/**
 * 借鉴InterruptibleChannel设计主动感知抛异常来中断
 *
 * 代码说明，几个取巧的点：
 *
 * 位置1：利用sun提供的blockedOn方法，绑定对应的Interruptible事件处理钩子到指定的Thread上。
 *
 * 位置2：执行完代码后，清空钩子。避免使用连接池时，对下一个Thread处理事件的影响。
 *
 * 位置3：定义了Interruptible事件钩子的处理方法，回调InterruptSupport.this.interrupt()方法，子类可以集成实现自己的业务逻辑
 **/
interface InterruptAble { // 定义可中断的接口

    public void interrupt() throws InterruptedException;
}

abstract class MyInterruptible implements Interruptible {
    @Override
    public void interrupt(Thread thread) {

    }

    abstract public void interruptNow();
}

abstract class InterruptSupport implements InterruptAble {

    private volatile boolean interrupted = false;
    private MyInterruptible interruptor = new MyInterruptible() {

        @Override
        public void interrupt(Thread thread) {

        }

        public void interruptNow() {
            interrupted = true;
            InterruptSupport.this.interrupt(); // 位置3
        }

    };

    public final boolean execute() throws InterruptedException {
        try {
            blockedOn(interruptor); // 位置1
            if (Thread.currentThread().isInterrupted()) { // 立马被interrupted
                interruptor.interruptNow();
            }
            // 执行业务代码
            bussiness();
        } finally {
            blockedOn(null);   // 位置2
        }

        return interrupted;
    }

    public abstract void bussiness();

    public abstract void interrupt();

    // -- sun.misc.SharedSecrets --
    static void blockedOn(Interruptible intr) { // package-private
        sun.misc.SharedSecrets.getJavaLangAccess().blockedOn(Thread.currentThread(), intr);
    }
}