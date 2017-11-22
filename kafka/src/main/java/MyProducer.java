import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;

/**
 * @author Wan Kaiming on 2016/8/1
 * @version 1.0
 */
public class MyProducer {


    public static void main(String[] args) {

        //统计时间
        System.out.println("程序开始时间戳信息："+new Date());
        final long startTime=System.currentTimeMillis();

        Properties props = new Properties();

        //指定clientID方便监控
        //props.put("client.id", "test_producer_1");

        //broker地址 这里用域名，记得修改本地的hosts文件
        props.put("bootstrap.servers", "10.8.12.139:9092,10.8.12.140:9092,10.8.12.144:9092");
        //消息可靠性语义
        props.put("acks", "all");
        //请求broker失败进行重试的次数，避免由于请求broker失败造成的消息重复
        props.put("retries", 3);
        //按批发送，每批的消息数量
        //props.put("batch.size", 16384);
        props.put("batch.size", 16384);
        //防止来不及发送，延迟一点点时间，使得能够批量发送消息
        props.put("linger.ms", 1);
        //缓冲大小，bytes
        props.put("buffer.memory", 33554432);
        //key的序列化类
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //value的序列化类
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建一个Producer对象，加载配置上下文信息
        Producer<String, String> producer = new KafkaProducer<String,String>(props);


        //创建个1K的消息用于发送
//        byte[] msg = new byte[1024];
//        String myValue = new String(msg);

        for(int i=0;i<5000000;i++){
            producer.send(new ProducerRecord<String, String>("hello-topic", Integer.toString(i), "one,two,three,four"));
        }

//        //发送一条后休眠2秒或者
//        try {
//            Thread.currentThread().sleep(900000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        producer.close();

        final long endTime=System.currentTimeMillis();
        float excTime=(float)(endTime-startTime)/1000;
        System.out.println("执行时间："+excTime+"s");
        System.out.println("当前时间为："+ new Date());



    }
}
