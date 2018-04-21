package com.example.igor.telega;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

public class game extends AppCompatActivity {

    private static final int WIDTH_BUTTON = 120;
    private static final int HEIGHT_BUTTON = 120;
    private static final int MIN_MARGIN = 20;
    private static String[] buttonState = {"CLICK", "EMPTY", "NO"};


    private int count;
    private int countCLICK;

    Boolean end;
    RelativeLayout lr;

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
            Button new_button = new Button(this);

            new_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout button_layout = (RelativeLayout) view.getParent();
                    if (button_layout != null) {

                        Button b = (Button) view;
                        if (b.getText().equals(buttonState[0])) {
                            count++;
                            countCLICK--;
                            create_buttons(height, width);
                            button_layout.removeView(view);
                        } else if (b.getText().equals(buttonState[2])) {
                            Intent intent = new Intent();
                            intent.putExtra("count", Integer.toString(count));
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                }
            });


            int temp_w = rand.nextInt(width)-WIDTH_BUTTON-MIN_MARGIN;
            temp_w = temp_w < 0 ? MIN_MARGIN : temp_w;

            int temp_h = rand.nextInt(height)-HEIGHT_BUTTON-MIN_MARGIN;
            temp_h = temp_h < 0 ? MIN_MARGIN : temp_h;

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams( WIDTH_BUTTON,HEIGHT_BUTTON);

            new_button.setBackgroundResource(R.drawable.telegram);

            int index = (countCLICK == 0) ? 0 : rand.nextInt(3);
            if (index == 0) countCLICK++;

            new_button.setText(buttonState[index]);

            layoutParams.setMargins(temp_w, temp_h, MIN_MARGIN, MIN_MARGIN);
            lr.addView(new_button, layoutParams);
            end = true;
        }
    }
}
