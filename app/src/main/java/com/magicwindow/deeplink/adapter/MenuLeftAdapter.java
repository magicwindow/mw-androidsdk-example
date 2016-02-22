package com.magicwindow.deeplink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.magicwindow.deeplink.R;
import com.magicwindow.deeplink.menu.MenuManager;

/**
 * Created by aaron on 15/11/25.
 */
public class MenuLeftAdapter extends BaseAdapter {
    private ViewHolder holder;

    private Context context;

    public MenuLeftAdapter (Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return MenuManager.MenuType.values().length-2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null){
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.menu_left_adapter, parent, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.menu_left_text);
            convertView.setTag(holder);
        }

        holder.text.setText(MenuManager.MenuType.getTitleByIndex(position));
        holder.text.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(MenuManager.MenuType.getIconByIndex(position)), null,null,null);

        return convertView;
    }

    private class ViewHolder{

        TextView text;
    }
}
