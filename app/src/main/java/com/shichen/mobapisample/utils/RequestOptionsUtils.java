package com.shichen.mobapisample.utils;

import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.shichen.mobapisample.R;

/**
 * Created by shichen on 2017/12/8.
 *
 * @author shichen 754314442@qq.com
 */

public class RequestOptionsUtils {
    public static RequestOptions rectOption() {
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.icon_cook_default)
                .error(R.drawable.icon_cook_default)
                .priority(Priority.HIGH);
    }
}
