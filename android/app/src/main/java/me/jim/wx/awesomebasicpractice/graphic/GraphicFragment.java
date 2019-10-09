package me.jim.wx.awesomebasicpractice.graphic;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import me.jim.wx.awesomebasicpractice.R;
import me.jim.wx.awesomebasicpractice.http.ResponseListener;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.BaseHeroBean;
import me.jim.wx.awesomebasicpractice.recyclerview.model.hero.HeroModel;
import me.jim.wx.awesomebasicpractice.view.primary.HexagonImageView;
import me.jim.wx.fragmentannotation.AttachFragment;

/**
 * 图形相关
 */
@AttachFragment("图形")
public class GraphicFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graphic, container, false);
    }

    private RecyclerView rvDrawable;
    private SimpleAdapter drawableAdapter;

    private RecyclerView rvAnim;
    private SimpleAdapter animAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LinearLayout ll = view.findViewById(R.id.ll_screen);
        final HexagonImageView imageView = new HexagonImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(160, 160);
        params.gravity = Gravity.CENTER;
        ll.addView(imageView, params);
        HeroModel.getHeros(new ResponseListener<List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean>>() {
            @Override
            public void onResult(final List<BaseHeroBean.AllHerosBean.AllHeroBean.ItemInfoBean> itemInfoBeans) {
                Runnable runnable = new Runnable() {
                    public void run() {
                        Glide.with(getContext()).load(itemInfoBeans.get(0).image_url).into(imageView);
                    }
                };
                imageView.post(runnable);
            }
        });

        rvDrawable = view.findViewById(R.id.rv_drawable);
        rvDrawable.setLayoutManager(new LinearLayoutManager(getContext()));
        drawableAdapter = new SimpleAdapter();
        rvDrawable.setAdapter(drawableAdapter);
        drawableAdapter.setData(Arrays.asList(getContext().getResources().getStringArray(R.array.drawable_item)));

        rvAnim = view.findViewById(R.id.rv_anim);
        rvAnim.setLayoutManager(new LinearLayoutManager(getContext()));
        animAdapter = new SimpleAdapter();
        rvAnim.setAdapter(animAdapter);
        animAdapter.setData(Arrays.asList(getContext().getResources().getStringArray(R.array.anim_item)));

        TextView tvDisplay = view.findViewById(R.id.tv_display);
//        String src = "<div style=text-align:center><font>登陆填写我的邀请码：</font></div>" +
//                "<div style=text-align:center><font color=blue size='40px'>YFREE</font></div>" +
//                "<div style=text-align:center><font>获得<font color= blue>答错复活</font>机会</font></div>";

//        String htmlStr = "<p><div style=text-align:center><font color='#0000FF' size='40px'>登陆填写我的邀请码</font></div><div style=text-align:center><font color='#ff0000' size='100px'>YFREE</font></div><div style=text-align:center><font color='#000000' size='40px'>获得</font><font color='#0000ff'>答错复活</font><font color='#000000'>机会</font></p>";
//        String str = "<p><div style='text-align:center;color:#000000;font-size:14px;'>登陆填写我的邀请码</div><div style='text-align:center;color:#ff00ff;font-size:20px;'>YFREE</div><div style='text-align:center;color:#000000;font-size:14px;'>获得<font color='#0000ff'>答错复活</font><font color='#000000'>机会</font></p>";
//        String str = "<p><font color='#0000FF' size='40px'>登陆填写我的邀请码</font><br><<font color='#ff0000' size='100px'>YFREE</font><br><font color='#000000' size='40px'>获得</font><font color='#0000ff'>答错复活</font><font color='#000000'>机会</font></p>";
//        String str1 = "<html><font color= white>登录填写我的邀请码：</font><font color='#FFd215'>YFREE</font><br><br><font color = white>获得</font><font color='#FFD215'>答错复活</font><font color=white>机会</font></html>";
//        String str = "<html><<font color='#333333' size='16'>登陆填写我的邀请码</font><br><<font color='#7340b5' size='40'>YFREE</font><br><font color='#333333' size='16'>获得</font><font size= ‘16’ color='#7340b5'>答错复活</font><font size=‘16’ color='#333333'>机会</font></html>";
        String str = "<html><<font color='#333333' size=16>登陆填写我的邀请码</font><br><<font color='#7340b5' size=40>YFREE</font><br><font color='#333333' size=16>获得</font><font size=16 color='#7340b5'>答错复活</font><font size=16 color='#333333'>机会</font></html>";
        String str1 = "<html><font size =14 color= white>登录填写我的邀请码：</font><font size =16 color='#FFd215'>YFREE</font><br><br><font size = 14 color = white>获得</font><font size = 14 color='#FFD215'>答错复活</font><font color=white size = 14>机会</font></html>";
        String str2 = "<html><font size=14 color=white>输入邀请码</font><font size=14 color='#FBFB99'>686868</font><br><font size=14 color=white>即可获得一张复活卡</font></html>";
        String src = str2;
        src = src.replaceAll("font", "fontBySize");
//        tvDisplay.setText(Html.fromHtml(src, null, new HTMLTagGetter()));
        tvDisplay.setText(Html.fromHtml(src, null, new HTMLTagGetter()));
    }

    private class HTMLTagGetter implements Html.TagHandler{


        private int startIndex = 0;
        private int stopIndex = 0;
        final HashMap<String, String> attributes = new HashMap<String, String>();

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
            processAttributes(xmlReader);

            if(tag.equalsIgnoreCase("fontBySize")){
                if(opening){
                    startFont(tag, output, xmlReader);
                }else{
                    endFont(tag, output, xmlReader);
                }
            }
        }

        private void processAttributes(final XMLReader xmlReader) {
            try {
                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                elementField.setAccessible(true);
                Object element = elementField.get(xmlReader);
                Field attsField = element.getClass().getDeclaredField("theAtts");
                attsField.setAccessible(true);
                Object atts = attsField.get(element);
                Field dataField = atts.getClass().getDeclaredField("data");
                dataField.setAccessible(true);
                String[] data = (String[])dataField.get(atts);
                Field lengthField = atts.getClass().getDeclaredField("length");
                lengthField.setAccessible(true);
                int len = (Integer)lengthField.get(atts);

                for(int i = 0; i < len; i++){
                    attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
                }
            }
            catch (Exception e) {

            }
        }


        public void startFont(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endFont(String tag, Editable output, XMLReader xmlReader){
            stopIndex = output.length();

            String color = attributes.get("color");
            String size = attributes.get("size");
//            if (size != null) {
//                size = size.split("px")[0];
//            }

            if(!TextUtils.isEmpty(color) /*&& !TextUtils.isEmpty(size)*/){
                output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(!TextUtils.isEmpty(size)){
                output.setSpan(new AbsoluteSizeSpan(Integer.parseInt(size), true), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

    }

    private class SimpleAdapter extends RecyclerView.Adapter {
        private List<String> texts = new ArrayList<>();
        @Override
        public SimpleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleHolder h = (SimpleHolder) holder;
            h.data(texts.get(position));
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }

        public void setData(List<String> texts) {
            this.texts = texts;
            notifyDataSetChanged();
        }
    }

    private class SimpleHolder extends RecyclerView.ViewHolder {
        private TextView tvText;

        public SimpleHolder(View itemView) {
            super(itemView);
            tvText = (TextView) itemView;
        }

        public void data(String s) {
            tvText.setText(s);
        }
    }
}
