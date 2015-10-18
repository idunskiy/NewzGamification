package mobapply.newzgamification.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import mobapply.newzgamification.R;
import mobapply.newzgamification.activities.MainActivity;
import mobapply.newzgamification.model.NewsItem;

/**
 * Created by ${IvanDunskiy} on 10/17/15.
 * Copyright (c) 2015 ${MobApply}. All rights reserved.
 */
public class NewsListAdapter extends ArrayAdapter<NewsItem> {
    private final static DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm");
    private static final String TAG = "NewsListAdapter";
    private final HashMap<NewsItem, Boolean> liked = new HashMap<>();

    public NewsListAdapter(final Context context, int resource, int textViewResourceId, List<NewsItem> newsItemList) {
        super(context, resource, newsItemList);
    }

    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder holder; // to reference the child views for later actions

        if (v == null) {
            LayoutInflater vi =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.fragment_newsitem, null);
            // cache view fields into the holder
            holder = new ViewHolder();
            holder.news_date = (TextView) v.findViewById(R.id.news_date);
            holder.news_title = (TextView) v.findViewById(R.id.news_title);
            holder.news_image = (ImageView) v.findViewById(R.id.news_image);
            holder.shareButton = (ImageButton) v.findViewById(R.id.shareButton);
            holder.shareButton.setTag(holder);
            holder.thumbUpButton = (ImageButton) v.findViewById(R.id.thumbUpButton);
            holder.thumbUpButton.setTag(holder);
            holder.thumbDownButton = (ImageButton) v.findViewById(R.id.thumbDownButton);
            holder.thumbDownButton.setTag(holder);
            // associate the holder with the view for later lookup
            v.setTag(holder);
        }
        else {
            // view already exists, get the holder instance from the view
            holder = (ViewHolder) v.getTag();
        }
        NewsItem newsItem = getItem(position);
        holder.item = newsItem;
        holder.news_date.setText(df.format(newsItem.getDate().getTime()));
        holder.news_title.setText(newsItem.getSummary());
        Glide.with(getContext()).load(newsItem.getImage()).into(holder.news_image);
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.increaseProfileScore(20);
                Log.d(TAG, "onClick() called with: " + "v = [" + v + "]");
                ViewHolder localHolder = (ViewHolder) v.getTag();

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, localHolder.item.getSummary());
                share.putExtra(Intent.EXTRA_TEXT, localHolder.item.getUrl());
                getContext().startActivity(Intent.createChooser(share,
                        getContext().getString(R.string.share_news)));
            }
        });
        holder.thumbUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder localHolder = (ViewHolder) v.getTag();
                if (! liked.containsKey(localHolder.item)) {
                    MainActivity.increaseProfileScore(5);
                }
                localHolder.thumbUpButton.setEnabled(false);
                localHolder.thumbDownButton.setEnabled(true);
                liked.put(localHolder.item, true);
            }
        });
        holder.thumbDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolder localHolder = (ViewHolder) v.getTag();
                if (! liked.containsKey(localHolder.item)) {
                    MainActivity.increaseProfileScore(5);
                }
                localHolder.thumbUpButton.setEnabled(true);
                localHolder.thumbDownButton.setEnabled(false);
                liked.put(localHolder.item, false);
            }
        });
        if (liked.containsKey(newsItem)) {
            holder.thumbUpButton.setEnabled(!liked.get(newsItem));
            holder.thumbDownButton.setEnabled(liked.get(newsItem));
        } else {
            holder.thumbUpButton.setEnabled(true);
            holder.thumbDownButton.setEnabled(true);
        }
        return v;
    }


    static class ViewHolder {
        TextView news_date;
        TextView news_title;
        ImageView news_image;
        ImageButton shareButton;
        ImageButton thumbUpButton;
        ImageButton thumbDownButton;
        NewsItem item;
    }

}
