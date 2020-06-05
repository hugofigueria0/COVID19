package pt.ipg.covid_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void VaiParaPeople(View view){

        Intent intent = new Intent(this, menu_ver_pessoas.class);
        startActivity(intent);

    }

    public void VaiParaMovimentos(View view){

        Intent intent = new Intent(this, MenuVerMovimento.class);
        startActivity(intent);

    }

    public void VaiParaInfetados(View view){

        Intent intent = new Intent(this, MenuMovimentos.class);
        startActivity(intent);

    }

    public void SaiDaApp(View view){
        finish();
        System.exit(0);

    }

}
