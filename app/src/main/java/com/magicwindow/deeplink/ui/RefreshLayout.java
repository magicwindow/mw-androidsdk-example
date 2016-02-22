/**
 * 
 */
package com.magicwindow.deeplink.ui;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author Tony Shen
 *
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		AbsListView.OnScrollListener {

	/**
	 * 滑动到最下面时的上拉操作
	 */
	private int mTouchSlop;

	/**
	 * listview实例
	 */
	private ListView mListView;

	/**
	 * RecyclerView 实例
	 */
	private RecyclerView recyclerView;

	/**
	 * 上拉监听器, 到了最底部的上拉加载操作
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView的加载中footer
	 */
	private View mFooterView;

	/**
	 * 按下时的y坐标
	 */
	private int mYDown;

	/**
	 * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
	 */
	// private int mLastY;

	/**
	 * 是否在加载中 ( 上拉加载更多 )
	 */
	private boolean isLoading = false;
	private float mLastY;

	/**
	 * 最后一行
	 */
	// boolean isLastRow = false;

	/**
	 * 是否还有数据可以加载
	 */
	private boolean moreData = true;
	
	private CircleProgressBar progressBar;

	public RefreshLayout(Context context) {
		super(context);
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mFooterView = LayoutInflater.from(context).inflate(
				com.magicwindow.deeplink.R.layout.footer_refresh_layout, null, false);
		
		progressBar = (CircleProgressBar) mFooterView.findViewById(com.magicwindow.deeplink.R.id.pull_to_refresh_load_progress);
		progressBar.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light, android.R.color.holo_purple,
				android.R.color.holo_orange_light);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		// 初始化ListView对象
		if (mListView == null) {
			getListView();
		}
	}

	/**
	 * 获取ListView对象
	 */
	private void getListView() {
		int childs = getChildCount();
		
		if (childs > 0) {
			for (int i = 0; i < childs; i++) {
				View childView = getChildAt(i);
				if (childView instanceof ListView) {

					mListView = (ListView) childView;
					// 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
					mListView.setOnScrollListener(this);
					mListView.setFooterDividersEnabled(false);

					if (mListView != null) {
						mListView.addFooterView(mFooterView, null, true);// 设置footview不可点击
						progressBar = (CircleProgressBar) mFooterView.findViewById(com.magicwindow.deeplink.R.id.pull_to_refresh_load_progress);
						progressBar.setColorSchemeResources(android.R.color.holo_blue_light,
								android.R.color.holo_red_light, android.R.color.holo_purple,
								android.R.color.holo_orange_light);
					}

					mFooterView.setVisibility(View.GONE);// 默认先隐藏
				}
			}
		}
	}

	/**
	 * 是否可以加载更多, listview不在加载中, 且为上拉操作.
	 *
	 * @param canload
	 *            是否还有可以加载的数据
	 * @return
	 */
	private boolean canLoad(boolean canload) {

		return isBottom() && canload && !isLoading && isPullUp();
	}

	/**
	 * 判断是否到了最底部
	 */
	private boolean isBottom() {

		if (mListView != null && mListView.getAdapter() != null) {
			return mListView.getLastVisiblePosition() == (mListView
					.getAdapter().getCount() - 1);
		}
		return false;
	}

	/**
	 * 是否是上拉操作
	 *
	 * @return
	 */
	private boolean isPullUp() {
		Log.d("RefreshLayout", "isPullUp--->");
		return (mYDown - mLastY) >= mTouchSlop;
	}

	/**
	 * 如果到了最底部,而且是上拉操作.onLoadMore
	 */
	private void loadData() {
		Log.d("RefreshLayout", "loadData--->");
		if (mOnLoadListener != null) {
			// 设置状态
			setLoading(true);
			mOnLoadListener.onLoadMore();
		}
	}

	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		isLoading = loading;
		if (isLoading) {
			mFooterView.setVisibility(View.VISIBLE);
			progressBar.requestLayout();
		} else {
			mFooterView.setVisibility(View.GONE);
			mYDown = 0;
			mLastY = 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad(moreData)) {
				loadData();
			}
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {
	}

	@Override
	public void onScroll(AbsListView absListView, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 判断是否滚到最后一行
		// if (firstVisibleItem + visibleItemCount == totalItemCount &&
		// totalItemCount > 0) {
		// 双重判断，应该没有必要
		// if (absListView.getLastVisiblePosition() ==
		// (absListView.getAdapter().getCount() - 1)) {
		// isLastRow = true;
		// mFooterView.setVisibility(View.VISIBLE);
		if (canLoad(moreData)) {
			loadData();
		}
		// }
		// } else {
		// isLastRow = false;
		// }
	}

	public boolean isMoreData() {
		return moreData;
	}

	public void setMoreData(boolean moreData) {
		this.moreData = moreData;
	}

	public void hideFooterView() {
		if (mFooterView != null) {
			mFooterView.setVisibility(View.GONE);
		}
	}

	public boolean isExistFooterView() {
		if (mListView != null && mListView.getFooterViewsCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 加载更多的监听器
	 * 
	 * @author Tony Shen
	 */
	public static interface OnLoadListener {
		public void onLoadMore();
	}
}
