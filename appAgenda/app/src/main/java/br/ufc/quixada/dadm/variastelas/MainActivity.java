package br.ufc.quixada.dadm.variastelas;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import br.ufc.quixada.dadm.variastelas.controller.AgendaController;
import br.ufc.quixada.dadm.variastelas.transactions.Agenda;
import br.ufc.quixada.dadm.variastelas.transactions.Constants;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class MainActivity extends AppCompatActivity {

    int selected;
    ArrayAdapter adapter;
    ListView listViewContatos;

    ProgressBar progressBar;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private final String TEMPKEY = "myagenda";
    private Agenda agenda;
    private static int id = 0;
    private static String nome = "Pessoa";
    private static int idade = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(TEMPKEY);

        agenda = new Agenda(nome, id, idade);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey().equals(TEMPKEY)) {
                    Agenda temp = dataSnapshot.getValue(Agenda.class);
                    if(temp != null) {
                        Log.d("FROM_FIREBASE", temp.toString());
                        agenda.setId(temp.getId());
                        agenda.setIdade(temp.getIdade());
                        agenda.setNome(temp.getNome());
                        agenda.setListaTelefone(new ArrayList<Contato>());
                        if(temp.getListaTelefone() != null) {
                            for(Contato c : temp.getListaTelefone()) {
                                Contato aux = new Contato(c.getNome(), c.getTelefone(), c.getEndereco());
                                agenda.getListaTelefone().add(aux);
                            }
                            adapter.addAll(agenda.getListaTelefone());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        selected = -1;

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                agenda.getListaTelefone() );

        listViewContatos = ( ListView )findViewById( R.id.listViewContatos );
        listViewContatos.setAdapter( adapter );
        listViewContatos.setSelector( android.R.color.holo_blue_light );

        listViewContatos.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(MainActivity.this,
                        "" + agenda.getListaTelefone().get( position ).toString(),
                        Toast.LENGTH_SHORT).show();
                selected = position;
            }
        });

        progressBar = findViewById( R.id.progressBar );
        progressBar.setIndeterminate( true );
        progressBar.setVisibility( View.VISIBLE );
    }

    public void updateListaContatos(Agenda agenda){
        progressBar.setVisibility( View.INVISIBLE );

        /*List<Contato> lista = agenda.getListaTelefone();
        for( Contato c: lista ) listaContatos.add( c );*/

        adapter.notifyDataSetChanged();
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

    private void apagarItemLista() {
        if(agenda.getListaTelefone().size() > 0) {
            agenda.getListaTelefone().remove(selected);
            databaseReference.setValue(agenda);
            Contato willBeRemove = (Contato) adapter.getItem(selected);
            adapter.remove(willBeRemove);
            adapter.notifyDataSetChanged();
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
        //Intent intent2 = new Intent( this, "br.ufc.quixada.dadm.variastelas.ContactActivity" );

        Contato contato = agenda.getListaTelefone().get( selected );

        intent.putExtra( "id", contato.getId() );
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

          Contato contato = new Contato( nome, telefone, endereco );
          agenda.getListaTelefone().add(contato);
          databaseReference.setValue(agenda);

          adapter.add(contato);
          adapter.notifyDataSetChanged();

      } else if( requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );
          int idEditar = (int)data.getExtras().get( "id" );

          for(Contato contato: agenda.getListaTelefone()) {
              if(contato.getId() == idEditar){
                  contato.setNome(nome);
                  contato.setEndereco(endereco);
                  contato.setTelefone(telefone);
              }
          }
          databaseReference.setValue(agenda);
          //agendaController.editContato(idEditar, nome, telefone, endereco);

          adapter.notifyDataSetChanged();

      } //Retorno da tela de contatos com um conteudo para ser adicionado
        //Na segunda tela, o usuario clicou no botão ADD
      else if( resultCode == Constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado",
                    Toast.LENGTH_SHORT).show();
        }

    }

}
