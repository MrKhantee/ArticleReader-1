package com.article.base;

import android.content.Context;

import com.article.core.book.bean.CollectionBook;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Amos on 2017/6/14.
 * Desc：
 */

public class RealmHelper {

    public static final String DATABASES_NAME = "articleReader.realm";


    private Realm mRealm;

    public RealmHelper(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    public void addBook(CollectionBook collectionBook) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(collectionBook);
        mRealm.commitTransaction();
    }

    public void deleteBook(String id) {
        CollectionBook collectionBook = mRealm.where(CollectionBook.class).equalTo("_id", id).findFirst();
        mRealm.beginTransaction();
        collectionBook.deleteFromRealm();
        mRealm.commitTransaction();
    }

    public boolean isBookExit(String id) {
        CollectionBook collectionBook = mRealm.where(CollectionBook.class).equalTo("_id", id).findFirst();
        if (collectionBook != null) {
            return true;
        } else {
            return false;
        }
    }

    public List<CollectionBook> getAllCollectionBook() {
        RealmResults<CollectionBook> collectionBooks = mRealm.where(CollectionBook.class).findAll();
        collectionBooks.sort("_id");
        return collectionBooks;
    }

    public void close() {
        if (mRealm != null) {
            mRealm.close();

        }
    }
}
