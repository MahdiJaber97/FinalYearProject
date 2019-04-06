package com.example.bleh.myapplication.DB;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Plan {

    @PrimaryKey(autoGenerate = true)
    public int planid;

    public int uid;

    public double planDistribution;

    public int workOutSessionDuration;

    public String type;

    public int nbOfDays;

    public double amount;

    public int progress;

    public double bmr;

    public int workoutPerWeek;

    public double currentWeight;

    public double getPlanDistribution() {
        return planDistribution;
    }

    public void setPlanDistribution(double planDistribution) {
        this.planDistribution = planDistribution;
    }

    public int getWorkOutSessionDuration() {
        return workOutSessionDuration;
    }

    public void setWorkOutSessionDuration(int workOutSessionDuration) {
        this.workOutSessionDuration = workOutSessionDuration;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getWorkoutPerWeek() {
        return workoutPerWeek;
    }

    public void setWorkoutPerWeek(int workoutPerWeek) {
        this.workoutPerWeek = workoutPerWeek;
    }

    public int getPlanid() {
        return planid;
    }

    public void setPlanid(int planid) {
        this.planid = planid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbOfDays() {
        return nbOfDays;
    }

    public void setNbOfDays(int nbOfDays) {
        this.nbOfDays = nbOfDays;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public double getBmr() {
        return bmr;
    }

    public void setBmr(double bmr) {
        this.bmr = bmr;
    }


}
