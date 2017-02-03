package kr.co.mash_up.crema.model.cafe.meta;

/**
 * Created by bigstark on 2017. 2. 1..
 */
public class CafeMetaModel {

    private boolean wifiEnable;
    private boolean smokable;
    private boolean chargable;
    private boolean parkable;
    private boolean seminarEnable;
    private boolean allday;

    private CafeSize size;
    private CafeConvenience convenience;
    private CafeMood mood;
    private CafeToilet toilet;

    public boolean isWifiEnable() {
        return wifiEnable;
    }

    public void setWifiEnable(boolean wifiEnable) {
        this.wifiEnable = wifiEnable;
    }

    public boolean isSmokable() {
        return smokable;
    }

    public void setSmokable(boolean smokable) {
        this.smokable = smokable;
    }

    public boolean isChargable() {
        return chargable;
    }

    public void setChargable(boolean chargable) {
        this.chargable = chargable;
    }

    public boolean isParkable() {
        return parkable;
    }

    public void setParkable(boolean parkable) {
        this.parkable = parkable;
    }

    public boolean isSeminarEnable() {
        return seminarEnable;
    }

    public void setSeminarEnable(boolean seminarEnable) {
        this.seminarEnable = seminarEnable;
    }

    public boolean isAllday() {
        return allday;
    }

    public void setAllday(boolean allday) {
        this.allday = allday;
    }

    public CafeSize getSize() {
        return size;
    }

    public void setSize(CafeSize size) {
        this.size = size;
    }

    public CafeConvenience getConvenience() {
        return convenience;
    }

    public void setConvenience(CafeConvenience convenience) {
        this.convenience = convenience;
    }

    public CafeMood getMood() {
        return mood;
    }

    public void setMood(CafeMood mood) {
        this.mood = mood;
    }

    public CafeToilet getToilet() {
        return toilet;
    }

    public void setToilet(CafeToilet toilet) {
        this.toilet = toilet;
    }
}
