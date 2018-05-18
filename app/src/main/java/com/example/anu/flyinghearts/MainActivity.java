package com.example.anu.flyinghearts;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView startLabel;
    private TextView scoreLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;
    private ImageView blue;

    private Handler handler = new Handler();
    private Timer timer = new Timer();

    private boolean status_flg = false;
    private boolean action_flg = false;


    private int boxY;
    private int blackX;
    private int blackY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blueX;
    private int blueY;


    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        startLabel = (TextView) findViewById(R.id.startLabel);
        box = (ImageView) findViewById(R.id.box);
        black = (ImageView) findViewById(R.id.black);
        pink = (ImageView) findViewById(R.id.pink);
        orange = (ImageView) findViewById(R.id.orange);
        blue = (ImageView) findViewById(R.id.blue) ;


        WindowManager w = getWindowManager();
        Display disp = w.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;


        orange.setX(-80);
        orange.setY(-80);
        pink.setX(-80);
        pink.setY(-80);
        black.setX(-80);
        black.setY(-80);
        blue.setX(-80);
        blue.setY(-80);

    }

    public void changePos()
    {
        hitCheck();

        orangeX -= 12;
        if(orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (int) Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        blackX -= 18;
        if(blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        pinkX -= 20;
        if(pinkX < 0) {
            pinkX = screenWidth + 5000;
            pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        blueX -= 15;
        if(blueX < 0) {
            blueX = screenWidth + 2000;
            blueY = (int) Math.floor(Math.random() * (frameHeight - blue.getHeight()));
        }
        blue.setX(blueX);
        blue.setY(blueY);

        if(action_flg == true)
            boxY -= 20;
        else
            boxY += 20;

        if(boxY < 0)
            boxY = 0;

        if(boxY > frameHeight - boxSize)
            boxY = frameHeight - boxSize;
        box.setY(boxY);

        scoreLabel.setText("Score:" + score);
    }

    public void hitCheck()
    {
        int orangeCentreX = orangeX + orange.getWidth()/2;
        int orangeCentreY = orangeY + orange.getHeight()/2;

        if(0 <= orangeCentreX && orangeCentreX <= boxSize && boxY <= orangeCentreY && orangeCentreY <= boxY+boxSize)
        {
            orangeX = -10;
            score += 20;
        }

        int pinkCentreX = pinkX + pink.getWidth()/2;
        int pinkCentreY = pinkY + pink.getHeight()/2;

        if(0 <= pinkCentreX && pinkCentreX <= boxSize && boxY <= pinkCentreY && pinkCentreY <= boxY+boxSize)
        {
            pinkX = -10;
            score += 100;
        }

        int blueCentreX = blueX + blue.getWidth()/2;
        int blueCentreY = blueY + blue.getHeight()/2;

        if(0 <= blueCentreX && blueCentreX <= boxSize && boxY <= blueCentreY && blueCentreY <= boxY+boxSize)
        {
            blueX = -10;
            score += 60;
        }

        int blackCentreX = blackX + black.getWidth()/2;
        int blackCentreY = blackY + black.getHeight()/2;

        if(0 <= blackCentreX && blackCentreX <= boxSize && boxY <= blackCentreY && blackCentreY <= boxY+boxSize)
        {
            timer.cancel();
            timer = null;

            Intent intent = new Intent(getApplicationContext(),result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
        }
    }

    public boolean onTouchEvent(MotionEvent me)
    {
        if(status_flg == false)
        {
            status_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = (int)box.getY();
            boxSize = box.getHeight();


            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask(){

                public void run(){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

        }
        else
        {
            if (me.getAction() == MotionEvent.ACTION_DOWN)
                action_flg = true;

            else if (me.getAction() == MotionEvent.ACTION_UP)
                action_flg = false;
        }

        return true;
    }
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            switch(event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
