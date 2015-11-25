package com.tongyuan.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.tongyuan.R;
import com.tongyuan.fragment.MyFragment;
import com.tongyuan.fragment.TodayFragment;
import com.tongyuan.fragment.YangFragment;

import static com.tongyuan.R.style.main_tab_textview_style_default;
import static com.tongyuan.R.style.main_tab_textview_style_select;

/**
 * Created by zhangxh on 15-11-25.
 */
public class HomeActivity extends FragmentActivity {
    private ViewPager viewPager = null;
    private TextView today, yang, my;
    private Context context;
    private static final int TAB_TODAY = 0;
    private static final int TAB_YANG = 1;
    private static final int TAB_MY = 2;

    private static final int TAB_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = HomeActivity.this;
        initTextView();
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new HomeViewPagerChangeListener());
    }

    private void initTextView() {
        today = (TextView) this.findViewById(R.id.home_tv_today);
        yang = (TextView) this.findViewById(R.id.home_tv_yang);
        my = (TextView) this.findViewById(R.id.home_tv_my);

        today.setOnClickListener(new HomeTabOnClickListener(TAB_TODAY));
        yang.setOnClickListener(new HomeTabOnClickListener(TAB_YANG));
        my.setOnClickListener(new HomeTabOnClickListener(TAB_MY));
    }

    /**
     * 头标点击监听
     */
    public class HomeTabOnClickListener implements View.OnClickListener {
        private int index = 0;

        public HomeTabOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            //todo 修改下面的字体颜色
            viewPager.setCurrentItem(index);
        }
    }

    ;

    public class HomeViewPagerAdapter extends FragmentPagerAdapter {
        public HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TodayFragment();
                case 1:
                    return new YangFragment();
                case 2:
                    return new MyFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }


    private class HomeViewPagerChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Drawable topToday = null;
            Drawable topYang = null;
            Drawable topMy = null;
            switch (position) {

                case 0:
                    topToday = getResources().getDrawable(R.mipmap.main_tab_today_selected);
                    topToday.setBounds(0,0,topToday.getMinimumWidth(),topToday.getMinimumWidth());
                    topYang = getResources().getDrawable(R.mipmap.main_tab_life_default);
                    topYang.setBounds(0,0,topYang.getMinimumWidth(),topYang.getMinimumWidth());
                    topMy = getResources().getDrawable(R.mipmap.main_tab_me_default);
                    topMy.setBounds(0, 0, topMy.getMinimumWidth(), topMy.getMinimumWidth());
                    today.setCompoundDrawables(null, topToday, null, null);
                    today.setTextAppearance(context,R.style.main_tab_textview_style_select);
                    yang.setCompoundDrawables(null, topYang, null, null);
                    yang.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    my.setCompoundDrawables(null, topMy, null, null);
                    my.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    break;
                case 1:
                    topToday = getResources().getDrawable(R.mipmap.main_tab_today_default);
                    topToday.setBounds(0,0,topToday.getMinimumWidth(),topToday.getMinimumWidth());
                    topYang = getResources().getDrawable(R.mipmap.main_tab_life_selected);
                    topYang.setBounds(0,0,topYang.getMinimumWidth(),topYang.getMinimumWidth());
                    topMy = getResources().getDrawable(R.mipmap.main_tab_me_default);
                    topMy.setBounds(0,0,topMy.getMinimumWidth(),topMy.getMinimumWidth());
                    today.setCompoundDrawables(null, topToday, null, null);
                    today.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    yang.setCompoundDrawables(null, topYang, null, null);
                    yang.setTextAppearance(context,R.style.main_tab_textview_style_select);
                    my.setCompoundDrawables(null, topMy, null, null);
                    my.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    break;
                case 2:
                    topToday = getResources().getDrawable(R.mipmap.main_tab_today_default);
                    topToday.setBounds(0,0,topToday.getMinimumWidth(),topToday.getMinimumWidth());
                    topYang = getResources().getDrawable(R.mipmap.main_tab_life_default);
                    topYang.setBounds(0,0,topYang.getMinimumWidth(),topYang.getMinimumWidth());
                    topMy = getResources().getDrawable(R.mipmap.main_tab_me_selected);
                    topMy.setBounds(0,0,topMy.getMinimumWidth(),topMy.getMinimumWidth());
                    today.setCompoundDrawables(null, topToday, null, null);
                    today.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    yang.setCompoundDrawables(null, topYang, null, null);
                    yang.setTextAppearance(context,R.style.main_tab_textview_style_default);
                    my.setCompoundDrawables(null, topMy, null, null);
                    my.setTextAppearance(context,R.style.main_tab_textview_style_select);
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
