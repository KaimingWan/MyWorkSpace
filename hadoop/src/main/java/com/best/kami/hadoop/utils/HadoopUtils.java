package com.best.kami.hadoop.utils;

import org.apache.hadoop.conf.Configuration;

/**
 *
 * 提供一些hadoop的工具方法
 * @author Wan Kaiming on 2016/11/15
 * @version 1.0
 */
public class HadoopUtils {

    /**
     *
     * @param userName
     * @param FileSystemURL
     * @param yarnAddress
     * @return 返回一个Configuration对象
     */
    public static Configuration initialConfig(String userName, String FileSystemURL, String yarnAddress){
        //设置使用root用户操作
        System.setProperty("HADOOP_USER_NAME", userName);

        Configuration conf = new Configuration();

        //设置hdfs和yarn地址
        conf.set("fs.defaultFS",FileSystemURL);
        conf.set("yarn.resourcemanager.hostname",yarnAddress);

        return conf;
    }




}
