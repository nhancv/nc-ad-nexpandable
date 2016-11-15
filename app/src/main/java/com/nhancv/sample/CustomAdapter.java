package com.nhancv.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nhancv.nexpandable.IExpandable;
import com.nhancv.nexpandable.NExpandableItem;

import java.util.List;

/**
 * Created by nhancao on 11/15/16.
 */

public class CustomAdapter<T> extends ArrayAdapter<T> {

    private BaseAdapter contentListAdapter;

    public CustomAdapter(Context context, List<T> objs) {
        super(context, 0, objs);
    }

    public BaseAdapter getContentListAdapter() {
        return contentListAdapter;
    }

    public void setContentListAdapter(BaseAdapter contentListAdapter) {
        this.contentListAdapter = contentListAdapter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final T obj = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_row_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvHeader = (TextView) convertView.findViewById(R.id.vHeaderText);
            viewHolder.vHeaderLine = convertView.findViewById(R.id.vHeaderLine);
            viewHolder.lvItems = (ListView) convertView.findViewById(R.id.lvItems);
            viewHolder.vRow = (NExpandableItem) convertView.findViewById(R.id.vRow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (obj != null) {
            if (viewHolder.vRow.isOpened()) {
                viewHolder.tvHeader.setText(obj.toString() + " - Open");
            } else {
                viewHolder.tvHeader.setText(obj.toString() + " - Close");
            }
            viewHolder.vRow.setiExpandableListener(new IExpandable() {
                @Override
                public void close(boolean anim) {
                    viewHolder.tvHeader.setText(obj.toString() + " - Close");
                }

                @Override
                public void open(boolean anim) {
                    viewHolder.tvHeader.setText(obj.toString() + " - Open");
                }
            });
            if (contentListAdapter != null) viewHolder.lvItems.setAdapter(contentListAdapter);
            if (position == getCount() - 1) {
                viewHolder.vHeaderLine.setVisibility(View.GONE);
            } else {
                viewHolder.vHeaderLine.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    private static class ViewHolder {
        NExpandableItem vRow;
        TextView tvHeader;
        View vHeaderLine;
        ListView lvItems;
    }
}
