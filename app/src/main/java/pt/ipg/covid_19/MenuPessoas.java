package pt.ipg.covid_19;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MenuPessoas extends AppCompatActivity  {

    private EditText editNomeDaPessoa, editTipoDePessoa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pessoas);

        editNomeDaPessoa = (EditText) findViewById(R.id.NomeDaPessoa);
        editTipoDePessoa = (EditText) findViewById(R.id.TipoDePessoa);

    }



    public void GuardarPessoas(View view){

        String ConteudoNomeDaPessoa = editNomeDaPessoa.getText().toString();
        String ConteudoTipoDePessoa = editTipoDePessoa.getText().toString();

        if (ConteudoNomeDaPessoa.trim().isEmpty()){

            editNomeDaPessoa.setError(getString(R.string.FaltaNomeDaPessoa));
            editNomeDaPessoa.requestFocus();
            return;

        }

        if (  ConteudoTipoDePessoa.trim().isEmpty()) {

            editTipoDePessoa.setError(getString(R.string.AvisoFaltaOTipo));
            editTipoDePessoa.requestFocus();
            return;

        }

        // save data

        PessoasModel pessoasModel = new PessoasModel();

        pessoasModel.setNome(ConteudoNomeDaPessoa);
        pessoasModel.setTipoPessoa(ConteudoTipoDePessoa);

        try {
            this.getContentResolver().insert(CovidContentProvider.ENDERECO_PESSOAS, Converte.pessoasToContentValues(pessoasModel));
            Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
            finish();

        } catch (Exception e) {
            Snackbar.make(editNomeDaPessoa, "Erro: Não foi possível criar a Pessoa", Snackbar.LENGTH_INDEFINITE).show();
        }


    }

    public void SairParaPrincipal(View view){
        Toast.makeText(this, R.string.Regressar, Toast.LENGTH_LONG).show();
        finish();
    }
}
