package kr.co.mash_up.crema.model;

import java.util.List;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class BaseListModel<T> {

    private List<T> datas;
    private PageModel page;


    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public PageModel getPage() {
        return page;
    }

    public void setPage(PageModel page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "BaseListModel{" +
                "datas=" + datas +
                ", page=" + page +
                '}';
    }
}
