import com.best.oasis.express.util.kafka.KafkaMessageDeserializer;
import com.best.oasis.express.util.kafka.model.KafkaMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Wan Kaiming on 2016/8/2
 * @version 1.0
 */
public class MyConsumer {

    //记录多线程消费的所有记录数
    private static AtomicLong totalRecordsCount = new AtomicLong(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyConsumer myConsumer = new MyConsumer();
        //myConsumer.runAuto();
        //myConsumer.runMannual();
        myConsumer.runAutoWithMultiThreads();

    }


    /**
     *  自动提交offset,多线程消费
     */

    private void runAutoWithMultiThreads() throws ExecutionException, InterruptedException {
        Properties props = new Properties();

        //broker的地址
        props.put("bootstrap.servers", "10.8.30.31:9092,10.8.30.32:9092,10.8.30.33:9092");
        //唯一标识消费者组的ID
        props.put("group.id", "q9.check");
        //开启自动提交offset
        props.put("enable.auto.commit", "true");
        //自动提交offset间隔1s
        props.put("auto.commit.interval.ms", "1000");
        //消费会话超时时间10s(做测试,默认30s)
        props.put("session.timeout.ms", "30000");
        //key的反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化类
        props.put("value.deserializer", KafkaMessageDeserializer.class);

        //设置从头消费
        props.put("auto.offset.reset", "earliest");

        //创建多个consumer实例，并且全部订阅同一个topic
        List<KafkaConsumer<String, KafkaMessage>> consumerList = new ArrayList<>();
        for(int i=0;i<6;i++){
            //指定clientID方便监控
            String clientID = "test_consumer_" + Integer.toString(i);
            props.put("client.id", clientID);
            KafkaConsumer<String, KafkaMessage> consumer = new KafkaConsumer<String, KafkaMessage>(props);
            consumer.subscribe(Arrays.asList("kafka.scanAllTransfer"));
            consumerList.add(consumer);
        }


        ExecutorService executorService = Executors.newFixedThreadPool(6);

        //保存future结果的list
        List<Future<String>> futuresList = new ArrayList<>();

        //启动6个消费线程,future==null表示任务完成了
        Future future = null;
        for(int i=0;i<6;i++) {
            future =  executorService.submit(new ConsumerRunnable(consumerList.get(i)));
            futuresList.add(future);
        }





        //尝试关闭，没完成的会等待完成再彻底关闭
        executorService.shutdown();
//
//        while (true) {
//            if(executorService.isTerminated()){
//                System.out.println("所有的子线程都结束了！");
//                break;
//            }
//            Thread.sleep(1000);
//        }
//
//        for(Future futureElement : futuresList) {
//            System.out.println(futureElement.get());
//        }
//
//        //所有线程都跑完后输出下总共消费的记录条数
//        System.out.println("Total count:"+totalRecordsCount);


    }



    //创建消费类，用于执行多线程
    class ConsumerRunnable implements Runnable{

        KafkaConsumer<String, KafkaMessage> consumer;

        public ConsumerRunnable(KafkaConsumer<String, KafkaMessage> consumer){
            this.consumer=consumer;
        }

        @Override
        public void run() {
            int count = 0;

            String tempStr;


            //这里采用批处理。
            //final int minBatchSize = 100000;
            //List<ConsumerRecord<String, KafkaMessage>> buffer = new ArrayList<ConsumerRecord<String, KafkaMessage>>();


            //长连接
            while (true) {
                ConsumerRecords<String, KafkaMessage> records = consumer.poll(5000);
                for (ConsumerRecord<String, KafkaMessage> record : records) {

                    //查找下单号
                    if (record.key().equals("21091341117")) {
                        System.out.println("成功找到！！");
                    }


                    count++;
                    //tempStr="offset = "+ record.offset()+", key = "+record.key()+", value = "+record.value();
                    //System.out.println(tempStr);
                    //MyConsumer.totalRecordsCount.addAndGet(1);
                    //System.out.print(MyConsumer.totalRecordsCount.get()+" ");


                }



            }

        }
    }



    /**
     * 自动提交offset
     */
    private void runAuto(){

        Properties props = new Properties();
        //指定clientID方便监控
        props.put("client.id", "test_consumer_12");
        //broker的地址
        props.put("bootstrap.servers", "mysql1:9092,mysql4:9092");
        //唯一标识消费者组的ID
        props.put("group.id", "test_new");
        //开启自动提交offset
        props.put("enable.auto.commit", "true");
        //自动提交offset间隔1s
        props.put("auto.commit.interval.ms", "1000");
        //消费会话超时时间30s
        props.put("session.timeout.ms", "30000");
        //key的反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        //value的反序列化类
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        //多个主题用逗号分隔
        consumer.subscribe(Arrays.asList("my-topic-new"));

        String tempStr;

        //长连接
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records){
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                tempStr = record.key() + record.value();
            }

            //消费完毕后休眠10秒
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 手动提交offset
     */
    private void runMannual(){
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.45.10.31:9092,10.45.10.34:9092");
        props.put("group.id", "test");
        //不开启自动提交
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList("my-topic-new"));
        final int minBatchSize = 200;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<ConsumerRecord<String, String>>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {

                //打印结果
                for(ConsumerRecord<String,String> record:buffer){
                    System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
                }

                //同步提交
                consumer.commitSync();
                buffer.clear();
            }
        }
    }


    /**
     *     消费的数据传递到HDFS
     *     由于本来就是健值对，所以就采用SequenceFile
     */
//    private void hdfsWriter(List<ConsumerRecord<String, String>> buffer) throws IOException {
//        Configuration conf = RWHDFSTest.initialConfig("root", "alluxio://10.45.10.33:19998", "10.45.10.33");
//
//        //设置需要访问的文件在HDFS上的URL
//        String uri = "/linecount/kafka_input_file.txt";
//
//        Path path = new Path(uri);
//
//
//
//        //使用已经写好的类，将kafka数据持久化到HDFS
//        RWHDFSTest.normalFileWriterForKafka(conf,path,uri,buffer);
//
//    }

//    private void hdfsReader() throws IOException {
//        Configuration conf = RWHDFSTest.initialConfig("root", "alluxio://10.45.10.33:19998", "10.45.10.33");
//
//        //设置需要访问的文件在HDFS上的URL
//        String uri = "/linecount/kafka_input_file.txt";
//
//        Path path = new Path(uri);
//
//
//        //使用已经写好的类，将kafka数据持久化到HDFS
//        RWHDFSTest.seqFileReader(conf,path);
//    }

}