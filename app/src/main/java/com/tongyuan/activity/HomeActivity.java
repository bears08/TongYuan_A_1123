package com.tongyuan.activity;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tongyuan.R;
import com.tongyuan.dao.ShopCarDao;
import com.tongyuan.domain.ShopCar;
import com.tongyuan.fragment.MyFragment;
import com.tongyuan.fragment.TodayFragment;
import com.tongyuan.fragment.YangFragment;

import java.util.List;

import static com.tongyuan.R.style.main_tab_textview_style_default;
import static com.tongyuan.R.style.main_tab_textview_style_select;

/**
 * Created by zhangxh on 15-11-25.
 */
public class HomeActivity extends FragmentActivity {
    private ViewPager viewPager = null;
    private TextView today, yang, my,shopCarNum;
    private ImageView shopCar;

    private Context context;
    protected ShopCarClickListener shopCarClickListener;
    protected ShopCarDao shopCarDao;
    protected int shopCarNums;
    private static final int TAB_TODAY = 0;
    private static final int TAB_YANG = 1;
    private static final int TAB_MY = 2;

    private static final int TAB_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = HomeActivity.this;
        initView();
        shopCarDao = new ShopCarDao(context);
        shopCarClickListener = new ShopCarClickListener();
        shopCarNums = getShopCarNum();
        if (shopCarNums > 0) {
            shopCarNum.setVisibility(View.VISIBLE);
            // 用String类型
            shopCarNum.setText(shopCarNums + "");
            shopCarNum.setOnClickListener(shopCarClickListener);
        } else {
            shopCarNum.setVisibility(View.INVISIBLE);
        }
        shopCar.setOnClickListener(shopCarClickListener);

        IntentFilter filter = new IntentFilter(TodayFragment.action);
        registerReceiver(broadcastReceiver, filter);

        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new HomeViewPagerChangeListener());
    }

    private void initView() {
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.home_viewpager);
        today = (TextView) this.findViewById(R.id.home_tv_today);
        yang = (TextView) this.findViewById(R.id.home_tv_yang);
        my = (TextView) this.findViewById(R.id.home_tv_my);
        shopCarNum = (TextView) this.findViewById(R.id.shop_car_num);
        shopCar = (ImageView) this.findViewById(R.id.right_button);


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

    private class ShopCarClickListener implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            if(shopCarNums < 1){
                Toast.makeText(getApplicationContext(), "没有商品，请继续购物。。。",
                        Toast.LENGTH_SHORT).show();
            }else{
				Intent intent = new Intent(context, ShopCarActivity.class);
				context.startActivity(intent);
            }
        }
    }

    private int getShopCarNum() {
        List<ShopCar> list = shopCarDao.selectAll();
        int num = 0;
        if(list != null && list.size() > 0){
            for(int i = 0 ; i < list.size() ; i++){
                num += list.get(i).getProductNum();
            }
        }
        return num;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // todo
//			int i = (Integer) intent.getExtras().get("num");
            ShopCar shopCar = (ShopCar) intent.getSerializableExtra("shopCar");
            ShopCar shopCarDB = shopCarDao.selectByProductId(shopCar.getProductId());
            //如果是添加操作
            if(shopCar.getProductNum() > 0 ){
                shopCarNums = shopCarNums + 1 ;
                //1、先做一次查询，如果没有则执行insert，如果有则执行update
                if(shopCarDB == null){
                    shopCarDao.insert(shopCar);
                }else{
                    shopCar.setProductNum(shopCarDB.getProductNum() + 1);
                    shopCarDao.update(shopCar);
                }
            }else{
                shopCarNums = shopCarNums - 1;
                //删除操作，如果不为0，就是更新，否则就是删除
                if(shopCarDB != null){
                    if(shopCarDB.getProductNum() > 1){
                        shopCar.setProductNum(shopCarDB.getProductNum() - 1);
                        shopCarDao.update(shopCar);
                    }else{
                        shopCarDao.delete(shopCar);
                    }
                }
            }

            if (shopCarNums > 0) {
                shopCarNum.setVisibility(View.VISIBLE);
                // 用String类型
                shopCarNum.setText(shopCarNums + "");
                shopCarNum.setOnClickListener(shopCarClickListener);
            } else {
                shopCarNum.setVisibility(View.INVISIBLE);
            }
        }
    };
}
