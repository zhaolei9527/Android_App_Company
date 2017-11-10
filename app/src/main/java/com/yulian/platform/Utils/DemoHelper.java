package com.yulian.platform.Utils;

import android.content.Context;
import android.util.Log;

import easeui.EaseUI;
import easeui.domain.EaseAvatarOptions;
import easeui.domain.EaseUser;
import easeui.utils.EaseCommonUtils;

public class DemoHelper {
    EaseUI easeUI;
    private static DemoHelper instance = null;

    public synchronized static DemoHelper getInstance() {
        if (instance == null) {
            instance = new DemoHelper();
        }
        return instance;
    }

    Context context;

    /**
     * init helper
     *
     * @param context application context
     */
    public void init(Context context) {
        easeUI = EaseUI.getInstance();
        this.context = context;
        setEaseUIProviders();
    }

    protected void setEaseUIProviders() {
        //set user avatar to circle shape
        EaseAvatarOptions avatarOptions = new EaseAvatarOptions();
        avatarOptions.setAvatarShape(1);
        easeUI.setAvatarOptions(avatarOptions);

        // set profile provider if you want easeUI to handle avatar and nickname
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                try {
                    Log.d("HomeFragment", String.valueOf(username));
                    username = (String) SPUtil.get(context, username, "");
                    Log.d("HomeFragment", String.valueOf(username));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return getUserInfo(username);
            }
        });
    }

    private EaseUser getUserInfo(String username) {
        EaseUser user = null;

        // if user is not in your contacts, set inital letter for him/her
        if (user == null) {
            user = new EaseUser(username);
            EaseCommonUtils.setUserInitialLetter(user);
        }
        return user;
    }

}
