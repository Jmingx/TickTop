package club.jming;

import club.jming.entity.Msg;
import club.jming.utils.ClientUtil;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {


    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
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

        CyclicBarrier end = new CyclicBarrier(17);
        CyclicBarrier start = new CyclicBarrier(17);
        //TODO 用配置文件写好用例的参数，读取到内存中...
        while (true) {
            //启动线程
            for (int i = 0; i < 16; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            start.await();
                            for (int j = 0; j < 10; j++) {
                                Thread.sleep(1000);
                                System.out.println(Thread.currentThread().getName());
                            }
                            end.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (BrokenBarrierException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            start.await();
            long first = System.currentTimeMillis();
            end.await();
            long second = System.currentTimeMillis();
            long useTime = second-first;
            System.out.println("use time : "+useTime);

            //计算CPU、内存、kafka发送速率
            float CPUMsg = 0;
            float memoryMsg = 0;
            for (Msg msg : list) {
                CPUMsg += msg.getCPUMsg();
                memoryMsg += msg.getMemoryMsg();
            }
            System.out.println(list.size());
            System.out.println(Arrays.toString(list.toArray()));
            System.out.println("CPU:" + CPUMsg / list.size());
            System.out.println("Memory:" + memoryMsg / list.size());
            list.clear();
        }
    }
}
