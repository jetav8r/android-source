package com.bloc.android.blocly;

import android.app.Application;

import com.bloc.android.blocly.api.DataSource;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Wayne on 12/24/2014.
 */
public class BloclyApplication extends Application {

    private static BloclyApplication sharedInstance;
    private DataSource dataSource;

    public static BloclyApplication getSharedInstance() {
        return sharedInstance;
    }

    public static DataSource getSharedDataSource() {
        return BloclyApplication.getSharedInstance().getDataSource();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedInstance = this;
        dataSource = new DataSource();


        // create a default instance of these options
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();

        // #2
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(configuration);

    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
