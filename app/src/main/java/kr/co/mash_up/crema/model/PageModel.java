package kr.co.mash_up.crema.model;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class PageModel {

    public PageModel(int total, String cursor) {
        this.total = total;
        this.cursor = cursor;
    }

    private int total;
    private String cursor;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    @Override
    public String toString() {
        return "PageModel{" +
                "total=" + total +
                ", cursor='" + cursor + '\'' +
                '}';
    }
}
