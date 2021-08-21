package club.jming.utils;

import club.jming.entity.Msg;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class ClientUtil {

    private static String url;

    static {
        Properties properties = new Properties();
        InputStream in = ClientUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(in);
            url = "http://"+properties.getProperty("Host")+":"+properties.getProperty("Port")+properties.getProperty("Path");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Msg getMsg(){
        String res = ClientUtil.doGet(url);
        ObjectMapper mapper = new ObjectMapper();
        Msg msg = null;
        try {
            msg = mapper.readValue(res, Msg.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * Http get请求
     * @param httpUrl 连接
     * @return 响应数据
     */
    public static String doGet(String httpUrl){
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        StringBuffer result = new StringBuffer();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();
            //获取响应数据
            if (connection.getResponseCode() == 200) {
                //获取返回的数据
                is = connection.getInputStream();
                if (null != is) {
                    br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    String temp = null;
                    while (null != (temp = br.readLine())) {
                        result.append(temp);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭远程连接
            connection.disconnect();
        }
        return result.toString();
    }
}
