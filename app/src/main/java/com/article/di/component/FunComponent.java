package com.article.di.component;

import com.article.core.fun.FunMainActivity;

import dagger.Component;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */
@Component(dependencies = AppComponent.class)
public interface FunComponent {


    FunMainActivity inject(FunMainActivity funMainActivity);
}
