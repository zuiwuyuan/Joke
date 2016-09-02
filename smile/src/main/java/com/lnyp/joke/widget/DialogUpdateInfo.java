package com.lnyp.joke.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lnyp.joke.R;


/**
 * 更新APP提示
 *
 * @author lining
 */
public class DialogUpdateInfo extends Dialog {

    /**
     * 上下文对象
     */
    Activity context;

    private View.OnClickListener mClickListener;

    private String update_desc;

    public DialogUpdateInfo(Activity context, int theme, String update_desc, View.OnClickListener clickListener) {

        super(context, theme);
        this.context = context;
        this.update_desc = update_desc;
        this.mClickListener = clickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.dialog_update_info);

        final Window dialogWindow = this.getWindow();

        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        this.setCancelable(false);
        // 根据id在布局中找到控件对象
        TextView textUpdateDesc = (TextView) findViewById(R.id.textUpdateDesc);
        TextView btnCancle = (TextView) findViewById(R.id.btnCancle);
        TextView btnSubmit = (TextView) findViewById(R.id.btnSubmit);

        textUpdateDesc.setText(update_desc);

        btnSubmit.setOnClickListener(mClickListener);
        btnCancle.setOnClickListener(mClickListener);
    }
}
