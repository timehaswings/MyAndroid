package com.android.administrator.simpleplayer.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.simpleplayer.R;
import com.android.administrator.simpleplayer.entity.MusicEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class SingleMusicPlayAdapter extends SimpleBaseAdapter<MusicEntity>{

    private int playIndex = -1; // 记录播放的下标

    public SingleMusicPlayAdapter(Context context, List<MusicEntity> data) {
        super(context, data);
    }

    /**
     * 修改播放或者是点击的下标
     * @param playIndex
     */
    public void setPlayIndex(int playIndex){
        this.playIndex = playIndex;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = getInflater().inflate(R.layout.single_music_play_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.playIcon);
        TextView titleView = (TextView) convertView.findViewById(R.id.playTitle);
        TextView infoView = (TextView) convertView.findViewById(R.id.playInfo);
        ImageView moreView = (ImageView) convertView.findViewById(R.id.playMore);

        MusicEntity entity = getData().get(position);
        titleView.setText(entity.getName());
        infoView.setText(entity.getArtist()+"-"+entity.getAlbum());

        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(playIndex == position){
            iconView.setVisibility(View.VISIBLE);
        }else{
            iconView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
