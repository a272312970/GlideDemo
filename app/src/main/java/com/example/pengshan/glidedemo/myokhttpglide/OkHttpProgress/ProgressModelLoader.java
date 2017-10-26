package com.example.pengshan.glidedemo.myokhttpglide.OkHttpProgress;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

public class ProgressModelLoader implements StreamModelLoader<String> {

    private static final String TAG = ProgressModelLoader.class.getSimpleName();
    private final ModelCache<String, String> modelCache;
    private final Handler handler;

    public ProgressModelLoader(Handler handler) {
        this(null, handler);
    }

    public ProgressModelLoader(ModelCache<String, String> modelCache) {
        this(modelCache, null);
    }

    public ProgressModelLoader(ModelCache<String, String> modelCache, Handler handler) {
        this.modelCache = modelCache;
        this.handler = handler;
        Log.i(TAG, " 传进去的ProgressModelLoader: " + ProgressModelLoader.this.toString());
    }


    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        Log.i(TAG, "getResourceFetcher中走的ProgressModelLoader: " + ProgressModelLoader.this.toString());
        String result = null;
        if (modelCache != null) {
            result = modelCache.get(model, width, height);
        }
        if (result == null) {
            result = model;
            if (modelCache != null) {
                modelCache.put(model, width, height, result);
            }
        }
        return new ProgressDataFetcher(result, handler);
    }

    public static class Factory implements ModelLoaderFactory<String, InputStream> {

        private final ModelCache<String, String> mModelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<String, InputStream> build(Context context, GenericLoaderFactory factories) {
            ProgressModelLoader progressModelLoader = new ProgressModelLoader(mModelCache);
            Log.i(TAG, "Factory中的ProgressModelLoader: " + progressModelLoader.toString());
            return progressModelLoader;
        }

        @Override
        public void teardown() {

        }
    }

}
