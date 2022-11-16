package com.example.projetofinalcep.modelos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        if (level < 50){
            Toast.makeText(context, "O nível da sua bateria está abaixo de 50%", Toast.LENGTH_SHORT).show();
        }
    }
}