package pt.ipg.covid_19;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MenuAdicionarInfectado extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_INFECTADO = 0;
    private Spinner spinnerBuscarPessoas, spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adicionar_infectado);

        spinnerBuscarPessoas = (Spinner) findViewById(R.id.spinnerBuscarPessoas);
        spinner = (Spinner) findViewById(R.id.spinnerInfectado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Infectado_ID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        mostraDadosSpinnerPessoas(null);



        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_INFECTADO, null, this);

    }



    private void mostraDadosSpinnerPessoas(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTabelaPessoas.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );

        spinnerBuscarPessoas.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void GuardarInfectado(View view){

        String idInfectado = spinner.getSelectedItem().toString();
        long idPessoa = spinnerBuscarPessoas.getSelectedItemId();


        InfectadoModel infectadoModel = new InfectadoModel();

        infectadoModel.setInfectado(idInfectado);
        infectadoModel.setId_pessoa(idPessoa);

        try {

            this.getContentResolver().insert(CovidContentProvider.ENDERECO_INFECTADO, Converte.infectadosToContentValues(infectadoModel));
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, R.string.OcorreuErro, Toast.LENGTH_SHORT).show();
        }



        finish();
    }

    public void RegressarParaTras(View view){
        Toast.makeText(this, R.string.Regressar, Toast.LENGTH_LONG).show();
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PESSOAS, BdTabelaPessoas.TODOS, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinnerPessoas(data);

    }
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinnerPessoas(null);
    }
}

