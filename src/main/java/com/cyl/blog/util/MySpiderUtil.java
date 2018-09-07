package com.cyl.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class MySpiderUtil {
    private static final Logger log = LoggerFactory.getLogger(MySpiderUtil.class);

    public static void main(String[] args) {
        String  a = "aaaa";
        serializeObject(a, "/test1");
        try {
            deserializeObject("/test1");
            log.info("反系列化成功!");
        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    /**
     * 序列化对象
     * @param object
     * @throws Exception
     */
    public static void serializeObject(Object object,String filePath){
        ///src/main/resources/proxies
        OutputStream fos = null;
        //filePath="E:\\good_project\\mine\\target\\classes\\proxies.txt";
        try {
            fos = new FileOutputStream(filePath, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            log.warn("序列化成功");
            oos.flush();
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化对象
     * @param path
     * @throws Exception
     */
    public static Object deserializeObject (String path) throws Exception {
		//InputStream fis = MySpiderUtil.class.getResourceAsStream(path);
//        SpiderConfig.class.getResourceAsStream("/proxies.txt");
//        InputStream fis = SpiderConfig.class.getResourceAsStream("/proxies.txt");
        //path = "src/main/resources/proxies.txt";
        ObjectInputStream ois = null;
        Object object = null;
        InputStream fis = null;
        try {
        File file = new File(path);
        fis = new FileInputStream(file);
        ois = null;
        ois = new ObjectInputStream(fis);
        object = ois.readObject();
            log.warn("反序列化成功");
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                fis.close();
                ois.close();
            } catch (IOException e) {
               // e.printStackTrace();
            }

        }

        return object;
    }
}
