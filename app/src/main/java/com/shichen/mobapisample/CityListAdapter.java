package com.shichen.mobapisample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.databinding.ItemCityBinding;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class CityListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SupportCity.ResultBean.CityBean> cityList;
    private Context context;
    private PickTargetCityActivity.PickTargetCityHandler handler;

    public CityListAdapter(List<SupportCity.ResultBean.CityBean> cityList, Context context, PickTargetCityActivity.PickTargetCityHandler handler) {
        this.cityList = cityList;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_city, parent, false);
        return new SampleView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleView) {
            SampleView mHolder = (SampleView) holder;
            mHolder.getBinding().setCity(cityList.get(position).getCity());
            mHolder.getBinding().setHandler(handler);
            // 立刻刷新界面
            mHolder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return cityList == null ? 0 : cityList.size();
    }

    private class SampleView extends RecyclerView.ViewHolder {
        private ItemCityBinding binding;

        private SampleView(ItemCityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private ItemCityBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemCityBinding binding) {
            this.binding = binding;
        }
    }
}
