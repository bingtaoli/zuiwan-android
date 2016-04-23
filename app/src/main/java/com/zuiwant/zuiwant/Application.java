package com.zuiwant.zuiwant;

import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.File;

/**
 * Created by matthew on 16/4/23.
 */
public class Application extends android.app.Application {

    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initImageLoader();
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisc(true)
                .displayer(new FadeInBitmapDisplayer(200))
                .showImageOnLoading(R.drawable.zw_logo)
                .build();

        File cacheDir;
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            cacheDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            cacheDir = getCacheDir();
        }
        ImageLoaderConfiguration.Builder configBuilder = new ImageLoaderConfiguration.Builder(mContext)
                .threadPoolSize(2)
                .memoryCache(new WeakMemoryCache())
                .denyCacheImageMultipleSizesInMemory()
                .discCache(new UnlimitedDiscCache(cacheDir))
                .defaultDisplayImageOptions(options);
        if (BuildConfig.DEBUG) {
            configBuilder.writeDebugLogs();
        }
        ImageLoader.getInstance().init(configBuilder.build());
    }

}
