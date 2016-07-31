package com.example.steve.nytarticlesearch.Activities;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.steve.nytarticlesearch.Models.Doc;
import com.example.steve.nytarticlesearch.Models.Multimedium;
import com.example.steve.nytarticlesearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleArrayAdapter extends ArrayAdapter<Doc>{

    private Context context;
    private LayoutInflater inflater;

    public static class ViewHolder
    {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
    }


    public ArticleArrayAdapter(Context context, List<Doc> articles)
    {
        super(context,android.R.layout.simple_list_item_1,articles);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get data item for position
        Doc article = this.getItem(position);
        List<Multimedium> multimedia = article.getMultimedia();
        String thumbnail = "http://www.nytimes.com/";

        if (multimedia.size() > 0) {
            thumbnail += multimedia.get(0).getUrl();
        }
        else {
            thumbnail = "";
        }

        ViewHolder viewHolder;

        //check if existing view is being used

        //if not being used -> inflate the layout
        if (convertView == null || !(convertView.getTag() instanceof ViewHolder))
        {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
            viewHolder = new ViewHolder(convertView);

            //set viewholder pieces
            viewHolder.tvTitle.setText(article.getHeadline().getMain());


            if(!TextUtils.isEmpty(thumbnail)) {
                Glide
                        .with(context)
                        .load(thumbnail)
                        //.placeholder(R.drawable.video_placeholder_640).error(R.drawable.notification_error)
                        .into(viewHolder.ivImage);
            }

            convertView.setTag(viewHolder);
        }
        else { viewHolder = (ViewHolder) convertView.getTag(); }


        //clear out recycled image from convertView from last time



        return convertView;
    }
}
