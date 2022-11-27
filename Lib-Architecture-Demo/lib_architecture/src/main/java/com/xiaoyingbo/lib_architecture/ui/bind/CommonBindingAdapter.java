package com.xiaoyingbo.lib_architecture.ui.bind;

import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.databinding.BindingAdapter;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

public class CommonBindingAdapter {
    /**设置navGraph*/
    @BindingAdapter(value = {"navGraph","bundle"},requireAll = false)
    public static void setGraph(FragmentContainerView view, @NavigationRes int navGraphId, Bundle bundle){
        NavHostFragment navHostFragment = view.getFragment();
        if(bundle!=null){
            navHostFragment.getNavController().setGraph(navGraphId,bundle);
        }else {
            navHostFragment.getNavController().setGraph(navGraphId);
        }
    }
}
