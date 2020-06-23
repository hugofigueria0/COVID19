package pt.ipg.covid_19;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MenuVerInfectados extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int ID_CURSOR_LOADER_INFECTADO = 0;

    private AdaptadorInfectado adaptadorInfectado;
    private RecyclerView recyclerViewInfectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_infectados);

        recyclerViewInfectado = (RecyclerView) findViewById(R.id.recyclerViewInfectado);
        adaptadorInfectado = new AdaptadorInfectado(this);
        recyclerViewInfectado.setAdapter(adaptadorInfectado);
        recyclerViewInfectado.setLayoutManager(new LinearLayoutManager(this));

        adaptadorInfectado.setCursor(null);

        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_INFECTADO, null, this);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.infectado, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_Infectado) {
            Intent intent = new Intent(this, MenuAdicionarInfectado.class);
            startActivity(intent);
            return true;
    }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_INFECTADO, BdTabelaInfectados.TODOS, null, null, BdTabelaInfectados.CAMPO_INFECTADO);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adaptadorInfectado.setCursor(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        adaptadorInfectado.setCursor(null);

    }
}
