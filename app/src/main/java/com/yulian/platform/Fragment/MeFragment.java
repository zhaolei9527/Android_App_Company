package com.yulian.platform.Fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yulian.platform.Acitivity.ChangePasswordActivity;
import com.yulian.platform.Acitivity.ChatListActivity;
import com.yulian.platform.Acitivity.LoginActivity;
import com.yulian.platform.Acitivity.MerchantsCollectionActivity;
import com.yulian.platform.Acitivity.MyCollectActivity;
import com.yulian.platform.Acitivity.SettingActivity;
import com.yulian.platform.R;
import com.yulian.platform.Utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by 赵磊 on 2017/5/17.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.rl_change_password)
    RelativeLayout rlChangePassword;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.ll_headbg)
    LinearLayout llHeadbg;
    @BindView(R.id.img2)
    ImageView img2;
    @BindView(R.id.img3)
    ImageView img3;
    @BindView(R.id.img4)
    ImageView img4;
    @BindView(R.id.img_back)
    ImageView img_back;
    @BindView(R.id.expandablelistview)
    ExpandableListView expandablelistview;
    @BindView(R.id.rl_consult)
    RelativeLayout rlConsult;
    private String account;
    private Map<String, List<String>> dataset = new HashMap<>();
    private String[] parentList;
    private int[] childreniconList = new int[]{R.mipmap.shoucang_sj3, R.mipmap.shoucang_cp};
    private List<String> childrenList1 = new ArrayList<>();
    private MyExpandableListViewAdapter adapter;

    private boolean isopen = false;

    @Override
    protected int setLayoutResouceId() {
        parentList = new String[]{getResources().getString(R.string.mycollect)};
        childrenList1.add(getResources().getString(R.string.my_merchants_collect));
        childrenList1.add(getResources().getString(R.string.The_product_collection));
        dataset.put(parentList[0], childrenList1);
        return R.layout.f_me_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        img_back.setVisibility(View.GONE);
        rlChangePassword.setOnClickListener(this);
        rlConsult.setOnClickListener(this);
        rlSetting.setOnClickListener(this);
        account = (String) SPUtil.get(mActivity, "account", "");
        String email = (String) SPUtil.get(mActivity, "email", "");
        if (account.trim().isEmpty()) {
            tvAccount.setText(getString(R.string.Youarenotcurrentlyloggedin));
            llHeadbg.setEnabled(true);
            llHeadbg.setOnClickListener(this);
        } else {
            tvAccount.setText(account);
            llHeadbg.setEnabled(false);
        }
        tvEmail.setText(email);
        expandablelistview.setGroupIndicator(null);
        adapter = new MyExpandableListViewAdapter();
        expandablelistview.setAdapter(adapter);
        expandablelistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                final ImageView img = (ImageView) parent.findViewById(R.id.img);
                if (!isopen) {
                    //创建旋转动画
                    Animation anim = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setDuration(300);
                    anim.setRepeatCount(0);//动画的重复次数
                    anim.setFillAfter(true);//设置为true，动画转化结束后被应用
                    img.startAnimation(anim);//开始动画
                } else {
                    //创建旋转动画
                    Animation anim = new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    anim.setDuration(300);
                    anim.setRepeatCount(0);//动画的重复次数
                    anim.setFillAfter(true);//设置为true，动画转化结束后被应用
                    img.startAnimation(anim);//开始动画
                }
                isopen = !isopen;
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        account = (String) SPUtil.get(mActivity, "account", "");
        if (account.trim().isEmpty()) {
            mActivity.finish();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_change_password:
                if (account.trim().isEmpty()) {
                    Toast.makeText(mActivity, getString(R.string.Youarenotcurrentlyloggedin), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, LoginActivity.class));
                } else {
                    startActivity(new Intent(mActivity, ChangePasswordActivity.class));
                }
                break;
            case R.id.rl_consult:
                if (account.trim().isEmpty()) {
                    Toast.makeText(mActivity, getString(R.string.Youarenotcurrentlyloggedin), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(mActivity, LoginActivity.class));
                } else {
                    startActivity(new Intent(mActivity, ChatListActivity.class));
                }
                break;
            case R.id.rl_setting:
                startActivity(new Intent(mActivity, SettingActivity.class));
                break;
            case R.id.ll_headbg:
                startActivity(new Intent(mActivity, LoginActivity.class));
                break;
        }
    }


    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos) {
            return dataset.get(parentList[parentPos]).get(childPos);
        }

        //  获得父项的数量
        @Override
        public int getGroupCount() {
            return dataset.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos) {
            return dataset.get(parentList[parentPos]).size();
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos) {
            return dataset.get(parentList[parentPos]);
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos) {
            return parentPos;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos) {
            return childPos;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.parent_item, null);
            }
            view.setTag(R.layout.parent_item, parentPos);
            view.setTag(R.layout.child_item, -1);
            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(int parentPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) mActivity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.child_item, null);
            }
            view.setTag(R.layout.parent_item, parentPos);
            view.setTag(R.layout.child_item, childPos);
            TextView text = (TextView) view.findViewById(R.id.child_title);
            ImageView img = (ImageView) view.findViewById(R.id.img);
            RelativeLayout rl_check = (RelativeLayout) view.findViewById(R.id.rl_check);
            text.setText(dataset.get(parentList[parentPos]).get(childPos));
            img.setBackground(getResources().getDrawable(childreniconList[childPos]));
            rl_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (account.trim().isEmpty()) {
                        Toast.makeText(mActivity, getString(R.string.Youarenotcurrentlyloggedin), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mActivity, LoginActivity.class));
                    } else {
                        if (childPos == 0) {
                            startActivity(new Intent(mActivity, MerchantsCollectionActivity.class));
                        } else {
                            startActivity(new Intent(mActivity, MyCollectActivity.class));
                        }
                    }
                }
            });
            return view;
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }
}
