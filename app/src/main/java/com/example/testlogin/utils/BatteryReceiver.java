package com.example.testlogin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.testlogin.LoginActivity;
import com.example.testlogin.R;

public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        TextView txtBattery = ((LoginActivity) context).findViewById(R.id.txtBattery);
        ImageView imgBatteryLevel = ((LoginActivity) context).findViewById(R.id.imgBatteryLevel);
        ImageView imgCharging = ((LoginActivity) context).findViewById(R.id.imgCharging);

        String action = intent.getAction();

        if(action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            txtBattery.setText(context.getString(R.string.lblBattery, percentage));

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_100));
                    return;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_unknown));
                    return;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    imgCharging.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_power));
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    imgCharging.setImageDrawable(null);
                    break;
            }

            if(percentage >= 99)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_100));
            else if (percentage >= 90)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_90));
            else if (percentage >= 80)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_80));
            else if (percentage >= 60)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_60));
            else if (percentage >= 40)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_50));
            else if (percentage >= 30)
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_30));
            else
                imgBatteryLevel.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_battery_20));
        }
    }
}
