package kr.co.mash_up.crema.model.cafe.review;

import com.mashup.crema.backend.model.cafe.CafeModel;
import com.mashup.crema.backend.model.user.UserModel;

import java.util.Date;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class ReviewModel {

    private CafeModel cafe;
    private UserModel user;

    private String content;
    private Date createdAt;


}
