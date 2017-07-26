package com.article.core.book.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.article.CoreApplication;
import com.article.R;
import com.article.common.api.BookApi;
import com.article.common.utils.AppUtils;
import com.article.common.utils.LogUtils;
import com.article.common.utils.NetworkUtils;
import com.article.core.book.bean.BookMixAToc;
import com.article.core.book.bean.support.BookDownloadMessage;
import com.article.core.book.bean.support.BookDownloadProgress;
import com.article.core.book.bean.support.BookDownloadQueue;
import com.article.core.book.manager.CacheManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Amos on 2017/7/21.
 * Desc：
 */

public class BookDownloadService extends Service {

    private List<BookDownloadQueue> mBookDownloadQueues = new ArrayList<>();
    //当前队列中是否有任务
    private boolean isBusy = false;
    private BookApi mBookApi;
    private CompositeDisposable mCompositeDisposable;
    public static boolean canceled = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        mBookApi = CoreApplication.getInstance().getAppComponent().getBookApi();
    }

    /**
     * 提交到下载队列
     *
     * @param queue
     */
    public static void post(BookDownloadQueue queue) {
        EventBus.getDefault().post(queue);
    }

    /**
     * 更新进度
     *
     * @param progress
     */
    public void post(BookDownloadProgress progress) {
        EventBus.getDefault().post(progress);
    }

    /**
     * 发送下载消息
     *
     * @param message
     */
    private void post(BookDownloadMessage message) {
        EventBus.getDefault().post(message);
    }

    /**
     * 添加到下载队列
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addToDownloadQueue(BookDownloadQueue queue) {
        LogUtils.i("-------------------------------------------->>");
        if (!TextUtils.isEmpty(queue.bookId)) {
            boolean exists = false;
            //判断小说是否在下载队列中
            for (int i = 0; i < mBookDownloadQueues.size(); i++) {
                //根据小说的ID判断
                if (mBookDownloadQueues.get(i).bookId.equals(queue.bookId)) {
                    exists = true;
                    LogUtils.i("addToDownloadQueue: The book already exists!");
                    break;
                }
            }
            if (exists) {
                //已经在下载队列
                post(new BookDownloadMessage(queue.bookId, "已经在缓存队列中了", false));
                return;
            }
            mBookDownloadQueues.add(queue);
            LogUtils.i("addToDownloadQueue" + queue.bookId);
            post(new BookDownloadMessage(queue.bookId, "成功加入缓存", false));
        }
        //线程不繁忙，取出第一条开始下载
        if (mBookDownloadQueues.size() > 0 && !isBusy) {
            BookDownloadQueue bookDownloadQueue = mBookDownloadQueues.get(0);
            isBusy = true;
            downloadBook(bookDownloadQueue);
        }

    }

    /**
     * 开始下载小说
     *
     * @param queue
     */
    private synchronized void downloadBook(final BookDownloadQueue queue) {
        new AsyncTask<Integer, Integer, Integer>() {
            List<BookMixAToc.mixToc.Chapters> chapterses = queue.list;
            String bookId = queue.bookId;
            int start = queue.start;
            int end = queue.end;

            @Override
            protected Integer doInBackground(Integer... params) {
                int failureCount = 0;
                int progress = 0;
                for (int i = start; i <= end && i <= chapterses.size(); i++) {
                    if (canceled) {
                        break;
                    }
                    if (!NetworkUtils.isAvailable(AppUtils.getAppContext())) {
                        queue.isCancel = true;
                        post(new BookDownloadMessage(bookId, getString(R.string.book_read_download_error), true));
                        failureCount = -1;
                        break;
                    }
                    if (!queue.isFinish && !queue.isCancel) {
                        BookMixAToc.mixToc.Chapters chapters = chapterses.get(i - 1);
                        String link = chapters.link;
                        int result = download(link, bookId, chapters.title, i, chapterses.size());
                        if (result != 1) {
                            failureCount++;
                        }
                    } else {
                        int size = (end - start) / 100;
                        progress += size;
                        post(new BookDownloadProgress(bookId, progress, true));
                        LogUtils.i("--------------------->" + size);
                    }
                }
                return failureCount;
            }

            @Override
            protected void onPostExecute(Integer failureCount) {
                super.onPostExecute(failureCount);
                queue.isFinish = true;
                if (failureCount > -1) {
                    //完成通知
                    post(new BookDownloadMessage(bookId,
                            String.format(getString(R.string.book_read_download_complete), failureCount), true));
                }
                mBookDownloadQueues.remove(queue);
                // 释放 空闲状态
                isBusy = false;
                if (!canceled) {
                    // post一个空事件，通知继续执行下一个任务
                    post(new BookDownloadQueue());
                } else {
                    mBookDownloadQueues.clear();
                }
                canceled = false;
                LogUtils.i(bookId + "缓存完成，失败" + failureCount + "章");
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    /**
     * 开始下载小说
     *
     * @param url         连接地址
     * @param bookId      小说ID
     * @param title       小说标题
     * @param chapter     小说章节
     * @param chapterSize 章节大小
     * @return
     */
    private int download(String url, final String bookId, final String title, final int chapter,
                         final int chapterSize) {
        final int[] result = {-1};
        Disposable disposable = mBookApi.getChapterRead(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(chapterRead -> {
                    if (chapterRead != null) {
                        CacheManager.getInstance().saveChapterFile(bookId, chapter, chapterRead.chapter);
                        result[0] = 1;
                    } else {
                        result[0] = 0;
                    }
                }, throwable -> result[0] = 0, () -> result[0] = 1);
        addSubscribe(disposable);
        while (result[0] == -1) {
            try {
                Thread.sleep(350);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result[0];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    public static void cancel() {
        canceled = true;
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }
}
