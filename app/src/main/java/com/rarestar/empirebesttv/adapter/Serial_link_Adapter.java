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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rarestar.empirebesttv.Movie_model.serial_model;
import com.rarestar.empirebesttv.R;
import com.rarestar.empirebesttv.video_player.VideoPlayer;
import com.rarestar.empirebesttv.views.PlayMovieActivity;

import java.io.File;
import java.util.List;

public class Serial_link_Adapter extends RecyclerView.Adapter<Serial_link_Adapter.ViewHolder> {
    List<serial_model> list;
    Context context;
    public static String linkSerial;
    public Serial_link_Adapter(List<serial_model> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quality_serial,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (PlayMovieActivity.DownloadingMovie){
            if (list.get(position).getQuality().equals("زیرنویس فارسی")){
                holder.cardView.setVisibility(View.GONE);
            }else{
                holder.qualityView.setText("پخش با کیفیت :" + list.get(position).getQuality());
            }
        }else {
            if (list.get(position).getQuality().equals("زیرنویس فارسی")){
                holder.qualityView.setText("دانلود : " + list.get(position).getQuality());
            }else{
                holder.qualityView.setText("دانلود با کیفیت :" + list.get(position).getQuality());
            }
            holder.Vol.setText("(" + list.get(position).getVol() + ")");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView qualityView,Vol,VolView;
        CardView cardView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            qualityView = itemView.findViewById(R.id.qualityView);
            Vol = itemView.findViewById(R.id.Vol);
            imageView = itemView.findViewById(R.id.imageView);
            VolView = itemView.findViewById(R.id.VolView);
            cardView = itemView.findViewById(R.id.cardView);

            if (PlayMovieActivity.DownloadingMovie){
                imageView.setImageResource(R.drawable.baseline_play_arrow_24);
                Vol.setVisibility(View.GONE);
                VolView.setVisibility(View.GONE);
                cardView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, VideoPlayer.class);
                    linkSerial = list.get(getAdapterPosition()).getLink();
                    context.startActivity(intent);
                });
            }else {
                imageView.setImageResource(R.drawable.baseline_download_24);
                Vol.setVisibility(View.VISIBLE);
                VolView.setVisibility(View.VISIBLE);
                cardView.setOnClickListener(view -> {
                    linkSerial = list.get(getAdapterPosition()).getLink();
                    downloadFile(linkSerial);
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
}
