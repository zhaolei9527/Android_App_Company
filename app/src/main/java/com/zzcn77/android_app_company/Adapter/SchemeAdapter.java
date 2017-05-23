package com.zzcn77.android_app_company.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.hitomi.universalloader.UniversalImageLoader;
import com.zzcn77.android_app_company.R;
import com.zzcn77.android_app_company.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 赵磊 on 2017/5/23.
 */

public class SchemeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> sourceImageList = null;
    private ArrayList datas = new ArrayList();
    private List<ImageView> originImgList;
    private final Transferee transferee;

    public SchemeAdapter(Context context, ArrayList datas) {
        this.context = context;
        this.datas = datas;
        transferee = Transferee.getDefault(context);

    }

    public ArrayList getDatas() {
        return datas;
    }

    public void setDatas(ArrayList datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.scheme_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Utils.displayImageFresco(R.drawable.tu, viewHolder.imgScheme);
        viewHolder.tvTitle.setText(String.valueOf(position));

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.imgScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originImgList = new ArrayList<>();
                sourceImageList = new ArrayList<>();
                for (int i = 0; i < datas.size(); i++) {
                    originImgList.add(finalViewHolder.imgScheme);
                    sourceImageList.add("http://7xi8d6.com1.z0.glb.clouddn.com/2017-04-16-17934400_1738549946443321_2924146161843437568_n.jpg");
                }
                TransferConfig config = TransferConfig.build()
                        .setNowThumbnailIndex(position)
                        .setSourceImageList(sourceImageList)
                        .setMissPlaceHolder(R.drawable.imgloading)
                        .setErrorPlaceHolder(R.drawable.imgloading)
                        .setOriginImageList(originImgList)
                        .setProgressIndicator(new ProgressPieIndicator())
                        .setIndexIndicator(new NumberIndexIndicator())
                        .setJustLoadHitImage(true)
                        .setImageLoader(UniversalImageLoader.with(context.getApplicationContext()))
                        .create();
                transferee.apply(config).show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.img_scheme)
        SimpleDraweeView imgScheme;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
