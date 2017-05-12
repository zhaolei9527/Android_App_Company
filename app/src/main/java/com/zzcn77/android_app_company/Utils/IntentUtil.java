package com.zzcn77.android_app_company.Utils;

import android.content.Intent;

/**
 * Created by 赵磊 on 2017/5/11.
 */

public class IntentUtil  {

    /**
     * 判断intent和它的bundle是否为空
     *
     * @param intent
     * @return
     */
    public static boolean isBundleEmpty(Intent intent) {
        return (intent == null) && (intent.getExtras() == null);
    }
}