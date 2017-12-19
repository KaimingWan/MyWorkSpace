package concurrent;



/**
 * Created by wanshao
 * Date: 2017/12/18
 * Time: 下午11:21
 **/
class InterruptRead extends InterruptSupport {

    private Thread t;

    @Override
    public void bussiness() {
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        }
    }



    @Override
    public void interrupt() {

            t.interrupt();

    }

    public static void main(String args[]) throws Exception {
        final InterruptRead test = new InterruptRead();
        Thread t = new Thread() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                try {
                    System.out.println("InterruptRead start!");
                    //无业务侵入，自动根据中断异常进行中断
                    test.execute();
                } catch (InterruptedException e) {
                    System.out.println("InterruptRead end! cost time : " + (System.currentTimeMillis() - start));
                    e.printStackTrace();
                }
            }
        };
        t.start();
        // 先让Read执行3秒
        Thread.sleep(3000);
        // 发出interrupt中断
        t.interrupt();
    }

}


