package com.article.di.component;

import com.article.core.welfare.ui.fragment.GankMeiZiFragment;

import dagger.Component;

/**
 * Created by Amos on 2017/7/26.
 * Desc：
 */
@Component(dependencies = AppComponent.class)
public interface WelfareComponent {

    GankMeiZiFragment inject(GankMeiZiFragment gankMeiZiFragment);
}
