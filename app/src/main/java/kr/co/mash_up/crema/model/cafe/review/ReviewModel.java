package kr.co.mash_up.crema.model.cafe.review;


import java.util.Date;

import kr.co.mash_up.crema.model.cafe.CafeModel;
import kr.co.mash_up.crema.model.user.UserModel;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class ReviewModel {

    private CafeModel cafe;
    private UserModel user;

    private String content;
    private Date createdAt;


    public CafeModel getCafe() {
        return cafe;
    }

    public UserModel getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
