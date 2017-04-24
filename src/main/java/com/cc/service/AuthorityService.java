package com.cc.service;

import com.cc.pojo.PostData;

import java.util.Date;

/**
 * Created by cc on 2017/4/24.
 */
public interface AuthorityService {


    String responseMessage(PostData postData);
    String responseMessage2(PostData postData);
    String verify(String token);
    String dataCompare(Date expiration_date);

}
