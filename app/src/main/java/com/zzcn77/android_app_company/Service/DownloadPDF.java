package com.zzcn77.android_app_company.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.zzcn77.android_app_company.Acitivity.MainActivity;
import com.zzcn77.android_app_company.Acitivity.ProductDetailsActivity;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.zzcn77.android_app_company.Service.DownloadService.installApk;

/**
 * Created by 赵磊 on 2017/5/20.
 */

public class DownloadPDF extends Service {

    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/downloads/";
    public static final String TAG = "download";
    private String url;//下载链接
    private int length;//文件长度
    private String fileName = null;//文件名
    private Notification notification;
    private RemoteViews contentView;
    private NotificationManager notificationManager;

    private static final int MSG_INIT = 0;
    private static final int URL_ERROR = 1;
    private static final int NET_ERROR = 2;
    private static final int DOWNLOAD_SUCCESS = 3;
    private String id;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    length = (int) msg.obj;
                    new DownloadThread(url, length, id).start();
                    createNotification();
                    break;
                case DOWNLOAD_SUCCESS:
                    //下载完成
                    notifyNotification(100, 100);
                    Toast.makeText(DownloadPDF.this, "下载完成", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DownloadPDF.this, "文件已经下载到:" + file.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case URL_ERROR:
                    Toast.makeText(DownloadPDF.this, "下载地址错误", Toast.LENGTH_SHORT).show();
                    break;
                case NET_ERROR:
                    Toast.makeText(DownloadPDF.this, "连接失败，请检查网络设置", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private File file;
    private long aLong;

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            url = intent.getStringExtra("url");
            if (url != null && !TextUtils.isEmpty(url)) {
                new InitThread(url).start();
            } else {
                mHandler.sendEmptyMessage(URL_ERROR);
            }
            id = intent.getStringExtra("id");

        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化子线程
     *
     * @author dong
     */
    class InitThread extends Thread {
        String url = "";

        public InitThread(String url) {
            this.url = url;
        }

        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            try {
                //连接网络文件
                URL url = new URL(this.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int length = -1;
                if (conn.getResponseCode() == HttpStatus.SC_OK) {
                    //获得文件长度
                    length = conn.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                fileName = this.url.substring(this.url.lastIndexOf("/") + 1, this.url.length());
                if (fileName == null && TextUtils.isEmpty(fileName) && !fileName.contains(".pdf")) {
                    fileName = getPackageName() + ".pdf";
                }
                File file = new File(dir, fileName);
                raf = new RandomAccessFile(file, "rwd");
                //设置文件长度
                raf.setLength(length);
                mHandler.obtainMessage(MSG_INIT, length).sendToTarget();
            } catch (Exception e) {
                mHandler.sendEmptyMessage(URL_ERROR);
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    raf.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载线程
     *
     * @author dong
     */
    class DownloadThread extends Thread {
        String url;
        int length;
        String id;

        public DownloadThread(String url, int length, String id) {
            this.url = url;
            this.length = length;
            this.id = id;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream input = null;
            try {

                URL url = new URL(this.url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = 0;
                conn.setRequestProperty("Range", "bytes=" + 0 + "-" + length);
                //设置文件写入位置
                file = new File(DownloadPDF.DOWNLOAD_PATH, fileName);
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                long mFinished = 0;
                //开始下载
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT || conn.getResponseCode() == HttpStatus.SC_OK) {
                    //LogUtil.i("下载开始了。。。");
                    //读取数据
                    input = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long speed = 0;
                    long time = System.currentTimeMillis();
                    while ((len = input.read(buffer)) != -1) {
                        //写入文件
                        raf.write(buffer, 0, len);
                        //把下载进度发送广播给Activity
                        mFinished += len;
                        speed += len;
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            notifyNotification(mFinished, length);
                            Intent intent = new Intent();
                            aLong = mFinished * 100 / length;
                            intent.setAction("pdfisdownloading");
                            intent.putExtra("progress", aLong);
                            intent.putExtra("id", id);
                            sendBroadcast(intent);
                            Log.i(TAG, "mFinished==" + mFinished);
                            Log.i(TAG, "length==" + length);
                            Log.i(TAG, "speed==" + speed);
                            speed = 0;
                        }
                    }
                    mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                    Log.i(TAG, "下载完成了。。。");
                    contentView.setTextViewText(R.id.tv_title, "下载完成");
                } else {
                    Log.i(TAG, "下载出错了。。。" + conn.getResponseCode() + conn.getURL());
                    mHandler.sendEmptyMessage(NET_ERROR);
                    contentView.setTextViewText(R.id.tv_title, "出错了。。。");

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @SuppressWarnings("deprecation")
    public void createNotification() {
        notification = new Notification(
                R.mipmap.icon,//应用的图标
                "资料正在下载...",
                System.currentTimeMillis());
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        //notification.flags = Notification.FLAG_AUTO_CANCEL;

        /*** 自定义  Notification 的显示****/
        contentView = new RemoteViews(getPackageName(), R.layout.notification_item);
        contentView.setProgressBar(R.id.progress, 100, 0, false);
        contentView.setTextViewText(R.id.tv_progress, "0%");
        notification.contentView = contentView;

        /*updateIntent = new Intent(this, AboutActivity.class);
          updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
     	updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     	pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
      	notification.contentIntent = pendingIntent;*/
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //设置notification的PendingIntent
        Intent intt = new Intent(this, ProductDetailsActivity.class);
        intt.putExtra("id", id);
        notification.contentIntent = PendingIntent.getActivity(this, 100, intt,
                Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationManager.notify(R.layout.notification_item, notification);
    }

    private void notifyNotification(long percent, long length) {
        aLong = percent * 100 / length;
        contentView.setTextViewText(R.id.tv_progress, aLong + "%");

        contentView.setProgressBar(R.id.progress, (int) length, (int) percent, false);
        notification.contentView = contentView;
        notificationManager.notify(R.layout.notification_item, notification);
    }

}