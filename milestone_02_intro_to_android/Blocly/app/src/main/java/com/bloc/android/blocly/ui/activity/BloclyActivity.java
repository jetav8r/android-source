package com.bloc.android.blocly.ui.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;

import com.bloc.android.blocly.api.model.RssFeed;
import com.bloc.android.blocly.ui.adapter.ItemAdapter;
import com.bloc.android.blocly.ui.adapter.NavigationDrawerAdapter;
import com.bloc.android.blocly.utilities.Message;
import com.bloc.blocly.R;

/**
 * Created by Wayne on 12/23/2014.
 */
public class BloclyActivity extends Activity implements NavigationDrawerAdapter.NavigationDrawerAdapterDelegate, ItemAdapter.ItemAdapterDelegate {

    private ItemAdapter itemAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationDrawerAdapter navigationDrawerAdapter;

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
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, 0, 0);
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
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
