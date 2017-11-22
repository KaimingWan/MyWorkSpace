package design;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 使用CGLib来做动态代理，本质使用的是字节码编辑技术
 * @author Wan Kaiming on 2016/11/16
 * @version 1.0
 */
public class PerformanceCGLibProxy implements MethodInterceptor{


    private static final Logger logger = LoggerFactory.getLogger(PerformanceCGLibProxy.class);


    //用于创建子类
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        //1. 设置需要创建子类的类
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //2. 通过字节码技术动态创建子类
        return enhancer.create();
    }

    //3. 拦截父类所有方法的调用
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long startTime = startMonitor();
        //4. 通过方法代理类来调用父类所有方法的调用，这里第一个参数是目标代理类target，第二个参数是方法的动态入参
        Object obj = methodProxy.invokeSuper(o, objects);
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
