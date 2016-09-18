package com.lnyp.joke.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.lnyp.flexibledivider.HorizontalDividerItemDecoration;
import com.lnyp.joke.PhotoActivity;
import com.lnyp.joke.R;
import com.lnyp.joke.adapter.JokeListAdapter;
import com.lnyp.joke.http.HttpUtils;
import com.lnyp.joke.pengfu.JokeApi;
import com.lnyp.joke.pengfu.JokeBean;
import com.lnyp.joke.pengfu.JokeUtil;
import com.lnyp.joke.widget.SmartisanDrawable;
import com.lnyp.recyclerview.EndlessRecyclerOnScrollListener;
import com.lnyp.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.lnyp.recyclerview.RecyclerViewLoadingFooter;
import com.lnyp.recyclerview.RecyclerViewStateUtils;
import com.victor.loading.rotate.RotateLoading;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.rotateloading)
    public RotateLoading rotateloading;

    @BindView(R.id.swipeRefreshLayout)
    public PullRefreshLayout swipeRefreshLayout;

    @BindView(R.id.listInspirations)
    public RecyclerView listInspirations;

    private HeaderAndFooterRecyclerViewAdapter mAdapter;

    private List<JokeBean> mDatas;

    private int page = 1;

    private boolean mHasMore = false;

    private boolean isRefresh = true;


    // 处理请求返回信息
    private MyHandler mHandler = new MyHandler();

    private class MyHandler extends Handler {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                    RecyclerViewStateUtils.setFooterViewState(listInspirations, RecyclerViewLoadingFooter.State.Normal);
                    swipeRefreshLayout.setRefreshing(false);
                    rotateloading.stop();

                    mAdapter.notifyDataSetChanged();

                    break;
                case 1:
                    RecyclerViewStateUtils.setFooterViewState(getActivity(), listInspirations, mHasMore, RecyclerViewLoadingFooter.State.NetWorkError, mFooterClick);
                    break;
            }
        }
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        unbinder = ButterKnife.bind(this, view);

        initView();

        rotateloading.start();
        refreshReq();

        return view;
    }

    private void initView() {

        mDatas = new ArrayList<>();

        JokeListAdapter jokeListAdapter = new JokeListAdapter(this, mDatas, onClickListener);
        mAdapter = new HeaderAndFooterRecyclerViewAdapter(jokeListAdapter);
        listInspirations.setAdapter(mAdapter);

        listInspirations.setLayoutManager(new LinearLayoutManager(getActivity()));
        listInspirations.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .colorResId(R.color.divider_color)
                        .build());

        listInspirations.addOnScrollListener(mOnScrollListener);

        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        swipeRefreshLayout.setRefreshDrawable(new SmartisanDrawable(getActivity(), swipeRefreshLayout));
        swipeRefreshLayout.setBackgroundColor(Color.parseColor("#EFEFEF"));
        swipeRefreshLayout.setColor(Color.parseColor("#8F8F81"));

    }

    private PullRefreshLayout.OnRefreshListener onRefreshListener = new PullRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshReq();
        }
    };

    private void refreshReq() {

        isRefresh = true;

        page = 1;

        qryJokes();
    }

    private void qryJokes() {

        final String url = JokeApi.PENGFU_NEW_JOKES + page + JokeApi.URL_SUFFIX;
        System.out.println(url);

        HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {

            @Override
            public void onRequestComplete(String result) {

                if (result == null) {
                    mHandler.sendEmptyMessage(1);
                    return;
                }

                Document doc = Jsoup.parse(result);

                if (doc != null) {

                    JokeUtil jokeUtil = new JokeUtil();
                    List<JokeBean> jokeBeens = jokeUtil.getNewJokelist(doc);

                    if (jokeBeens != null) {

                        page++;
                        mHasMore = true;

                        if (isRefresh) {
                            mDatas.clear();
                            isRefresh = false;
                        }

                        mDatas.addAll(jokeBeens);

                        mHandler.sendEmptyMessage(0);

                    }
                }
            }
        });

    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            RecyclerViewLoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(listInspirations);

            if (state == RecyclerViewLoadingFooter.State.Loading) {
                return;
            }

            if (mHasMore) {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), listInspirations, mHasMore, RecyclerViewLoadingFooter.State.Loading, null);
                qryJokes();

            } else {
                RecyclerViewStateUtils.setFooterViewState(getActivity(), listInspirations, mHasMore, RecyclerViewLoadingFooter.State.TheEnd, null);
            }
        }
    };

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), listInspirations, mHasMore, RecyclerViewLoadingFooter.State.Loading, null);
            qryJokes();
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                int pos = (int) view.getTag(R.string.app_name);
                JokeBean jokeBean = mDatas.get(pos);
                String showImg = jokeBean.getDataBean().getShowImg();
                String gifSrcImg = jokeBean.getDataBean().getGifsrcImg();
//
//                System.out.println(showImg + "   " + gifSrcImg);

                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra("showImg", showImg);
                intent.putExtra("gifSrcImg", gifSrcImg);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
