package com.example.igor.telega;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class game extends AppCompatActivity {

    private static final int WIDTH_BUTTON = 120;
    private static final int HEIGHT_BUTTON = 120;
    private static final int MIN_MARGIN = 20;
    private static final int [] bad_pictures = {R.drawable.google, R.drawable.skype, R.drawable.viber};

    private int count;
    private int countCLICK;

    Boolean end;
    RelativeLayout lr;
    Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        lr = (RelativeLayout) findViewById(R.id.buttons_filed);

        ViewTreeObserver observer = lr.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                init();
                lr.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        h = new  Handler() {
            public void handleMessage(android.os.Message msg) {
                create_buttons(lr.getHeight(), lr.getWidth());
            }
        };
    }

    protected void init() {
        final int a= lr.getHeight();
        final int b = lr.getWidth();
        Toast.makeText(this,""+a+" "+b,Toast.LENGTH_LONG).show();
        ( (TextView) findViewById(R.id.test_view) ).setText(a + " " + b);
        create_buttons(lr.getHeight(), lr.getWidth());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                h.sendEmptyMessage(1);
            }
        }, 1000, 1000);

    }

    private void create_buttons(final int height, final int width) {
        Random rand = new Random();

        int i = 0;
        while (i++ < 2) {
            final Button new_button = new Button(this);

            new_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    RelativeLayout button_layout = (RelativeLayout) view.getParent();

                    if (button_layout != null) {
                        Button b = (Button) view;
                        if (b.getBackground().getConstantState().equals
                                (getResources().getDrawable(R.drawable.telegram).getConstantState())) {
                            count++;
                            countCLICK--;
                            b.clearAnimation();
                            button_layout.removeView(view);
                        } else if (!b.getBackground().getConstantState().
                                equals(getResources().getDrawable(R.drawable.blue_circle).getConstantState())) {
                            returnToMain();
                        } else {
                            b.clearAnimation();
                            button_layout.removeView(view);
                        }
                    }
                }
            });


            int temp_w = rand.nextInt(width) - WIDTH_BUTTON - MIN_MARGIN;
            temp_w = temp_w < 0 ? MIN_MARGIN : temp_w;

            int temp_h = rand.nextInt(height) - HEIGHT_BUTTON - MIN_MARGIN;
            temp_h = temp_h < 0 ? MIN_MARGIN : temp_h;

            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(WIDTH_BUTTON, HEIGHT_BUTTON);

            int index = (countCLICK == 0) ? 0 : rand.nextInt(3);
            if (index == 0) countCLICK++;

            switch (index) {
                case 0:
                    new_button.setBackgroundResource(R.drawable.telegram);
                    break;
                case 1:
                    new_button.setBackgroundResource(R.drawable.blue_circle);
                    break;
                default:
                    new_button.setBackgroundResource(bad_pictures[rand.nextInt(rand.nextInt(3))]);
            }


            layoutParams.setMargins(temp_w, temp_h, MIN_MARGIN, MIN_MARGIN);
            lr.addView(new_button, layoutParams);

            final Animation anim = AnimationUtils.loadAnimation(new_button.getContext(), R.anim.first);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (new_button.getBackground().getConstantState().equals
                            (getResources().getDrawable(R.drawable.telegram).getConstantState()))
                        returnToMain();
                }
            });

            new_button.startAnimation(anim);
        }
    }

    private void returnToMain() {
        Intent intent = new Intent();
        intent.putExtra("count", Integer.toString(count));
        setResult(RESULT_OK, intent);
        finish();
    }

}
