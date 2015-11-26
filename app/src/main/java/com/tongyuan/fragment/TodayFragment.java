package com.tongyuan.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tongyuan.R;
import com.tongyuan.adapter.TodayListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangxh on 15-11-25.
 */
public class TodayFragment extends Fragment {
    private String TAG = "TodayFragment";
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
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what){
                case 1 :
                    Log.i(TAG,"----------------------------1");
                    viewPager.setCurrentItem(currentItem);
                    break;
                case 2 :
                    Log.i(TAG,"----------------------------2");
                    listView.setAdapter(new TodayListViewAdapter(context,mData));
                    break;
            }

        };
    };

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
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollRunnable(), 1, 2,
                TimeUnit.SECONDS);
        handler.post(new GetListViewRunable());

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

        @Override
        public Object instantiateItem(View container, int position) {
            ImageView iv = imageViews.get(position);
            ((ViewPager) container).addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

    }

    private class ScrollRunnable implements Runnable {
        @Override
        public void run() {
            synchronized (viewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                Message message = handler.obtainMessage();
                message.what =1;
                handler.sendMessage(message);
            }
        }
    }

    private class GetListViewRunable implements Runnable{

        @Override
        public void run() {
            //todo
            mData = getData();
            Message message = handler.obtainMessage();
            message.what = 2;
            handler.sendMessage(message);
        }
    }
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("productid", 1);
        map.put("name", "麻辣豆腐");
        map.put("price", 8.20);
        map.put("info", "豆类补肾");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/c/e/6/220_ce3f53dbf8b0858e2d199edf89f564a6.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 2);
        map.put("name", "羊肉串");
        map.put("price", 18.80);
        map.put("info", "羊肉补益阳气");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/1/9/3/220_1942de4ded23a5e4f7ac96a1ce552b73.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 3);
        map.put("name", "茄子肉沫");
        map.put("price", 9.80);
        map.put("info", "茄子多子，补肾");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/5/9/b/220_592cce76981298bc812f4a273d4691bb.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 4);
        map.put("name", "排骨莲藕");
        map.put("price", 15.80);
        map.put("info", "莲藕补肺气");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/c/f/1/220_cfb73424a42d37ab9535fa159d0bafb1.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 5);
        map.put("name", "水煮花生");
        map.put("price", 6.00);
        map.put("info", "花生补血补气");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/d/d/9/220_dd575450344b29e9b5314733be24c019.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 6);
        map.put("name", "糖醋排骨");
        map.put("price", 16.80);
        map.put("info", "红糖补血，排骨补肾，醋主酸，补肝");
        map.put("img",
                "http://zt1.douguo.net/upload/post/b/1/6/b11ba6d18e1bff227e19a08890dccd16.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 7);
        map.put("name", "肉沫海参");
        map.put("price", 23.80);
        map.put("info", "肉沫补脾，海参补肾");
        map.put("img", "http://i1.douguo.net/upload/banners/1443403123.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 8);
        map.put("name", "南瓜汤");
        map.put("price", 2.90);
        map.put("info", "南瓜黄色补脾");
        map.put("img", "http://i1.douguo.net/upload/banners/1443431898.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 9);
        map.put("name", "菠菜丸子");
        map.put("price", 11.80);
        map.put("info", "菠菜补肝，丸子补肾");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/2/1/0/220_21ac74abaa460c96e3f9b4e2bc2b9de0.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 10);
        map.put("name", "家常豆腐");
        map.put("price", 7.20);
        map.put("info", "豆腐补肾");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/5/6/4/220_56ac24ef47a4231bd410d8b3cb827e04.jpg");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("productid", 11);
        map.put("name", "甜点");
        map.put("price", 8.80);
        map.put("info", "甜点补脾");
        map.put("img",
                "http://cp1.douguo.net/upload/caiku/7/b/d/220_7b31b4b30bb7f794ff838a1478ce60ed.jpg");
        list.add(map);
        return list;
    }
}
