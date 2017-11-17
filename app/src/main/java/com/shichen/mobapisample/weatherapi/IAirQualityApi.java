package com.shichen.mobapisample.weatherapi;

import com.shichen.mobapisample.bean.AirQuality;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shichen on 2017/11/17.
 *
 * @author shichen 754314442@qq.com
 */

public interface IAirQualityApi {
    /**
     * 获取指定城市的空气质量数据
     * @param city
     * @param province
     * @return
     */
    @GET("query?key=212d475a7aaa0")
    Observable<AirQuality> getAirQuality(@Query("city") String city,
                                         @Query("province") String province);
}
