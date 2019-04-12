package com.example.bleh.myapplication;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bleh.myapplication.DB.AppDatabase;
import com.example.bleh.myapplication.DB.Exercise;
import com.example.bleh.myapplication.DB.Food;
import com.example.bleh.myapplication.DB.Plan;
import com.example.bleh.myapplication.DB.User;
import com.example.bleh.myapplication.Utils1.FormulaUtils;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class feature2 extends AppCompatActivity {

    public AppDatabase mydb;
    TextView maintainText,BMR,requirements,days,burnedCalories,progressbar;
    Button addfood,addex,nextday,resetPlan;
    LinearLayout mainLayout;
    Button Meas,Bluetooth;
    DonutProgress donutProgress;
    Plan plan;
    int BreakfastAverageCalories = 350;
    int LunchAverageCalories = 500;
    int DinnerAverageCalories = 350;
    double caloriesConsumed = 0;
    double caloriesBurned = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature2);
        //Getting Intent
        Intent intent = getIntent();
        //////////

        //Defining everything on the layout
        requirements = findViewById(R.id.requirements);
        burnedCalories = findViewById(R.id.burnedCalories);
        donutProgress = findViewById(R.id.donut_progress);
        days = findViewById(R.id.days);
        addex = findViewById(R.id.addexercise);
        addfood = findViewById(R.id.addfood);
        Meas = findViewById(R.id.meas);
        BMR = findViewById(R.id.BMR);
        progressbar = findViewById(R.id.progressbar);
        maintainText = findViewById(R.id.maintainText);
        resetPlan = findViewById(R.id.resetPlan);
        mainLayout = findViewById(R.id.mainlayout);
        //////////

        //Getting Plan and User IDs and database
        final long planid = intent.getExtras().getLong("planid");
        final long userid = intent.getExtras().getLong("uid");
        mydb = AppDatabase.getInstance(feature2.this);
        plan = mydb.getPlanDao(feature2.this).getPlanById((int) planid);
        //////////

        //Getting the user and setting his BMR and Number of days he still got on his plan.
        final User user = mydb.getUserDao(feature2.this).getUserById((int) userid);
        DecimalFormat df = new DecimalFormat("#.##");
        BMR.setText(df.format(plan.bmr));
        days.setText("" + plan.nbOfDays + " days");
        ////////////

        //Getting the Enter Count
        final SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        final int EnteringCount = sharedPreferences.getInt("EnterCount",-2);
        if(EnteringCount==-1)
        {
            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            View promptsView = li.inflate(R.layout.prompts, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // get user input and set it to result
                                    // edit text
                                    try {
                                        user.setWeight(Integer.parseInt(userInput.getText().toString()));
                                        dialog.cancel();
                                    }
                                    catch (Exception e)
                                    {
                                        Toast.makeText(getApplicationContext(),"You didn't enter a valid weight",Toast.LENGTH_SHORT);
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("EnterCount",EnteringCount+1);
            editor.apply();
        }
        else
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("EnterCount",EnteringCount+1);
            editor.apply();
        }

        //Calculating Required Calories to stay on the same weight
        String requiredCalories = FormulaUtils.CalulcateDailyRequirements(plan.getWorkoutPerWeek(), plan.getBmr());
        Double CaloriesRequired = Double.parseDouble(requiredCalories);
        //////////

        //Getting WorkOutSession Duration
        int sessionDuration = plan.workOutSessionDuration;
        /////////
        final float caloriesRequiredToBeConsumed1 = sharedPreferences.getFloat("Requirements",-1);
        final float caloriesRequiredToBeBurned1 = sharedPreferences.getFloat("BurnedRequirements",-1);
        double caloriesRequiredToBeConsumed = caloriesRequiredToBeConsumed1;
        double caloriesRequiredToBeBurned = caloriesRequiredToBeBurned1;
        BreakfastAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
        DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
        DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
        requirements.setText(df.format(caloriesRequiredToBeConsumed));
        burnedCalories.setText(df.format(caloriesRequiredToBeBurned));
        //Defining the requirements of Calories Burned and Consumed according to the plan type.
//        if(plan.type.equals("Lose Weight"))
//        {
//            Double caloriesRequiredDaily = FormulaUtils.calculateRequired(plan);
//
//            try
//            {
//                requirements.setText(df.format(caloriesRequiredToBeConsumed));
//                burnedCalories.setText(df.format(caloriesRequiredToBeBurned));
//            }
//            catch (Exception ex)
//            {
//                Log.wtf("There is no plan","!");
//            }
//        }
//        else if(plan.type.equals("Gain Weight"))
//        {
//            Double caloriesRequiredDaily = FormulaUtils.calculateRequired(plan);
//            BreakfastAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            Log.wtf("calories",caloriesRequiredToBeConsumed+"");
//            Log.wtf("calories",caloriesRequiredToBeBurned+"");
//            try
//            {
//                requirements.setText(df.format(caloriesRequiredToBeConsumed));
//                burnedCalories.setText(df.format(caloriesRequiredToBeBurned));
//            }
//            catch (Exception ex)
//            {
//                Log.wtf("There is no plan",ex);
//            }
//        }
//        else
//        {
//            BreakfastAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            DinnerAverageCalories = (int)(caloriesRequiredToBeConsumed*0.25);
//            try
//            {
//                requirements.setText(df.format(caloriesRequiredToBeConsumed));
//                burnedCalories.setText(df.format(caloriesRequiredToBeBurned));
//            }
//            catch (Exception ex)
//            {
//                Log.wtf("There is no plan","!");
//            }
//        }
        /////////////

        //Defining the AlarmReceiver to update the Database everyday at midnight
        Intent intent1 = new Intent(this, AlarmReceiver.class);
        intent1.putExtra("uid", userid);
        intent1.putExtra("planid", planid);
        intent1.putExtra("requirements",requirements.getText().toString());
        Calendar updateTime = Calendar.getInstance();
        updateTime.setTimeZone(TimeZone.getTimeZone("GMT"));
        updateTime.set(Calendar.HOUR_OF_DAY, 12);
        updateTime.set(Calendar.MINUTE, 0);
        updateTime.set(Calendar.SECOND,0);
        Date milliseconds = updateTime.getTime();
        long millis = milliseconds.getTime();
//        boolean isWorking = (PendingIntent.getBroadcast(this, 5555,
//                intent1, PendingIntent.FLAG_NO_CREATE) != null);//just changed the flag
//        Log.wtf("Checking: ", "Alarm is " + (isWorking ? "" : "not") + " working...");
//        //if(!isWorking) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 5555,
                intent1, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, millis, AlarmManager.INTERVAL_DAY, pendingIntent);
        //}
        ///////////

        //Defining Bluetooth Activity where you can connect to a smart Watch
        Bluetooth = findViewById(R.id.BluetoothConnection);
        Bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BLEConnection.class);
                startActivity(intent);
            }
        });
        //////////////

        //Defining the measurements button were you can see all the measurements in detail
        Meas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DetailedMeasurements.class);
                startActivity(intent);
            }
        });
        /////////////

        //ResetFunctionality
        resetPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                mydb.getUserDao(feature2.this).update(user);
                Intent newIntent = new Intent(feature2.this, feature1.class);
                newIntent.putExtra("uid", userid);
                newIntent.putExtra("planid", planid);
                newIntent.putExtra("flag", 1);
                startActivity(newIntent);
            }
        });
        //////////
        double progress = 0;
        //Getting the progress done till now
        if(plan.type.equals("Maintain Weight"))
        {
            progressbar.setVisibility(View.GONE);
            donutProgress.setVisibility(View.GONE);
            maintainText.setVisibility(View.VISIBLE);
            double NewWeight = user.weight;
            double originalWeight = plan.getCurrentWeight();
            double difference = NewWeight - originalWeight;
            if(difference>0) {
                maintainText.setText("You have gained "+df.format(difference)+" Kgs.");
            }
            else if(difference==0)
            {
                maintainText.setText("You have maintained your original weight of "+originalWeight+" Kgs.");
            }
            else
            {
                maintainText.setText("You have lost "+df.format(difference)+" Kgs.");
            }
        }
        else
            {
                progress = FormulaUtils.calculatePercentage(user.getWeight(), plan.currentWeight, plan.amount, plan.type);
                try {
                    donutProgress.setDonut_progress(String.valueOf(progress));
                } catch (Exception e) {
                    donutProgress.setDonut_progress("0");
                }
                donutProgress.setProgress((float) progress); }
        /////////////////

        //Checking if Plan Duration have finished or Progress have been successfully met
        if (progress >= 100 || plan.nbOfDays <= 0) {
            if (progress >= 100)
            {
                user.setWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                mydb.getUserDao(feature2.this).update(user);
                Intent newIntent = new Intent(feature2.this, feature1.class);
                newIntent.putExtra("uid", userid);
                newIntent.putExtra("planid", planid);
                newIntent.putExtra("flag", 1);
                startActivity(newIntent);
                finish();
            }
            else
                {
                user.setWeight(FormulaUtils.reCalculateWeight(plan.getCurrentWeight(), Double.parseDouble(requirements.getText().toString())));
                mydb.getUserDao(feature2.this).update(user);
                Intent newIntent = new Intent(feature2.this, feature1.class);
                newIntent.putExtra("uid", userid);
                newIntent.putExtra("planid", planid);
                newIntent.putExtra("flag", -1);
                startActivity(newIntent);
                finish();
            }
        }
        ///////////////

        //Adding Meals
        addfood.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = new LinearLayout(feature2.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsLeft.setMargins(60, 60, 0, 0);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(600,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsRight.setMargins(30, 60, 0, 0);
                TextView foodView = new TextView(feature2.this);
                foodView.setText("Add Food");
                foodView.setLayoutParams(paramsLeft);
                foodView.setTextColor(Color.BLACK);


                final Spinner myspinner = new Spinner(feature2.this);
                myspinner.setLayoutParams(paramsRight);
                myspinner.setTag("myspinner");

                List<Food> foodList = mydb.getFoodDao(feature2.this).getAll();
                List<String> foodSpinner = new ArrayList<String>();
                for (Food food : foodList) {
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

                TextView RatioText = new TextView(feature2.this);
                RatioText.setText("");
                RatioText.setLayoutParams(paramsLeft);

                final EditText quantityText = new EditText(feature2.this);
                quantityText.setText("1");
                quantityText.setTag("quantityText");
                quantityText.setLayoutParams(paramsRight);
                quantityText.setBackgroundResource(0);

                LinearLayout linearLayout3 = new LinearLayout(feature2.this);
                linearLayout3.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout linearLayout4 = new LinearLayout(feature2.this);
                linearLayout4.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout linearLayout5 = new LinearLayout(feature2.this);
                linearLayout5.setOrientation(LinearLayout.HORIZONTAL);

                LinearLayout linearLayout6 = new LinearLayout(feature2.this);
                linearLayout6.setOrientation(LinearLayout.HORIZONTAL);

                final ImageView Decision = new ImageView(feature2.this);
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
                        String FoodType = pickedFood.getFoodType();
                        if(FoodType.equals("Breakfast"))
                        {
                            BreakfastAverageCalories = BreakfastAverageCalories - (int)caloriesUsed;
                        }
                        else if(FoodType.equals("Lunch"))
                        {
                            LunchAverageCalories = LunchAverageCalories - (int)caloriesUsed;
                        }
                        else if(FoodType.equals("Dinner"))
                        {
                            DinnerAverageCalories = DinnerAverageCalories - (int)caloriesUsed;
                        }
                        else
                        {
                            Calendar currentTime = Calendar.getInstance();
                            int currentHourIn24Format = currentTime.get(Calendar.HOUR_OF_DAY);
                            if(currentHourIn24Format>14)
                            {
                                DinnerAverageCalories = DinnerAverageCalories - (int)caloriesUsed;
                            }
                            else
                            {
                                BreakfastAverageCalories = BreakfastAverageCalories - (int)caloriesUsed;
                            }
                        }
                        DecimalFormat df = new DecimalFormat("#.##");
                        double newRequirements = Double.parseDouble(requirements.getText().toString()) - caloriesUsed;
                        if(plan.type.equals("Maintain Weight"))
                        {
                            if(newRequirements<0)
                            {
                                AlertDialog.Builder a_builder1 = new AlertDialog.Builder(feature2.this);
                                a_builder1.setMessage("You have went over the consuming requirements and we advice to workout a little bit!")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert1 = a_builder1.create();
                                alert1.setTitle("Alert");
                                alert1.show();
                            }
                            else
                            {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(feature2.this);
                                a_builder.setMessage("You still have this amount of Calories to be consumed : " + df.format(newRequirements))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Reminder");
                                alert.show();
                            }
                        }
                        else if(plan.type.equals("Gain Weight"))
                        {
                            if(newRequirements<0)
                            {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(feature2.this);
                                a_builder.setMessage("You have exceeded the consumed target by " + df.format(newRequirements*-1))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Reminder");
                                alert.show();
                            }
                            else {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(feature2.this);
                                a_builder.setMessage("You still have this amount of Calories to be consumed " + df.format(newRequirements))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Reminder");
                                alert.show();
                            }
                        }
                        else
                        {
                            if(newRequirements<0)
                            {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(feature2.this);
                                a_builder.setMessage("You have reached the consumed target now please look after the burned target")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Reminder");
                                alert.show();
                            }
                            else
                            {
                                AlertDialog.Builder a_builder = new AlertDialog.Builder(feature2.this);
                                a_builder.setMessage("You still have this amount of Calories to be consumed " + df.format(newRequirements))
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                AlertDialog alert = a_builder.create();
                                alert.setTitle("Reminder");
                                alert.show();
                            }
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat("Requirements",(float)newRequirements);
                        editor.apply();
                        requirements.setText(df.format(newRequirements));
                    }
                });

                final ProgressBar pb = new ProgressBar(getApplicationContext(), null, android.R.attr.progressBarStyleHorizontal);
                LayerDrawable ld = (LayerDrawable) pb.getProgressDrawable();
                final Drawable progressDrawable = ld.findDrawableByLayerId(android.R.id.progress);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,40);
                params.gravity = Gravity.CENTER_HORIZONTAL
                        | Gravity.CENTER_VERTICAL;
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_HORIZONTAL
                        | Gravity.CENTER_VERTICAL;
                pb.setLayoutParams(params );
                Decision.setLayoutParams(params1);
                mainLayout.addView(linearLayout);
                mainLayout.addView(linearLayout2);
                mainLayout.addView(linearLayout5);
                mainLayout.addView(linearLayout4);
                linearLayout.addView(foodView);
                linearLayout.addView(myspinner);
                linearLayout2.addView(quantityView);
                linearLayout2.addView(quantityText);
                linearLayout4.addView(submitFood);
                linearLayout5.addView(RatioText);

                if(plan.type.equals("Lose Weight"))
                {
                    linearLayout3.addView(pb);
                    linearLayout6.addView(Decision);
                    mainLayout.addView(linearLayout3);
                    mainLayout.addView(linearLayout6);
                }
                myspinner.setAdapter(adapter);
                myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        Food pickedFood = mydb.getFoodDao(feature2.this).findByName(myspinner.getSelectedItem().toString());
                        double caloriesUsed = pickedFood.getCalorieValue() * Double.parseDouble(quantityText.getText().toString());
                        int progress = (int) ( caloriesUsed * 100 / BreakfastAverageCalories);
                        float[] hsv = {
                                (float)caloriesUsed * 360 / BreakfastAverageCalories, 1, 1
                        };
                        int color = Color.HSVToColor(hsv);
                        int GlobalAverage = 0;
                        String FoodType = pickedFood.getFoodType();
                        if(FoodType.equals("Breakfast"))
                        {
                            GlobalAverage = BreakfastAverageCalories;
                        }
                        else if(FoodType.equals("Dinner"))
                        {
                            GlobalAverage = DinnerAverageCalories;
                        }
                        else if(FoodType.equals("Lunch"))
                        {
                            GlobalAverage = LunchAverageCalories;
                        }
                        else
                        {
                            Calendar currentTime = Calendar.getInstance();
                            int currentHourIn24Format = currentTime.get(Calendar.HOUR_OF_DAY);
                            if(currentHourIn24Format>14)
                            {
                                GlobalAverage = DinnerAverageCalories;
                                FoodType = "Dinner";
                            }
                            else
                            {
                                GlobalAverage = BreakfastAverageCalories;
                                FoodType = "Breakfast";
                            }
                        }

                        if(plan.type.equals("Lose Weight"))
                        {
                            progressDrawable.setColorFilter(color, PorterDuff.Mode.SRC);
                            pb.setProgress(progress);
                            if (caloriesUsed > GlobalAverage) {
                                RatioText.setText("This meal has more Calorific Value than the left average value for" + FoodType + "which is " + GlobalAverage);
                                Decision.setBackgroundResource(R.drawable.chefnoobese);

                            } else {
                                RatioText.setText("This meal has less Calorific Value than the left average value for" + FoodType + "which is " + GlobalAverage);
                                Decision.setBackgroundResource(R.drawable.okchef);
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView)
                    {

                    }

                });
            }
        });
        /////////////////

        //Add Exercises
        addex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout linearLayout = new LinearLayout(feature2.this);

                LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(300,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsLeft.setMargins(60, 60, 0, 0);
                LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(600,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsRight.setMargins(30, 60, 0, 0);

                TextView exView = new TextView(feature2.this);
                exView.setText("Add Exercise");
                exView.setLayoutParams(paramsLeft);
                exView.setTextColor(Color.BLACK);


                final Spinner myspinner = new Spinner(feature2.this);
                myspinner.setLayoutParams(paramsRight);
                myspinner.setTag("myspinner");

                List<Exercise> exerciseList = mydb.getExerciseDao(feature2.this).getAll();
                List<String> exSpinner = new ArrayList<String>();
                for (Exercise ex : exerciseList) {
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
                LinearLayout linearLayout4 = new LinearLayout(feature2.this);

                linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
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
                        double caloriesGained = FormulaUtils.CalculateCaloriesFromMET(plan.getCurrentWeight(), pickedEx.getMetValue(), plan.getWorkOutSessionDuration());
                        DecimalFormat df = new DecimalFormat("#.##");
                        caloriesBurned = caloriesBurned + caloriesGained;
                        AlertDialog.Builder a_builder1 = new AlertDialog.Builder(feature2.this);
                        a_builder1.setMessage("Your consumed calories target have increased by !" + caloriesGained)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert1 = a_builder1.create();
                        alert1.setTitle("Alert");
                        alert1.show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat("Requirements",(float)Double.parseDouble(requirements.getText().toString()) + (float)caloriesGained);
                        editor.putFloat("BurnedRequirements",(float)Double.parseDouble(burnedCalories.getText().toString()) - (float)caloriesGained);
                        editor.apply();
                        burnedCalories.setText(df.format(Double.parseDouble(burnedCalories.getText().toString()) - caloriesGained));
                        requirements.setText(df.format(Double.parseDouble(requirements.getText().toString()) + caloriesGained));
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
    ///////////////////
}



