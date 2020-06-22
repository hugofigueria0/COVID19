package pt.ipg.covid_19;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MenuPessoasEliminar extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int ID_CURSOR_LOADER_PESSOAS = 0;
    private TextView textViewNome, textViewTipoPessoa;
    private PessoasModel pessoasModel;
    private Uri enderecoPessoaEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pessoas_eliminar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewNome = (TextView) findViewById(R.id.textViewNome);
        TextView textViewTipoPessoa = (TextView) findViewById(R.id.textViewTipoPessoa);


        Intent intent = getIntent();

        long idPessoa = intent.getLongExtra(menu_ver_pessoas.ID_PESSOAS,-1);

        if(idPessoa == -1){
            Toast.makeText(this, "Erro: não foi possivel ler o carro!", Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        enderecoPessoaEliminar = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PESSOAS, String.valueOf(idPessoa));

        Cursor cursor = getContentResolver().query(enderecoPessoaEliminar, BdTabelaPessoas.TODOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,"Erro não foi possivel ler o Pessoa!!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        pessoasModel = PessoasModel.fromCursor(cursor);

        textViewNome.setText(pessoasModel.getNome());
        textViewTipoPessoa.setText(pessoasModel.getTipoPessoa());


    }

     public void Eliminar (View view) {
         int PessoasApagadas = getContentResolver().delete(enderecoPessoaEliminar, null, null);

         if (PessoasApagadas == 1) {
             Toast.makeText(this, "nao sei o que acontece", Toast.LENGTH_SHORT).show();
             finish();
         } else {
             Toast.makeText(this, "aconteceu", Toast.LENGTH_LONG).show();
         }

     }



    public void Leave(View view){
        finish();
        Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
      //  return new CursorLoader(this, CovidContentProvider.ENDERECO_PESSOAS, BdTabelaPessoas.TODOS, null, null, BdTabelaPessoas.CAMPO_NOME);
    return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
