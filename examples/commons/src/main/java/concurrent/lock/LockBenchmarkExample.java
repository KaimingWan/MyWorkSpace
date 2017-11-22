package concurrent.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 比较不同锁在JDK1.8下面的性能。现在lock和synchrnize应该都用CAS
 *
 * @author Wan Kaiming on 2017/1/12
 * @version 1.0
 */
public class LockBenchmarkExample {
    static class SynRunner implements Runnable {
        private long v = 0;
        @Override
        public synchronized void run() {
            v = v + 1;
        }
    }
    static class LockRunner implements Runnable {
        private ReentrantLock lock = new ReentrantLock();
        private long          v    = 0;
        @Override
        public void run() {
            lock.lock();
            try {
                v = v + 1;
            } finally {
                lock.unlock();
            }
        }
    }
    static class Tester {
        private AtomicLong runCount = new AtomicLong(0);
        private AtomicLong start    = new AtomicLong();
        private AtomicLong end      = new AtomicLong();
        public Tester(final Runnable runner, int threadCount) {
            final ExecutorService pool = Executors.newFixedThreadPool(threadCount);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        runner.run();
                        long count = runCount.incrementAndGet();
                        if (count == 1) {
                            start.set(System.nanoTime());
                        } else if (count >= 10000000) {
                            //执行1000万次
                            if (count == 10000000) {
                                end.set(System.nanoTime());
                                System.out.println(runner.getClass().getSimpleName() + ", cost: "
                                        + (end.longValue()- start.longValue())/1000000 + "ms");                            }
                            pool.shutdown();
                            return;
                        }
                    }
                }
            };
            for (int i = 0; i < threadCount; i++) {
                pool.submit(task);
            }
        }
    }
    public static void main(String[] args) {
        new Tester(new SynRunner(), 3000);
        new Tester(new LockRunner(), 3000);
    }
}
