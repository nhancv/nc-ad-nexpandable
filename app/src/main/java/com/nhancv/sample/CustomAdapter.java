package com.nhancv.sample;

import android.content.Context;
import android.widget.ListView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nhancv.nexpandable.IExpandable;
import com.nhancv.nexpandable.NExpandableItem;

import java.util.List;

/**
 * Created by nhancao on 11/15/16.
 */

public class CustomAdapter<T> extends QuickAdapter<T> {

    private List<T> objs;

    public CustomAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
        this.objs = data;
    }

    @Override
    protected void convert(final BaseAdapterHelper helper, final T obj) {
        if (obj != null) {
            NExpandableItem vRow = helper.getView(R.id.vRow);
            if (vRow.isOpened()) {
                helper.setText(R.id.tvHeaderText, obj.toString() + " - Open");
            } else {
                helper.setText(R.id.tvHeaderText, obj.toString() + " - Close");
            }
            vRow.setiExpandableListener(new IExpandable() {
                @Override
                public void close(boolean anim) {
                    helper.setText(R.id.tvHeaderText, obj.toString() + " - Close");
                }

                @Override
                public void open(boolean anim) {
                    helper.setText(R.id.tvHeaderText, obj.toString() + " - Open");
                }
            });
            ListView lvItems = helper.getView(R.id.lvItems);
            lvItems.setAdapter(new QuickAdapter<T>(helper.getView().getContext(), R.layout.view_list_item, objs) {
                @Override
                protected void convert(BaseAdapterHelper helper, T item) {
                    helper.setText(R.id.tvContextText, (String) item);
                    helper.setVisible(R.id.vContentLine, (helper.getPosition() < getCount() - 1));
                }
            });
            if (helper.getPosition() == getCount() - 1) {
                helper.setVisible(R.id.vHeaderLine, false);
            } else {
                helper.setVisible(R.id.vHeaderLine, true);
            }
        }
    }

}
