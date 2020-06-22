package pt.ipg.covid_19;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MenuPessoasEliminar extends AppCompatActivity {
    private TextView textViewNome;
    private Uri enderecoPessoaApagar;
    private PessoasModel pessoasModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pessoas_eliminar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  TextView textViewNome = (TextView) findViewById(R.id.NomePessoa);
        TextView textViewTipoPessoa = (TextView) findViewById(R.id.TipoPessoa);

        Intent intent = getIntent();

        long idPessoa = intent.getLongExtra(menu_ver_pessoas.ID_PESSOAS,-1);

        if(idPessoa == -1){
            Toast.makeText(this, "Erro: não foi possivel ler o carro!", Toast.LENGTH_LONG ).show();
            finish();
            return;
        }

        enderecoPessoaApagar = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PESSOAS, String.valueOf(idPessoa));

        Cursor cursor = getContentResolver().query(enderecoPessoaApagar, BdTabelaPessoas.TODOS, null, null, null);

        if(!cursor.moveToNext()){
            Toast.makeText(this,"Erro não foi possivel ler o Carro!!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        PessoasModel pessoasModel = PessoasModel.fromCursor(cursor);

        textViewNome.setText(pessoasModel.getNome());
        textViewTipoPessoa.setText(pessoasModel.getTipoPessoa());


*/
    }

     public void Eliminar (View view) {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);

         builder.setTitle("Eliminar Livro");
         builder.setMessage("Tem a certeza que pretende eliminar o livro '" + pessoasModel.getNome() + "'");
         builder.setIcon(R.drawable.ic_delete_black_24dp);
         builder.setPositiveButton("Sim, eliminar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 confirmaEliminar();
             }
         });

         builder.setNegativeButton("Não, cancelar", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 // cancelar
             }
         });

         builder.show();
    }

    private void confirmaEliminar() {
        try {
            Uri enderecoPessoa = Uri.withAppendedPath(CovidContentProvider.ENDERECO_PESSOAS, String.valueOf(pessoasModel.getId()));

            int apagados = this.getContentResolver().delete(enderecoPessoa, null, null);

            if (apagados == 1) {
                Toast.makeText(this, "Pessoa eliminada com sucesso", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
        }


        Snackbar.make(textViewNome, "Erro: Não foi possível eliminar o livro", Snackbar.LENGTH_INDEFINITE).show();
    }

    public void Leave(View view){
        finish();
        Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show();
    }
}
