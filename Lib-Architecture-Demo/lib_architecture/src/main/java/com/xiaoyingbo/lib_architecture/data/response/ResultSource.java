package com.xiaoyingbo.lib_architecture.data.response;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({ResultSource.NETWORK,ResultSource.DATABASE,ResultSource.LOCAL_FILE})
public @interface ResultSource {
    int NETWORK=0x1;
    int DATABASE=0x2;
    int LOCAL_FILE=0x3;
}
