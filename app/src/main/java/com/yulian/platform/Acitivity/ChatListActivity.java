package com.yulian.platform.Acitivity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.yulian.platform.Fragment.ConversationListFragment;
import com.yulian.platform.R;

import java.util.List;

public class ChatListActivity extends FragmentActivity {
    String toChatUsername;
    private ConversationListFragment ConversationListFragment;
    private EMMessageListener msgListener;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.em_activity_chat);
        //get user id or group id
        ConversationListFragment = new ConversationListFragment();

//        ConversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
//            @Override
//            public void onListItemClicked(EMConversation conversation) {
//                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.getLastMessage().getFrom()));
//            }
//        });
        getSupportFragmentManager().beginTransaction().add(R.id.container, ConversationListFragment).commit();

        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                ConversationListFragment.refresh();
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                ConversationListFragment.refresh();
                //收到透传消息
            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                ConversationListFragment.refresh();
            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                ConversationListFragment.refresh();
            }

            @Override
            public void onMessageRecalled(List<EMMessage> messages) {
                //消息被撤回
                ConversationListFragment.refresh();
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                ConversationListFragment.refresh();
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(msgListener);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        //记得在不需要的时候移除listener，如在activity的onDestroy()时
        new Thread() {
            @Override
            public void run() {
                super.run();
                EMClient.getInstance().chatManager().removeMessageListener(msgListener);
            }
        }.start();
        super.onDestroy();
    }
}
