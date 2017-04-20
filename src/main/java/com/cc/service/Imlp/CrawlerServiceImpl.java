package com.cc.service.Imlp;

import com.cc.service.CrawlerService;
import com.cc.timeTask.MyTimeTaskImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by cc on 2017/4/19.
 */

@Service("crawlerService")
public class CrawlerServiceImpl implements CrawlerService{
    @Override
    public void DataCrawler() {
        String crawler_path=null;
        String crawler_path2=null;
        Properties prop = new Properties();
        try {
            prop.load(MyTimeTaskImpl.class.getClassLoader().getResourceAsStream("file.properties"));
            crawler_path=prop.getProperty("crawler_path");
            crawler_path2=prop.getProperty("crawler_path2");
        } catch(IOException e) {
            e.printStackTrace();
        }
        //调用python脚本进行文件爬取
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec("python  "+crawler_path);
            proc = Runtime.getRuntime().exec("python  "+crawler_path2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
