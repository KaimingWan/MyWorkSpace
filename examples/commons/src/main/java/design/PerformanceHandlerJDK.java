package design;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 利用JDK自带的Proxy来做动态代理
 * @author Wan Kaiming on 2016/11/16
 * @version 1.0
 */
public class PerformanceHandlerJDK implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceHandlerJDK.class);


    //    定义目标业务类,使用任何Object
    private Object target;

    public PerformanceHandlerJDK(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = startMonitor();
        Object obj = method.invoke(target, args);
        endMonitor(startTime);
        return obj;
    }


    //启动监控，并且返回开始监控的时间戳信息
    private long startMonitor() {
        logger.info("程序开始时间戳信息："+new Date());
        return System.currentTimeMillis();
    }

    //结束监控，并且返回开始监控的时间戳信息
    private void endMonitor(long startTime) {
        final long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-startTime)/1000;
        logger.info("执行时间："+excTime+"s");
        logger.info("当前时间为：" + new Date());

    }
}
