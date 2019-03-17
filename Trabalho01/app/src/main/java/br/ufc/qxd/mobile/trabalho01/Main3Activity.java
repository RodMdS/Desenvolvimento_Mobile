package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.option1:
                Toast.makeText(getApplicationContext(),
                        "Option 1",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:
                Toast.makeText(getApplicationContext(),
                        "Option 2",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:
                Toast.makeText(getApplicationContext(),
                        "Option 3",
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }
}
