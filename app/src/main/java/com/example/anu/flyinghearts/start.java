package com.example.anu.flyinghearts;

/**
 * Created by anu on 18/5/18.
 */

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;


public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startGame(View view )
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class)
        );
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
