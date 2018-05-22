package com.shichen.mobapisample.cookbookpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBookTab;
import com.shichen.mobapisample.databinding.ItemCookBookMenuBinding;

/**
 * Created by shichen on 2017/12/8.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBookMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private CookBookTab.CookBookBean.ChildsBeanX childsBeanX;
    private Context context;
    private CookBookMenuActivity.Handler handler;

    public CookBookMenuAdapter(CookBookTab.CookBookBean.ChildsBeanX childsBeanX, Context context, CookBookMenuActivity.Handler handler) {
        this.childsBeanX = childsBeanX;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCookBookMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cook_book_menu, parent, false);
        return new DataView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataView) {
            DataView mHolder = (DataView) holder;
            mHolder.binding.setChildBean(childsBeanX.getChilds().get(position));
            mHolder.binding.setPosition(mHolder.getAdapterPosition());
            mHolder.binding.setHandler(handler);
        }
    }

    @Override
    public int getItemCount() {
        if (childsBeanX == null) {
            return 0;
        }
        if (childsBeanX.getChilds() == null) {
            return 0;
        }
        return childsBeanX.getChilds().size();
    }

    private class DataView extends RecyclerView.ViewHolder {
        ItemCookBookMenuBinding binding;

        private DataView(ItemCookBookMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemCookBookMenuBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemCookBookMenuBinding binding) {
            this.binding = binding;
        }
    }
}
