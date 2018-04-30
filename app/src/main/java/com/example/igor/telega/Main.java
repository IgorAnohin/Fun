package com.example.igor.telega;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class Main extends AppCompatActivity {
    private TextView count;
    private TextView bestcount;
    private int best_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = findViewById(R.id.currentScore);
        bestcount = findViewById(R.id.bestScore);

        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        best_score = prefs.getInt("key", 0);
        String sc = Integer.toString(best_score);
        bestcount.setText(sc);
        count.setText("0");

        TextView rule = findViewById(R.id.rule);

        Button start_button = findViewById(R.id.start_button);

        start_button.startAnimation(getAnimation(false));
        rule.startAnimation(getAnimation(true));

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main.this, game.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private Animation getAnimation(boolean direct) {
        Animation animation = direct ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(4000);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        return animation;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        String result = data.getStringExtra("count");
        count.setText(result);

        if (best_score < Integer.parseInt(result)) {
            SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("key", Integer.parseInt(result)).apply();
            editor.commit();
            bestcount.setText(result);
        }
    }
}
