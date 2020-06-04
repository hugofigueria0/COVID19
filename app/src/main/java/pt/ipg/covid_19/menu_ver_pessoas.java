package pt.ipg.covid_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class menu_ver_pessoas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_pessoas);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nomebar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more) {
            Intent intent = new Intent(this, MenuPessoas.class);
            startActivity(intent);
            return true;
        } else if(id == R.id.action_moreEdit) {
            Intent intent = new Intent(this, MenuPessoasEditar.class);
            startActivity(intent);
        }else if(id == R.id.action_moreDelete) {
            Intent intent = new Intent(this, MenuPessoasEliminar.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
