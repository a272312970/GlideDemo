package com.example.pengshan.glidedemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.pengshan.glidedemo.Progress.LoadingImgViewWithProgress;
import com.example.pengshan.glidedemo.Progress.ProgressImageView;
import com.example.pengshan.glidedemo.Progress.ProgressPhotoView;
import com.example.pengshan.glidedemo.myokhttpglide.OkHttpProgress.ProgressModelLoader;

/**
 * Created by pengshan on 2017/8/25.
 */

public class GlideUtils {

    public final static void loadImageWithLoadingBar(Context context, String url, final LoadingImgViewWithProgress loadingImgViewWithProgress, final OnDrawableLoadListern onDrawableLoadListern){
        Glide.with(context).load(url).override(320,320).thumbnail(0.1f).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageDrawable(resource);
                if(onDrawableLoadListern != null){
                    onDrawableLoadListern.onDrawableLoadEnd();
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                loadingImgViewWithProgress.startProgress();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageResource(R.mipmap.error);
            }
        });

    }

    public final static void loadImageWithCircleLoadingBar(Context context, String url, final LoadingImgViewWithProgress loadingImgViewWithProgress, final OnDrawableLoadListern onDrawableLoadListern){
        Glide.with(context).load(url).thumbnail(0.1f).transform(new GlideCircleTransform(context)).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageDrawable(resource);
                if(onDrawableLoadListern != null){
                    onDrawableLoadListern.onDrawableLoadEnd();
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                loadingImgViewWithProgress.startProgress();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageResource(R.mipmap.error);
            }
        });

    }
    public final static void loadImageWithRoundLoadingBar(Context context, String url, final LoadingImgViewWithProgress loadingImgViewWithProgress, final OnDrawableLoadListern onDrawableLoadListern){
        Glide.with(context).load(url).thumbnail(0.1f).transform(new GlideRoundTransform(context,10)).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageDrawable(resource);
                if(onDrawableLoadListern != null){
                    onDrawableLoadListern.onDrawableLoadEnd();
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                loadingImgViewWithProgress.startProgress();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                loadingImgViewWithProgress.stopProgress();
                loadingImgViewWithProgress.setImageResource(R.mipmap.error);
            }
        });

    }

    public final static void loadImage(final Context context, String url, final ImageView imageView, final OnDrawableLoadListern onDrawableLoadListern){
        Glide.with(context).load(url).thumbnail(0.1f).error(R.mipmap.error).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.setImageDrawable(resource);
                if(onDrawableLoadListern != null){
                    onDrawableLoadListern.onDrawableLoadEnd();
                }
            }
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if(onDrawableLoadListern != null) {
                    onDrawableLoadListern.onDrawableLoadStart();
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if(onDrawableLoadListern != null) {
                    onDrawableLoadListern.onLoadFailed();
                }
            }
        });

    }

    public final static void loadImageWithProgress(Context context, String url, final ProgressImageView imageView){
        Glide.with(context).using(new ProgressModelLoader(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                imageView.setProgress((int) ((int)msg.arg1 * 1f/(int)msg.arg2 * 100));
//                imageView.setMaxValue(msg.arg2);
            }
        })).load(url).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imageView.closeProgress();
                imageView.setImageDrawable(resource);
            }
            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                imageView.setImageResource(R.mipmap.ic_launcher);
                imageView.startProgress();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                imageView.setImageResource(R.mipmap.error);
                imageView.closeProgress();
            }
        });
    }

    public final static void loadImageWithPhotoViewProgress(Context context, String url, final ProgressPhotoView imageView, final String bitmap){
        Glide.with(context).using(new ProgressModelLoader(new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                imageView.setProgress((int) ((int)msg.arg1 * 1f/(int)msg.arg2 * 100));
//                imageView.setMaxValue(msg.arg2);
            }
        })).load(url).into(new ProgressTarget(imageView));
    }

    /**
     * 一个检测进度的Target
     */
    static class ProgressTarget extends SimpleTarget<GlideDrawable>{

        private ProgressPhotoView imageView;

        public ProgressTarget(ProgressPhotoView imageView) {
            this.imageView = imageView;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            imageView.closeProgress();
            imageView.setImageDrawable(resource);
        }
        @Override
        public void onLoadStarted(Drawable placeholder) {
            super.onLoadStarted(placeholder);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.startProgress();
        }

        @Override
        public void onLoadFailed(Exception e, Drawable errorDrawable) {
            super.onLoadFailed(e, errorDrawable);
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.closeProgress();
        }
    }


    public interface OnDrawableLoadListern {
        void onDrawableLoadEnd();
        void onDrawableLoadStart();
        void onLoadFailed();
    }
}
