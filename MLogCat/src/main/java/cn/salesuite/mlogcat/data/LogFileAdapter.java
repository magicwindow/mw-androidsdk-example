package cn.salesuite.mlogcat.data;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import cn.salesuite.mlogcat.R;
import cn.salesuite.mlogcat.helper.SaveLogHelper;
import cn.salesuite.saf.utils.JodaUtils;

public class LogFileAdapter extends ArrayAdapter<CharSequence> {

	public static final String USER_READABLE_DATE_FORMAT = "MMM dd, yyyy hh:mm aaa";
	
	private List<CharSequence> objects;
	private int checked;
	private boolean multiMode;
	private boolean[] checkedItems;
	private int resId;
	private Context mContext;
	private LayoutInflater mInflater;
    private ViewHolder holder;
	
	public LogFileAdapter(Context context, List<CharSequence> objects, int checked, boolean multiMode) {
		
		super(context, -1, objects);
		this.mContext = context;
		this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.objects = objects;
		this.checked = checked;
		this.multiMode = multiMode;
		if (multiMode) {
			checkedItems = new boolean[objects.size()];
		}
		resId = multiMode? R.layout.logcat_checkbox_dropdown_filename : R.layout.logcat_spinner_dropdown_filename;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
        if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(resId, parent, false);
			holder = new ViewHolder();
			holder.text1 = (CheckedTextView) convertView.findViewById(android.R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
			convertView.setTag(holder);
		}
        
		CharSequence filename = objects.get(position);

		holder.text1.setText(filename);
		if (multiMode) {
			holder.text1.setChecked(checkedItems[position]);
		} else {
			holder.text1.setChecked(checked == position);
		}

		Date lastModified = SaveLogHelper.getLastModifiedDate(filename.toString());
		holder.text2.setText(JodaUtils.format(lastModified, USER_READABLE_DATE_FORMAT));
		
		return convertView;
	}
	
	public void checkOrUncheck(int position) {
		checkedItems[position] = !checkedItems[position];
		notifyDataSetChanged();
	}
	
	public boolean[] getCheckedItems() {
		return checkedItems;
	}
	
    class ViewHolder {
        public CheckedTextView text1;
        public TextView text2;
    }
}
