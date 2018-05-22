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

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shichen.mobapisample.R;
import com.shichen.mobapisample.bean.CookBook;
import com.shichen.mobapisample.bean.CookBookTab;
import com.shichen.mobapisample.config.BaseActivity;
import com.shichen.mobapisample.config.Config;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cook_book_menu);
        //初始化页数
        binding.setCurPage(1);
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
                        pageState = Config.PAGE_DATA_FOR_LOAD_INIT;
                        searchCookBookList();
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
        Handler handler = new Handler();
        binding.setHandler(handler);
        binding.searchCook.setSubmitButtonEnabled(true);
        //添加下面一句,防止数据两次加载
        binding.searchCook.setIconified(true);
        binding.searchCook.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCookBookList();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageState = Config.PAGE_DATA_FOR_LOAD_REFRESH;
                //初始化页数
                binding.setCurPage(1);
                searchCookBookList();
            }
        });
        binding.refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageState = Config.PAGE_DATA_FOR_LOAD_LOAD_MORE;
                binding.setCurPage(binding.getCurPage() + 1);
                searchCookBookList();
            }
        });
    }

    private void bindTab(final CookBookTab cookBookTab) {
        TabLayout tabLayout = binding.tabCookBook;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.setChildsBeanX(cookBookTab.getResult().getChilds().get(tab.getPosition()));
                if (cookBookTab.getResult().getChilds().get(tab.getPosition()) != null) {
                    if (cookBookTab.getResult().getChilds().get(tab.getPosition()).getChilds() != null) {
                        binding.setMChildBean(cookBookTab.getResult().getChilds().get(tab.getPosition()).getChilds().get(0));
                        binding.setMenuPosition(0);
                    }
                }
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
        public void menuClick(CookBookTab.CookBookBean.ChildsBeanX.ChildsBean childsBean,int position) {
            binding.setMChildBean(childsBean);
            binding.setCurPage(1);
            pageState = Config.PAGE_DATA_FOR_LOAD_INIT;
            searchCookBookList();
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

    private void searchCookBookList() {
        if (binding.getMChildBean()==null){
            return;
        }
        if (pageState == Config.PAGE_DATA_FOR_LOAD_INIT) {
            showLoadingDialog("获取菜谱列表");
        }
        getCookBookRetrofit().create(ICookBookApi.class)
                .getCookBook(binding.getCurPage(), binding.searchCook.getQuery().toString(), binding.getMChildBean().getCategoryInfo().getCtgId(), 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CookBook>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposableList.add(d);
                    }

                    @Override
                    public void onNext(CookBook cookBook) {
                        if (pageState == Config.PAGE_DATA_FOR_LOAD_REFRESH) {
                            if (cookBook.getResult().getList() != null) {
                                binding.getCookBookList().clear();
                                binding.getCookBookList().addAll(cookBook.getResult().getList());
                                binding.rvCookList.getAdapter().notifyDataSetChanged();
                                binding.refreshLayout.finishRefresh(true);
                            } else {
                                binding.refreshLayout.finishRefresh(false);
                            }
                        } else if (pageState == Config.PAGE_DATA_FOR_LOAD_LOAD_MORE) {
                            if (cookBook.getResult().getList() != null) {
                                if (cookBook.getResult().getList().size() == 0) {
                                    binding.setCurPage(binding.getCurPage() - 1);
                                } else {
                                    binding.getCookBookList().addAll(cookBook.getResult().getList());
                                    binding.rvCookList.getAdapter().notifyDataSetChanged();
                                }
                                binding.refreshLayout.finishLoadmore(true);
                            } else {
                                binding.refreshLayout.finishLoadmore(false);
                            }
                        } else if (pageState == Config.PAGE_DATA_FOR_LOAD_INIT) {
                            disMissLoadingDialog();
                            if (cookBook.getResult().getList() != null) {
                                binding.setCookBookList(cookBook.getResult().getList());
                            }else {
                                binding.getCookBookList().clear();
                                binding.rvCookList.getAdapter().notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (pageState == Config.PAGE_DATA_FOR_LOAD_LOAD_MORE) {
                            binding.setCurPage(binding.getCurPage() - 1);
                        }
                        if (pageState == Config.PAGE_DATA_FOR_LOAD_INIT) {
                            disMissLoadingDialog();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (pageState == Config.PAGE_DATA_FOR_LOAD_INIT) {
                            disMissLoadingDialog();
                        }
                        if (pageState == Config.PAGE_DATA_FOR_LOAD_REFRESH) {
                            binding.refreshLayout.finishRefresh();
                        } else if (pageState == Config.PAGE_DATA_FOR_LOAD_LOAD_MORE) {
                            binding.refreshLayout.finishLoadmore();
                        }
                    }
                });
    }
}
