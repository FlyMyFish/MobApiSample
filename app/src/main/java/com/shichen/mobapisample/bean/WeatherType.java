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
}
