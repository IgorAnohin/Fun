package com.example.igor.telega;

import android.content.Intent;
import android.os.Build;
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

public class game extends AppCompatActivity {

    private static final int WIDTH_BUTTON = 120;
    private static final int HEIGHT_BUTTON = 120;
    private static final int MIN_MARGIN = 20;

    private int count;
    private int countCLICK;

    Boolean end;
    RelativeLayout lr;
//    Handler h;

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



    }

    protected void init() {
        int a= lr.getHeight();
        int b = lr.getWidth();
        Toast.makeText(this,""+a+" "+b,Toast.LENGTH_LONG).show();
        ( (TextView) findViewById(R.id.test_view) ).setText(a + " " + b);
        create_buttons(a,b);

    }

    private void create_buttons(final int height, final int width) {
        Random rand = new Random();

        end = false;
        int i = 0;
        while (i++ < 2) {
            final Button new_button = new Button(this);

            new_button.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                            create_buttons(height, width);
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


            int temp_w = rand.nextInt(width)-WIDTH_BUTTON-MIN_MARGIN;
            temp_w = temp_w < 0 ? MIN_MARGIN : temp_w;

            int temp_h = rand.nextInt(height)-HEIGHT_BUTTON-MIN_MARGIN;
            temp_h = temp_h < 0 ? MIN_MARGIN : temp_h;

            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(WIDTH_BUTTON,HEIGHT_BUTTON);

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
                    new_button.setBackgroundResource(R.drawable.skype);
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
            end = true;
        }
    }

    private void returnToMain() {
        Intent intent = new Intent();
        intent.putExtra("count", Integer.toString(count));
        setResult(RESULT_OK, intent);
        finish();
    }

}
