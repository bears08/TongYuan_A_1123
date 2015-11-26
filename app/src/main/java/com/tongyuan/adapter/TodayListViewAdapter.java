package com.tongyuan.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tongyuan.R;
import com.tongyuan.activity.ProductActivity;
import com.tongyuan.dao.ShopCarDao;
import com.tongyuan.domain.ShopCar;
import com.tongyuan.image.AsyncImageLoader;
import com.tongyuan.util.image.ImageLoaderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxh on 15-11-26.
 */
public class TodayListViewAdapter  extends BaseAdapter {

    private LayoutInflater mInflater;
    private AsyncImageLoader imageLoader;
    private ImageLoaderFactory factory;
    private ShopCarDao shopCarDao;
    private Map<String, Integer> nums = new HashMap<String, Integer>();
    private List<Map<String, Object>> mData;
    private static final String NUM = "nums";
    private static final String PRODUCTID = "productid";
    private static final String NAME = "name";
    private static final String INFO = "info";
    private static final String PRICE = "price";
    private static final String IMG = "img";
    public static final String action = "jason.broadcast.action";
    private Context context;

    public TodayListViewAdapter(Context context ,List<Map<String, Object>> mData) {
        this.context = context;
        factory = new ImageLoaderFactory(context);
        this.mInflater = LayoutInflater.from(context);
        this.shopCarDao = new ShopCarDao(context);
        this.mData =mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final int pos = position;
        if (convertView == null) {

            holder = new ViewHolder();

            convertView = mInflater.inflate(R.layout.list_item_today, null);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.num = (TextView) convertView.findViewById(R.id.num);
            holder.jia = (ImageView) convertView.findViewById(R.id.jia);
            holder.jian = (ImageView) convertView.findViewById(R.id.jian);
            holder.productid = (TextView) convertView
                    .findViewById(R.id.product_id);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText((String) mData.get(position).get(NAME));
        holder.price.setText("" + mData.get(position).get(PRICE));
        holder.productid.setText("" + mData.get(position).get(PRODUCTID));
        if (mData.get(position).get(NUM) == null) {
            mData.get(position).put(NUM, 0);
        }

        ShopCar shopCar = shopCarDao.selectByProductId((Integer) mData.get(
                position).get(PRODUCTID));
        int ns = 0;
        if (shopCar != null && shopCar.getProductNum() > 0) {
            ns = shopCar.getProductNum();
        }
        holder.num.setText("" + ns);
        if (ns > 0) {
            holder.num.setVisibility(View.VISIBLE);
            holder.jian.setVisibility(View.VISIBLE);
        } else {
            holder.num.setVisibility(View.INVISIBLE);
            holder.jian.setVisibility(View.INVISIBLE);
        }
        holder.jia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String s = (String) holder.num.getText();
                int n = 0;
                if (s != null) {
                    n = Integer.parseInt(s);
                }
                n++;
                holder.num.setText("" + n);
                ShopCar shopCar = new ShopCar();
                shopCar.setProductId((Integer) mData.get(pos)
                        .get(PRODUCTID));
                shopCar.setProductNum(1);
                shopCar.setName((String) mData.get(pos).get(NAME));
                shopCar.setPrice((Double) mData.get(pos).get(PRICE));
                shopCar.setSmallPic((String) mData.get(pos).get(IMG));

                if (n > 0) {
                    holder.num.setVisibility(View.VISIBLE);
                    holder.jian.setVisibility(View.VISIBLE);
                }
                Intent intent = new Intent(action);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopCar", shopCar);
                intent.putExtras(bundle);

                context.sendBroadcast(intent);
                // adapter.notifyDataSetChanged();
            }
        });
        holder.jian.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String s = (String) holder.num.getText();
                int n = 0;
                if (s != null) {
                    n = Integer.parseInt(s);
                }
                n--;
                holder.num.setText("" + n);
                ShopCar shopCar = new ShopCar();
                shopCar.setProductId((Integer) mData.get(pos)
                        .get(PRODUCTID));
                shopCar.setProductNum(-1);
                shopCar.setName((String) mData.get(pos).get(NAME));
                shopCar.setPrice((Double) mData.get(pos).get(PRICE));
                shopCar.setSmallPic((String) mData.get(pos).get(IMG));
                if (n <= 0) {
                    holder.num.setVisibility(View.INVISIBLE);
                    holder.jian.setVisibility(View.INVISIBLE);
                }
                Intent intent = new Intent(action);
                Bundle bundle = new Bundle();
                bundle.putSerializable("shopCar", shopCar);
                intent.putExtras(bundle);
                context.sendBroadcast(intent);
            }
        });
        factory.displayImage((String) mData.get(position).get("img"),
                holder.img);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,
                        ProductActivity.class);
                ShopCar shopCar = new ShopCar();
                shopCar.setProductId((Integer) mData.get(pos)
                        .get(PRODUCTID));
                shopCar.setName((String) mData.get(pos).get(NAME));
                shopCar.setPrice((Double) mData.get(pos).get(PRICE));
                shopCar.setSmallPic((String) mData.get(pos).get(IMG));

                context.startActivity(intent);
            }
        });
        return convertView;

    }

    private class ViewHolder {
        public ImageView img;
        public TextView name;
        public TextView price;
        public TextView info;
        public TextView num;
        public ImageView jia;
        public ImageView jian;
        public TextView productid;
    }
}