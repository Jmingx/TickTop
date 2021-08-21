package club.jming.ticktopserver.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author 78289
 * <p>
 * 获取CPU信息
 */
public class MemoryUtil {

    /**
     * @return 当前系统的内存占用率
     * @throws IOException
     */
    public static float getMemoryUsage() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process exec = runtime.exec("cat /proc/meminfo");
        InputStream inputStream = exec.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        String[] memTotals = null;
        String[] memAvailables = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("MemTotal")) {
                memTotals = line.split("\\s+");
            } else if (line.startsWith("MemAvailable")) {
                memAvailables = line.split("\\s+");
            }
        }
        inputStream.close();
        reader.close();
        int memTotal = Integer.parseInt(memTotals[1]);
        int memAvailable = Integer.parseInt(memAvailables[1]);
        if (memAvailables[2].toLowerCase().equals("kb")) {
            memAvailable *= 1024;
        } else if (memAvailables[2].toLowerCase().equals("mb")) {
            memAvailable *= 1024 * 1024;
        } else if (memAvailables[2].toLowerCase().equals("gb")) {
            memAvailable *= 1024 * 1024 * 1024;
        }

        if (memTotals[2].toLowerCase().equals("kb")) {
            memTotal *= 1024;
        } else if (memTotals[2].toLowerCase().equals("mb")) {
            memTotal *= 1024 * 1024;
        } else if (memTotals[2].toLowerCase().equals("gb")) {
            memTotal *= 1024 * 1024 * 1024;
        }

        return 100.0f * (memTotal - memAvailable) / memTotal;
    }

}
