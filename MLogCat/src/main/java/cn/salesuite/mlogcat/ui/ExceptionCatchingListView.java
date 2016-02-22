package cn.salesuite.mlogcat.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ExceptionCatchingListView extends ListView {
	
	public ExceptionCatchingListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExceptionCatchingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExceptionCatchingListView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		try {
			return super.onTouchEvent(ev);
		} catch (Exception e) {
			return false;
		}
	}
}
