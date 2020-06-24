package pt.ipg.covid_19;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

public class MenuEditarMovimento extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSOR_LOADER_PESSOAS = 0;

    private MovimentoModel movimentoModel;

    private EditText editTextEditarMovimentoData, editTextEditarMovimentoSair;

    private static final String TAG = "MenuEditarMovimento";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Spinner BuscarPessoaspinner;

    private boolean pessoasCarregadas = false;
    private boolean pessoasAtualizada = false;

    private Uri enderecPessoasEditar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editar_movimento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BuscarPessoaspinner = (Spinner) findViewById(R.id.BuscarPessoaspinner);
        editTextEditarMovimentoData = (EditText) findViewById(R.id.dataEntradaEditar);
        editTextEditarMovimentoSair = (EditText) findViewById(R.id.dataSaidaEditar);
        mDisplayDate = (TextView) findViewById(R.id.selectDateEditar);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MenuEditarMovimento.this,
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

        Intent intent = getIntent();

        movimentoModel = (MovimentoModel) intent.getSerializableExtra("Movimento");
        editTextEditarMovimentoData.setText(movimentoModel.getHoraEntrada());
        editTextEditarMovimentoSair.setText(movimentoModel.getHoraSaida());
        mDisplayDate.setText(movimentoModel.getData());


        LoaderManager.getInstance(this).initLoader(ID_CURSOR_LOADER_PESSOAS, null, this);

        actualizaPessoasSelecionada();



    }

    private void actualizaPessoasSelecionada() {
        if (!pessoasCarregadas) return;
        if (pessoasAtualizada) return;

        long idPessoa = movimentoModel.getId_pessoa();

        for (int i= 0; i < BuscarPessoaspinner.getCount(); i++) {
            if (BuscarPessoaspinner.getItemIdAtPosition(i) == idPessoa) {
                BuscarPessoaspinner.setSelection(i, true);
                break;
            }
        }

        pessoasAtualizada = true;
    }

    private void mostraDadosSpinnerPessoas(Cursor data) {
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                data,
                new String[]{BdTabelaPessoas.CAMPO_NOME},
                new int[]{android.R.id.text1}
        );

        BuscarPessoaspinner.setAdapter(adapter);
    }





    public void EditarDataMovimento(View view){

        String ConteudoDaDataEntrada = editTextEditarMovimentoData.getText().toString();
        String ConteudoDaDataSaida = editTextEditarMovimentoSair.getText().toString();
        String ConteudoDaData= mDisplayDate.getText().toString();

        long idPessoa = BuscarPessoaspinner.getSelectedItemId();


        if (ConteudoDaDataEntrada.trim().isEmpty()){

            editTextEditarMovimentoData.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoData.requestFocus();
            return;

        }else if(ConteudoDaDataSaida.trim().isEmpty()){

            editTextEditarMovimentoSair.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoSair.requestFocus();
            return;

        }

        movimentoModel.setHoraEntrada(ConteudoDaDataEntrada);
        movimentoModel.setHoraSaida(ConteudoDaDataSaida);
        movimentoModel.setData(ConteudoDaData);
        movimentoModel.setId_pessoa(idPessoa);

        try {
            Uri enderecoMovimento= Uri.withAppendedPath(CovidContentProvider.ENDERECO_MOVIMENTO, String.valueOf(movimentoModel.getId()));

            int registos = this.getContentResolver().update(enderecoMovimento, Converte.movimentosToContentValues(movimentoModel), null, null);

            if (registos == 1) {
                Toast.makeText(this, "Livro guardado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
        }


    }

    public void CancelarEditarMovimento(View view){
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
        pessoasCarregadas = true;
        actualizaPessoasSelecionada();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mostraDadosSpinnerPessoas(null);
    }
}