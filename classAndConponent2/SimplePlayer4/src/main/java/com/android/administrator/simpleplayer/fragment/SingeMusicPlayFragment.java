package com.android.administrator.simpleplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.administrator.simpleplayer.MainActivity;
import com.android.administrator.simpleplayer.R;
import com.android.administrator.simpleplayer.adapter.SingleMusicPlayAdapter;
import com.android.administrator.simpleplayer.entity.MusicEntity;
import com.android.administrator.simpleplayer.service.PlayService;
import com.android.administrator.simpleplayer.service.PlayStatus;
import com.android.administrator.simpleplayer.util.MusicScannerUtil;
import com.android.administrator.simpleplayer.util.PlayListUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class SingeMusicPlayFragment extends Fragment{

    private View loadingView;
    private ListView mainListView;
    private View emptyView;
    private List<MusicEntity> data;
    private SingleMusicPlayAdapter playAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_layout, container, false);
        initRootView(rootView);
        checkData();
        return rootView;
    }

    private void initRootView(View rootView){
        loadingView = rootView.findViewById(R.id.LoadingLayout);
        mainListView = (ListView) rootView.findViewById(R.id.ListView);
        emptyView = rootView.findViewById(R.id.EmptyView);

        mainListView.setEmptyView(emptyView);
        mainListView.setOnItemClickListener(playItemClick);
    }

    // 列表点击事件
    private AdapterView.OnItemClickListener playItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            playAdapter.setPlayIndex(position);// 修改界面状态
            // 启动服务进行播放控制
            Intent intent = new Intent(getActivity(), PlayService.class);
            intent.putExtra(PlayStatus.PLAY_ACTION, PlayStatus.PLAY_LISTCLICK);
            intent.putExtra(PlayStatus.PLAY_INDEX, position);
            getActivity().startService(intent);
        }
    };

    // 检查数据存不存在
    private void checkData(){
        if(data == null){ // 如果数据不存在 则查找数据，否则就显示数据
            data = PlayListUtil.getPlayList(getActivity());
            if(data == null){
                // 浏览音频
                MusicScannerUtil.startScan(getActivity(), new MusicScannerUtil.OnScanCompletedListener() {
                    @Override
                    public void onCompleted(List<MusicEntity> data) {
                        // 保存数据
                        SingeMusicPlayFragment.this.data = data;
                        PlayListUtil.savePlayList(getActivity(), data);
                        loadData();
                    }
                });
            }else{
                loadData();
            }
        }else{
            loadData();
        }
    }

    // 在Fragment加载完数据之后，通知Activity初始化播放信息
    private void notifyInitControllerInfo(){
        ((MainActivity)getActivity()).onFragmentLoadData(data);
    }

    // 加载数据
    private void loadData(){
        loadingView.setVisibility(View.GONE);
        Log.d("zhou", "SingleMusicPlayFragment--loadData--66--size："+data.size());
        playAdapter = new SingleMusicPlayAdapter(getActivity(), data);
        mainListView.setAdapter(playAdapter);
        notifyInitControllerInfo();
    }

    public void dataChanged(){
        // 浏览音频
        MusicScannerUtil.startScan(getActivity(), new MusicScannerUtil.OnScanCompletedListener() {
            @Override
            public void onCompleted(List<MusicEntity> data) {
                // 保存数据
                SingeMusicPlayFragment.this.data = data;
                PlayListUtil.savePlayList(getActivity(), data);
                loadData();
            }
        });
    }
}
