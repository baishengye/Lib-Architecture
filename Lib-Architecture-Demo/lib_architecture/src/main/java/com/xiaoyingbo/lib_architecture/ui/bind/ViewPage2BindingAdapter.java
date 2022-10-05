package com.xiaoyingbo.lib_architecture.ui.bind;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.xiaoyingbo.lib_util.BSY.BSYLogUtils;
import com.xiaoyingbo.lib_util.BSY.BSYUtils;

import java.util.Locale;

/**ViewPager2的BindingAdapter*/
public class ViewPage2BindingAdapter {
    private static final String TAG="ViewPage2BindingAdapter";

    @BindingAdapter(value = {"viewPager2OffsetLimit"})
    public static void setViewPager2OffsetLimit(ViewPager2 viewPager2,int limit){
        BSYLogUtils.d(BSYUtils.isDebug(), TAG,String.format(Locale.CHINA,"offscreenPageLimit:%d",limit),null);
        viewPager2.setOffscreenPageLimit(limit);
    }
}
