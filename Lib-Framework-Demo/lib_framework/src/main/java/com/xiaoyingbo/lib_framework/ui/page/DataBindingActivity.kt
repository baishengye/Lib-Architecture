package com.xiaoyingbo.lib_framework.ui.page

import android.os.Bundle
import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.size
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.xiaoyingbo.lib_util.Utils

/**DataBinding的base类*/
abstract class DataBindingActivity: AppCompatActivity() {
    private  var mBinding: ViewDataBinding?=null

    /**初始化ViewModel,所有子类都必须要重写*/
    protected abstract fun initViewModel()

    /**获取DataBindingConfig*/
    protected abstract fun getDataBindingConfig():DataBindingConfig

    protected fun getBind():ViewDataBinding?{
        return mBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        val dataBindingConfig:DataBindingConfig = getDataBindingConfig()

        /*绑定布局和activity*/
        val binding:ViewDataBinding = DataBindingUtil.setContentView(this,dataBindingConfig.getLayout())
        /*绑定生命周期*/
        binding.lifecycleOwner = this
        /*绑定viewModel*/
        binding.setVariable(dataBindingConfig.getVmVariableId(),dataBindingConfig.getStateViewModel())
        /*绑定参数*/
        val bindingParams:SparseArray<Any> = dataBindingConfig.getBindingParams()
        for (i in 0..dataBindingConfig.getBindingParams().size){
            binding.setVariable(bindingParams.keyAt(i),bindingParams.valueAt(i))
        }
        /*保存Binding实例*/
        mBinding=binding
    }

    override fun onDestroy() {
        super.onDestroy()

        mBinding?.unbind()
        mBinding=null
    }
}