package com.article.base;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class RealmHelper {

    private static final String DATABASES_NAME = "articleReader.realm";

    public static Realm newRealmInstance() {
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASES_NAME)
                .build());
    }
}
