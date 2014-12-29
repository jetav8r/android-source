package com.bloc.android.blocly.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.api.DataSource;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.utilities.Message;
import com.bloc.blocly.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Wayne on 12/25/2014.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {

    private static String TAG = ItemAdapter.class.getSimpleName();// for logging purposes

    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_item, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
        DataSource sharedDataSource = BloclyApplication.getSharedDataSource();
        itemAdapterViewHolder.update(sharedDataSource.getFeeds().get(0), sharedDataSource.getItems().get(index));
    }

    @Override
    public int getItemCount() {
        return BloclyApplication.getSharedDataSource().getItems().size();
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements ImageLoadingListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

        private final ImageView headerImage;
        private final View headerWrapper;
        CheckBox archiveCheckbox;
        CheckBox favoriteCheckbox;
        TextView title;
        TextView feed;
        TextView content;
        private String imageURL;
        RssItem rssItem;
        boolean contentExpanded;
        View expandedContentWrapper;
        TextView expandedContent;
        TextView visitSite;


        public ItemAdapterViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_rss_item_title);
            feed = (TextView) itemView.findViewById(R.id.tv_rss_item_feed_title);
            content = (TextView) itemView.findViewById(R.id.tv_rss_item_content);
            headerWrapper = itemView.findViewById(R.id.fl_rss_item_image_header);
            headerImage = (ImageView) headerWrapper.findViewById(R.id.iv_rss_item_image);
            archiveCheckbox = (CheckBox) itemView.findViewById(R.id.cb_rss_item_check_mark);
            favoriteCheckbox = (CheckBox) itemView.findViewById(R.id.cb_rss_item_favorite_star);
            expandedContentWrapper = itemView.findViewById(R.id.ll_rss_item_expanded_content_wrapper);
            expandedContent = (TextView) expandedContentWrapper.findViewById(R.id.tv_rss_item_content_full);
            //visitSite = (TextView) expandedContentWrapper.findViewById(R.id.tv_rss_item_visit_site);
            visitSite = (TextView) expandedContentWrapper.findViewById(R.id.tv_roboto_button);

            visitSite.setOnClickListener(this);
            itemView.setOnClickListener(this);
            archiveCheckbox.setOnCheckedChangeListener(this);
            favoriteCheckbox.setOnCheckedChangeListener(this);
        }

        void update(RssFeed rssFeed, RssItem rssItem) {
                this.rssItem = rssItem;
                feed.setText(rssFeed.getTitle());
                title.setText(rssItem.getTitle());
                content.setText(rssItem.getDescription());
                expandedContent.setText(rssItem.getDescription());
                imageURL = rssItem.getImageUrl();
                if (imageURL != null) {
                //  If the RssItem has an image URL, we will make visible our FrameLayout
                // However, if it does not, we hide our FrameLayout
                    if (rssItem.getImageUrl() != null) {
                        headerWrapper.setVisibility(View.VISIBLE);
                        headerImage.setVisibility(View.INVISIBLE);
                        ImageLoader.getInstance().loadImage(imageURL, this);
                    } else {
                        headerWrapper.setVisibility(View.GONE);
                    }
                }

        }

        /*
          * ImageLoadingListener
          */
        @Override
        public void onLoadingStarted(String imageUri, View view) {}

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            try {
                Log.e(TAG, "onLoadingFailed: " + failReason.toString() + " for URL: " + imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        // make sure that the image recovered was found at the URL of our most recent request
        // before we associate the Bitmap as our ImageView's content
            if (imageUri.equals(rssItem.getImageUrl())) {
                headerImage.setImageBitmap(loadedImage);
                headerImage.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            // Attempt a retry
            ImageLoader.getInstance().loadImage(imageUri, this);
        }

        @Override
        public void onClick(View view) {
            view.setBackgroundColor(Color.parseColor("#ffeb3b"));
            Message.message(view.getContext(), rssItem.getTitle());
            if (view == itemView) {
                contentExpanded = !contentExpanded;
                expandedContentWrapper.setVisibility(contentExpanded ? View.VISIBLE : View.GONE);
                content.setVisibility(contentExpanded ? View.GONE : View.VISIBLE);
            } else {
                Message.message(view.getContext(), "Visit " + rssItem.getUrl());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            Log.v(TAG, "Checkbox " + compoundButton + " changed to: " + isChecked);
        }
    }
}

