package com.lnyp.joke.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lnyp.joke.R;
import com.lnyp.joke.fragment.MainFragment;
import com.lnyp.joke.fragment.QutuFragment;
import com.lnyp.joke.fragment.ShenHuifuFragment;
import com.lnyp.joke.fragment.XiaohuaFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.radioBtn1)
    public RadioButton radioBtn1;

    @BindView(R.id.radioBtn2)
    public RadioButton radioBtn2;

    @BindView(R.id.radioBtn3)
    public RadioButton radioBtn3;

    private FragmentManager fragmentManager;

    private MainFragment mainFragment = null;

    private XiaohuaFragment xiaohuaFragment = null;

    private QutuFragment qutuFragment = null;

    private ShenHuifuFragment shenHuifuFragment = null;

    // 定义一个变量，来标识是否退出
    private static boolean enableExit = false;

    // 处理请求返回信息
    private MyHandler mHandler = new MyHandler();

    private static class MyHandler extends Handler {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    enableExit = false;
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {

        fragmentManager = getSupportFragmentManager();

        RadioGroup rgbottomBar = (RadioGroup) findViewById(R.id.rgBottomBar);

        rgbottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                selectTab();
            }
        });

        radioBtn1.setChecked(true);

        selectTab();

    }


    private void selectTab() {

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        if (radioBtn1.isChecked()) {
            if (mainFragment == null) {
                mainFragment = new MainFragment();
                transaction.add(R.id.content, mainFragment);
            } else {
                transaction.show(mainFragment);
            }

        } else if (radioBtn2.isChecked()) {
            if (xiaohuaFragment == null) {
                xiaohuaFragment = new XiaohuaFragment();
                transaction.add(R.id.content, xiaohuaFragment);
            } else {
                transaction.show(xiaohuaFragment);
            }

        } else if (radioBtn3.isChecked()) {
            if (qutuFragment == null) {
                qutuFragment = new QutuFragment();
                transaction.add(R.id.content, qutuFragment);
            } else {
                transaction.show(qutuFragment);
            }

        } else {
            if (shenHuifuFragment == null) {
                shenHuifuFragment = new ShenHuifuFragment();
                transaction.add(R.id.content, shenHuifuFragment);
            } else {
                transaction.show(shenHuifuFragment);
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (xiaohuaFragment != null) {
            transaction.hide(xiaohuaFragment);
        }
        if (qutuFragment != null) {
            transaction.hide(qutuFragment);
        }
        if (shenHuifuFragment != null) {
            transaction.hide(shenHuifuFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!enableExit) {
                enableExit = true;
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                // 利用handler延迟发送更改状态信息
                mHandler.sendEmptyMessageDelayed(0, 3000);
            } else {
                MainActivity.this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}