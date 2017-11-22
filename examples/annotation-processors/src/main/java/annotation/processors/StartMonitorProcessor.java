package annotation.processors;


import annotations.StartMonitor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * 用于处理StartMonitor注解
 *
 * @author Wan Kaiming on 2016/11/16
 * @version 1.0
 */



@SupportedAnnotationTypes("annotations.StartMonitor")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class StartMonitorProcessor extends AbstractProcessor{



    /**
     *
     * @param processingEnvironment ProcessingEnviroment提供很多有用的工具类Elements, Types和Filer
     *           Elements：一个用来处理Element的工具类（后面将做详细说明）；
     *          Types：一个用来处理TypeMirror的工具类（后面将做详细说明）；
     *          Filer：正如这个名字所示，使用Filer你可以创建文件。
     */
    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }



    /**
     *
     * @param annotations 注解处理需要执行一次或者多次。每次执行时，处理器方法被调用，并且传入了当前要处理的注解类型。
     * @param roundEnv 这个对象提供当前或者上一次注解处理中被注解标注的源文件元素。（简单点说，就是可以获得所有被标注的元素，无论是类，参数，函数还是变量）
     * @return 返回布尔类型
     */

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //1. 创建cglib代理类
//        PerformanceCGLibProxy proxy = new PerformanceCGLibProxy();

        System.out.println("hello annotation processor");

        //2. 代理一个类对象,这里先用反射获取下注解的对象的name
        if(!roundEnv.processingOver()){
            for (Element element : roundEnv.getElementsAnnotatedWith(StartMonitor.class)) {

//                //获取被注解的类
//                Object obj = element.getClass();
//
//
//                //获取被注解类的所有方法
//                Method[] methods= element.getClass().getDeclaredMethods();
//
//
//                //代理类
//                Object proxyClazz = proxy.getProxy((Class) obj);
//
//
//                //遍历所有方法
//                for (Method method : methods) {
//                    //打印下方法名字
//                    System.out.println(method.getName());
//                    try {
//                        method.invoke(proxyClazz);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//                }



                String name = element.getSimpleName().toString();
                System.out.println(name);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "element name: " + name);
            }
        }


        return true;
    }
}
