package com.zzcn77.android_app_company.Acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.chat.EMConversation;
import com.zzcn77.android_app_company.R;

import easeui.EaseConstant;
import easeui.ui.EaseConversationListFragment;

public class ChatListActivity extends FragmentActivity {
    String toChatUsername;
    private EaseConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, toChatUsername));
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.container, conversationListFragment).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
