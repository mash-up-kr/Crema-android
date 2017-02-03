package kr.co.mash_up.crema.model.cafe;

import com.mashup.crema.backend.model.cafe.meta.CafeMetaModel;
import com.mashup.crema.backend.model.image.ImageResourceModel;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class CafeModel {

    private String id;
    private String name;
    private boolean opened;
    private CafeLocationModel location;
    private ImageResourceModel thumbnail;
    private CafeMetaModel meta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public CafeLocationModel getLocation() {
        return location;
    }

    public void setLocation(CafeLocationModel location) {
        this.location = location;
    }

    public ImageResourceModel getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageResourceModel thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CafeMetaModel getMeta() {
        return meta;
    }

    public void setMeta(CafeMetaModel meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "CafeModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", opened=" + opened +
                ", location=" + location +
                ", thumbnail=" + thumbnail +
                ", meta=" + meta +
                '}';
    }
}
