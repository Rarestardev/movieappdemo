package com.rarestar.empirebesttv.video_player;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.rarestar.empirebesttv.R;

public class BrightnessDialog extends AppCompatDialogFragment {
    TextView brt_number;
    SeekBar brt_SeekBar;
    ImageView brt_close;
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.brt_dialog_item, null);
        builder.setView(view);

        brt_number = view.findViewById(R.id.brt_number);
        brt_SeekBar = view.findViewById(R.id.brt_SeekBar);
        brt_close = view.findViewById(R.id.brt_close);

        int brightness = Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS,0);
        brt_number.setText(brightness + "");
        brt_SeekBar.setProgress(brightness);
        brt_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Context context = getContext().getApplicationContext();
                boolean canWrite = Settings.System.canWrite(context);
                if (canWrite){
                    int sBrightness = progress * 255 / 255 ;
                    brt_number.setText(sBrightness + "");
                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS_MODE,
                            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                    Settings.System.putInt(context.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS,sBrightness);
                }else {
                    Toast.makeText(context, "لطفا دسترسی به کنترل روشنایی صفحه را به برنامه بدهید!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    startActivityForResult(intent,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        brt_close.setOnClickListener(view1 -> dismiss());
        return builder.create();
    }
}
