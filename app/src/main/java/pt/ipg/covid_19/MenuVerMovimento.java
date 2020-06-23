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

public class MenuVerMovimento extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String ID_MOVIMENTO = "ID_MOVIMENTO";
    public static final int ID_CURSOR_LOADER_MOVIMENTOS = 0;
    private AdaptadorMovimentos adaptadorMovimentos;
    private RecyclerView recyclerViewMovimento;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_movimento);

        recyclerViewMovimento = (RecyclerView) findViewById(R.id.recyclerViewMovimentos);
        adaptadorMovimentos = new AdaptadorMovimentos(this);
        recyclerViewMovimento.setAdapter(adaptadorMovimentos);
        recyclerViewMovimento.setLayoutManager(new LinearLayoutManager(this));

        adaptadorMovimentos.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_MOVIMENTOS, null, this);

    }

    public void atualizaOpcoesMenu() {
        MovimentoModel movimentoModel = adaptadorMovimentos.getMovimentoSelecionado();

        boolean mostraAlterarEliminar = (movimentoModel != null);
        menu.findItem(R.id.action_more_MovimentoInserir).setVisible(mostraAlterarEliminar);

    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_MOVIMENTOS, null, this);
        super.onResume();
    }




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

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_MOVIMENTO, BdTabelaMovimento.TODOS, null, null, BdTabelaMovimento.CAMPO_HORA_ENTRADA);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorMovimentos.setCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorMovimentos.setCursor(null);
    }

}


