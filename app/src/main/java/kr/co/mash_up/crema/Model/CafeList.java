package kr.co.mash_up.crema.Model;

/**
 * Created by sun on 2017. 1. 24..
 */

public class CafeList {
    int img;
    String name;
    String addr;
    String hours;

    public CafeList(int img, String name, String addr, String hours) {
        this.img = img;
        this.name = name;
        this.addr = addr;
        this.hours = hours;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String time) {
        this.hours = hours;
    }
}
