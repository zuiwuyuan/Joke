package com.lnyp.joke.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lnyp.joke.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 图片浏览
 */
public class PhotoActivity extends FragmentActivity {

    @BindView(R.id.imgJoke)
    public ImageView imgJoke;

    private String showImg;
    private String gifSrcImg;

    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ButterKnife.bind(this);

        mAttacher = new PhotoViewAttacher(imgJoke);

        showImg = getIntent().getStringExtra("showImg");
        gifSrcImg = getIntent().getStringExtra("gifSrcImg");
        System.out.println(showImg + "   " + gifSrcImg);

        if (TextUtils.isEmpty(gifSrcImg)) {
            Glide.with(this).load(showImg).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgJoke);
        } else {
            Glide.with(this).load(gifSrcImg).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imgJoke);
        }

        mAttacher.update();

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                System.out.println("111");
                PhotoActivity.this.finish();
            }

            @Override
            public void onOutsidePhotoTap() {
                System.out.println("222");
                PhotoActivity.this.finish();
            }
        });
    }
}
