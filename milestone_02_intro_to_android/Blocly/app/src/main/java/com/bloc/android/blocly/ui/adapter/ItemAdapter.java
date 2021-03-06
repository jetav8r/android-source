package com.bloc.android.blocly.ui.adapter;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.blocly.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.lang.ref.WeakReference;

/**
 * Created by Wayne on 12/25/2014.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterViewHolder> {

    public static interface DataSource {
        // The DataSource interface (#1) will supply the ItemAdapter with information. This pattern de-couples ItemAdapter from the DataSource class, and allows any controller to use ItemAdapter for its own purposes. E.g. favorites list, archived list, feed list. etc.
        public RssItem getRssItem(ItemAdapter itemAdapter, int position);
        public RssFeed getRssFeed(ItemAdapter itemAdapter, int position);
        public int getItemCount(ItemAdapter itemAdapter);
    }

    public static interface Delegate {
        public void onItemClicked(ItemAdapter itemAdapter, RssItem rssItem);
        public void onVisitClicked(ItemAdapter itemAdapter, RssItem rssItem);
        public void didSelectVisit(RssItem rssItem);
        public void didSelectShare(RssItem rssItem);
    }

    private WeakReference<Delegate> delegate;
    private WeakReference<DataSource> dataSource;

    private static String TAG = ItemAdapter.class.getSimpleName();// for logging purposes

    private RssItem expandedItem = null; // used for tracking state of view expansion

    /*
    public static interface ItemAdapterDelegate {
        public void didSelectExpandItem();
        public void didSelectContractItem();
        public void didSelectVisit(RssItem rssItem);
        public void didSelectFavorite();
        public void didSelectUnfavorite();
        public void didSelectArchive();
        public void didSelectShare();
    }


    // WeakReference object to store our itemAdapterDelegate. A WeakReference allows us to use an object
    // as long as a strong reference to it exists somewhere
    WeakReference<ItemAdapterDelegate> itemAdapterDelegate;

    //getter and setter for itemAdapterDelegate
    //Use WeakReference.get() to recover the object within, but remember, if the original reference
    // has been removed, this method will return null.
    public ItemAdapterDelegate getItemAdapterDelegate() {
        if (itemAdapterDelegate == null) {
            return null;
        }
        return itemAdapterDelegate.get();
    }

    public void setItemAdapterDelegate(ItemAdapterDelegate itemAdapterDelegate) {
        this.itemAdapterDelegate = new WeakReference<ItemAdapterDelegate>(itemAdapterDelegate);
    }
    */


    @Override
    public ItemAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int index) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rss_item, viewGroup, false);
        return new ItemAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ItemAdapterViewHolder itemAdapterViewHolder, int index) {
        //DataSource sharedDataSource = BloclyApplication.getSharedDataSource();
        //itemAdapterViewHolder.update(sharedDataSource.getFeeds().get(0), sharedDataSource.getItems().get(index));
        if (getDataSource() == null) {
            return;
        }
        // Deciding which RSS items to display is no longer ItemAdapter's responsibility, it now behaves strictly as a view (with respect to the Model-View-Controller paradigm). It will be a controller's responsibility (BloclyActivity) to specify which models to display.
        RssItem rssItem = getDataSource().getRssItem(this, index);
        RssFeed rssFeed = getDataSource().getRssFeed(this, index);
        itemAdapterViewHolder.update(rssFeed, rssItem);
    }



    @Override
    public int getItemCount() {
        return BloclyApplication.getSharedDataSource().getItems().size();
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            return null;
        }
        return dataSource.get();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = new WeakReference<DataSource>(dataSource);
    }

    public Delegate getDelegate() {
        if (delegate == null) {
            return null;
        }
        return delegate.get();
    }

    public void setDelegate(Delegate delegate) {
        this.delegate = new WeakReference<Delegate>(delegate);
    }

    //An expandedItem field to represent the RssItem.
    public RssItem getExpandedItem() {
        return expandedItem;
    }

    public void setExpandedItem(RssItem expandedItem) {
        this.expandedItem = expandedItem;
    }

    class ItemAdapterViewHolder extends RecyclerView.ViewHolder implements ImageLoadingListener, View.OnClickListener,
            CompoundButton.OnCheckedChangeListener {

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
        ImageButton shareButton;


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
            shareButton = (ImageButton) expandedContentWrapper.findViewById(R.id.action_share);

            visitSite.setOnClickListener(this);
            shareButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
            archiveCheckbox.setOnCheckedChangeListener(this);
            favoriteCheckbox.setOnCheckedChangeListener(this);
        }


        /*
        //commented below was working for background color change and content expansion
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

        //
          //ImageLoadingListener

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
                //contentExpanded = !contentExpanded;
                //expandedContentWrapper.setVisibility(contentExpanded ? View.VISIBLE : View.GONE);
                //content.setVisibility(contentExpanded ? View.GONE : View.VISIBLE);
                //replace the 3 lines above with an animated transition using code below
                animateContent(!contentExpanded);
            } else {
                Message.message(view.getContext(), "Visit " + rssItem.getUrl());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            Log.v(TAG, "Checkbox " + compoundButton + " changed to: " + isChecked);
        }

        //replaced with new methods to get image and wrapper animations

        */
        void update(RssFeed rssFeed, RssItem rssItem) {
            this.rssItem = rssItem;
            feed.setText(rssFeed.getTitle());
            title.setText(rssItem.getTitle());
            content.setText(Html.fromHtml(rssItem.getDescription()));
            expandedContent.setText(Html.fromHtml(rssItem.getDescription()));
            imageURL = rssItem.getImageUrl();
            if (imageURL != null) {
                //  If the RssItem has an image URL, we will make visible our FrameLayout
                // However, if it does not, we hide our FrameLayout
                headerWrapper.setVisibility(View.VISIBLE);
                headerImage.setVisibility(View.INVISIBLE);
                ImageLoader.getInstance().loadImage(imageURL, this);

            } else {
                //we let the animator fade from visible to invisible instead of doing it here
                animateCollapseWrapper(!contentExpanded);
            }
            //guarantee that the View associated with the expanded RSS item is the only View which expands
            //If the ItemAdapterViewHolder's RssItem matches ItemAdapter's expanded RssItem, it
            // will expand its View, otherwise it will contract it.
            animateContent(getExpandedItem() == rssItem);
        }


          //ImageLoadingListener

        @Override
        public void onLoadingStarted(String imageUri, View view) {
        }

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
                // after image loads, we let animator fade from 0 alpha to fully visible
                headerImage.setVisibility(View.VISIBLE);
                //animateImage(!contentExpanded);
                }
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {
            // Attempt a retry
            ImageLoader.getInstance().loadImage(imageUri, this);
        }

        @Override
        public void onClick(View view) {
            //view.setBackgroundColor(Color.parseColor("#ffeb3b"));
            //Message.message(view.getContext(), rssItem.getTitle());
            if (view == itemView) {
                if (getDelegate() != null) {
                    getDelegate().onItemClicked(ItemAdapter.this, rssItem);
                }

                //Message.message(view.getContext(),"content expanded = " +contentExpanded);
                /*if (contentExpanded == false) {
                    getItemAdapterDelegate().didSelectExpandItem();
                }
                else {
                    getItemAdapterDelegate().didSelectContractItem();
                }*/


                //contentExpanded = !contentExpanded;
                //expandedContentWrapper.setVisibility(contentExpanded ? View.VISIBLE : View.GONE);
                //content.setVisibility(contentExpanded ? View.GONE : View.VISIBLE);
                //replace the 3 lines above with an animated transition using code below
                //animateContent(!contentExpanded);
            } else {
                if (view.getId() == R.id.action_share) {

                    getDelegate().didSelectShare(rssItem);
                    //Message.message(view.getContext(), "share was clicked");

                } else {

                    //getDelegate().didSelectVisit(rssItem);
                    if (getDelegate() != null) {
                        getDelegate().onVisitClicked(ItemAdapter.this, rssItem);
                    }
                    //Message.message(view.getContext(), "Visit " + rssItem.getUrl());
                }
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            /*if (compoundButton == favoriteCheckbox) {
                if (isChecked) {
                    getItemAdapterDelegate().didSelectFavorite();
                } else {
                    getItemAdapterDelegate().didSelectUnfavorite();
                }
            }
            if (compoundButton == archiveCheckbox) {
                if (isChecked) {
                    getItemAdapterDelegate().didSelectArchive();
                }
            }*/
            Log.v(TAG, "Checkbox " + compoundButton.getId() + " changed to: " + isChecked);
        }


        //Private Methods


        //private void animateImage(final boolean expand) {
        private void animateImage() {
            /*boolean mExpand = expand;
            // If the image is already in the desired state, we simply return
            if ((expand && contentExpanded) || (!expand && !contentExpanded)) {
                return;
            }*/
            // If we must animate to a new state, we create initial and final height variables to animate between
            //int startingHeight = expandedContentWrapper.getMeasuredHeight();
            int startingHeight = 0;
            //int finalHeight = headerImage.getMeasuredHeight();
            int finalHeight = 140;

            //if (expand) {

                startingHeight = 0;
                headerImage.setAlpha(0f);
                headerWrapper.setVisibility(View.VISIBLE);
                // determine the target height of expansion, measure itself given the constraints provided. We
                // constrain it to the width of content but leave its height unlimited. getMeasuredHeight() then
                // provides us with the height (in pixels) that expandedContentWrapper wishes to be.
                headerImage.measure(
                        View.MeasureSpec.makeMeasureSpec(content.getWidth(), View.MeasureSpec.EXACTLY),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                //finalHeight = headerImage.getMeasuredHeight();
            //} else {
                //headerWrapper.setVisibility(View.INVISIBLE);
            //}
            startAnimator(startingHeight, finalHeight, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    // recover the animation's progress as a float value, set the opacity level for both content
                    // and expandedContentWrapper. This performs a cross-fade from one View to the other
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    //float imageAlpha = expand ? animatedFraction : 1f - animatedFraction;
                    float imageAlpha = animatedFraction;
                    float wrapperAlpha = 1f - imageAlpha;

                    headerWrapper.setAlpha(imageAlpha);
                    headerImage.setAlpha(imageAlpha);
                    // set the height of headerWrapper. As we animate, we do so from startingHeight to
                    // finalHeight, the current integer value is recovered by invoking getAnimatedValue().
                    headerWrapper.getLayoutParams().height = animatedFraction == 1f ?
                            ViewGroup.LayoutParams.WRAP_CONTENT :
                            (Integer) valueAnimator.getAnimatedValue();
                    // asks the View to redraw itself
                    headerWrapper.requestLayout();
                    /*
                    if (animatedFraction == 1f) {
                        if (expand) {
                            headerImage.setVisibility(View.VISIBLE);
                        } else {
                            headerWrapper.setVisibility(View.GONE);
                        }
                    }
                    */

                }
            });
            //contentExpanded = expand;
        }




        private void animateCollapseWrapper(final boolean expand) {
            boolean mExpand = expand;
            // If the image is already in the desired state, we simply return
            if ((expand && contentExpanded) || (!expand && !contentExpanded)) {
                return;
            }
            // If we must animate to a new state, we create initial and final height variables to animate between
            //int startingHeight = expandedContentWrapper.getMeasuredHeight();
            int startingHeight = 140;
            int finalHeight = 0;
            if (expand) {

                headerWrapper.setAlpha(1f);
                headerWrapper.setVisibility(View.VISIBLE);
                // determine the target height of expansion, measure itself given the constraints provided. We
                // constrain it to the width of content but leave its height unlimited. getMeasuredHeight() then
                // provides us with the height (in pixels) that expandedContentWrapper wishes to be.
                //headerWrapper.measure(
                //View.MeasureSpec.makeMeasureSpec(content.getWidth(), View.MeasureSpec.EXACTLY),
                //ViewGroup.LayoutParams.WRAP_CONTENT
                //);
                //startingHeight = headerImage.getMeasuredHeight();
            } else {
                headerWrapper.setVisibility(View.GONE);
            }
            startAnimator(startingHeight, finalHeight, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    // recover the animation's progress as a float value, set the opacity level for both content
                    // and expandedContentWrapper. This performs a cross-fade from one View to the other
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float imageAlpha = expand ? animatedFraction : 1f - animatedFraction;
                    float wrapperAlpha = 1f - imageAlpha;

                    headerWrapper.setAlpha(wrapperAlpha);
                    //headerWrapper.setAlpha(imageAlpha);

                    headerWrapper.getLayoutParams().height = animatedFraction == 1f ?
                            ViewGroup.LayoutParams.WRAP_CONTENT :
                            (Integer) valueAnimator.getAnimatedValue();
                    // asks the View to redraw itself unless animation complete
                    headerWrapper.requestLayout();



                    if (animatedFraction == 1f) {
                        headerWrapper.setVisibility(View.GONE);

                        /*
                        if (expand) {
                            headerImage.setVisibility(View.VISIBLE);
                        } else {
                            headerWrapper.setVisibility(View.GONE);
                        }
                        */
                    }


                }
            });
            //contentExpanded = expand;
        }



        //Private Methods


    private void animateContent(final boolean expand) {
        // If the RSS item is already in the desired state, we simply return
        if ((expand && contentExpanded) || (!expand && !contentExpanded)) {
            return;
        }
        // If we must animate to a new state, we create initial and final height variables to animate between
        int startingHeight = expandedContentWrapper.getMeasuredHeight();
        int finalHeight = content.getMeasuredHeight();
        if (expand) {

        //  set the starting height to that of the preview content

            shareButton.setAlpha(0f);
            startingHeight = finalHeight;
            expandedContentWrapper.setAlpha(0f);
            expandedContentWrapper.setVisibility(View.VISIBLE);
        // determine the target height of expansion, measure itself given the constraints provided. We
        // constrain it to the width of content but leave its height unlimited. getMeasuredHeight() then
        // provides us with the height (in pixels) that expandedContentWrapper wishes to be.
            expandedContentWrapper.measure(
                    View.MeasureSpec.makeMeasureSpec(content.getWidth(), View.MeasureSpec.EXACTLY),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            finalHeight = expandedContentWrapper.getMeasuredHeight();
        } else {
            content.setVisibility(View.VISIBLE);
        }
        startAnimator(startingHeight, finalHeight, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
        // recover the animation's progress as a float value, set the opacity level for both content
        // and expandedContentWrapper. This performs a cross-fade from one View to the other
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    float wrapperAlpha = expand ? animatedFraction : 1f - animatedFraction;
                    float contentAlpha = 1f - wrapperAlpha;

                    expandedContentWrapper.setAlpha(wrapperAlpha);
                    content.setAlpha(contentAlpha);
                    shareButton.setAlpha(wrapperAlpha);

        // set the height of expandedContentWrapper. As we animate, we do so from startingHeight to
        // finalHeight, the current integer value is recovered by invoking getAnimatedValue().
                    expandedContentWrapper.getLayoutParams().height = animatedFraction == 1f ?
                            ViewGroup.LayoutParams.WRAP_CONTENT :
                            (Integer) valueAnimator.getAnimatedValue();
        // asks the View to redraw itself
                    expandedContentWrapper.requestLayout();
                    if (animatedFraction == 1f) {
                        if (expand) {
                            content.setVisibility(View.GONE);
                        } else {
                            expandedContentWrapper.setVisibility(View.GONE);
                        }
                    }
                }
        });
        contentExpanded = expand;
    }
        private void startAnimator(int start, int end, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
            valueAnimator.addUpdateListener(animatorUpdateListener);
            // set the duration of the animation ..for testing, I'll use 2.5 secs
            //valueAnimator.setDuration(2500);
            valueAnimator.setDuration(itemView.getResources().getInteger(android.R.integer.config_mediumAnimTime));
            // This interpolator accelerates to a constant speed, then decelerates to stop at the end
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.start();
        }
   }
}

