package com.cc.service.Imlp;

import com.cc.algorithm.Algorithm;
import com.cc.dao.AuthorityMapper;
import com.cc.dao.BookMapper;
import com.cc.dao.ShipMapper;
import com.cc.pojo.Authority;
import com.cc.pojo.PostData;
import com.cc.pojo.Result;
import com.cc.pojo.Ship;
import com.cc.service.AuthorityService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by cc on 2017/4/24.
 */
@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private ShipMapper shipMapper;
    @Resource
    private AuthorityMapper authorityMapper;



    @Override
    public String responseMessage(PostData postData) {
        Result result = new Result();
        String message=verify(postData.getToken());
        if(message.equals("success")){
            //验证成功进行预测
            Algorithm algorithm = new Algorithm();
            List<Ship> data=algorithm.predict(postData.getData());
            //将预测结果和数据存入数据库。
            for (Ship ship:data
                    ) {
                shipMapper.insertShip(ship);
            }

            result.setMessage(message);
            result.setData(data);
        }else {
            result.setMessage(message);
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @Override
    public String responseMessage2(PostData postData) {
        Result result = new Result();
        String message=verify(postData.getToken());
        if(message.equals("success")){
            //验证成功进行预测
            Algorithm algorithm = new Algorithm();
            List<Ship> data=algorithm.predict2(postData.getData());
            //将预测结果和数据存入数据库。
            for (Ship ship:data
                    ) {
                shipMapper.insertShip(ship);
            }
            result.setMessage(message);
            result.setData(data);
        }else {
            result.setMessage(message);
        }
        Gson gson = new Gson();
        return gson.toJson(result);
    }

    @Override
    public String verify(String token) {
        Authority authority = new Authority();
        authority.setToken("donghui");
        //find the expiration_date through the token which is get from the post request
        Date expiration_date=null;
        try {
            expiration_date = authorityMapper.selectAuthority(authority).getExpirationDate();
        }catch (Exception e){
            return "Please contact your administrator!";
        }
        if(expiration_date==null){
            return "Please contact your administrator!";
        }
        return dataCompare(expiration_date);
    }

    @Override
    public String dataCompare(Date expiration_date) {
        //获取当前时间
        Date nowdate=new Date();
        //给定一个到期时间
        String outDate = new SimpleDateFormat("yyyy-MM-dd").format(expiration_date);
        //设置要转换的日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

        Date d;
        try {
            d = sdf.parse(outDate);
            boolean flag = d.before(nowdate);
            if(flag)
            {
                //今天的日期大于比给定的到期时间(outDate)(即今天的日期在到期日期之后，也就是当前时间已到期)
//                System.out.print("已到期");
                return "使用权限已到期";
            }

            else
            {
                //今天的日期小于比给定的到期时间(outDate)(即今天的日期在到期日期之前，也就是当前时间还未到期)
//                System.out.print("未到期");
                return "success";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
