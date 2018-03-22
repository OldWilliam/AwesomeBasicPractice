package me.jim.wx.awesomebasicpractice.anim;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.graphic.ExpandDrawable;

/**
 * Created by wx on 2018/3/21.
 */

public class ExpandAnimView extends RelativeLayout {
    private View host, normal, game, video, sound;

    public ExpandAnimView(Context context) {
        super(context);
        init();
    }

    public ExpandAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        initView();
        initAnim();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.anim_escape_view, this);
        host = findViewById(R.id.host);
        normal = findViewById(R.id.normal);
        game = findViewById(R.id.game);
        video = findViewById(R.id.video);
        sound = findViewById(R.id.sound);
    }

    private void initAnim() {
        final ExpandDrawable drawable = new ExpandDrawable();
        setBackground(drawable);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable.start();
            }
        });
    }
}
