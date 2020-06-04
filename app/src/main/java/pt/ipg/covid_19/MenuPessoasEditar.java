package pt.ipg.covid_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MenuPessoasEditar extends AppCompatActivity {
    private EditText editNomeDaPessoaEdit, editTipoDePessoaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pessoas_editar);

        editNomeDaPessoaEdit = (EditText) findViewById(R.id.PessoasEditarNome);
        editTipoDePessoaEdit = (EditText) findViewById(R.id.PessoasEditarTipo);
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

        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void SairEditparaPrincipal(View view){
        finish();
    }
}
