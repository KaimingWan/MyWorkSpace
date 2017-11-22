import com.best.oasis.express.util.kafka.model.KafkaMessage;
import com.best.oasis.express.util.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @auther BG241127
 * @create 2016-10-19 8:54
 */
@ContextConfiguration(locations = { "classpath*:applicationContext.xml" })
public class TestSearchOriginalSettleOrder extends AbstractJUnit4SpringContextTests {
    @Autowired
    private KafkaConsumerUtils kafkaUtils;
//    @Autowired
//    KafkaListenerFactory[] factories;
    @Autowired
    KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    String topic = "kafka.scanAllTransfer";
    @Test
    public void testPartitionFor() throws ParseException {
//        kafkaUtils.searchOriginalSettleOrder("kafka",110l, 1476838922000l);
        SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date date = a.parse("2016-11-29 13:58:30.111");
        System.out.println(date.getTime());
        System.out.println(new Date(1476838922000l));
        System.out.println(new Date().getTime());
    }

    @Test
    public void testSearch() throws InterruptedException, ParseException{
        for(int i=1; i<120; i++){
            kafkaTemplate.send(topic, Integer.toString(i), mockMsg());
            TimeUnit.MILLISECONDS.sleep(10L);
        }
        SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        List<ConsumerRecord> records =
                kafkaUtils.searchOriginalSettleOrder(
                        topic,
                        a.parse("2016-11-29 13:57:30.111").getTime(),
                        new Date().getTime());
        System.out.println(records.size());
//        assertEquals(records.size(), 3);
    }

    @Test
    public void testOnProducerEnv() throws ParseException, InterruptedException {
        testSearchInTimeRange("2016-11-30 13:57:30.111", "2016-11-30 13:58:30.111", topic);
    }

    @Test
    public void testOnTestEnv() throws ParseException, InterruptedException {
        testSearchInTimeRange("2016-12-01 14:42:10.111", "2016-12-01 14:48:10.111", "testIO");
    }

    private void testSearchInTimeRange(String fromTime, String toTime, String topic)
            throws ParseException, InterruptedException {
        SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        List<ConsumerRecord> records =
                kafkaUtils.searchOriginalSettleOrder(
                        topic, a.parse(fromTime).getTime(),
                        a.parse(toTime).getTime());
        System.out.println(records.size());
        int outCnt = 0;
        File outFile = new File("out.txt");
        try {
            outFile.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
            for(ConsumerRecord record : records){
                out.write("[" + record.partition() + "-" + record.offset() + "] time:" +
                        Long.toString(record.timestamp()) + "\n");
                Long timeStamp = record.timestamp();
                if(timeStamp < a.parse(fromTime).getTime()
                        || timeStamp > a.parse(toTime).getTime()){
                    outCnt ++;
                    logger.info("Out of date message: date: " + record.timestamp() + " at [" + record
                            .partition() + " - " + record.offset() + "]");
                }
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(outCnt > 0){
            System.out.println("There are " + outCnt + " messages out of date!");
        }else{
            System.out.println("No message out of date");
        }
    }



    @Test
    public void testListSplit(){
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            list.add(i);
        }
//        System.out.println(list.subList(0, 1) + "\n" + list.subList(0, 11) + "\n" +list.subList
//                (0,12));
//        List<List<Integer>> ra = kafkaUtils.listSplit(list, 3);
//        System.out.println(ra);
    }


    @Test
    public void testBinarySearch(){
        List<Integer> list = Arrays.asList(2,4,5,7,9,11);
        System.out.println(BinarySearch(list, 6, 1));
        for(int i=1; i<=12; i++){
            System.out.println("i = " + i + "\t" + BinarySearch(list, i, 1));
        }
    }

    /**
     * mod是取整方式，0表示向下取整，1表示向上取整
     * */
    private int BinarySearch(List<Integer> list, int dest, int mod){
        int low = 0;
        int high = list.size()-1;
        if(mod == 1 && list.get(low) > dest){
            return -1;
        }else if(mod == 0 && list.get(high) < dest){
            return -1;
        }

        int mid = -1;
        int tmp;
        while(low <= high){
            mid = low + ((high - low) >> 1);
            tmp = list.get(mid);
            if( tmp == dest){
                return mid;
            }else if(tmp > dest){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return (mod == 1) ? high : low;
    }

    private KafkaMessage mockMsg() {
        KafkaMessage msg = new KafkaMessage();
        msg.setDocType("testDoc");
        msg.setMessages(new ArrayList<>());
        return msg;
    }

}
