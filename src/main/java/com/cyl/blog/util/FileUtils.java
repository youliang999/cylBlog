package com.cyl.blog.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * 文件工具类
 * 
 * @author zhou
 * 
 */
public class FileUtils{
  private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
  private FileUtils(){
  }

  /**
   * 获取文件名后缀(不含".")
   * 
   * @param filename
   * @return
   */
  public static String getFileExt(String filename){
    int point = filename.lastIndexOf(".");
    return filename.substring(point + 1);
  }

  /**
   * 获取文件名(不包含后缀)
   * 
   * @param filename
   * @return
   */
  public static String getFileName(String filename){
    log.info("===>>> fileName:{}", filename);
    filename = getFileNameWithExt(filename);
    log.info("===>>> getFileNameWithExt fileName:{}", filename);
    int point = filename.lastIndexOf(".");
    return filename.substring(0, point);
  }

  /**
   * 获取文件名(包含后缀)
   * 
   * @param filename
   * @return
   */
  public static String getFileNameWithExt(String filename){
    int slash = filename.lastIndexOf("/");
    return filename.substring(slash + 1);
  }

  /**
   * 判断指定格式是否为图片
   * 
   * @param ext
   * @return
   */
  public static boolean isImageExt(String ext){
    return ext != null && Arrays.asList("jpg", "jpeg", "png", "bmp", "gif").contains(ext.toLowerCase());
  }

  /**
   * 生成文件存储名
   * 
   * @param parent
   * @param fileName
   * @return
   */
  public static File determineFile(File parent, String fileName){
    String name = getFileName(fileName);
    String ext = getFileExt(fileName);
    File temp = new File(parent, fileName);
    for(int i = 1; temp.exists(); i++){
      temp = new File(parent, name + i + "." + ext);
    }

    return temp;
  }

}
