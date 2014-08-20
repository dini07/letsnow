package com.cremamobile.filemanager.treeview;

import com.cremamobile.filemanager.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TreeViewList extends ListView {
	private static final int DEFAULT_ROOT_EXPENDED_RESOURCE = R.drawable.cross_01;
	private static final int DEFAULT_ROOT_COLLAPSED_RESOURCE = R.drawable.minus_01;	
    private static final int DEFAULT_COLLAPSED_RESOURCE = R.drawable.cross_01;
    private static final int DEFAULT_EXPANDED_RESOURCE = R.drawable.minus_01;
    private static final int DEFAULT_INDENT = 0;
    private static final int DEFAULT_GRAVITY = Gravity.LEFT
            | Gravity.CENTER_VERTICAL;
    private Drawable rootCollapsedDrawable;
    private Drawable rootExpendedDrawable;
    private Drawable expandedDrawable;
    private Drawable collapsedDrawable;
    private Drawable rowBackgroundDrawable;
    private Drawable indicatorBackgroundDrawable;
    private int indentWidth = 0;
    private int indicatorGravity = 0;
    private TreeViewAdapter treeAdapter;
    private boolean collapsible;
    private boolean handleTrackballPress;
    private Handler	parentHandler;
    
    public TreeViewList(final Context context, final AttributeSet attrs) {
        this(context, attrs, R.style.treeViewListStyle);
    }

    public TreeViewList(final Context context) {
        this(context, null);
    }

    public TreeViewList(final Context context, final AttributeSet attrs,
            final int defStyle) {
        super(context, attrs, defStyle);
        parseAttributes(context, attrs);
    }

    private void parseAttributes(final Context context, final AttributeSet attrs) {
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TreeViewList);
        rootCollapsedDrawable = a.getDrawable(R.styleable.TreeViewList_src_root_collapsed);
        if (rootCollapsedDrawable == null) {
        	rootCollapsedDrawable = context.getResources().getDrawable(DEFAULT_ROOT_COLLAPSED_RESOURCE);
        }
        rootExpendedDrawable = a.getDrawable(R.styleable.TreeViewList_src_root_expended);
        if (rootExpendedDrawable == null) {
        	rootExpendedDrawable = context.getResources().getDrawable(DEFAULT_ROOT_EXPENDED_RESOURCE);
        }
        
        expandedDrawable = a.getDrawable(R.styleable.TreeViewList_src_expanded);
        if (expandedDrawable == null) {
            expandedDrawable = context.getResources().getDrawable(
                    DEFAULT_EXPANDED_RESOURCE);
        }
        collapsedDrawable = a
                .getDrawable(R.styleable.TreeViewList_src_collapsed);
        if (collapsedDrawable == null) {
            collapsedDrawable = context.getResources().getDrawable(
                    DEFAULT_COLLAPSED_RESOURCE);
        }
        indentWidth = a.getDimensionPixelSize(
                R.styleable.TreeViewList_indent_width, DEFAULT_INDENT);
        indicatorGravity = a.getInteger(
                R.styleable.TreeViewList_indicator_gravity, DEFAULT_GRAVITY);
        indicatorBackgroundDrawable = a
                .getDrawable(R.styleable.TreeViewList_indicator_background);
        rowBackgroundDrawable = a
                .getDrawable(R.styleable.TreeViewList_row_background);
        collapsible = a.getBoolean(R.styleable.TreeViewList_collapsible, true);
        handleTrackballPress = a.getBoolean(
                R.styleable.TreeViewList_handle_trackball_press, true);
    }

    @Override
    public void setAdapter(final ListAdapter adapter) {
        if (!(adapter instanceof TreeViewAdapter)) {
            throw new TreeConfigurationException(
                    "The adapter is not of TreeViewAdapter type");
        }
        treeAdapter = (TreeViewAdapter) adapter;
        syncAdapter();
        super.setAdapter(treeAdapter);
    }

    private void syncAdapter() {
    	treeAdapter.setRootCollapsedDrawable(rootCollapsedDrawable);
    	treeAdapter.setRootExpendedDrawable(rootExpendedDrawable);    	
        treeAdapter.setCollapsedDrawable(collapsedDrawable);
        treeAdapter.setExpandedDrawable(expandedDrawable);
        treeAdapter.setIndicatorGravity(indicatorGravity);
        treeAdapter.setIndentWidth(indentWidth);
        treeAdapter.setIndicatorBackgroundDrawable(indicatorBackgroundDrawable);
        treeAdapter.setRowBackgroundDrawable(rowBackgroundDrawable);
        treeAdapter.setCollapsible(collapsible);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView< ? > parent,
                    final View view, final int position, final long id) {
                treeAdapter.handleItemClick(parent, view, position, (Long)view.getTag());
            }
        });
//        if (handleTrackballPress) {
//            setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(final AdapterView< ? > parent,
//                        final View view, final int position, final long id) {
//                    treeAdapter.handleItemClick(view, view.getTag());
//                }
//            });
//        } else {
//            setOnClickListener(null);
//        }

    }

    
    public void setExpandedDrawable(final Drawable expandedDrawable) {
        this.expandedDrawable = expandedDrawable;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setCollapsedDrawable(final Drawable collapsedDrawable) {
        this.collapsedDrawable = collapsedDrawable;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setRowBackgroundDrawable(final Drawable rowBackgroundDrawable) {
        this.rowBackgroundDrawable = rowBackgroundDrawable;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setIndicatorBackgroundDrawable(
            final Drawable indicatorBackgroundDrawable) {
        this.indicatorBackgroundDrawable = indicatorBackgroundDrawable;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setIndentWidth(final int indentWidth) {
        this.indentWidth = indentWidth;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setIndicatorGravity(final int indicatorGravity) {
        this.indicatorGravity = indicatorGravity;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setCollapsible(final boolean collapsible) {
        this.collapsible = collapsible;
        syncAdapter();
        treeAdapter.refresh();
    }

    public void setHandleTrackballPress(final boolean handleTrackballPress) {
        this.handleTrackballPress = handleTrackballPress;
        syncAdapter();
        treeAdapter.refresh();
    }

    public Drawable getRootExpendedDrawable() {
    	return rootExpendedDrawable;
    }
    
    public Drawable getRootCollapsedDrawable() {
    	return rootCollapsedDrawable;
    }
    
    
    public Drawable getExpandedDrawable() {
        return expandedDrawable;
    }

    public Drawable getCollapsedDrawable() {
        return collapsedDrawable;
    }

    public Drawable getRowBackgroundDrawable() {
        return rowBackgroundDrawable;
    }

    public Drawable getIndicatorBackgroundDrawable() {
        return indicatorBackgroundDrawable;
    }

    public int getIndentWidth() {
        return indentWidth;
    }

    public int getIndicatorGravity() {
        return indicatorGravity;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public boolean isHandleTrackballPress() {
        return handleTrackballPress;
    }

}
