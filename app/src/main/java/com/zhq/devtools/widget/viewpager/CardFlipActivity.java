package com.zhq.devtools.widget.viewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.lin.cardlib.CardSetting;
import com.lin.cardlib.OnSwipeCardListener;
import com.lin.cardlib.utils.ReItemTouchHelper;
import com.zhq.devtools.R;
import com.zhq.devtools.base.BaseActivity;
import com.zhq.devtools.databinding.ActivityCardFlipBinding;

public class CardFlipActivity extends BaseActivity<ActivityCardFlipBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_flip;
    }

    @Override
    protected void initView() {
        CardSetting setting=new CardSetting();
        setting.setSwipeListener(new OnSwipeCardListener<String>() {
            @Override
            public void onSwipedOut(RecyclerView.ViewHolder viewHolder, String imageUrl, int direction) {
                switch (direction) {
                    case ReItemTouchHelper.DOWN:
                        break;
                    case ReItemTouchHelper.UP:
                        break;
                    case ReItemTouchHelper.LEFT:
                        break;
                    case ReItemTouchHelper.RIGHT:
                        break;
                }
            }

            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float dx, float dy, int direction) {
                switch (direction) {
                    case ReItemTouchHelper.DOWN:
                        break;
                    case ReItemTouchHelper.UP:
                        break;
                    case ReItemTouchHelper.LEFT:
                        break;
                    case ReItemTouchHelper.RIGHT:
                        break;
                }
            }

            @Override
            public void onSwipedClear() {

            }
        });

    }
}