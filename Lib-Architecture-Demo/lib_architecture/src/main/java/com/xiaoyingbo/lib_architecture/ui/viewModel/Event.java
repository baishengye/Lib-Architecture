package com.xiaoyingbo.lib_architecture.ui.viewModel;

/**需要分发的事件*/
public class Event<P, R> {
    public int eventId;
    public P param;
    public R result;
}
