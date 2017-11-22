package aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Wan Kaiming on 2016/12/5
 * @version 1.0
 */


//1. 通过Aspect将该类标记为一个切面
@Aspect
public class PerformanceMonitorAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    private long startTime;


    //启动监控，并且返回开始监控的时间戳信息
    @Before("@annotation(annotations.StartMonitor)")
    private void startMonitor() {
        System.out.println("test");
        logger.info("程序开始时间戳信息："+new Date());
        startTime=System.currentTimeMillis();

    }

    //结束监控，并且返回开始监控的时间戳信息
    @After("@annotation(annotations.StartMonitor)")
    private void endMonitor() {
        final long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-startTime)/1000;
        logger.info("执行时间："+excTime+"s");
        logger.info("当前时间为：" + new Date());

    }
}
