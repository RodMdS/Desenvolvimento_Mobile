package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main5Activity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        activeButtonWithLongClick();
    }

    public void activeButtonWithLongClick() {
        textView = (TextView) findViewById(R.id.txtView);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Você pressionou por tempo suficiente",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Você não pressionou por tempo suficiente",
                       Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, Main6Activity.class);
        startActivity(intent);
    }
}
