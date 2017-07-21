package com.article;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.article.common.utils.AppUtils;
import com.article.common.utils.SharedPreferencesUtils;
import com.article.di.component.AppComponent;
import com.article.di.component.DaggerAppComponent;
import com.article.di.module.AppModule;
import com.article.di.module.BookApiModule;
import com.article.di.module.FunApiModule;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Amos on 2017/6/8.
 * 全局的application
 */

public class CoreApplication extends Application {
    private static CoreApplication instance;
    private Set<Activity> allActivities;
    public static AppComponent mAppComponent;


    public static synchronized CoreApplication getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppUtils.init(this);
        initPrefs();

    }


    /**
     * 添加activity
     *
     * @param act
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除activity
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 初始化SharedPreference
     */
    protected void initPrefs() {
        SharedPreferencesUtils.init(getApplicationContext(),
                getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    /**
     * 返回AppComponent
     *
     * @return
     */

    public AppComponent getAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .bookApiModule(new BookApiModule())
                    .funApiModule(new FunApiModule())
                    .build();
        }
        return mAppComponent;
    }


}

