package com.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.ui.adapter.ItemAdapter;
import com.bloc.android.blocly.ui.adapter.NavigationDrawerAdapter;
import com.bloc.android.blocly.utilities.Message;
import com.bloc.blocly.R;

import java.util.ArrayList;

/**
 * Created by Wayne on 12/23/2014.
 */
public class BloclyActivity extends Activity implements NavigationDrawerAdapter.NavigationDrawerAdapterDelegate, ItemAdapter.ItemAdapterDelegate {

    private ItemAdapter itemAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;
    private Menu menu;
    private View overflowButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocly);
        itemAdapter = new ItemAdapter();
        itemAdapter.setDelegate(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_blocly);

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
        drawerLayout.openDrawer(Gravity.LEFT);

        navigationDrawerAdapter = new NavigationDrawerAdapter();
        navigationDrawerAdapter.setDelegate(this);
        RecyclerView navigationRecyclerView = (RecyclerView) findViewById(R.id.rv_nav_activity_blocly);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        navigationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        navigationRecyclerView.setAdapter(navigationDrawerAdapter);

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
        getMenuInflater().inflate(R.menu.blocly, menu);
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

    @Override
    public void didSelectExpandItem() {
        Message.message(this, "Item was expanded");
    }

    @Override
    public void didSelectContractItem() {
        Message.message(this, "Item was contracted");
    }

    @Override
    public void didSelectVisit() {
        Message.message(this, "Visit site was selected");
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
    }
}
