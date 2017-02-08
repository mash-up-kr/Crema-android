package kr.co.mash_up.crema.model.cafe;

import java.util.Date;

import kr.co.mash_up.crema.model.image.ImageResourceModel;
import kr.co.mash_up.crema.model.user.UserModel;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class CafeImageModel {

    private String id;
    private String cafeId;
    private UserModel user;
    private ImageResourceModel image;
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCafeId() {
        return cafeId;
    }

    public void setCafeId(String cafeId) {
        this.cafeId = cafeId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public ImageResourceModel getImage() {
        return image;
    }

    public void setImage(ImageResourceModel image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "CafeImageModel{" +
                "id='" + id + '\'' +
                ", cafeId='" + cafeId + '\'' +
                ", user=" + user +
                ", image=" + image +
                ", createdAt=" + createdAt +
                '}';
    }
}
