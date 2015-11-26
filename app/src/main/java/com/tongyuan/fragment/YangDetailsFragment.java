package com.tongyuan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by zhangxh on 15-11-26.
 */
public class YangDetailsFragment extends Fragment {

    public static YangDetailsFragment newInstance(int index) {
        YangDetailsFragment f = new YangDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        ScrollView scroller = new ScrollView(getActivity());
        TextView text = new TextView(getActivity());

        int padding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources()
                        .getDisplayMetrics());
        text.setPadding(padding, padding, padding, padding);
        scroller.addView(text);
        text.setText("xiaoxiongxiong");
//        text.setText(Shakespeare.DIALOGUE[getArguments().getInt("index", 0)]);
        return scroller;
    }
}