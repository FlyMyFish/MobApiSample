package com.shichen.mobapisample.weatherapi;

import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.bean.CookBookTab;
import com.shichen.mobapisample.bean.CookInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public interface ICookBookApi {
    /**
     * 得到菜谱
     *
     * @return Observable
     */
    @GET("category/query?key=212d475a7aaa0")
    Observable<CookBookTab> getCookBookTab();

    /**
     * 按标签查询菜谱
     *
     * @param page
     * @param name
     * @param cid
     * @param size
     * @return
     */
    @GET("menu/search?key=212d475a7aaa0")
    Observable<CookBook> getCookBook(@Query("page") int page,
                                     @Query("name") String name,
                                     @Query("cid") String cid,
                                     @Query("size") int size);

    /**
     * 菜谱查询
     *
     * @param id
     * @return
     */
    @GET("menu/query?key=212d475a7aaa0")
    Observable<CookInfo> getCookInfo(@Query("id") int id);
}
