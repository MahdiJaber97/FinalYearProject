package com.example.bleh.myapplication.Utils1;

import java.text.DecimalFormat;
import java.util.Calendar;

public class FormulaUtils {

    public static String calculateBmr(String sex, double weight, double height, int birthday){
        double bmr;
        if (sex.equals("male"))
        {
            bmr = 66.5 + ( 13.75 * weight ) + ( 5.003 * height) - ( 6.755 * ( birthday - Calendar.getInstance().get(Calendar.YEAR)));
        }else{
            bmr = 655.1  + ( 4.35 * weight ) + ( 4.7  * height) - ( 4.7  * ( birthday - Calendar.getInstance().get(Calendar.YEAR)));
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(bmr);
    }

    public static double calculatePercentage(double weight, double currentWeight, double goalWeight,String type){
        double percentage;
        if (type.equals("Gain Weight"))
        {
            percentage = ((currentWeight-weight) / goalWeight) *100;
        }else{
            percentage = ((weight-currentWeight) / goalWeight) *100;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(percentage));
    }

    public static String CalulcateDailyRequirements(int workout, double bmr)
    {
        double dailyRequirement = 0;
        if (workout==0)
            dailyRequirement = bmr * 1.2;
        else if (workout<3 && workout>0)
            dailyRequirement = bmr * 1.375;
        else if (workout <6 && workout >2)
            dailyRequirement = bmr * 1.55;
        else if (workout <8 && workout >6)
            dailyRequirement = bmr * 1.725;
        else
            dailyRequirement = bmr * 1.9;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(dailyRequirement);
    }

    public static Double CalculateCaloriesFromMET (double weight, double met,int timeOfActivity)
    {
        return  (met * weight * (double)timeOfActivity)/60;
    }

    public static Double reCalculateWeight (double currentweight, double remainingCalories)
    {
        return currentweight - (remainingCalories * 0.454 / 3500);
    }
}
