package com.example.igor.telega;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = findViewById(R.id.bestScore);
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
    }
}
