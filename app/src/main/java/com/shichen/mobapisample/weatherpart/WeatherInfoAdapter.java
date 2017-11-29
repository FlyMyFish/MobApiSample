package com.shichen.mobapisample.weatherpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.WeatherInfo;
import com.shichen.mobapisample.databinding.ItemWeatherInfoBaseBinding;
import com.shichen.mobapisample.databinding.ItemWeatherInfoFutureBinding;

/**
 * Created by shichen on 2017/11/9.
 *
 * @author shichen 754314442@qq.com
 */

public class WeatherInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int WEATHER_INFO_BASE = 0;
    private final int WEATHER_INFO_FUTURE = 1;
    private Context context;
    private WeatherInfo.WeatherBean weatherBean;
    private WeatherInfoActivity.Handler handler;

    public WeatherInfoAdapter(Context context, WeatherInfo.WeatherBean weatherBean, WeatherInfoActivity.Handler handler) {
        this.context = context;
        this.weatherBean = weatherBean;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case WEATHER_INFO_BASE: {
                ItemWeatherInfoBaseBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_weather_info_base, parent, false);
                return new WeatherInfoBase(binding);
            }
            case WEATHER_INFO_FUTURE: {
                ItemWeatherInfoFutureBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_weather_info_future, parent, false);
                return new WeatherInfoFuture(binding);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null) {
            if (holder instanceof WeatherInfoBase) {
                WeatherInfoBase mHolder = (WeatherInfoBase) holder;
                mHolder.getBinding().setWeatherBean(weatherBean);
                mHolder.getBinding().executePendingBindings();
                mHolder.getBinding().setHandler(handler);
            }
            if (holder instanceof WeatherInfoFuture) {
                WeatherInfoFuture mHolder = (WeatherInfoFuture) holder;
                mHolder.getBinding().setFutureBean(weatherBean.getFuture().get(position - 1));
                mHolder.getBinding().executePendingBindings();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (weatherBean == null) {
            return 0;
        }
        if (weatherBean.getFuture()==null){
            return 0;
        }
        return weatherBean.getFuture().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return WEATHER_INFO_BASE;
        } else {
            return WEATHER_INFO_FUTURE;
        }
    }

    private class WeatherInfoBase extends RecyclerView.ViewHolder {
        private ItemWeatherInfoBaseBinding binding;

        private WeatherInfoBase(ItemWeatherInfoBaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private ItemWeatherInfoBaseBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemWeatherInfoBaseBinding binding) {
            this.binding = binding;
        }
    }

    private class WeatherInfoFuture extends RecyclerView.ViewHolder {
        private ItemWeatherInfoFutureBinding binding;

        private WeatherInfoFuture(ItemWeatherInfoFutureBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemWeatherInfoFutureBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemWeatherInfoFutureBinding binding) {
            this.binding = binding;
        }
    }
}
