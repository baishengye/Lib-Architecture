package com.xiaoyingbo.lib_architecture.ui.viewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.domain.queue.FixedLengthList;
import com.kunminx.architecture.domain.result.MutableResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**基于 "单一职责原则"，应将 ViewModel 划分为 state-ViewModel 和 event-ViewModel,<br></br>
state-ViewModel 职责仅限于托管、保存和恢复本页面 state，<br></br>
event-ViewModel 职责仅限于 "消息分发" 场景承担 "唯一可信源"。<br></br>
 EventDispatcher用于分发event*/
@SuppressWarnings("rawtypes")
public class EventDispatcher<E extends Event> extends ViewModel implements DefaultLifecycleObserver {

    private final static int DEFAULT_QUEUE_LENGTH = 10;
    private final HashMap<Integer, LifecycleOwner> mOwner = new HashMap<>();
    private final HashMap<Integer, LifecycleOwner> mFragmentOwner = new HashMap<>();
    private final HashMap<Integer, Observer<E>> mObservers = new HashMap<>();
    private final FixedLengthList<MutableResult<E>> mResults = new FixedLengthList<>();

    protected int initQueueMaxLength() {
        return DEFAULT_QUEUE_LENGTH;
    }

    public final void output(@NonNull AppCompatActivity activity, @NonNull Observer<E> observer) {
        activity.getLifecycle().addObserver(this);
        Integer identityId = System.identityHashCode(activity);
        outputTo(identityId, activity, observer);
    }

    public final void output(@NonNull Fragment fragment, @NonNull Observer<E> observer) {
        fragment.getLifecycle().addObserver(this);
        Integer identityId = System.identityHashCode(fragment);
        this.mFragmentOwner.put(identityId, fragment);
        outputTo(identityId, fragment.getViewLifecycleOwner(), observer);
    }

    private void outputTo(Integer identityId, LifecycleOwner owner, Observer<E> observer) {
        this.mOwner.put(identityId, owner);
        this.mObservers.put(identityId, observer);
        for (MutableResult<E> result : mResults) {
            result.observe(owner, observer);
        }
    }

    protected final void sendResult(@NonNull E event) {
        mResults.init(initQueueMaxLength(), mutableResult -> {
            for (Map.Entry<Integer, Observer<E>> entry : mObservers.entrySet()) {
                Observer<E> observer = entry.getValue();
                mutableResult.removeObserver(observer);
            }
        });
        boolean eventExist = false;
        for (MutableResult<E> result : mResults) {
            int id1 = System.identityHashCode(result.getValue());
            int id2 = System.identityHashCode(event);
            if (id1 == id2) {
                eventExist = true;
                break;
            }
        }
        if (!eventExist) {
            MutableResult<E> result = new MutableResult<>(event);
            for (Map.Entry<Integer, Observer<E>> entry : mObservers.entrySet()) {
                Integer key = entry.getKey();
                Observer<E> observer = entry.getValue();
                LifecycleOwner owner = mOwner.get(key);
                assert owner != null;
                result.observe(owner, observer);
            }
            mResults.add(result);
        }

        MutableResult<E> result = null;
        for (MutableResult<E> r : mResults) {
            int id1 = System.identityHashCode(r.getValue());
            int id2 = System.identityHashCode(event);
            if (id1 == id2) {
                result = r;
                break;
            }
        }
        if (result != null) result.setValue(event);
    }

    public final void input(E event) {
        onHandle(event);
    }

    protected void onHandle(E event) {
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onDestroy(owner);
        boolean isFragment = owner instanceof Fragment;
        for (Map.Entry<Integer, LifecycleOwner> entry : isFragment ? mFragmentOwner.entrySet() : mOwner.entrySet()) {
            LifecycleOwner owner1 = entry.getValue();
            if (owner1.equals(owner)) {
                Integer key = entry.getKey();
                mOwner.remove(key);
                if (isFragment) mFragmentOwner.remove(key);
                for (MutableResult<E> mutableResult : mResults) {
                    mutableResult.removeObserver(Objects.requireNonNull(mObservers.get(key)));
                }
                mObservers.remove(key);
                break;
            }
        }
        if (mObservers.size() == 0) mResults.clear();
    }
}