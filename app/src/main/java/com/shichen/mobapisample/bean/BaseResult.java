package com.shichen.mobapisample.bean;

import com.google.gson.Gson;

/**
 * @author shichen 754314442@qq.com
 *         Created by shichen on 2017/10/18.
 */

public class BaseResult {
    /**
     * 转换为json字符串
     * @return string
     */
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
