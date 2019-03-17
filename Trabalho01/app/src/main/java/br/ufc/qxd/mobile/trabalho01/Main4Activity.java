package br.ufc.qxd.mobile.trabalho01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {

    private Button showPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        activePopupMenu();
    }

    public void activePopupMenu() {
        showPopup = (Button) findViewById(R.id.showPopup);
        showPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(Main4Activity.this, showPopup);

                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(Main4Activity.this,
                                "Você clicou " + item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    public void nextActivity(View view) {
        Intent intent = new Intent(this, Main5Activity.class);
        startActivity(intent);
    }
}
