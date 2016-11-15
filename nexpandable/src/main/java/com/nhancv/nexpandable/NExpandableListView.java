/***********************************************************************************
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014 Robin Chutaux
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 ***********************************************************************************/
package com.nhancv.nexpandable;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NExpandableListView extends ListView {
    private boolean autoCollapse;
    private Integer position = -1;
    private List<Integer> disableItemClickList;

    public NExpandableListView(Context context) {
        this(context, null);
    }

    public NExpandableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.disableItemClickList = new ArrayList<>();
        this.setOnScrollListener(new OnExpandableLayoutScrollListener());
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        if (disableItemClickList.indexOf(position) == -1) {
            this.position = position;
            try {
                if (autoCollapse) {
                    for (int index = 0; index < getChildCount(); ++index) {
                        if (index != (position - getFirstVisiblePosition())) {
                            NExpandableItem currentExpandableLayout = (NExpandableItem) getChildAt(index).findViewWithTag(NExpandableItem.class.getName());
                            currentExpandableLayout.hide();
                        }
                    }
                }

                NExpandableItem expandableLayout = (NExpandableItem) getChildAt(position - getFirstVisiblePosition()).findViewWithTag(NExpandableItem.class.getName());

                if (expandableLayout.isOpened())
                    expandableLayout.hide();
                else
                    expandableLayout.show();
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }
        return super.performItemClick(view, position, id);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        if (!(l instanceof OnExpandableLayoutScrollListener))
            throw new IllegalArgumentException("OnScrollListener must be an OnExpandableLayoutScrollListener");

        super.setOnScrollListener(l);
    }

    public List<Integer> getDisableItemClickList() {
        return disableItemClickList;
    }

    public void setDisableItemClickList(List<Integer> disableItemClickList) {
        this.disableItemClickList = disableItemClickList;
    }

    public boolean isAutoCollapse() {
        return autoCollapse;
    }

    public void setAutoCollapse(boolean autoCollapse) {
        this.autoCollapse = autoCollapse;
    }

    public class OnExpandableLayoutScrollListener implements OnScrollListener {
        private int scrollState = 0;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            this.scrollState = scrollState;
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (autoCollapse && scrollState != SCROLL_STATE_IDLE) {
                for (int index = 0; index < getChildCount(); ++index) {
                    try {
                        NExpandableItem currentExpandableLayout = (NExpandableItem) getChildAt(index).findViewWithTag(NExpandableItem.class.getName());
                        if (currentExpandableLayout.isOpened() && index != (position - getFirstVisiblePosition())) {
                            currentExpandableLayout.hideNow();
                        } else if (!currentExpandableLayout.getCloseByUser() && !currentExpandableLayout.isOpened() && index == (position - getFirstVisiblePosition())) {
                            currentExpandableLayout.showNow();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
