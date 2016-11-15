package com.nhancv.sample;

import android.content.Context;
import android.util.Log;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.nhancv.nexpandable.IExpandable;
import com.nhancv.nexpandable.NExpandableItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 11/15/16.
 */

public class CustomAdapter<T> extends QuickAdapter<T> implements Filterable {

    private List<T> listsItems = new ArrayList<>();
    private List<T> listFiltered = new ArrayList<>();
    private String searchText;

    public CustomAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public void setListsItems(List<T> listsItems) {
        this.listsItems = listsItems;
        this.listFiltered = listsItems;
        replaceAll(listFiltered);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFiltered.size();
    }

    @Override
    protected void convert(final BaseAdapterHelper helper, final T obj) {
        if (obj != null) {
            final NExpandableItem vRow = helper.getView(R.id.vRow);
            final TextView tvHeaderText = helper.getView(R.id.tvHeaderText);
            if (vRow.isOpened()) {
                tvHeaderText.setText(NUtil.highlightText(searchText, obj.toString() + " - Open"));
            } else {
                tvHeaderText.setText(NUtil.highlightText(searchText, obj.toString() + " - Close"));
            }

            vRow.setiExpandableListener(new IExpandable() {
                @Override
                public void close(boolean anim) {
                    tvHeaderText.setText(NUtil.highlightText(searchText, obj.toString() + " - Close"));
                }

                @Override
                public void open(boolean anim) {

                    tvHeaderText.setText(NUtil.highlightText(searchText, obj.toString() + " - Open"));
                }
            });
            ListView lvItems = helper.getView(R.id.lvItems);
            lvItems.setAdapter(new QuickAdapter<T>(helper.getView().getContext(), R.layout.view_list_item, listFiltered) {
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.toString().length() == 0) {
                    results.count = listsItems.size();
                    results.values = listsItems;
                } else {
                    List<Object> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString();
                    for (Object item : listsItems) {
                        if (NUtil.isContainText(searchStr, (String) item)) {
                            resultsData.add(item);
                        }
                    }
                    results.count = resultsData.size();
                    results.values = resultsData;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (searchText == null || searchText.length() == 0) {
                    listFiltered = listsItems;
                } else {
                    listFiltered = (List<T>) results.values;
                    replaceAll(listFiltered);
                }
            }
        };
    }

    /**
     * Update search text
     *
     * @param searchText
     */
    public void setSearchText(String searchText) {
        this.searchText = searchText;
        getFilter().filter(searchText);
        Log.e(TAG, "setSearchText: " + searchText);
    }
}
