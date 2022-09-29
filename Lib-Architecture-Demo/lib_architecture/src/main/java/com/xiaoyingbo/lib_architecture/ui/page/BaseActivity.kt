package com.xiaoyingbo.lib_architecture.ui.page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager
import com.xiaoyingbo.lib_architecture.ui.scope.ViewModelScope

/**所有Activity的基类*/
abstract class BaseActivity : DataBindingActivity() {
    /**懒汉式的获取到ViewModel的生产工厂*/
    private val mViewModelScope= ViewModelScope()

    /**获取网络变化的时候的回调*/
    protected abstract fun getNetworkStateCallback(): NetworkStateCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        观察网络状态
        lifecycle.addObserver(NetworkStateManager.getInstance(getNetworkStateCallback()))
    }

    //通过 "工厂模式" 实现 ViewModel 作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域
    /**获取Activity作用域下的ViewModel*/
    protected open fun <T : ViewModel> getActivityScopeViewModel(@NonNull modelClass: Class<T>): T {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass)
    }
    /**获取Application作用域下的ViewModel*/
    protected open fun <T : ViewModel> getApplicationScopeViewModel(@NonNull modelClass: Class<T>): T {
        return mViewModelScope.getApplicationScopeViewModel(modelClass)
    }

    /**关闭软键盘*/
    protected open fun toggleSoftInput() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    /**打开浏览器*/
    protected open fun openUrlInBrowser(url: String?) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}