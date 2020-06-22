package pt.ipg.covid_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MenuVerMovimento extends AppCompatActivity {

    public static final String ID_Movimento = "ID_MOVIMENTO";

    public static final int ID_CURSOR_LOADER_MOVIMENTOS = 0;

    private AdaptadorMovimentos adaptadorMovimentos;
    private RecyclerView recyclerViewPessoas;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ver_movimento);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_more_Movimento) {
            Intent intent = new Intent(this, MenuAdicionarMovimento.class);
            startActivity(intent);
            return true;
        }if (id == R.id.action_more_MovimentoInserir) {
            Intent intent = new Intent(this, MenuEditarMovimento.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
