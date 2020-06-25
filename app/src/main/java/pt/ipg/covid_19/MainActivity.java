package pt.ipg.covid_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void VaiParaPeople(View view){

        Intent intent = new Intent(this, menu_ver_pessoas.class);
        Toast.makeText(this, R.string.EntrouRegistoPessoas, Toast.LENGTH_LONG).show();

        startActivity(intent);

    }

    public void VaiParaMovimentos(View view){

        Intent intent = new Intent(this, MenuVerMovimento.class);
        Toast.makeText(this, R.string.EntrouRegistoMovimentos, Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    public void VaiParaInfetados(View view){

        Intent intent = new Intent(this, MenuVerInfectados.class);
        Toast.makeText(this, R.string.EntrouRegistoInfectados, Toast.LENGTH_LONG).show();
        startActivity(intent);

    }

    public void SaiDaApp(View view){
        Toast.makeText(this, R.string.SaiuDaApp, Toast.LENGTH_LONG).show();
        finish();
        System.exit(0);

    }

}
