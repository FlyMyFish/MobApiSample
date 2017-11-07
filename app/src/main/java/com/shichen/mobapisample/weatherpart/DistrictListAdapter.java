package com.shichen.mobapisample.weatherpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.databinding.ItemDistrictBinding;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class DistrictListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SupportCity.ResultBean.CityBean.DistrictBean> districtList;
    private Context context;
    private PickTargetCityActivity.PickTargetCityHandler handler;

    public DistrictListAdapter(List<SupportCity.ResultBean.CityBean.DistrictBean> districtList, Context context, PickTargetCityActivity.PickTargetCityHandler handler) {
        this.districtList = districtList;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDistrictBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_district, parent, false);
        return new SampleView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleView) {
            SampleView mHolder = (SampleView) holder;
            mHolder.getBinding().setDistrict(districtList.get(position).getDistrict());
            mHolder.getBinding().setHandler(handler);
            // 立刻刷新界面
            mHolder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return districtList == null ? 0 : districtList.size();
    }

    private class SampleView extends RecyclerView.ViewHolder {
        private ItemDistrictBinding binding;

        private SampleView(ItemDistrictBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private ItemDistrictBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemDistrictBinding binding) {
            this.binding = binding;
        }
    }
}
