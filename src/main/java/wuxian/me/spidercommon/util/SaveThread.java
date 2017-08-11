package wuxian.me.spidercommon.util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wuxian on 11/8/2017.
 * <p>
 * using example:
 * thread t = new SaveThread();
 * t.start();
 * <p>
 * when data comes,call t.notifyDatachanged();
 * <p>
 * when close thread,call t.quit();
 */
public class SaveThread<T> extends Thread {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    private final long SLEEP_TIME = 10000;

    private Boolean hasNewData = false;
    private AtomicBoolean isDataChanged = new AtomicBoolean(false);
    private AtomicBoolean isQuit = new AtomicBoolean(false);
    private Boolean isAwait = false;
    private AtomicBoolean isSleep = new AtomicBoolean(false);

    private ArrayList<T> mCachedNewsList = new ArrayList<T>();

    public SaveThread(ArrayList<T> list) {
        mCachedNewsList = list;
    }


    //Todo
    private synchronized void saveNewsToDB() {
        /*
        SomanewsDao dao = CocoDBFactory.getInstance().getSomanewsDao();
        if (null != dao) {
            try {
                dao.save(mCachedNewsList);
            } catch (Throwable th) {

            }
        }
        */
    }

    public void quit() {
        isQuit.set(true);
        saveNewsToDB();  //退出的时候强制存一次
        mCachedNewsList = new ArrayList<T>();
    }

    public void notifyDataChanged() {

        try {
            lock.lock();
            isDataChanged.set(true);

            if (isAwait) {
                condition.signalAll();
            } else if (isSleep.get()) {
                interrupt();
            }
        } finally {
            lock.unlock();
        }

    }

    /**
     * 运作方式：
     * 1 正常情况下没有数据时isDatachanged,hasNewData都为false,线程转成wait状态
     * 2 第一次notifyDatachanged之后,执行sleep。若期间有新数据进来,则继续sleep,否则在sleep整个时间块之后进行存数据库然后转成wait
     */
    @Override
    public void run() {
        while (!isQuit.get()) {

            boolean goSleep = false;
            boolean goWait = false;
            boolean saveData = false;

            try {
                lock.lock();
                if (isDataChanged.get()) {
                    goSleep = true;

                    hasNewData = true;
                    isDataChanged.set(false);
                } else {
                    if (hasNewData) {
                        hasNewData = false;
                        saveData = true;
                    }

                    goWait = true;
                }
            } finally {
                lock.unlock();
            }

            if (saveData && !isDataChanged.get()) {
                saveNewsToDB();
            }

            if (goWait && !isDataChanged.get()) {
                try {
                    lock.lock();
                    if (!isDataChanged.get()) {
                        isAwait = true;
                        condition.awaitUninterruptibly();
                    }
                } finally {
                    isAwait = false;
                    lock.unlock();
                }
            }


            if (goSleep && !isDataChanged.get()) {
                try {
                    isSleep.set(true);

                    if (!isDataChanged.get()) {
                        interrupted();
                        sleep(SLEEP_TIME);
                    }
                } catch (InterruptedException e) {

                } finally {
                    isSleep.set(false);
                }
            }

        }

    }
}
