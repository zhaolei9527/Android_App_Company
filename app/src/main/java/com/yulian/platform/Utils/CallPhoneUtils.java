package com.yulian.platform.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

import static anet.channel.util.Utils.context;

/**
 * Created by 赵磊 on 2017/5/11.
 */

public class CallPhoneUtils {

    // TODO: 2017/5/11
    //拨打电话
    public static void CallPhone(Activity activity, String phone) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Acp.getInstance(context).request(new AcpOptions.Builder()
                            .setPermissions(Manifest.permission.CALL_PHONE)
                /*以下为自定义提示语、按钮文字
                .setDeniedMessage()
                .setDeniedCloseBtn()
                .setDeniedSettingBtn()
                .setRationalMessage()
                .setRationalBtn()*/
                            .build(),
                    new AcpListener() {
                        @Override
                        public void onGranted() {

                        }
                        @Override
                        public void onDenied(List<String> permissions) {
                            Toast.makeText(context, "权限申请被拒绝", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phone);
            intent.setData(data);
            activity.startActivity(intent);
        }

    }
}
