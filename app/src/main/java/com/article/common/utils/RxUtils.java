package com.article.common.utils;

import android.text.TextUtils;

import com.article.CoreApplication;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/6/8.
 * Desc：
 */

public class RxUtils {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 从缓存中读取数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Flowable rxCreateDiskFlowable(String key, Class<T> clazz) {
        return Flowable.create((FlowableOnSubscribe<String>) e -> {
            LogUtils.d("get data from disk: key==" + key);
            String json = ACache.get(CoreApplication.getInstance()).getAsString(key);
            LogUtils.d("get data from disk finish , json==" + json);
            if (!TextUtils.isEmpty(json)) {
                e.onNext(json);
            }
            e.onComplete();
        }, BackpressureStrategy.BUFFER).map(s -> new Gson().fromJson(s, clazz)).subscribeOn(Schedulers.io());
    }

    /**
     * 缓存List数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxCacheListHelper(final String key) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .doOnNext(data -> Schedulers.io().createWorker().schedule(() -> {
                    LogUtils.d("get data from network finish ,start cache...");
                    //通过反射获取List,再判空决定是否缓存
                    if (data == null)
                        return;
                    Class clazz = data.getClass();
                    Field[] fields = clazz.getFields();
                    for (Field field : fields) {
                        String className = field.getType().getSimpleName();
                        // 得到属性值
                        if (className.equalsIgnoreCase("List")) {
                            try {
                                List list = (List) field.get(data);
                                LogUtils.d("list==" + list);
                                if (list != null && !list.isEmpty()) {
                                    ACache.get(CoreApplication.getInstance())
                                            .put(key, new Gson().toJson(data, clazz));
                                    LogUtils.d("cache finish");
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 缓存Bean数据
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxCacheBeanHelper(final String key) {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .doOnNext(data -> Schedulers.io().createWorker().schedule(() -> {
                    LogUtils.d("get data from network finish ,start cache...");
                    ACache.get(CoreApplication.getInstance())
                            .put(key, new Gson().toJson(data, data.getClass()));
                    LogUtils.d("cache finish");
                })).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(emitter -> {
            try {
                emitter.onNext(t);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }, BackpressureStrategy.BUFFER);
    }
}
