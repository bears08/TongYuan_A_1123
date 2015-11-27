package com.tongyuan.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tongyuan.R;

import java.util.List;

/**
 * Created by zhangxh on 15-11-25.
 */
public class YangFragment extends Fragment {
    private Context context;
    private ListView listView;
    private String[] categorys = {"补肝明目", "补心补气", "固本配元", "补脾健胃", "湿热体质"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = this.getActivity();
        View view = inflater.inflate(R.layout.fragment_yang, null);
        listView = (ListView) view.findViewById(R.id.yang_list_view);
        listView.setAdapter(new CategoryListViewAdapter(context));
        return view;
    }

    private class CategoryListViewAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public CategoryListViewAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return categorys.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = mInflater.inflate(R.layout.list_item_fragment_category, null);
//            view = mInflater.inflate(android.R.layout.simple_list_item_activated_1, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_category);
            textView.setText(categorys[i]);
            return view;
        }
    }
}
