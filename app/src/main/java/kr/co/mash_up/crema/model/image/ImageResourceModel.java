package kr.co.mash_up.crema.model.image;

import java.util.Date;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class ImageResourceModel {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageResourceModel{" +
                "url='" + url + '\'' +
                '}';
    }
}
