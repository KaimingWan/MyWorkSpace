import com.best.oasis.express.util.kafka.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * @author Wan Kaiming on 2017/1/5
 * @version 1.0
 */
public class Q9Consumer {

    public static void main(String[] args) {
        KafkaConsumerUtils kafkaConsumerUtils = new KafkaConsumerUtils();
        kafkaConsumerUtils.setKafkaServers("10.8.30.31:9092,10.8.30.32:9092,10.8.30.33:9092");
        //用的UNIX时间戳，这里查找2017年1月5日16时11分0秒到2017年1月5日16时13分0秒的数据

        List<ConsumerRecord>  consumerRecordList;

        consumerRecordList=kafkaConsumerUtils.searchOriginalSettleOrder("kafka.scanAllTransfer",1483603860,1483603980);

        //isNeededRecordExists("21091341117",consumerRecordList);

        printSome(consumerRecordList);
    }


    /**
     *
     * @param billcode  输入指定的单号
     * @param consumerRecordList
     * @return  是否在指定的consumer record list当中存在
     */
    private static boolean isNeededRecordExists(String billcode, List<ConsumerRecord> consumerRecordList){

        for(ConsumerRecord consumerRecord:consumerRecordList){
            if (billcode.equals(consumerRecord.key())) {
                System.out.println("找到指定单号的记录！");
                System.out.println(consumerRecord.value());
                return true;
            }
        }

        System.out.println("没有找到制定单号的记录！");
        return false;
    }

    private static void printSome(List<ConsumerRecord> consumerRecordList){

        int count=1;

        System.out.println("总共获得消息条数： "+consumerRecordList.size());

        for(ConsumerRecord consumerRecord:consumerRecordList){
            System.out.println(consumerRecord.key());
            System.out.println(consumerRecord.value());
            count++;
            if (count>10) break;
        }

    }
}
