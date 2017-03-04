package com.android.administrator.simpleplayer.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.administrator.simpleplayer.R;
import com.android.administrator.simpleplayer.entity.MusicEntityByArtist;
import com.android.administrator.simpleplayer.util.AlbumBitmapUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class ArtistAdapter extends SimpleBaseAdapter<MusicEntityByArtist>{

    private Context context;
    private int lastPlayIndex = -1;

    public ArtistAdapter(Context context, List<MusicEntityByArtist> data) {
        super(context, data);
        this.context = context;
    }

    public void setLastPlayIndex(int lastPlayIndex) {
        this.lastPlayIndex = lastPlayIndex;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = getInflater().inflate(R.layout.artist_item, parent, false);
        }
        ImageView artistImageView = (ImageView) convertView.findViewById(R.id.artist_image);
        TextView artistTitleView = (TextView) convertView.findViewById(R.id.artist_title);
        TextView artistCountView = (TextView) convertView.findViewById(R.id.artist_count);
        ImageView artistIconView = (ImageView) convertView.findViewById(R.id.artist_icon);

        MusicEntityByArtist entityByArtist = getItem(position);
        Bitmap bitmap = AlbumBitmapUtil.getAlbumImage(context, entityByArtist.getData().get(0).getAlbumId(), 48, 48, true);
        if(bitmap == null){
            artistImageView.setImageResource(R.mipmap.icn_nomusic);
        }else{
            artistImageView.setImageBitmap(bitmap);
        }
        artistTitleView.setText(entityByArtist.getArtist());
        artistCountView.setText(entityByArtist.getCount()+" 首");

        if(lastPlayIndex == position){
            artistIconView.setImageResource(R.mipmap.song_play_icon);
        }else{
            artistIconView.setImageResource(R.mipmap.list_icn_more);
        }

        return convertView;
    }
}
