package pt.ipg.covid_19;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class MenuAdicionarMovimento extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int ID_CURSOR_LOADER_PESSOAS = 0;
    private EditText editDataEntrada, editDataSaida;
    private Spinner spinnerNomePessoa;

    private static final String TAG = "MenuAdicionarMovimento";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adicionar_movimento);

        editDataEntrada = (EditText) findViewById(R.id.DataEntrada);
        editDataSaida = (EditText) findViewById(R.id.DataSaida);
        spinnerNomePessoa = (Spinner) findViewById(R.id.NomePessoaSpinner);

        mDisplayDate = (TextView) findViewById(R.id.SelectDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MenuAdicionarMovimento.this,
                        android.R.style.Widget_DeviceDefault_Light_ActionBar_Solid,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        mostraDadosSpinnerPessoas(null);



        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);



    }

    private void mostraDadosSpinnerPessoas(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTabelaPessoas.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );

        spinnerNomePessoa.setAdapter(adapter);
    }

    public void GuardarData(View view){

        String ConteudoDaDataEntrada = editDataEntrada.getText().toString();
        String ConteudoDaDataSaida = editDataSaida.getText().toString();
        String ConteudoDaData = mDisplayDate.getText().toString();

        if (ConteudoDaDataEntrada.trim().isEmpty()){

            editDataEntrada.setError(getString(R.string.FaltaData));
            editDataEntrada.requestFocus();
            return;

        }

        String DataEntrada = editDataEntrada.getText().toString();
        String DataSaida = editDataSaida.getText().toString();
        String Data = mDisplayDate.getText().toString();
        long idPessoa = spinnerNomePessoa.getSelectedItemId();

        MovimentoModel movimentoModel = new MovimentoModel();
        movimentoModel.setHoraEntrada(DataEntrada);
        movimentoModel.setHoraSaida(DataSaida);
        movimentoModel.setData(Data);
        movimentoModel.setId_pessoa(idPessoa);

        try {

            this.getContentResolver().insert(CovidContentProvider.ENDERECO_MOVIMENTO, Converte.movimentosToContentValues(movimentoModel));
            Toast.makeText(this, "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Livro adicionado com sucesso", Toast.LENGTH_SHORT).show();
        }





        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void SairParaMovimento(View view){
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_PESSOAS, BdTabelaPessoas.TODOS, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mostraDadosSpinnerPessoas(data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinnerPessoas(null);
    }
}
