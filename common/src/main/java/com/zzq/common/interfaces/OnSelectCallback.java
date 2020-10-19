package com.zzq.common.interfaces;

import androidx.annotation.Nullable;

public abstract class OnSelectCallback {

    public abstract void onPositive(@Nullable String msg);

    public void onNegative(){}
}
