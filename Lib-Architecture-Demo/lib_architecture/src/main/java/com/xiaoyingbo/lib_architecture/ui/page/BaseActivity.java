package com.xiaoyingbo.lib_architecture.ui.page;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.scope.ViewModelScope;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateCallback;
import com.xiaoyingbo.lib_architecture.data.response.networkState.NetworkStateManager;
import com.xiaoyingbo.lib_util.BSY.BSYBarUtils;

public abstract class BaseActivity extends DataBindingActivity {

    private final ViewModelScope mViewModelScope = new ViewModelScope();

    /**获取网络变化的时候的回调*/
    protected abstract NetworkStateCallback getNetworkStateCallback();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getLifecycle().addObserver(NetworkStateManager.getInstance(getNetworkStateCallback()));

    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    protected void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void openUrlInBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
