package com.tongyuan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.tongyuan.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by zhangxh on 15-11-25.
 */
public class TodayFragment extends Fragment {
    private Context context;
    public static final String action = "jason.broadcast.action";
    // /////////////////////ListView////////////////
    private List<Map<String, Object>> mData;
    //    private TodayAdapter adapter;
    private ListView listView;
    private ProgressDialog progressDialog;
    private int current = 0;
    private int count = 10;
    private String url = "suansuan";

    // /////////////////////ViewPager////////////////
    private ViewPager viewPager;
    int pics[] = {R.mipmap.shop_car_icon_dj, R.mipmap.ic_launcher, R.mipmap.shop_car_collection, R.mipmap.shop_car_icon_dm};
    private List<ImageView> imageViews;// 滑动的图片集合

    // 定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private int currentItem = 0; // 当前图片的索引号

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_today, null);
        viewPager = (ViewPager) view.findViewById(R.id.today_viewpager);
        listView = (ListView) view.findViewById(R.id.today_listview);

        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < pics.length; i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(pics[i]);
            imageViews.add(img);
        }
        viewPager.setAdapter(new TodayViewPagerAdapter());
        viewPager.setCurrentItem(0);
        return view;
    }

    private class TodayViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
