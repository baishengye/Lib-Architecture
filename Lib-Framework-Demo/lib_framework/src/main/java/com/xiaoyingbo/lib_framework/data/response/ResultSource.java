package com.xiaoyingbo.lib_framework.data.response;

import androidx.annotation.IntDef;

@IntDef({ResultSource.NETWORK,ResultSource.DATABASE,ResultSource.LOCAL_FILE})
public @interface ResultSource {
    int NETWORK=0x1;
    int DATABASE=0x2;
    int LOCAL_FILE=0x3;
}
