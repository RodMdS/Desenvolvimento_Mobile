package br.ufc.quixada.dadm.variastelas;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.dadm.variastelas.transactions.AppDatabase;
import br.ufc.quixada.dadm.variastelas.transactions.Constants;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class MainActivity extends AppCompatActivity {

    int selected;
    List<Contato> listaContatos = new ArrayList<>();
    ExpandableListAdapter adapter;
    ExpandableListView listViewContatos;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "agenda")
                .allowMainThreadQueries()
                .build();

        selected = -1;

        listaContatos = db.contatoDao().getAll();
        adapter = new ExpandableListAdapter( this, listaContatos );

        listViewContatos = findViewById( R.id.expandableListView );
        listViewContatos.setAdapter( adapter );
        listViewContatos.setSelector( android.R.color.holo_blue_light );

        listViewContatos.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id)
            {
                selected = groupPosition;
                return false;
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main_activity, menu );
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected( MenuItem item ) {

        switch(item.getItemId())
        {
            case R.id.add:
                clicarAdicionar();
                break;
            case R.id.edit:
                clicarEditar();
                break;
            case R.id.delete:
                apagarItemLista();
                break;
            case R.id.settings:
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    private void apagarItemLista(){

        if( listaContatos.size() > 0 ){
            db.contatoDao().delete(listaContatos.get(selected));
            listaContatos = db.contatoDao().getAll();
            adapter = new ExpandableListAdapter( this, listaContatos );
            listViewContatos.setAdapter(adapter);
        } else {
            selected = -1;
        }

    }

    public void clicarAdicionar(){
        Intent intent = new Intent( this, ContactActivity.class );
        startActivityForResult( intent, Constants.REQUEST_ADD );
    }

    public void clicarEditar(){

        Intent intent = new Intent( this, ContactActivity.class );

        Contato contato = listaContatos.get(selected);

        intent.putExtra( "nome", contato.getNome() );
        intent.putExtra( "telefone", contato.getTelefone() );
        intent.putExtra( "endereco", contato.getEndereco() );

        startActivityForResult( intent, Constants.REQUEST_EDIT );
    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

      if( requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );

          Contato contato = new Contato();
          contato.setNome(nome);
          contato.setTelefone(telefone);
          contato.setEndereco(endereco);

          db.contatoDao().insertAll(contato);
          listaContatos = db.contatoDao().getAll();
          adapter = new ExpandableListAdapter( this, listaContatos );
          listViewContatos.setAdapter(adapter);

      } else if( requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );
          int idEditar = (int)data.getExtras().get( "id" );

          for( Contato contato : listaContatos ){
              if( contato.getId() == idEditar ){
                  contato.setNome( nome );
                  contato.setEndereco( endereco );
                  contato.setTelefone( telefone );

                  db.contatoDao().update(contato);
                  listaContatos = db.contatoDao().getAll();
                  adapter = new ExpandableListAdapter( this, listaContatos );
                  listViewContatos.setAdapter(adapter);
              }
          }

      }
      else if( resultCode == Constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado",
                    Toast.LENGTH_SHORT).show();
        }

    }








}
