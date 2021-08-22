package club.jming.exec;

import club.jming.entity.Msg;
import club.jming.utils.ClientUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

@Slf4j
public class Client {

    /**
     * 可以获取到，在kafka生产者运行期间的服务器的CPU、内存、生产者速率信息
     *
     * @param threads
     * @throws InterruptedException
     * @throws BrokenBarrierException
     */
    public static void exec(int threads) throws InterruptedException, BrokenBarrierException {

        String msg01 = "SSXVNJHPDQDXVCRASTVYBCWVMGNYKRXVZXKGXTSPSJDGYLUEGQFLAQLOCFLJBEPOWFNSOMYARHAOPUFOJHHDXEHXJBHWGSMZJGNLONJVXZXZOZITKXJBOZWDJMCBOSYQQKCPRRDCZWMRLFXBLGQPRPGRNTAQOOSVXPKJPJLAVSQCCRXFRROLLHWHOHFGCFWPNDLMWCSSHWXQQYKALAAWCMXYLMZALGDESKKTEESEMPRHROVKUMPSXHELIDQEOOHOIHEGJOAZBVPUMCHSHGXZYXXQRUICRIJGQEBBWAXABQRIRUGZJUUVFYQOVCDEDXYFPRLGSGZXSNIAVODTJKSQWHNWVPSAMZKOUDTWHIORJSCZIQYPCZMBYWKDIKOKYNGWPXZWMKRDCMBXKFUILWDHBFXRFAOPRUGDFLPDLHXXCXCUPLWGDPPHEMJGMTVMFQQFVCUPOFYWLDUEBICKPZKHKVMCJVWVKTXBKAPWAPENUEZNWNWDCACDRLPIPHJQQK";
        String msg02 = "SSXVNJHPDQDXVCRASTVYBCWVMGNYKRXVZXKGXTSPSJDGYLUEGQFLAQLOCFLJBEPOWFNSOMYARHAOPUFOJHHDXEHXJBHWGSMZJGNLONJVXZXZOZITKXJBOZWDJMCBOSYQQKCPRRDCZWMRLFXBLGQPRPGRNTAQOOSVXPKJPJLAVSQCCRXFRROLLHWHOHFGCFWPNDLMWCSSHWXQQYKALAAWCMXYLMZALGDESKKTEESEMPRHROVKUMPSXHELIDQEOOHOIHEGJOAZBVPUMCHSHGXZYXXQRUICRIJGQEBBWAXABQRIRUGZJUUVFYQOVCDEDXYFPRLGSGZXSNIAVODTJKSQWHNWVPSAMZKOUDTWHIORJSCZIQYPCZMBYWKDIKOKYNGWPXZWMKRDCMBXKFUILWDHBFXRFAOPRUGDFLPDLHXXCXCUPLWGDPPHEMJGMTVMFQQFVCUPOFYWLDUEBICKPZKHKVMCJVWVKTXBKAPWAPENUEZNWNWDCACDRLPIPHJQQKMOFDQSPKKNURFBORJLBPCBIWTSJNPRBNITTKJYWAHWGKZYNUSFISPIYPIOGAUPZDXHCFVGXGIVVCPFHIXAACZXZLFDMOOSSNTKUPJQEIRRQAMUCTBLBSVPDDYOIHAOODZNJTVHDCIEGTAVMYZOCIVSKUNSMXEKBEWNZPRPWPUJABJXNQBOXSHOEGMJSNBUTGTIFVEQPSYBDXEXORPQDDODZGBELOISTRWXMEYWVVHGMJKWLJCCHPKAFRASZEYQZCVLFSLOWTLBMPPWPPFPQSAZPTULSTCDMODYKZGSRFQTRFTGCNMNXQQIYVUQZHVNIPHZWVBSGOBYIFDNNXUTBBQUYNXOZCSICGRTZSSRHROJRGBHMHEQJRDLOQBEPTOBMYLMIGPPDPOLTEUVDGATCGYPQOGOYYESKEGBLOCBIYSLQEYGCCIPBXPNSPKDYTBEWDHBHWVDPLOVHJPNYGJUHKKHDASNFGZDAIWWQEPPBRJKDGOSAFAPRLWFFXRVMZQTKRY";
        String msg03 = "SSXVNJHPDQDXVCRASTVYBCWVMGNYKRXVZXKGXTSPSJDGYLUEGQFLAQLOCFLJBEPOWFNSOMYARHAOPUFOJHHDXEHXJBHWGSMZJGNLONJVXZXZOZITKXJBOZWDJMCBOSYQQKCPRRDCZWMRLFXBLGQPRPGRNTAQOOSVXPKJPJLAVSQCCRXFRROLLHWHOHFGCFWPNDLMWCSSHWXQQYKALAAWCMXYLMZALGDESKKTEESEMPRHROVKUMPSXHELIDQEOOHOIHEGJOAZBVPUMCHSHGXZYXXQRUICRIJGQEBBWAXABQRIRUGZJUUVFYQOVCDEDXYFPRLGSGZXSNIAVODTJKSQWHNWVPSAMZKOUDTWHIORJSCZIQYPCZMBYWKDIKOKYNGWPXZWMKRDCMBXKFUILWDHBFXRFAOPRUGDFLPDLHXXCXCUPLWGDPPHEMJGMTVMFQQFVCUPOFYWLDUEBICKPZKHKVMCJVWVKTXBKAPWAPENUEZNWNWDCACDRLPIPHJQQKMOFDQSPKKNURFBORJLBPCBIWTSJNPRBNITTKJYWAHWGKZYNUSFISPIYPIOGAUPZDXHCFVGXGIVVCPFHIXAACZXZLFDMOOSSNTKUPJQEIRRQAMUCTBLBSVPDDYOIHAOODZNJTVHDCIEGTAVMYZOCIVSKUNSMXEKBEWNZPRPWPUJABJXNQBOXSHOEGMJSNBUTGTIFVEQPSYBDXEXORPQDDODZGBELOISTRWXMEYWVVHGMJKWLJCCHPKAFRASZEYQZCVLFSLOWTLBMPPWPPFPQSAZPTULSTCDMODYKZGSRFQTRFTGCNMNXQQIYVUQZHVNIPHZWVBSGOBYIFDNNXUTBBQUYNXOZCSICGRTZSSRHROJRGBHMHEQJRDLOQBEPTOBMYLMIGPPDPOLTEUVDGATCGYPQOGOYYESKEGBLOCBIYSLQEYGCCIPBXPNSPKDYTBEWDHBHWVDPLOVHJPNYGJUHKKHDASNFGZDAIWWQEPPBRJKDGOSAFAPRLWFFXRVMZQTKRYFUBWIQPZQTJBNWGDJVETQVPEWVPNIIDKZQOUPLQFESWNPIPKEFXYMTWQYAGCGWPGIRDAEDEJLDKBSZNEAOLJHTOFHWEVAUAGXMBUTQQCVTKAQCKKSYUQZZSTBFFRTYZZVYCGMKOFWNTRJLMZGMQQGLMIEQTSVYYYDKOQTHGGQQYVBXYRYJDAFJLDUXZMJBUDONBQWDSANTPWYJDCIBAGHRPPHWPAWXYXMJICVPCBFJXSOVRTMFLYUFDTXKPVRRJFDKHOTSXJAGQZIWZDMJJZCCILKZLAGFITOFAUAIDJYNIFRWAVIIPZPAKHABRQJEYBWYFCRKXGZIMCWMJVSXIKYMEFXDVTCCXKYKAAAFGJMSYYUGRTWTDBDIDNXAGTIFWBPDCIZTYERDCQDTFGKZOJUNAXNCEKQZSAHEQLNLKLEWWWNFXXIHYYWRIEHPTCDENCGJCTEJZBCLKYWCUMYJFSVDUGPEBYJYHVRPGWGTOZSVITYEFJWEFYVIHCGNOUDDTHFZUHTXMEEQANXXZFYEQYOKHRNERUEDNYDABZRWCEXTRMHTLJRXXIBEJRIKKVUSQQCPVKJADZKRPOLAPHRSWBUHCBEZTGRCBDWZRWCWPKETONHKSYQTDMJBGDUSKRZVGBUTRCZGTYSYKZTIHNNXWLXIIHVFBYWJKIBEXCEXQVWMBZQABATWLMAZOVEXBIVOXMVLWEJNEVQZOTZPPLSANWORQJJWAZGXTIRDPRSHYDTMJRJFZLLEXZMUFTCTQBESPSUJIEHBSAEBKACLEDTEFMQOSMSRDJJEODSPOBKRSQEGDEVDNJPGCOKBPRWXTQXZSWECDGXNHIWCGPNARMOZFEJOSNHSPYBMAHJQOADPQLGXQAWNHKAQGNTNXYGVFTLNNMLIBXUWTPKUFOLKAAZBIKCJDUZNLKBWAKDMLOCQIBFVDCDUWVGCEENWHVVVDTXBCUCHDDUOHBPZMTPQCXXIAYCDZEFGNTBOIFEHFGALNDEWAZNUTNINSFZEXMLMJHMZAH";

        //监控线程
        List<Msg> list = new LinkedList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    list.add(ClientUtil.getMsg());
                }

            }
        }).start();

        CyclicBarrier end = new CyclicBarrier(threads + 1);
        CyclicBarrier start = new CyclicBarrier(threads + 1);
        //每个生产者发送的消息数
        int times = 1000;

        //TODO 用配置文件写好用例的参数，读取到内存中...

        while (true) {
            /* 1.创建kafka生产者的配置信息 */
            Properties props = new Properties();
            /*2.指定连接的kafka集群, broker-list */
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.160.133:9092");
            /*3.ack应答级别*/
            props.put(ProducerConfig.ACKS_CONFIG, "-1");
            /*4.重试次数*/
            props.put(ProducerConfig.RETRIES_CONFIG, 1);
            /*5.批次大小，一次发送多少数据，当数据大于16k，生产者会发送数据到 kafka集群 */
            props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16 * 1024);
            /*6.等待时间， 等待时间超过1毫秒，即便数据没有大于16k， 也会写数据到kafka集群 */
            props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
            /*7. RecordAccumulator 缓冲区大小*/
            props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 32 * 1024 * 1024);
            /*8. key, value 的序列化类 */
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            //启动线程
            for (int i = 0; i < threads; i++) {
                KafkaProducer<String, String> producer = new KafkaProducer<>(props);
                new Thread(new KafkaProducerThread(producer, times, start, end,msg02)).start();
            }
            log.info(props.toString());
            start.await();
            long first = System.currentTimeMillis();
            end.await();
            long second = System.currentTimeMillis();
            long useTime = second - first;
            log.info("use time : " + useTime);

            //计算CPU、内存、kafka发送速率
            float CPUMsg = 0;
            float memoryMsg = 0;
            for (Msg msg : list) {
                CPUMsg += msg.getCPUMsg();
                memoryMsg += msg.getMemoryMsg();
            }
            log.info("CPU:{}" , CPUMsg / list.size());
            log.info("Memory:{}" , memoryMsg / list.size());
            float speed = 0;
            speed = 100.0f * (times * threads) / (useTime);
            log.info("kafka speed : {}/ms",speed);
            list.clear();
        }
    }
}

class KafkaProducerThread implements Runnable {

    private KafkaProducer producer;
    private int times;
    private CyclicBarrier start;
    private CyclicBarrier end;
    private String msg;

    public KafkaProducerThread(KafkaProducer producer, int times, CyclicBarrier start, CyclicBarrier end, String msg) {
        this.producer = producer;
        this.times = times;
        this.start = start;
        this.end = end;
        this.msg = msg;
    }

    @SneakyThrows
    @Override
    public void run() {
        try {
            start.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < times; i++) {
            producer.send(new ProducerRecord("test", msg));
        }
        producer.close();
        end.await();
    }
}