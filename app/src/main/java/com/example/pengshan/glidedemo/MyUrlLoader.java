package com.example.pengshan.glidedemo;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/11/16 0016.
 */
public class MyUrlLoader extends BaseGlideUrlLoader<MyDataModel> {

    public MyUrlLoader(Context context) {
        super(context);
    }

    public MyUrlLoader(ModelLoader<GlideUrl, InputStream> urlLoader) {
        super(urlLoader, null);
    }

    @Override
    protected String getUrl(MyDataModel model, int width, int height) {
        return model.buildUrl(width, height);
    }


    /**
     */
    public static class Factory implements ModelLoaderFactory<MyDataModel, InputStream> {

        @Override
        public ModelLoader<MyDataModel, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new MyUrlLoader(factories.buildModelLoader(GlideUrl.class, InputStream.class));
        }

        @Override
        public void teardown() {
        }
    }

}
