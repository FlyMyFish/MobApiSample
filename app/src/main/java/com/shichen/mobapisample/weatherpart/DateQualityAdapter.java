package com.shichen.mobapisample.weatherpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.AirQuality;
import com.shichen.mobapisample.databinding.ItemDateQualityBinding;

/**
 * Created by shichen on 2017/11/21.
 *
 * @author shichen 754314442@qq.com
 */

public class DateQualityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private AirQuality.AirData airQuality;

    public DateQualityAdapter(Context context, AirQuality.AirData airQuality) {
        this.context = context;
        this.airQuality = airQuality;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDateQualityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_date_quality, parent, false);
        return new DataView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataView) {
            DataView mHolder = (DataView) holder;
            mHolder.binding.setFetureAirData(airQuality.getFetureData().get(position));
        }
    }

    @Override
    public int getItemCount() {
        return airQuality.getFetureData().size();
    }

    private class DataView extends RecyclerView.ViewHolder {
        ItemDateQualityBinding binding;

        private DataView(ItemDateQualityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private ItemDateQualityBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemDateQualityBinding binding) {
            this.binding = binding;
        }
    }
}
