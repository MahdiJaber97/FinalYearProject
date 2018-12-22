package com.example.bleh.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bleh.myapplication.DB.AppDatabase;
import com.example.bleh.myapplication.DB.Exercise;
import com.example.bleh.myapplication.DB.Food;
import com.example.bleh.myapplication.DB.Plan;
import com.example.bleh.myapplication.DB.User;
import com.example.bleh.myapplication.Utils.FormulaUtils;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class feature2 extends AppCompatActivity {

    public AppDatabase mydb;

    TextView BMR,requirements,days,percentage;
    Button addfood,addex,nextday;
    LinearLayout mainLayout;
    Button Meas;
    Plan plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature2);
        Intent intent = getIntent();
        final long planid = intent.getExtras().getLong("planid");
        final long userid = intent.getExtras().getLong("uid");
        mydb = AppDatabase.getInstance(feature2.this);
        Meas = findViewById(R.id.meas);
        Meas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetailedMeasurements.class);
                startActivity(intent);
            }
        });
        nextday = findViewById(R.id.nextday);
        addex = findViewById(R.id.addexercise);
        addfood = findViewById(R.id.addfood);
        BMR = findViewById(R.id.BMR);
        requirements = findViewById(R.id.requirements);
        days = findViewById(R.id.days);
        percentage = findViewById(R.id.percentage);

        final User user = mydb.getUserDao(feature2.this).getUserById((int) userid);
        plan = mydb.getPlanDao(feature2.this).getPlanById((int) planid);
        DecimalFormat df = new DecimalFormat("#.##");
        BMR.setText(df.format(plan.bmr));
        days.setText(""+plan.nbOfDays+" days");
        double progress = FormulaUtils.calculatePercentage(user.getWeight(), plan.currentWeight, plan.amount, plan.type);
        percentage.setText(""+progress+" %");
        requirements.setText(FormulaUtils.CalulcateDailyRequirements(plan.getWorkoutPerWeek(), plan.getBmr()));
        if( progress >= 100 || plan.nbOfDays <0)
        {
            if(progress>=100)
            {
                user.setWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                mydb.getUserDao(feature2.this).update(user);
                Intent newIntent = new Intent(feature2.this,feature1.class);
                newIntent.putExtra("uid",userid);
                newIntent.putExtra("planid",planid);
                newIntent.putExtra("flag",1);
                startActivity(newIntent);
                finish();
            }else{
                user.setWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                mydb.getUserDao(feature2.this).update(user);
                Intent newIntent = new Intent(feature2.this,feature1.class);
                newIntent.putExtra("uid",userid);
                newIntent.putExtra("planid",planid);
                newIntent.putExtra("flag",-1);
                startActivity(newIntent);
                finish();
            }
        }
        mainLayout = findViewById(R.id.mainlayout);

        nextday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan.setCurrentWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                plan.setBmr(Double.parseDouble(FormulaUtils.calculateBmr(user.getSex(), plan.getCurrentWeight(), user.getHeight(), user.getBirthDay())));
                plan.setNbOfDays(plan.getNbOfDays()-1);
                mydb.getPlanDao(feature2.this).update(plan);
                Intent newIntent = new Intent(feature2.this,feature2.class);
                newIntent.putExtra("uid",userid);
                newIntent.putExtra("planid",planid);
                startActivity(newIntent);
                finish();


            }
        });

        addfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = new LinearLayout(feature2.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(300,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsLeft.setMargins(60,60,0,0);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(600,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsRight.setMargins(30,60,0,0);
                TextView foodView = new TextView(feature2.this);
                foodView.setText("Add Food");
                foodView.setLayoutParams(paramsLeft);
                foodView.setTextColor(Color.BLACK);


                final Spinner myspinner = new Spinner(feature2.this);
                myspinner.setLayoutParams(paramsRight);
                myspinner.setTag("myspinner");

                List<Food> foodList = mydb.getFoodDao(feature2.this).getAll();
                List<String> foodSpinner = new ArrayList<String>();
                for(Food food : foodList)
                {
                    foodSpinner.add(food.getFoodName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(feature2.this, android.R.layout.simple_spinner_item, foodSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                LinearLayout linearLayout2 = new LinearLayout(feature2.this);

                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

                TextView quantityView = new TextView(feature2.this);
                quantityView.setText("Add Quantity");
                quantityView.setLayoutParams(paramsLeft);
                quantityView.setTextColor(Color.BLACK);

                final EditText quantityText = new EditText(feature2.this);
                quantityText.setText("1");
                quantityText.setTag("quantityText");
                quantityText.setLayoutParams(paramsRight);
                quantityText.setBackgroundResource(0);

                LinearLayout linearLayout3 = new LinearLayout(feature2.this);

                linearLayout3.setOrientation(LinearLayout.HORIZONTAL);

                final Button submitFood = new Button(feature2.this);

                submitFood.setTextColor(Color.WHITE);
                submitFood.setText("Submit");
                submitFood.setTag("submitFood");
                submitFood.setLayoutParams(paramsLeft);
                submitFood.setBackgroundResource(R.color.colorAccent);
                submitFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitFood.setClickable(false);
                        Food pickedFood = mydb.getFoodDao(feature2.this).findByName(myspinner.getSelectedItem().toString());
                        double caloriesUsed = pickedFood.getCalorieValue() * Double.parseDouble(quantityText.getText().toString());
                        DecimalFormat df = new DecimalFormat("#.##");
                        requirements.setText(df.format(Double.parseDouble(requirements.getText().toString())-caloriesUsed));
                    }
                });

                mainLayout.addView(linearLayout);
                mainLayout.addView(linearLayout2);
                mainLayout.addView(linearLayout3);
                linearLayout.addView(foodView);
                linearLayout.addView(myspinner);
                linearLayout2.addView(quantityView);
                linearLayout2.addView(quantityText);
                linearLayout3.addView(submitFood);
                myspinner.setAdapter(adapter);


            }
        });

        addex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = new LinearLayout(feature2.this);

                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(300,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsLeft.setMargins(60,60,0,0);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(600,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsRight.setMargins(30,60,0,0);

                TextView exView = new TextView(feature2.this);
                exView.setText("Add Exercise");
                exView.setLayoutParams(paramsLeft);
                exView.setTextColor(Color.BLACK);


                final Spinner myspinner = new Spinner(feature2.this);
                myspinner.setLayoutParams(paramsRight);
                myspinner.setTag("myspinner");

                List<Exercise> exerciseList = mydb.getExerciseDao(feature2.this).getAll();
                List<String> exSpinner = new ArrayList<String>();
                for(Exercise ex : exerciseList)
                {
                    exSpinner.add(ex.getExerciseName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(feature2.this, android.R.layout.simple_spinner_item, exSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                LinearLayout linearLayout2 = new LinearLayout(feature2.this);

                linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

                TextView quantityView = new TextView(feature2.this);
                quantityView.setText("Add Quantity");
                quantityView.setLayoutParams(paramsLeft);
                quantityView.setTextColor(Color.BLACK);

                final EditText quantityText = new EditText(feature2.this);
                quantityText.setText("1");
                quantityText.setTag("quantityText");
                quantityText.setLayoutParams(paramsRight);
                quantityText.setBackgroundResource(0);

                LinearLayout linearLayout3 = new LinearLayout(feature2.this);

                linearLayout3.setOrientation(LinearLayout.HORIZONTAL);

                final Button submitEx = new Button(feature2.this);
                submitEx.setTextColor(Color.WHITE);
                submitEx.setText("Submit");
                submitEx.setTag("submitEx");
                submitEx.setLayoutParams(paramsLeft);
                submitEx.setBackgroundResource(R.color.colorAccent);
                submitEx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Exercise pickedEx = mydb.getExerciseDao(feature2.this).findByName(myspinner.getSelectedItem().toString());
                        double caloriesGained = FormulaUtils.CalculateCaloriesFromMET(plan.getCurrentWeight(),pickedEx.getMetValue(),Integer.valueOf(quantityText.getText().toString())) ;
                        DecimalFormat df = new DecimalFormat("#.##");
                        requirements.setText(df.format(Double.parseDouble(requirements.getText().toString())+caloriesGained));
                        submitEx.setClickable(false);
                    }
                });

                mainLayout.addView(linearLayout);
                mainLayout.addView(linearLayout2);
                mainLayout.addView(linearLayout3);
                linearLayout.addView(exView);
                linearLayout.addView(myspinner);
                linearLayout2.addView(quantityView);
                linearLayout2.addView(quantityText);
                linearLayout3.addView(submitEx);
                myspinner.setAdapter(adapter);

            }
        });

    }
}
