package com.shichen.mobapisample.cookbookpart;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SearchView;

import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.bean.CookBookTab;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.databinding.ActivityCookBookMenuBinding;
import com.shichen.mobapisample.utils.DividerItemDecoration;
import com.shichen.mobapisample.utils.SharePreferenceUtils;
import com.shichen.mobapisample.weatherapi.ICookBookApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shichen.mobapisample.cookbookpart.ViewCookBookActivity.VIEW_COOK_BOOK;

/**
 * Created by shichen on 2017/12/7.
 *
 * @author shichen 754314442@qq.com
 */

public class CookBookMenuActivity extends BaseActivity {
    private ActivityCookBookMenuBinding binding;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cook_book_menu);
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showLoadingDialog("获取菜谱内容");
        getCookBookRetrofit().create(ICookBookApi.class)
                .getCookBookTab()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CookBookTab>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(CookBookTab cookBookTab) {
                        disMissLoadingDialog();
                        bindTab(cookBookTab);
                        searchCookBookList(cookBookTab.getResult().getChilds().get(0).getChilds().get(0), "");
                    }

                    @Override
                    public void onError(Throwable e) {
                        disMissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        disMissLoadingDialog();
                    }
                });
        binding.rvCookBook.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        binding.rvCookList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        handler = new Handler();
        binding.setHandler(handler);
        binding.searchCook.setSubmitButtonEnabled(true);
        //添加下面一句,防止数据两次加载
        binding.searchCook.setIconified(true);
        binding.searchCook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCookBookList(handler.mChildsBean, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void bindTab(final CookBookTab cookBookTab) {
        TabLayout tabLayout = binding.tabCookBook;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.setChildsBeanX(cookBookTab.getResult().getChilds().get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < cookBookTab.getResult().getChilds().size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(cookBookTab.getResult().getChilds().get(i).getCategoryInfo().getName().replace("选择菜谱", "")));
        }
    }

    public class Handler {
        private CookBookTab.CookBookBean.ChildsBeanX.ChildsBean mChildsBean;

        public void menuClick(CookBookTab.CookBookBean.ChildsBeanX.ChildsBean childsBean) {
            mChildsBean = childsBean;
            searchCookBookList(childsBean, "");
        }

        public void cookBookClick(CookBook.ResultBean.ListBean listBean, View view) {
            SharePreferenceUtils preferenceUtils = new SharePreferenceUtils(getApplicationContext());
            preferenceUtils.saveData(VIEW_COOK_BOOK, listBean.toString());
            Intent intent = new Intent(CookBookMenuActivity.this, ViewCookBookActivity.class);
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(CookBookMenuActivity.this
                    , new Pair<>(view, "img_food_pic_"));
            startActivity(intent, activityOptionsCompat.toBundle());
        }
    }

    private void searchCookBookList(CookBookTab.CookBookBean.ChildsBeanX.ChildsBean childsBean, String keyWord) {
        handler.mChildsBean=childsBean;
        if (childsBean == null) {
            return;
        }
        showLoadingDialog("获取菜谱列表");
        getCookBookRetrofit().create(ICookBookApi.class)
                .getCookBook(1, keyWord, childsBean.getCategoryInfo().getCtgId(), 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CookBook>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(CookBook cookBook) {
                        disMissLoadingDialog();
                        binding.setCookBook(cookBook);
                    }

                    @Override
                    public void onError(Throwable e) {
                        disMissLoadingDialog();
                    }

                    @Override
                    public void onComplete() {
                        disMissLoadingDialog();
                    }
                });
    }
}
