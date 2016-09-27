package com.example.dell.app3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DELL on 2016/9/23.
 */

public class ABAdapter extends BaseAdapter {
    //itemA类的type标志
    private static final int TYPE_A = 0;
    //itemB类的type标志
    private static final int TYPE_B = 1;

    private Context context;
    //整合数据
    private List<Object> data = new ArrayList<>();


    public ABAdapter(Context context, ArrayList<Object> data) {
        this.context = context;
        this.data = data;
    }
    /**
     * 获得itemView的type
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position) instanceof A) {
            result = TYPE_A;
        } else if (data.get(position) instanceof B) {
            result = TYPE_B;
        }
        return result;
    }
    /**
     * 获得有多少中view type
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if (convertView == null) {
            //实例化
            holder1 = new ViewHolder1();
            holder2 = new ViewHolder2();
            //根据不同的type 来inflate不同的item layout
            //然后设置不同的tag
            //这里的tag设置是用的资源ID作为Key
            switch (type) {
                case TYPE_A:
                    convertView = View.inflate(context, R.layout.item_list_for_a, null);
                    holder1.week = (TextView) convertView.findViewById(R.id.a_week);
                    holder1.date = (TextView) convertView.findViewById(R.id.a_date);
                    holder1.content = (TextView) convertView.findViewById(R.id.a_content);
                    convertView.setTag(R.id.tag_first, holder1);
                    break;
                case TYPE_B:
                    convertView = View.inflate(context, R.layout.item_list_for_b, null);
                    holder2.point = (ImageView) convertView.findViewById(R.id.b_point);
                    convertView.setTag(R.id.tag_second, holder2);
                    break;
            }
        }else {
            //根据不同的type来获得tag
            switch (type) {
                case TYPE_A:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_first);
                    break;
                case TYPE_B:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.tag_second);
                    break;
            }
        }
        Object o = data.get(position);
        switch (type) {
            case TYPE_A:
                A a = (A) o;

                holder1.week.setText(""+a.getWeek());
                holder1.date.setText(""+a.getDate());
                holder1.content.setText(""+a.getContent());
                break;

            case TYPE_B:
                B b = (B) o;
                holder2.point.setImageResource(b.getImgResourceID());
                break;
        }
        return convertView;
    }

    /**
         * item A 的Viewholder
         */
        private static class ViewHolder1 {
            TextView week;
            TextView date;
            TextView content;

        }
        /**
         * item B 的Viewholder
         */
        private static class ViewHolder2 {
            ImageView point;
        }

}
