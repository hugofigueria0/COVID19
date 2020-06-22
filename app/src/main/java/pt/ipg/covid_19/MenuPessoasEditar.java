package pt.ipg.covid_19;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MenuPessoasEditar extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSOR_LOADER_PESSOAS = 0;

    private EditText editNomeDaPessoaEdit, editTipoDePessoaEdit;
    private PessoasModel pessoasModel;
    private Uri enderecoPessoaEditar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pessoas_editar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editNomeDaPessoaEdit = (EditText) findViewById(R.id.PessoasEditarNome);
        editTipoDePessoaEdit = (EditText) findViewById(R.id.PessoasEditarTipo);

        getSupportLoaderManager().initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);

        Intent intent = getIntent();

        long idPessoa = intent.getLongExtra(menu_ver_pessoas.ID_PESSOAS,-1);

        if(idPessoa == -1){
            Toast.makeText(this, "Erro: não foi possivel ler o carro!", Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        enderecoPessoaEditar = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PESSOAS, String.valueOf(idPessoa));

        Cursor cursor = getContentResolver().query(enderecoPessoaEditar, BdTabelaPessoas.TODOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,"Erro não foi possivel ler o Carro!!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        pessoasModel = PessoasModel.fromCursor(cursor);

        editNomeDaPessoaEdit.setText(pessoasModel.getNome());
        editTipoDePessoaEdit.setText(pessoasModel.getTipoPessoa());

    }

    public void EditPessoas(View view){

        String ConteudoNomeDaPessoa = editNomeDaPessoaEdit.getText().toString();
        String ConteudoTipoDePessoa = editTipoDePessoaEdit.getText().toString();

        if (ConteudoNomeDaPessoa.trim().isEmpty()){

            editNomeDaPessoaEdit.setError(getString(R.string.FaltaNomeDaPessoa));
            editNomeDaPessoaEdit.requestFocus();
            return;

        }

        if (  ConteudoTipoDePessoa.trim().isEmpty()) {

            editTipoDePessoaEdit.setError(getString(R.string.AvisoFaltaOTipo));
            editTipoDePessoaEdit.requestFocus();
            return;

        }

        String nome = editNomeDaPessoaEdit.getText().toString();
        String tipoPessoa = editTipoDePessoaEdit.getText().toString();

        // SAVE

        PessoasModel pessoasModel = new PessoasModel();

        pessoasModel.setNome(nome);
        pessoasModel.setTipoPessoa(tipoPessoa);



        try {
            getContentResolver().update( enderecoPessoaEditar, pessoasModel.getContentValues(), null, null);

            Toast.makeText(this, ("ALGO CERTO"), Toast.LENGTH_SHORT).show();
            finish();

        } catch (Exception e) {
            Toast.makeText(this,("correu ?!?!?!"), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        finish();
    }

    public void SairEditparaPrincipal(View view){
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PESSOAS, BdTabelaPessoas.TODOS, null, null, BdTabelaPessoas.CAMPO_NOME);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
