package com.shichen.mobapisample.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shichen on 2017/10/20.
 * Share缓存文件工具类
 *
 * @author shichen 754314442@qq.com
 */

public class SharePreferenceUtils {
    private Context context;

    public SharePreferenceUtils(Context context) {
        this.context = context;
    }

    public void saveData(String dataName,String dataStr){
        SharedPreferences sharedPreferences=context.getSharedPreferences(dataName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(dataName,dataStr);
        editor.apply();
    }

    public String getData(String dataName){
        SharedPreferences sharedPreferences=context.getSharedPreferences(dataName,Context.MODE_PRIVATE);
        return sharedPreferences.getString(dataName,"");
    }
}
