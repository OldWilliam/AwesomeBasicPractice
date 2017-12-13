package me.jim.wx.awesomebasicpractice.view.recyclerview.model.hero;

import java.util.List;

/**
 * Created by wx on 2017/12/13.
 */

public class BaseHeroBean {

    /**
     * error_msg : 操作成功
     * dm_error : 0
     */

    public String error_msg;
    public int dm_error;
    public AllHerosBean all_heros;

    public static class AllHerosBean {
        /**
         * title : 全部英雄
         */

        public String title;
        public List<AllHeroBean> all_hero;

        public static class AllHeroBean {
            /**
             * sort : 0
             * p_tab_key : EBF51F2AE0E3FDA3
             * p_name : 刺客
             */

            public int sort;
            public String p_tab_key;
            public String p_name;
            public List<ItemInfoBean> item_info;

            public static class ItemInfoBean {
                /**
                 * sort : 0
                 * name : 孙悟空
                 * hero_use_num : 8
                 * content : 8场直播
                 * image_id : 334
                 * tab_key : 545172E565FF2100
                 * image_url : http://img2.inke.cn/NjQ3MTgxNTA2MDc0MTc4.jpg
                 */

                public int sort;
                public String name;
                public int hero_use_num;
                public String content;
                public int image_id;
                public String tab_key;
                public String image_url;
            }
        }
    }
}
