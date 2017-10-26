package com.shichen.mobapisample.bean;

/**
 * @author shichen 754314442@qq.com
 *         Created by Administrator on 2017/9/21.
 */

public class WeatherType extends BaseResult {

    /**
     * msg : success
     */
    private String msg;
    /**
     * result : 多云,少云,晴,阴,小雨,雨,雷阵雨,中雨,阵雨,零散阵雨,零散雷雨,小雪,雨夹雪,阵雪,霾
     */
    private String result;
    /**
     * retCode : 200
     */
    private String retCode;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public static final String BIG_CLOUD = "多云";
    public static final String SMALL_CLOUD = "少云";
    public static final String BIG_SUN = "晴";
    public static final String BLACK_CLOUD = "阴";
    public static final String SMALL_RAIN = "小雨";
    public static final String NORMAL_RAIN = "雨";
    public static final String THUNDER_CIRCLE_RAIN = "雷阵雨";
    public static final String MIDDLE_RAIN = "中雨";
    public static final String CIRCLE_RAIN = "阵雨";
    public static final String ZERO_CIRCLE_RAIN = "零散阵雨";
    public static final String ZERO_THUNDER_RAIN = "零散雷雨";
    public static final String SMALL_SNOW = "小雪";
    public static final String RAIN_ADD_SNOW = "雨夹雪";
    public static final String CIRCLE_SNOW = "阵雪";
    public static final String HAZE = "霾";
}
