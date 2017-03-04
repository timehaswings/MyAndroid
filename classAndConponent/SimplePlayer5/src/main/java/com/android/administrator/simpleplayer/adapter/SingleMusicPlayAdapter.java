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

    public SingleMusicPlayAdapter(Context context, List<MusicEntity> data) {
        super(context, data);
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

        return convertView;
    }
}
