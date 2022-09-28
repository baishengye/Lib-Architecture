package com.xiaoyingbo.lib_framework.ui.page

import android.text.Layout
import android.util.SparseArray
import android.view.View
import androidx.annotation.IntegerRes
import androidx.lifecycle.ViewModel

/**为base页面中的DataBinding提供绑定项*/
class DataBindingConfig(
    @IntegerRes private val layout: Int,
    private val vmVariableId:Int,
    private val stateViewModel:ViewModel) {
    /**布局文件的资源id*/
    /**layout中variable的id*/
    /**layout中viewModel的类型*/


    private val bindingParams:SparseArray<Any> = SparseArray<Any>()

    fun getLayout(): Int {
        return layout
    }

    fun getVmVariableId():Int{
        return vmVariableId
    }

    fun getStateViewModel():ViewModel{
        return stateViewModel
    }

    fun getBindingParams():SparseArray<Any>{
        return bindingParams
    }

    /**添加要绑定的参数*/
    fun addBindingParam(variableId: Int, any:Any): DataBindingConfig {
        if (bindingParams[variableId] == null) {
            bindingParams.put(variableId, any)
        }
        return this
    }


}