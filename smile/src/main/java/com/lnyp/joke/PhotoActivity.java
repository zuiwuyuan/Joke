package com.lnyp.joke;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

/**
 * 图片浏览
 */
public class PhotoActivity extends FragmentActivity {

    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        imgUrl = getIntent().getStringExtra("imgUrl");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            transformOut();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void transformOut() {
        finish();
        this.overridePendingTransition(0, 0);
    }
}
