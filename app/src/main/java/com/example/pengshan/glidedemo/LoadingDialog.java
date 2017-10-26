package com.example.pengshan.glidedemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;

/**
 * 加载框
 */
public class LoadingDialog implements DialogInterface.OnCancelListener {

    private Context context;
    public Dialog _dialog;
    public LoadingDialog instance;

    public LoadingDialog() {


    }

    public LoadingDialog getInstance() {
        if (instance != null) {
            instance = new LoadingDialog();
        }
        return instance;
    }


    /**
     * 显示加载框
     *
     * @param context
     */
    public void show(final Context context) {

        if (null == context) {
            return;
        }
        this.context = context;
        if (_dialog != null) {
            if (_dialog.isShowing()) {
                _dialog.dismiss();
            }

        }
        if (null == context) {
            return;
        }
        if (_dialog == null) {
            _dialog = new Dialog(context, R.style.myDialogTheme);
        }
        _dialog.setContentView(R.layout.loading_dialog);
        _dialog.setCanceledOnTouchOutside(false);
        _dialog.setOnCancelListener(this);
            /*_dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

                        boolean isSuccess = ActivityUtil.popOtherCurrentActivity();
                        if (isSuccess) {
                            _dialog.dismiss();
                            OkHttpUtils.getInstance().cancelTag(context);
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return false;
                }
            });*/
        if (!_dialog.isShowing()) {
            _dialog.show();
        }

    }


    /**
     * 隐藏加载框
     */
    public void dismiss() {

        if (null != _dialog && _dialog.isShowing()) {
            _dialog.dismiss();
            _dialog = null;
        }

    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }


}
