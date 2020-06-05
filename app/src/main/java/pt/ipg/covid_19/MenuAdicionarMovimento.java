package pt.ipg.covid_19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MenuAdicionarMovimento extends AppCompatActivity {
    private EditText editDataEntrada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adicionar_movimento);

        editDataEntrada = (EditText) findViewById(R.id.DataEntrada);



    }


    public void GuardarData(View view){

        String ConteudoNomeDaPessoa = editDataEntrada.getText().toString();

        if (ConteudoNomeDaPessoa.trim().isEmpty()){

            editDataEntrada.setError(getString(R.string.FaltaData));
            editDataEntrada.requestFocus();
            return;

        }


        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void SairParaMovimento(View view){
        finish();
    }

}
