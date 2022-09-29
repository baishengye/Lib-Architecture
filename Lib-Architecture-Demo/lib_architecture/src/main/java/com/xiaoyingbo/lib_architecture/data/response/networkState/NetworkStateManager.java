/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaoyingbo.lib_architecture.data.response.networkState;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.xiaoyingbo.lib_util.Utils;

/**
 * Create by KunMinX at 19/10/11
 */
public class NetworkStateManager implements DefaultLifecycleObserver{

    private final NetworkStateReceive mNetworkStateReceive;

    /**构造函数不让外界直接调用*/
    private NetworkStateManager(NetworkStateCallback callback) {
        mNetworkStateReceive=new NetworkStateReceive(callback);
    }

    /**虽然式getInstance()但是不用单例，因为单例是static的,也就是一个进程只会有一个实例
     * <br></br>但是我们需要确保我们在不同的界面时可以有不同的回调,所以不能使用单例获取*/
    public static NetworkStateManager getInstance(NetworkStateCallback callback) {
        return new NetworkStateManager(callback);
    }

    //tip：让 NetworkStateManager 可观察页面生命周期，
    // 从而在页面失去焦点时，
    // 及时断开本页面对网络状态的监测，以避免重复回调和一系列不可预期的问题。

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        Utils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceive, filter);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Utils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceive);
    }
}
