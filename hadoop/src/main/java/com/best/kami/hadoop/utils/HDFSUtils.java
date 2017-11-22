package com.best.kami.hadoop.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

/**
 *
 * 提供一些IO封装
 *
 * @author Wan Kaiming on 2016/11/15
 * @version 1.0
 */
public class HDFSUtils {

    private static final Logger logger = LoggerFactory.getLogger(HDFSUtils.class);

    //读取任意格式数据
    public void normalFileReader(Configuration conf, Path path, String uri,final int BUFFER_SIZE)throws IOException {

        logger.info("Normal File Reader Start!");

        FileSystem fileSystem = FileSystem.get(URI.create(uri), conf);
        FSDataInputStream fsDataInputStream = fileSystem.open(path);


        //按照给定的缓冲区大小读取
        byte[] buffer = new byte[BUFFER_SIZE];
        //记录读取长度
        int len = fsDataInputStream.read(buffer);
        while (len != -1) {
            //执行具体处理，默认什么都不做
            this.process();
            len = fsDataInputStream.read(buffer);
        }

        logger.info("Normal File Reader Finished!");
    }


    //读取顺序文件
    public void seqFileReader(Configuration conf, Path path) throws IOException {


        SequenceFile.Reader.Option fileOption = SequenceFile.Reader.file(path);
        SequenceFile.Reader reader = null;
        try {
            reader = new SequenceFile.Reader(conf, fileOption);

            //定义要读取的KEY VALUE
            Writable key = (Writable) ReflectionUtils.newInstance(reader.getKeyClass(), conf);
            Writable value = (Writable) ReflectionUtils.newInstance(reader.getValueClass(), conf);



            HashMap<Writable, Writable> pair = new HashMap<Writable, Writable>();

            while (reader.next(key, value)) {
                pair.put(key, value);
                //进行相关处理
                this.process(pair);
            }
        }finally {
            IOUtils.closeStream(reader);
        }

    }


    //用于被继承,执行具体的处理
    protected void process(){}

    protected void process(HashMap<Writable, Writable> pair){}

}
