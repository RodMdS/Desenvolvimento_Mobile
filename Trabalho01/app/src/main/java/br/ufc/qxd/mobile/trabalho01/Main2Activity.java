package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private Spinner spinner;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        activeAutoComplete();
        activeSpinner();
        activeRadioButtons();
    }

    public void activeAutoComplete() {
        String options[] = {"CC", "EC", "SI",
                "ES", "DD", "RC"};

        AutoCompleteTextView autoCompleteTextView =
                (AutoCompleteTextView)
                        findViewById(
                                R.id.autoCompleteTextView);

        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this,
                android.R.layout.simple_dropdown_item_1line,
                        options);

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adp);
    }

    public void activeSpinner() {
        final String[] semesters = {
                "1º semestre",
                "2º semestre",
                "3º semestre",
                "4º semestre",
                "5º semestre",
                "6º semestre",
                "7º semestre",
                "8º semestre",
                "9º semestre",
                "10º semestre",
                "11º semestre",
                "12º semestre",
                "13º semestre",
                "14º semestre",
                "15º semestre"
        };

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item,
                        semesters);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view,
                                       int position, long id) {
                int i = spinner.getSelectedItemPosition();

                Toast.makeText(getApplicationContext(),
                        "Você selecionou " +
                                semesters[i],
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void activeRadioButtons() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,
                                         int checkedId) {
                RadioButton rb = (RadioButton)
                        group.findViewById(checkedId);
                if(null != rb && checkedId > -1) {
                    Toast.makeText(Main2Activity.this,
                            rb.getText(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }
}
