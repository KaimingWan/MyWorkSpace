package practice.cn.bn.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import practice.cn.bn.entity.Car;
import practice.cn.bn.entity.DataBean;
import practice.cn.bn.entity.DateEntity;
import practice.cn.bn.entity.TestBean;
import practice.cn.bn.service.DataBeanService;
import practice.cn.bn.service.TestBeanService;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-09-19 PM04:24
 */
public class launcher {
    public static void main(String[] args) throws Exception {
//        Semaphore semaphore = new Semaphore(0);
        // 1.初始化的时候，会进行一次refresh事件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
//        context.start();
//        context.refresh();

//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
//        beanFactory.registerSingleton("dataBean", new DataBean("test", "source"));
//        DataController bean = context.getBean(DataController.class);
//        System.out.println(bean);

        // 2.测试aop
        DataBean dataBean = context.getBean(DataBean.class);
        dataBean.setData("test");

        // 3.手动扫描文件
//        Properties properties = PropertiesLoaderUtils.loadAllProperties("test.properties", Thread.currentThread().getClass().getClassLoader());

//        Resource resource = new FileSystemResource("./test.properties");
//        Resource resource = new ClassPathResource("test.properties");
//        System.out.println(resource.getURI().getPath());
//        Properties properties = PropertiesLoaderUtils.loadProperties(resource);
//
//        System.out.println(properties.getProperty("name"));

        // 4.配置注入属性
//        TestBean testBean = context.getBean(TestBean.class);
//        System.out.println(testBean.toString());

        // 5.测试factoryBean
        Car car = context.getBean(Car.class);
        System.out.println(car);
//        CarFactory carFactory = (CarFactory) context.getBean("&car");
//        System.out.println("some car ? " + (carFactory.getObject() == car));

        // 6.接口注入
        DataBeanService dataBeanService = context.getBean(DataBeanService.class);
        TestBeanService testBeanService = context.getBean(TestBeanService.class);

        DataBean bean = context.getBean(DataBean.class);
        bean.setData("My Data");
        // 7.自定义属性编辑器
        // 自定义属性编辑器有三种方式:
        // 1)org.springframework.bean.factory.config.CustomEditorConfigurer配置customEditors,继承PropertyEditorSupport
        //  但是这种方式在4.0之前是可用的,但是4.0之后使用class注册,而非之前的bean方式, 缺点是无法指定如Date的pattern
        // 2)org.springframework.bean.factory.config.CustomEditorConfigurer配置propertyCustomRegistrars,实现PropertyEditorRegistrar接口
        //  这种方式会自己控制注册过程
        // 3)org.springframework.context.support.ConversionServiceFactoryBean配置converters,实现Converter接口
        //  必须指定ConversionServiceFactoryBean的id为conversionService
        TestBean testBean = context.getBean(TestBean.class);
        System.out.println(testBean.toString());
        // 注意:使用DateFormat注解,只是指定日期的匹配格式
        DateEntity dateEntity = context.getBean(DateEntity.class);
        System.out.println("dateEntity : " + dateEntity);
        Thread.sleep(2000);
//        context.refresh(); // 清空所有注册的bean
        context.close();
//        semaphore.acquire();
    }
}
