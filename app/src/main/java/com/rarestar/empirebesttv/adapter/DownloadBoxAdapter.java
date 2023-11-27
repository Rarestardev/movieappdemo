package com.rarestar.empirebesttv.adapter;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.MovieDownloadAndPlay_model;
import com.rarestar.empirebesttv.R;

import java.io.File;
import java.util.List;

public class DownloadBoxAdapter extends RecyclerView.Adapter<DownloadBoxAdapter.myViewHolder>{
    List<MovieDownloadAndPlay_model> data;
    Context context;
    public static String LINK;
    public DownloadBoxAdapter(List<MovieDownloadAndPlay_model> data, Context context) {
        this.data = data;
        this.context=context;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_download,parent,false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txt.setText("دانلود  : " + data.get(position).getQuality());
        holder.volume.setText("(" + data.get(position).getVol() + ")");
    }
    @Override
    public int getItemCount() {
        if(data==null) return 0;
        return data.size();
    }
    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView txt,volume;
        CardView clickDownload;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
            volume = itemView.findViewById(R.id.volume);
            clickDownload = itemView.findViewById(R.id.clickDownload);
            context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            clickDownload.setOnClickListener(view -> {
                LINK = data.get(getAdapterPosition()).getLink();
                downloadFile(LINK);
                Toast.makeText(context, "در حال دانلود...", Toast.LENGTH_LONG).show();
            });
        }
    }
    public void downloadFile(String url) {
        String filename= URLUtil.guessFileName(url,null,null);
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File(downloadPath,filename);
        DownloadManager.Request request;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.N){
            request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(filename)
                    .setDescription("در حال دانلود")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(Uri.fromFile(file))
                    .setRequiresCharging(false)
                    .setAllowedOverMetered(false)
                    .setAllowedOverRoaming(true);
        }else {
            request = new DownloadManager.Request(Uri.parse(url))
                    .setTitle(filename)
                    .setDescription("در حال دانلود")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationUri(Uri.fromFile(file))
                    .setAllowedOverMetered(false)
                    .setAllowedOverRoaming(true);
        }
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }
    BroadcastReceiver onComplete=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(new DownloadManager.Query());
            cursor.moveToFirst();
        }
    };
}
