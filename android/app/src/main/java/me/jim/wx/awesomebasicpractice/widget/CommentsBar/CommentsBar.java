package me.jim.wx.awesomebasicpractice.widget.CommentsBar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wx on 2018/3/1.
 */

public class CommentsBar extends LinearLayout {
    private int num = 5;
    public CommentsBar(Context context) {
        super(context);
        initView();
    }

    public CommentsBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        setOrientation(HORIZONTAL);
    }
}
