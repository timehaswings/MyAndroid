package com.weylen.customdialogdemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhou on 2016/3/29.
 */
public class CustomDialog extends Dialog{

    public interface DialogInterface{
        void onButtonClick();
    }

    private String messageTitle, messageTime, messagePrice;
    private DialogInterface dialogInterface;
    private Activity activity;

    public CustomDialog(Activity context) {
        super(context);
        this.activity = context;
    }

    public CustomDialog(Activity context, int themeResId) {
        super(context, themeResId);
        this.activity = context;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public void setMessagePrice(String messagePrice) {
        this.messagePrice = messagePrice;
    }

    public void setDialogInterface(DialogInterface dialogInterface) {
        this.dialogInterface = dialogInterface;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_buy);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.rootLayout);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams (
                (int) (displayMetrics.widthPixels - 20 * displayMetrics.density), -2
        );
        linearLayout.setLayoutParams(layoutParams);

//        getWindow().getAttributes();

        // 信息标题
        TextView titleView = (TextView) findViewById(R.id.messageTitle);
        titleView.setText(messageTitle);
        // 时间
        TextView timeView = (TextView) findViewById(R.id.messageTime);
        timeView.setText(messageTime);
        // 价格
        TextView priceView = (TextView) findViewById(R.id.messagePrice);
        priceView.setText(messagePrice);

        // 按钮的监听事件
        findViewById(R.id.buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogInterface != null){
                    dialogInterface.onButtonClick();
                }
            }
        });
    }
}
