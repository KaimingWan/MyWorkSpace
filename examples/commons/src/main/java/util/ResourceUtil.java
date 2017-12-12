package util;

import java.net.URL;

import org.aspectj.weaver.ast.Test;

/**
 * Created by wanshao
 * Date: 2017/11/28
 * Time: 下午4:59
 **/
public class ResourceUtil {
    public static void main(String[] args) {



    }

    public void getResourceExample(){
        /**
         *
         *
         * 演示路径
         /
         |-com.cn.test
         |-Test.class
         |-test2.txt
         |-test1.txt
         */


        // 获取与当前类在同一个包下的资源
        URL url1 = this.getClass().getResource("test2.txt");
        // 获取com.cn.test包下的资源,需加/
        URL url2 = this.getClass().getResource("/com/cn/test/test2.txt");
        // 获取类路径根下的资源
        URL url3 = this.getClass().getClassLoader().getResource("test1.txt");
        // 获取包com.cn.test包下的资源
        URL url4 = this.getClass().getResource("com/cn/test/test2.txt");
    }

    /**
     * 从classPath获取资源
     */
    public void getResourceFromClasspath(){

        URL url1 = this.getClass().getResource("resource_name");
        URL url2 = this.getClass().getClassLoader().getResource("resource_name");
        //推荐方式
        URL url3 = Thread.currentThread().getContextClassLoader().getResource("resource_name");
    }

    /**
     * 获取WEB相关的资源
     */
    public void getWebResource(){
        //servletContext.getResourceAsStream(resource_name);
    }
}
