package club.jming.ticktopserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author 78289
 * <p>
 * 获取CPU信息
 */
public class CPUUtil {

    /**
     * tickTop时间
     */
    public static long tickTop;

    static {
        Properties properties = new Properties();
        InputStream in = CPUUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(in);
            tickTop = Long.parseLong(properties.getProperty("tickTop"));
        } catch (IOException e) {
            System.out.println("读取配置文件错误");
            e.printStackTrace();
        }
    }

    /**
     * 测试获取CPU信息(cat /proc/stat)
     *
     * @throws IOException
     */
    public static void getCPUStat() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process exec = runtime.exec("cat /proc/stat");
        InputStream execInputStream = exec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(execInputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        execInputStream.close();
        reader.close();
    }

    private static int[] getCPUUsage0() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process exec = runtime.exec("cat /proc/stat");
        InputStream execInputStream = exec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(execInputStream));
        String line = null;
        List<String> lines = new LinkedList<>();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.startsWith("cpu")) {
                lines.add(line.trim());
            }
        }
        int user = 0;
        int nice = 0;
        int system = 0;
        int idle = 0;

        for (String s : lines) {
//            name    user    nice    system  idle    iowait  irrq    softirq     steal   guest       guest_nice
//            CPU利用率   =   100   *（user   +   nice   +   system）/（user   +   nice   +   system   +   idle）
//            匹配一个或多个空格
            String[] msg = s.split("\\s+");
            user += Integer.parseInt(msg[1]);
            nice += Integer.parseInt(msg[2]);
            system += Integer.parseInt(msg[3]);
            idle += Integer.parseInt(msg[4]);
        }
        execInputStream.close();
        reader.close();
        return new int[]{user, nice, system, idle};
    }

    /**
     * @return 获取tickTop时间内的CPU使用率
     * @throws IOException
     * @throws InterruptedException
     */
    public static float getCPUUsage() throws IOException, InterruptedException {
        int[] first = getCPUUsage0();
        Thread.sleep(tickTop);
        int[] second = getCPUUsage0();
        int user = second[0] - first[0];
        int nice = second[1] - first[1];
        int system = second[2] - first[2];
        int idle = second[3] - first[3];
        float usage = 100.0f * (user + nice + system) / (user + nice + system + idle);
//        System.out.println("CPU使用率 : " + usage + "%");
        return usage;
    }

}
