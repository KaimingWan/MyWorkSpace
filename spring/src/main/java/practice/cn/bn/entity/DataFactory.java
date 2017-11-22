package practice.cn.bn.entity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM04:51
 */
@Component
public class DataFactory implements InitializingBean, ApplicationListener, ApplicationContextAware {
    private volatile boolean stop = false;
    private AbstractApplicationContext applicationContext;
    // 必须被spring注解
    public void afterPropertiesSet() throws Exception {
        System.out.println("execute afterPropertiesSet");
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
//        if (beanFactory.containsBeanDefinition("dataBean")) {
//            beanFactory.removeBeanDefinition("dataBean");
//        }
//
//        beanFactory.registerSingleton("dataBean", new DataBean("test", "source"));
//
//
//        new Thread(new Runnable() {
//            public void run() {
//                while (!stop) {
//                    try {
//                        System.out.println(applicationContext.getBean(DataBean.class));
//                        Thread.sleep(1000);
//                    } catch (BeansException e) {
//                        System.out.println("no register");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                System.out.println("thread stop!");
//            }
//        }).start();
    }

    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextClosedEvent) {
            System.out.println("close");
            stop = true;
        }

        if (event instanceof ContextRefreshedEvent) {
            System.out.println("refresh");
        }

        if (event instanceof ContextStartedEvent) {
            System.out.println("start");
        }

        if (event instanceof ContextStoppedEvent) {
            System.out.println("stop");
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ap");
        if (applicationContext instanceof AbstractApplicationContext) {
            this.applicationContext = (AbstractApplicationContext) applicationContext;
        }
    }
}
