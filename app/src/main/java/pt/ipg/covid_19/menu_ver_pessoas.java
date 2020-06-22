package pt.ipg.covid_19;

import android.content.Context;
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

public class menu_ver_pessoas extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String ID_PESSOAS = "ID_PESSOAS";

    public static final int ID_CURSOR_LOADER_PESSOAS = 0;

    private AdaptadorPessoas adaptadorPessoas;
    private RecyclerView recyclerViewPessoas;
    private Menu menu;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_pessoas);


        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);
        recyclerViewPessoas = (RecyclerView) findViewById(R.id.recyclerViewPessoas);
        adaptadorPessoas = new AdaptadorPessoas(this);
        recyclerViewPessoas.setAdapter(adaptadorPessoas);
        recyclerViewPessoas.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adaptadorPessoas.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);

    }

    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(ID_CURSOR_LOADER_PESSOAS, null, this);
        super.onResume();
    }

    public void atualizaOpcoesMenu() {
        PessoasModel pessoasModel = adaptadorPessoas.getPessoaSelecionado();

        boolean mostraAlterarEliminar = (pessoasModel != null);
        menu.findItem(R.id.action_moreEdit).setVisible(mostraAlterarEliminar);
        menu.findItem(R.id.action_moreDelete).setVisible(mostraAlterarEliminar);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nomebar, menu);

        this.menu = menu;
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more) {
            Intent intent = new Intent(this, MenuPessoas.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_moreEdit) {
            Intent intent = new Intent(this, MenuPessoasEditar.class);

            intent.putExtra(ID_PESSOAS, adaptadorPessoas.getPessoaSelecionado().getId());

            startActivity(intent);
        }else if(id == R.id.action_moreDelete) {
            Intent intent = new Intent(this, MenuPessoasEliminar.class);

           intent.putExtra(ID_PESSOAS, adaptadorPessoas.getPessoaSelecionado().getId());

            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PESSOAS, BdTabelaPessoas.TODOS, null, null, BdTabelaPessoas.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        adaptadorPessoas.setCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adaptadorPessoas.setCursor(null);

    }
}
