package mobapply.newzgamification.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import mobapply.newzgamification.R;
import mobapply.newzgamification.model.NewsItem;

/**
 * Created by ${IvanDunskiy} on 10/17/15.
 * Copyright (c) 2015 ${MobApply}. All rights reserved.
 */
public class NewsListAdapter extends ArrayAdapter<NewsItem> {

    public NewsListAdapter(Context context, int resource, int textViewResourceId, List<NewsItem> newsItemList) {
        super(context, resource, newsItemList);
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder; // to reference the child views for later actions

        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fragment_newsitem, null);
            // cache view fields into the holder
            holder = new ViewHolder();
            holder.news_date = (TextView) v.findViewById(R.id.news_date);
            holder.news_title = (TextView) v.findViewById(R.id.news_title);
            holder.news_image = (ImageView) v.findViewById(R.id.news_image);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }
        NewsItem newsItem = getItem(position);
        DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm");
        holder.news_date.setText(df.format(newsItem.getDate().getTime()));
        holder.news_title.setText(newsItem.getSummary());
        Glide.with(getContext()).load(newsItem.getImage()).into(holder.news_image);
        return v;
    }


    static class ViewHolder {
        TextView news_date;
        TextView news_title;
        ImageView news_image;
    }

}
