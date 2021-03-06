package com.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bloc.android.blocly.BloclyApplication;
import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.api.model.RssItem;
import com.bloc.android.blocly.ui.adapter.ItemAdapter;
import com.bloc.android.blocly.ui.adapter.NavigationDrawerAdapter;
import com.bloc.android.blocly.utilities.Message;
import com.bloc.blocly.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne on 12/23/2014.
 */
public class BloclyActivity extends Activity implements
        NavigationDrawerAdapter.NavigationDrawerAdapterDelegate,
        ItemAdapter.DataSource,
        ItemAdapter.Delegate {

    private ItemAdapter itemAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private Menu menu;
    private View overflowButton;
    RecyclerView recyclerView;
    Boolean show;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logIntents();
        invalidateOptionsMenu();
        setContentView(R.layout.activity_blocly);
        itemAdapter = new ItemAdapter();
        //itemAdapter.setItemAdapterDelegate(this);
        itemAdapter.setDataSource(this);
        itemAdapter.setDelegate(this);
        show = false;



        recyclerView = (RecyclerView) findViewById(R.id.rv_activity_blocly);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(itemAdapter);


        // recover the instance of ActionBar and invoke setDisplayHomeAsUpEnabled(boolean) to allow this behavior.
        getActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.dl_activity_blocly);
        // instantiate arrow icon and set it as our DrawerLayout's DrawerListener
        //drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0) {
            //overrides two of ActionBarDrawerToggle's methods: onDrawerOpened(View) and onDrawerClosed(View)
            // to invalidate the menu on each event
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (overflowButton != null) {
                    overflowButton.setAlpha(1f);
                // changing alpha of button and enabling as drawer closes
                    overflowButton.setEnabled(true);
                }
                if (menu == null) {
                    return;
                }
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    // same here for menu items
                    item.setEnabled(true);
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        icon.setAlpha(255);
                    }
                }            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (overflowButton != null) {
                // even though menu items aren't visible, they are still clickable...this fixes that
                    overflowButton.setEnabled(false);
                }
                if (menu == null) {
                    return;
                }
                for (int i = 0; i < menu.size(); i++) {
                // same here for each menu item that exists, but isn't visible
                    menu.getItem(i).setEnabled(false);
                }
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (overflowButton == null) {
                // There is no direct way of recovering a reference to the overflow button as it is not included within the Menu's items nor ActionBar's APIs. Therefore, at #9 we search for a View, any View on screen for that matter, whose content description matches the string found at R.string.abc_action_menu_overflow_description.
                    ArrayList<View> foundViews = new ArrayList<View>();
                    getWindow().getDecorView().findViewsWithText(foundViews,
                            getString(R.string.abc_action_menu_overflow_description),
                            View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                    if (foundViews.size() > 0) {
                        overflowButton = foundViews.get(0);
                    }
                }
                // set the opacity levels of both action items and overflow button
                if (overflowButton != null) {
                    overflowButton.setAlpha(1f - slideOffset);
                }
                if (menu == null) {
                    return;
                }
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem item = menu.getItem(i);
                    Drawable icon = item.getIcon();
                    if (icon != null) {
                        icon.setAlpha((int) ((1f - slideOffset) * 255));
                    }
                }
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        //drawerLayout.openDrawer(Gravity.LEFT);

        navigationDrawerAdapter = new NavigationDrawerAdapter();
        navigationDrawerAdapter.setDelegate(this);
        RecyclerView navigationRecyclerView = (RecyclerView) findViewById(R.id.rv_nav_activity_blocly);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationRecyclerView.setAdapter(navigationDrawerAdapter);

    }

    private void logIntents() {
        packageManager = getPackageManager();
        final Intent myIntent1 = new Intent(Intent.ACTION_SEND);
        final Intent myIntent2 = new Intent(Intent.ACTION_VIEW);
        final Intent myIntent3 = new Intent(Intent.ACTION_CALL);

        List<ResolveInfo> resInfoList1 = getPackageManager().queryIntentActivities(myIntent1, 0);
        for (int i = 0; i < resInfoList1.size(); i++) {
            ResolveInfo ri = resInfoList1.get(i);
            String packageName = ri.loadLabel(packageManager).toString();
            Log.i("Activities capable of email: ", packageName);
        }

        List<ResolveInfo> resInfoList2 = getPackageManager().queryIntentActivities(myIntent2, 0);
        for (int i = 0; i < resInfoList2.size(); i++) {
            ResolveInfo ri = resInfoList2.get(i);
            String packageName = ri.loadLabel(packageManager).toString();
            Log.i("Activities capable of browsing: ",packageName);
        }
        List<ResolveInfo> resInfoList3 = getPackageManager().queryIntentActivities(myIntent3, 0);
        for (int i = 0; i < resInfoList3.size(); i++) {
            ResolveInfo ri = resInfoList3.get(i);
            String packageName = ri.loadLabel(packageManager).toString();
            Log.i("Activities capable of calling: ",packageName);
        }
    }

    //  override and invoke required drawer updates
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (drawerToggle.onOptionsItemSelected(item)) {
        }
        if (id == R.id.action_search) {
            Message.message(this, "Searching is fun!");
            return true;
        }
        if (id == R.id.action_share) {
            Message.message(this, "I love to share!");
            return true;
        }
        if (id == R.id.action_refresh) {
            Message.message(this, "Refresh it up...");
            return true;
        }
        if (id == R.id.action_mark_as_read) {
            Message.message(this, "Mark 'em all!");
            return true;
        }
        //Message.message(this, "Item ID = " + id);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.blocly, menu);
        MenuItem share = menu.findItem(R.id.action_share);
        share.setVisible(show);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    /*
      * NavigationDrawerAdapterDelegate
      */

    @Override
    public void didSelectNavigationOption(NavigationDrawerAdapter adapter, NavigationDrawerAdapter.NavigationOption navigationOption) {
    // purpose and reference from interface in NavigationDrawerAdapter
        drawerLayout.closeDrawers();
        Message.message(this, "Show the " + navigationOption.name());
    }

    @Override
    public void didSelectFeed(NavigationDrawerAdapter adapter, RssFeed rssFeed) {
    // purpose and reference from interface in NavigationDrawerAdapter
        drawerLayout.closeDrawers();
        Message.message(this, "Show RSS items from " + rssFeed.getTitle());
    }

    //Set BloclyActivity as ItemAdapter's delegate and data source.
    /*
      * ItemAdapter.DataSource
      */

    @Override
    public RssItem getRssItem(ItemAdapter itemAdapter, int position) {
        return BloclyApplication.getSharedDataSource().getItems().get(position);
    }

    @Override
    public RssFeed getRssFeed(ItemAdapter itemAdapter, int position) {
        return BloclyApplication.getSharedDataSource().getFeeds().get(0);
    }

    @Override
    public int getItemCount(ItemAdapter itemAdapter) {
        return BloclyApplication.getSharedDataSource().getItems().size();
    }

     /*
      * ItemAdapter.Delegate
      */

    @Override
    public void onItemClicked(ItemAdapter itemAdapter, RssItem rssItem) {
        int positionToExpand = -1;
        int positionToContract = -1;
        //recyclerView.smoothScrollToPosition(itemAdapter.getItemCount()-1);  This moves to bottom of lists


        // check if ItemAdapter has an expanded item
        if (itemAdapter.getExpandedItem() != null) {
            positionToContract = BloclyApplication.getSharedDataSource().getItems().indexOf(itemAdapter.getExpandedItem());
            recyclerView.smoothScrollToPosition(positionToContract + 1);
        }
        // When a new item is clicked, we recover its position within the list and set it as the expanded item, unless item is
        // expanded, in which case, we set expanded item to null
        // indexOf(), found in List, retrieves the integer position of the given object if found in the list, otherwise, -1.
        if (itemAdapter.getExpandedItem() != rssItem) {
            menu.clear();
            show=true;
            invalidateOptionsMenu();
            positionToExpand = BloclyApplication.getSharedDataSource().getItems().indexOf(rssItem);
            recyclerView.smoothScrollToPosition(positionToExpand + 1);
            itemAdapter.setExpandedItem(rssItem);

        } else {
            menu.clear();
            show=false;
            invalidateOptionsMenu();
            itemAdapter.setExpandedItem(null);
        }
        if (positionToContract > -1) {
        // notify the ItemAdapter of a change
            itemAdapter.notifyItemChanged(positionToContract);
        }
        if (positionToExpand > -1) {
        // notify the ItemAdapter of a change
            itemAdapter.notifyItemChanged(positionToExpand);
        }
    }

    @Override
    public void onVisitClicked(ItemAdapter itemAdapter, RssItem rssItem) {
        Intent visitIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.getUrl()));
        startActivity(visitIntent);
    }

    public void shareRss(RssItem rssItem){
        String request = rssItem.getTitle() + " :  "+rssItem;
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this story!");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(request));
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    @Override
    public void didSelectVisit(RssItem rssItem) {
        Message.message(this, "Visit " + rssItem.getUrl());
    }

    @Override
    public void didSelectShare(RssItem rssItem) {
        String request = rssItem.getTitle() + " :  " +rssItem.getUrl();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this story!");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(request));
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    /*@Override
    public void didSelectExpandItem() {
        Message.message(this, "Item was expanded");
    }

    @Override
    public void didSelectContractItem() {
        Message.message(this, "Item was contracted");
    }


    @Override
    public void didSelectFavorite() {
        Message.message(this, "Favorite was added");
    }

    @Override
    public void didSelectUnfavorite() {
        Message.message(this, "Favorite was deleted");
    }

    @Override
    public void didSelectArchive() {
        Message.message(this, "Item was archived");
    }*/
}
