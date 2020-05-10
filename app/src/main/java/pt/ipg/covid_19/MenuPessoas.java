package pt.ipg.covid_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MenuPessoas extends AppCompatActivity {
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

        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void SairParaPrincipal(View view){
        finish();
    }
}
