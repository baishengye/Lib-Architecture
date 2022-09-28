package com.xiaoyingbo.lib_framework.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.xiaoyingbo.lib_util.Utils;

public abstract class DataBindingFragment extends Fragment {
    /**此Fragment所依附的Activity*/
    protected AppCompatActivity mActivity;

    private ViewDataBinding mBinding;


    /**当activity和fragment建立联系时调用*/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity=(AppCompatActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    protected abstract void initViewModel() ;

    protected abstract DataBindingConfig getDataBindingConfig();


    protected ViewDataBinding getBinding(){
        return mBinding;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        DataBindingConfig dataBindingConfig=getDataBindingConfig();

        ViewDataBinding binding= DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container,false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        SparseArray<Object> bindingParams= dataBindingConfig.getBindingParams();
        for (int i = 0 , length=bindingParams.size(); i < length; i++) {
            binding.setVariable(bindingParams.keyAt(i),bindingParams.valueAt(i));
        }
        mBinding=binding;
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding.unbind();
        mBinding = null;
    }
}
