package com.shichen.mobapisample.cookbookpart;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.databinding.ItemCookBookListBinding;
import com.shichen.mobapisample.utils.RequestOptionsUtils;

/**
 * Created by shichen on 2017/12/8.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBookListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private CookBook cookBook;

    public CookBookListAdapter(Context context, CookBook cookBook) {
        this.context = context;
        this.cookBook = cookBook;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCookBookListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_cook_book_list, parent, false);
        return new DataView(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof DataView) {
            DataView mHolder = (DataView) holder;
            mHolder.binding.setListBean(cookBook.getResult().getList().get(position));
            Glide.with(context).load(cookBook.getResult().getList().get(position).getThumbnail()).apply(RequestOptionsUtils.rectOption()).into(mHolder.binding.imgFood);
        }
    }

    @Override
    public int getItemCount() {
        if (cookBook == null) {
            return 0;
        }
        if (cookBook.getResult() == null) {
            return 0;
        }
        if (cookBook.getResult().getList() == null) {
            return 0;
        }
        return cookBook.getResult().getList().size();
    }

    private class DataView extends RecyclerView.ViewHolder {
        ItemCookBookListBinding binding;

        private DataView(ItemCookBookListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemCookBookListBinding getBinding() {
            return binding;
        }

        public void setBinding(ItemCookBookListBinding binding) {
            this.binding = binding;
        }
    }
}
