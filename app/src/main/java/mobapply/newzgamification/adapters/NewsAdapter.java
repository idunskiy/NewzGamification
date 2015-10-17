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
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private  Context context;
    private  ImageView news_image;
    private  TextView news_title;
    private  TextView news_date;
    private List<NewsItem> records;

    public NewsAdapter(Context context) {
        this.context = context;
    }
    public NewsAdapter(List<NewsItem> records) {
        this.records = records;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_newsitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        NewsItem newsItem = records.get(i);
        DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm");
        viewHolder.news_date.setText(df.format(df));
        viewHolder.news_title.setText(newsItem.getSummary());
        Glide.with(context).load(newsItem.getImage()).into( viewHolder.news_image);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView news_date;
        private TextView news_title;
        private ImageView news_image;

        public ViewHolder(View itemView) {
            super(itemView);
            news_date = (TextView) itemView.findViewById(R.id.news_date);
            news_title = (TextView) itemView.findViewById(R.id.news_title);
            news_image = (ImageView) itemView.findViewById(R.id.news_image);

        }
    }
}
