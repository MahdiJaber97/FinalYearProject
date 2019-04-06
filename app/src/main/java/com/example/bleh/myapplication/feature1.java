package com.example.bleh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bleh.myapplication.DB.AppDatabase;
import com.example.bleh.myapplication.DB.Plan;
import com.example.bleh.myapplication.DB.User;
import com.example.bleh.myapplication.Utils1.FormulaUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class feature1 extends AppCompatActivity {
    TextView BMR,meal,exer;
    EditText duration,amount;
    Spinner type;
    Button submit;
    private AppDatabase db;
    SeekBar seekBar;
    Spinner DurationExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature1);
        db= AppDatabase.getInstance(feature1.this);
        Intent intent = getIntent();
        final long uid = intent.getExtras().getLong("uid");
        final long planid = intent.getExtras().getLong("planid");
        int flag = intent.getExtras().getInt("flag");
        final User user = db.getUserDao(feature1.this).getUserById((int)uid);
        seekBar = findViewById(R.id.SeekBar);
        meal = findViewById(R.id.meal);
        exer = findViewById(R.id.exer);
        DurationExercise = findViewById(R.id.DurationExercise);
        if(flag==1)
        {
            DecimalFormat df = new DecimalFormat("#.##");
            Toast.makeText(feature1.this,"Congratulations, you accomplished your plan! Your current weight is "+df.format(user.weight),Toast.LENGTH_LONG ).show();
        }
        else if(flag==-1) {
            DecimalFormat df = new DecimalFormat("#.##");
            Toast.makeText(feature1.this,"Time is up! Better luck next time! Your current weight is "+df.format(user.weight),Toast.LENGTH_LONG ).show();
        }
        BMR = findViewById(R.id.BMR);
        duration = findViewById(R.id.duration);
        amount = findViewById(R.id.amount);
        BMR.setText(FormulaUtils.calculateBmr(user.getSex(),user.getWeight(),user.getHeight(),user.getBirthDay()));
        final List<String> spinnerType =  new ArrayList<String>();
        spinnerType.add("Lose Weight");
        spinnerType.add("Maintain Weight");
        spinnerType.add("Gain Weight");

        final List<String> Durations =  new ArrayList<String>();
        Durations.add("20 Mins");
        Durations.add("30 Mins");
        Durations.add("40 Mins");
        Durations.add("50 Mins");
        Durations.add("60 Mins");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerType);

        ArrayAdapter<String> Durationadapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Durations);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Durationadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        type = findViewById(R.id.type);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(type.getSelectedItem()=="Lose Weight")
                {
                    meal.setVisibility(View.VISIBLE);
                    seekBar.setVisibility(View.VISIBLE);
                    exer.setVisibility(View.VISIBLE);
                }
                else
                {
                    meal.setVisibility(View.GONE);
                    seekBar.setVisibility(View.GONE);
                    exer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        DurationExercise.setAdapter(Durationadapter);
        final Intent newintent = new Intent(this, feature2.class);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Plan plan = db.getPlanDao(feature1.this).getPlanById((int)planid);
                plan.amount=Double.parseDouble(amount.getText().toString());
                plan.bmr= Double.parseDouble(BMR.getText().toString());
                plan.nbOfDays=Integer.parseInt(duration.getText().toString());
                plan.type = type.getSelectedItem().toString();
                plan.workOutSessionDuration = Integer.parseInt(DurationExercise.getSelectedItem().toString().substring(0,2));
                if(plan.type.equals("Lose Weight"))
                {
                    int Max = seekBar.getMax();
                    int progress = seekBar.getProgress();
                    double max = new Double(Max);
                    double Progress = new Double(progress);
                    double Proportion = Progress / max;
                    plan.planDistribution = Proportion;
                }
                plan.currentWeight = user.weight;
                db.getPlanDao(feature1.this).update(plan);
                newintent.putExtra("uid",uid);
                newintent.putExtra("planid",planid);
                startActivity(newintent);
                finish();
            }
        });
    }
}
