package com.example.bleh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bleh.myapplication.DB.AppDatabase;
import com.example.bleh.myapplication.DB.Plan;
import com.example.bleh.myapplication.DB.User;
import com.example.bleh.myapplication.Utils1.FormulaUtils;
import com.github.lzyzsd.circleprogress.DonutProgress;

public class AlarmReceiver extends BroadcastReceiver {

    public AppDatabase mydb;
    Plan plan;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
        mydb = AppDatabase.getInstance(context);
        final long planid = intent.getExtras().getLong("planid");
        final long userid = intent.getExtras().getLong("uid");
        final String requirements = intent.getExtras().getString("requirements");
        plan = mydb.getPlanDao(context).getPlanById((int) planid);
        final User user = mydb.getUserDao(context).getUserById((int) userid);
        plan.setCurrentWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements)));
        plan.setBmr(Double.parseDouble(FormulaUtils.calculateBmr(user.getSex(), plan.getCurrentWeight(), user.getHeight(), user.getBirthDay())));
        plan.setNbOfDays(plan.getNbOfDays() - 1);
        mydb.getPlanDao(context).update(plan);
        final SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("EnterCount",0);
        editor.apply();
    }

}
