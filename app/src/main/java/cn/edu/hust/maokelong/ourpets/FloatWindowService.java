package cn.edu.hust.maokelong.ourpets;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maokelong on 2016/4/17.
 */

public class FloatWindowService extends Service {
    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private static NotimassageService mnotimassageService;
    private static FloatWindowMassageView MassageWindow;
    private static FloatWindowSmallView smallWindow;


    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();

    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;
    // public static int count=0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 开启定时器，每隔1秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(), 0, 1000);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }

    class RefreshTask extends TimerTask {

        @Override
        public void run() {
            // 当前没有悬浮窗显示，则创建悬浮窗。
            if (!MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        MyWindowManager.createSmallWindow(getApplicationContext());
                        MyWindowManager.getDot(getApplicationContext());
                    }
                });
            }
            // 当前有悬浮窗显示，则更新数据。
            else if (MyWindowManager.isWindowShowing()) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //MyWindowManager.updateUsedPercent(getApplicationContext());
                        MyWindowManager.getDot(getApplicationContext());
                    }
                });
            }
//            // 当前界面是桌面，且没有悬浮窗显示，则创建悬浮窗。
//            if (isHome() && !MyWindowManager.isWindowShowing()) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.createSmallWindow(getApplicationContext());
//                        MyWindowManager.getDot(getApplicationContext());
//                    }
//                });
//            }
//            // 当前界面不是桌面，且有悬浮窗显示，则移除悬浮窗。
//            else if (!isHome() && MyWindowManager.isWindowShowing()) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.removeSmallWindow(getApplicationContext());
//                        MyWindowManager.removeBigWindow(getApplicationContext());
//                    }
//                });
//            }
//            // 当前界面是桌面，且有悬浮窗显示，则更新内存数据。
//            else if (isHome() && MyWindowManager.isWindowShowing()) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        MyWindowManager.updateUsedPercent(getApplicationContext());
//                        MyWindowManager.getDot(getApplicationContext());
//                    }
//                });
//            }
        }


    }

/**
 * 判断当前界面是否是桌面
 * <p>
 * 获得属于桌面的应用的应用包名称
 *
 * @return 返回包含所有包名的字符串列表
 * //
 */
//    private boolean isHome() {
//        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
//        return getHomes().contains(rti.get(0).topActivity.getPackageName());
//    }

/**
 * 获得属于桌面的应用的应用包名称
 *
 * @return 返回包含所有包名的字符串列表
//     */
//    private List<String> getHomes() {
//        List<String> names = new ArrayList<String>();
//        PackageManager packageManager = this.getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
//                PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo ri : resolveInfo) {
//            names.add(ri.activityInfo.packageName);
//        }
//        return names;
//    }
}
