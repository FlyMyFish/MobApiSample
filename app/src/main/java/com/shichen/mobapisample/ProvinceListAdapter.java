package com.shichen.mobapisample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.bean.SupportCity;
import com.shichen.mobapisample.databinding.ItemProvinceBinding;

import java.util.List;

/**
 * Created by shichen on 2017/10/20.
 *
 * @author shichen 754314442@qq.com
 */

public class ProvinceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SupportCity.ResultBean> provinceList;
    private Context context;
    private PickTargetCityActivity.PickTargetCityHandler handler;

    public ProvinceListAdapter(List<SupportCity.ResultBean> provinceList, Context context, PickTargetCityActivity.PickTargetCityHandler handler) {
        this.provinceList = provinceList;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemProvinceBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_province, parent, false);
        return new SampleView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleView) {
            SampleView mHolder = (SampleView) holder;
            mHolder.getBinding().setDataStr(provinceList.get(position).getProvince());
            mHolder.getBinding().setHandler(handler);
            // 立刻刷新界面
            mHolder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return provinceList.size();
    }

    private class SampleView extends RecyclerView.ViewHolder {
        private ItemProvinceBinding binding;

        private SampleView(ItemProvinceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private ItemProvinceBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemProvinceBinding binding) {
            this.binding = binding;
        }
    }
}
