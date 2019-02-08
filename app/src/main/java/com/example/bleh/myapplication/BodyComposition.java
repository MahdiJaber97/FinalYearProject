package com.example.bleh.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BodyComposition extends AppCompatActivity {
    ImageView image;
    TextView FatsPercentage, MusclesPercentage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_composition);
        float fillProgress = 0.4f; // let's say image is 40% filled
        Bitmap onlyStroke = BitmapFactory.decodeResource(getResources(),R.drawable.body2);
        Bitmap mutableBitmap = onlyStroke.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Bitmap filled = BitmapFactory.decodeResource(getResources(),R.drawable.body1);
        canvas.drawBitmap(onlyStroke, 0f, 0f, null);  // draw just stroke first
        image = findViewById(R.id.body1);
        double fatspercent = fillProgress*100;
        double MusclesPercent = 100.0 - fillProgress*100;
        FatsPercentage = findViewById(R.id.FatsPercentage);
        MusclesPercentage = findViewById(R.id.MusclesPercentage);
        FatsPercentage.setText(Double.toString(fatspercent));
        MusclesPercentage.setText(Double.toString(MusclesPercent));
        canvas.save();
        canvas.clipRect(0f,                                       // left
                canvas.getHeight() - fillProgress * canvas.getHeight(), // top
                canvas.getWidth(),                               // right
                canvas.getHeight()                               // bottom
        );
        canvas.drawBitmap(filled, 0f, 0f, null);      // region of filled image specified by clipRect will now be drawn on top of onlyStroke image
        canvas.restore();
        image.setImageBitmap(mutableBitmap);
    }
}
