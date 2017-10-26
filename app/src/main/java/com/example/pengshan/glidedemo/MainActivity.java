package com.example.pengshan.glidedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.pengshan.glidedemo.Progress.LoadingImgViewWithProgress;
import com.example.pengshan.glidedemo.Progress.ProgressImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GlideUtils.OnDrawableLoadListern {

    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.iv4)
    ImageView mIv4;
    @BindView(R.id.iv5)
    ImageView mIv5;
    @BindView(R.id.iv_cache)
    ImageView mIvCache;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.tv_cache1)
    TextView tvCache1;
    @BindView(R.id.tv_cache2)
    TextView tvCache2;
    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.mylayout)
    MyLayout myLayout;
    @BindView(R.id.loadingImgView2)
    LoadingImgViewWithProgress mLoadingImgViewWithProgress;
    @BindView(R.id.loadingImgView3)
    LoadingImgViewWithProgress loadingImgView3;
    @BindView(R.id.loadingImgView4)
    LoadingImgViewWithProgress loadingImgView4;
    @BindView(R.id.loadingImgView5)
    LoadingImgViewWithProgress loadingImgView5;
    @BindView(R.id.loadingImgView6)
    LoadingImgViewWithProgress loadingImgView6;
    @BindView(R.id.loadingImgView7)
    ProgressImageView loadingImgView7;
    public GlideDrawable resource;
    @BindView(R.id.getCache)
    Button mGetCache;
    @BindView(R.id.clear2)
    Button mClear2;
    @BindView(R.id.tv_cache1_address)
    TextView mTvCache1Address;
    private String url = "http://img9.zol.com.cn/dp_800/380/379813.jpg";
    private String mAbsolutePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /*Glide.with(this).load(url).override(320,320).thumbnail(0.1f).error(R.mipmap.error).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<GlideDrawable>() {


            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                MainActivity.this.resource = resource;
                mPhotoView.stopProgress();
                mPhotoView.setImageDrawable(resource);
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                mPhotoView.startProgress();
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                mPhotoView.stopProgress();
                mPhotoView.setImageResource(R.mipmap.error);
            }
        });*/
        showCache();
        GlideUtils.loadImageWithLoadingBar(this, url, mLoadingImgViewWithProgress, this);
        GlideUtils.loadImageWithLoadingBar(this, url, loadingImgView3, this);
        GlideUtils.loadImageWithLoadingBar(this, url, loadingImgView4, this);
        GlideUtils.loadImageWithCircleLoadingBar(this, url, loadingImgView5, this);
        GlideUtils.loadImageWithRoundLoadingBar(this, url, loadingImgView6, this);
        File file = SimpleGlideModule.creatFileIfNotExist(SimpleGlideModule.getPath(this));
        Log.i("缓存：", file.getAbsolutePath());
    }

    private Bitmap bitmap;
    private SimpleTarget<Bitmap> mSimpleTarget = new SimpleTarget<Bitmap>() {


        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
            MainActivity.this.bitmap = bitmap;
            mIv4.setImageBitmap(bitmap);
        }
    };

    @OnClick({R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5, R.id.activity_main, R.id.loadingImgView2, R.id.loadingImgView3, R.id.loadingImgView4, R.id.loadingImgView5,
            R.id.loadingImgView6, R.id.loadingImgView7, R.id.clear, R.id.clear2, R.id.getCache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv1:
                Glide.with(this).load(url).override(320, 320).error(R.mipmap.error).into(mIv1);
                showCache();
                break;
            case R.id.iv2:
                Glide.with(this).load(url).thumbnail(0.1f).override(320, 320).skipMemoryCache(true).error(R.mipmap.error).diskCacheStrategy(DiskCacheStrategy.NONE).into(mIv2);
                break;
            case R.id.iv3:
                //自定义缩略图
                DrawableRequestBuilder<Integer> thumbnailRequest = Glide
                        .with(this)
                        .load(R.mipmap.ic_launcher);

                Glide.with(this).load(url).override(320, 320).diskCacheStrategy(DiskCacheStrategy.NONE).thumbnail(thumbnailRequest).error(R.mipmap.error).into(mIv3);
                showCache();
                break;
            case R.id.iv4:

                Glide.with(this).load(url).asBitmap().override(320, 320).error(R.mipmap.error).into(mSimpleTarget);
                showCache();
                break;
            case R.id.iv5:
                ViewTarget<MyLayout, GlideDrawable> viewTarget = myLayout.getViewTarget();
                Glide.with(this).load(url).override(320, 320).error(R.mipmap.error).into(viewTarget);
                showCache();
                break;

            case R.id.loadingImgView2:
                //点击查看大图
                ImageInfo info = new ImageInfo();
                info.imageViewWidth = mLoadingImgViewWithProgress.getWidth();
                info.imageViewHeight = mLoadingImgViewWithProgress.getHeight();
                int[] points = new int[2];
                mLoadingImgViewWithProgress.getLocationInWindow(points);
                info.imageViewX = points[0];
                info.imageViewY = points[1] - getStatusHeight(this);
                info.bigImageUrl = url;
                info.thumbnailUrl = url;
                Intent intent = new Intent(MainActivity.this, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cachePath", mAbsolutePath);
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, info);
//                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0, 0);
                showCache();
                break;

            case R.id.loadingImgView3:
                //点击查看大图
                ImageInfo info2 = new ImageInfo();
                info2.imageViewWidth = loadingImgView3.getWidth();
                info2.imageViewHeight = loadingImgView3.getHeight();
                int[] points2 = new int[2];
                loadingImgView3.getLocationInWindow(points2);
                info2.imageViewX = points2[0];
                info2.imageViewY = points2[1] - getStatusHeight(this);
                info2.bigImageUrl = url;
                info2.thumbnailUrl = url;

                Intent intent2 = new Intent(MainActivity.this, ImagePreviewActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(ImagePreviewActivity.IMAGE_INFO, info2);
//                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent2.putExtras(bundle2);
                startActivity(intent2);
                overridePendingTransition(0, 0);
                showCache();
                break;

            case R.id.loadingImgView4:
                //点击查看大图
                ImageInfo info4 = new ImageInfo();
                info4.imageViewWidth = loadingImgView4.getWidth();
                info4.imageViewHeight = loadingImgView4.getHeight();
                int[] points4 = new int[2];
                loadingImgView4.getLocationInWindow(points4);
                info4.imageViewX = points4[0];
                info4.imageViewY = points4[1] - getStatusHeight(this);
                info4.bigImageUrl = url;
                info4.thumbnailUrl = url;

                Intent intent4 = new Intent(MainActivity.this, ImagePreviewActivity.class);
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable(ImagePreviewActivity.IMAGE_INFO, info4);
//                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent4.putExtras(bundle4);
                startActivity(intent4);
                overridePendingTransition(0, 0);
                showCache();
                break;

            case R.id.loadingImgView5:
                //点击查看大图
                ImageInfo info5 = new ImageInfo();
                info5.imageViewWidth = loadingImgView5.getWidth();
                info5.imageViewHeight = loadingImgView5.getHeight();
                int[] points5 = new int[2];
                loadingImgView5.getLocationInWindow(points5);
                info5.imageViewX = points5[0];
                info5.imageViewY = points5[1] - getStatusHeight(this);
                info5.bigImageUrl = url;
                info5.thumbnailUrl = url;

                Intent intent5 = new Intent(MainActivity.this, ImagePreviewActivity.class);
                Bundle bundle5 = new Bundle();
                bundle5.putSerializable(ImagePreviewActivity.IMAGE_INFO, info5);
//                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent5.putExtras(bundle5);
                startActivity(intent5);
                overridePendingTransition(0, 0);
                showCache();
                break;

            case R.id.loadingImgView6:
                //点击查看大图
                ImageInfo info6 = new ImageInfo();
                info6.imageViewWidth = loadingImgView6.getWidth();
                info6.imageViewHeight = loadingImgView6.getHeight();
                int[] points6 = new int[2];
                loadingImgView6.getLocationInWindow(points6);
                info6.imageViewX = points6[0];
                info6.imageViewY = points6[1] - getStatusHeight(this);
                info6.bigImageUrl = url;
                info6.thumbnailUrl = url;

                Intent intent6 = new Intent(MainActivity.this, ImagePreviewActivity.class);
                Bundle bundle6 = new Bundle();
                bundle6.putSerializable(ImagePreviewActivity.IMAGE_INFO, info6);
//                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent6.putExtras(bundle6);
                startActivity(intent6);
                overridePendingTransition(0, 0);
                showCache();
                break;

            case R.id.clear:
//                DataCleanManager.clearAllCache(this);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MainActivity.this).clearDiskCache();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showCache();
                            }
                        });
                    }
                }).start();

                break;

            case R.id.clear2:
                Glide.get(MainActivity.this).clearMemory();
                showCache();
                break;

            case R.id.loadingImgView7:
                GlideUtils.loadImageWithProgress(this, url, loadingImgView7);
                showCache();
                break;

            case R.id.getCache:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File file = Glide.with(MainActivity.this)
                                    .load(url)
                                    .downloadOnly(100, 100)
                                    .get();
                            mAbsolutePath = file.getAbsolutePath();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    Glide.with(MainActivity.this).load(mAbsolutePath).into(mIvCache);
                                    Uri uri = Uri.fromFile(new File(mAbsolutePath));
                                    mIvCache.setImageURI(uri);
                                    mTvCache1Address.setText("缓存地址:" + mAbsolutePath);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                showCache();
                break;

        }
    }

    public void showCache() {
        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(this);
            tvCache1.setText("自定义缓存 ： " + totalCacheSize);
            File photoCacheDir = Glide.getPhotoCacheDir(this);
            long folderSize = DataCleanManager.getFolderSize(photoCacheDir);


            tvCache2.setText("系统缓存： " + DataCleanManager.getFormatSize(folderSize));
        } catch (Exception e) {
            Toast.makeText(this, "缓存计算错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    ;

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    public void onDrawableLoadEnd() {

    }

    @Override
    public void onDrawableLoadStart() {

    }

    @Override
    public void onLoadFailed() {

    }
}
