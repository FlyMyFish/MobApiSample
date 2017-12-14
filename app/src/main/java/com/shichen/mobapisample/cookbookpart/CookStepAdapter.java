package com.shichen.mobapisample.cookbookpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.databinding.ItemCookStepBinding;
import com.shichen.mobapisample.databinding.ItemCookStepInfoBinding;

/**
 * Created by shichen on 2017/12/13.
 *
 * @author shichen 754314442@qq.com
 */

public class CookStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CookBook.ResultBean.ListBean.RecipeBean recipeBean;
    private Context context;

    public CookStepAdapter(CookBook.ResultBean.ListBean.RecipeBean recipeBean, Context context) {
        this.recipeBean = recipeBean;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == viewTypeInfo) {
            ItemCookStepInfoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cook_step_info, parent, false);
            return new InfoView(binding);
        } else {
            ItemCookStepBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cook_step, parent, false);
            return new DataView(binding);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataView) {
            DataView mHolder = (DataView) holder;
            mHolder.binding.setMethod(recipeBean.getStep().get(position - 1));
            Glide.with(context).load(recipeBean.getStep().get(position - 1).getImg()).into(mHolder.binding.imgCookStep);
        }
        if (holder instanceof InfoView) {
            InfoView mHolder = (InfoView) holder;
            mHolder.binding.setRecipeBean(recipeBean);
        }
    }

    @Override
    public int getItemCount() {
        if (recipeBean == null) {
            return 0;
        }
        if (recipeBean.getMethod() == null) {
            return 1;
        }
        if (recipeBean.getStep() == null) {
            return 1;
        }
        return recipeBean.getStep().size() + 1;
    }

    private final int viewTypeInfo = 0;
    private final int viewTypeItem = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return viewTypeInfo;
        }
        return viewTypeItem;
    }

    private class InfoView extends RecyclerView.ViewHolder {
        ItemCookStepInfoBinding binding;

        private InfoView(ItemCookStepInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemCookStepInfoBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemCookStepInfoBinding binding) {
            this.binding = binding;
        }
    }

    private class DataView extends RecyclerView.ViewHolder {
        ItemCookStepBinding binding;

        private DataView(ItemCookStepBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemCookStepBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemCookStepBinding binding) {
            this.binding = binding;
        }
    }
}
