package com.shichen.mobapisample.weatherapi;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.bean.WeatherType;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 1.根据城市名查询天气http://apicloud.mob.com/v1/weather/query
 * http://apicloud.mob.com/v1/weather/query?key=appkey&city=通州&province=北京
 * key	string	是	用户申请的appkey
 * city	string	是	城市名（url编码）
 * province	string	否	当前城市的所属省份 如：北京-通州、江苏-通州（url编码）
 * 2.城市列表查询接口http://apicloud.mob.com/v1/weather/citys
 * http://apicloud.mob.com/v1/weather/citys?key=appkey
 * key	string	是	用户申请的appkey
 * 3.天气类型查询http://apicloud.mob.com/v1/weather/type
 * http://apicloud.mob.com/v1/weather/type?key=appkey
 * key	string	是	用户申请的appkey
 * @author shichen 754314442@qq.com
 * Created by Administrator on 2017/9/21.
 */

public interface IWeatherApi {
    /**
     * 根据省市获取天气信息
     * @param city 城市名
     * @param province 省名
     * @return Observable
     */
    @GET("query?key=212d475a7aaa0")
    Observable<WeatherInfo> getWeatherInfoByCityAndProvince(@Query("city") String city,
                                                            @Query("province") String province);

    /**
     * 得到现有的城市列表
     * @return Observable
     */
    @GET("citys?key=212d475a7aaa0")
    Observable<SupportCity> getApiSupportCity();

    /**
     * 天气类型列表
     * @return Observable
     */
    @GET("type?key=212d475a7aaa0")
    Observable<WeatherType> getApiWeatherType();
}
