package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton toggleButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        editText = (EditText) findViewById(R.id.editText);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(MainActivity.this,
                        toggleButton.getText(),
                        Toast.LENGTH_SHORT).show();*/

                editText.setText(toggleButton.getText());
            }
        });
    }

    public void nextActivity(View view) {
        Intent intent =
                new Intent(this,
                        Main2Activity.class);
        startActivity(intent);
    }

}
