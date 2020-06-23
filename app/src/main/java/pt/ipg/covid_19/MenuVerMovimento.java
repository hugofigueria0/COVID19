package pt.ipg.covid_19;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MenuVerMovimento extends AppCompatActivity {

    public static final String ID_Movimento = "ID_MOVIMENTO";

    public static final int ID_CURSOR_LOADER_MOVIMENTOS = 0;
    private AdaptadorMovimentos adaptadorMovimentos;
    private RecyclerView recyclerViewMovimentos;

    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_movimento);


        recyclerViewMovimentos = (RecyclerView) findViewById(R.id.recyclerViewMovimentos);
        adaptadorMovimentos = new AdaptadorMovimentos(this);
        recyclerViewMovimentos.setAdapter(adaptadorMovimentos);
        recyclerViewMovimentos.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }


   /* @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_MOVIMENTOS, null, this);
        super.onResume();
    }*/

    /*public void atualizaOpcoesMenu() {
        MovimentoModel movimentoModel = adaptadorMovimentos.getMovimentoelecionado();

        boolean mostraAlterarEliminar = (movimentoModel != null);
        menu.findItem(R.id.action_more_Movimento).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_more_MovimentoInserir).setVisible(mostraAlterarEliminar);

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movimento, menu);

        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_Movimento) {
            Intent intent = new Intent(this, MenuAdicionarMovimento.class);
            startActivity(intent);
            return true;
        }if (id == R.id.action_more_MovimentoInserir) {
            Intent intent = new Intent(this, MenuEditarMovimento.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
