package com.example.academycode.model.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.academycode.R;
import com.example.academycode.menu_principal.tutoriales.FullScreenVideo;
import com.example.academycode.model.VideoTeoria;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mCtx;
    private List<VideoTeoria> videoList;

    public VideoAdapter(Context mCtx, List<VideoTeoria> videoList) {
        this.mCtx = mCtx;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.video_tutoriales_cardview, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        VideoTeoria videosT = videoList.get(position);

        holder.tituloVideo.setText(videosT.getTitulo());
        holder.webView.loadUrl(videosT.getUrl_video());
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.setWebViewClient(new WebViewClient());
        holder.webView.getSettings().setUseWideViewPort(true);
        holder.webView.getSettings().setLoadWithOverviewMode(true);

        holder.fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, FullScreenVideo.class);
                intent.putExtra("url_video", videosT.getUrl_video());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    class VideoViewHolder extends RecyclerView.ViewHolder {

        public WebView webView;
        public Button fullscreen;
        public TextView tituloVideo;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            webView = itemView.findViewById(R.id.video_view);
            fullscreen = itemView.findViewById(R.id.fullscreen);
            tituloVideo = itemView.findViewById(R.id.titulovideo);

        }

    }


}
