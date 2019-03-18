package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main7Activity extends AppCompatActivity {

    MediaPlayer mySound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        mySound = MediaPlayer.create(this, R.raw.song);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mySound.release();
    }

    public void activeButtonStart(View view) {
        mySound.start();
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
