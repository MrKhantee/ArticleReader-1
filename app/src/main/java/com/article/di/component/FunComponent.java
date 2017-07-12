package com.article.di.component;

import com.article.core.fun.mvp.FunMainFragment;

import dagger.Component;

/**
 * Created by Amos on 2017/7/3.
 * Desc：
 */
@Component(dependencies = AppComponent.class)
public interface FunComponent {

    FunMainFragment inject(FunMainFragment funMainFragment);

}
