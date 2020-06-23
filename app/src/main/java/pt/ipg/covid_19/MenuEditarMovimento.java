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
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Calendar;

public class MenuEditarMovimento extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ID_CURSOR_LOADER_MOVIMENTOS = 0;

    private MovimentoModel movimentoModel;

    private EditText editTextEditarMovimentoData, editTextEditarMovimentoSair;

    private static final String TAG = "MenuEditarMovimento";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Uri enderecoMovimentoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editar_movimento);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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



    }

    public void EditarDataMovimento(View view){

        String ConteudoDaDataEntrada = editTextEditarMovimentoData.getText().toString();
        String ConteudoDaDataSaida = editTextEditarMovimentoSair.getText().toString();
        String ConteudoDaData= mDisplayDate.getText().toString();



        if (ConteudoDaDataEntrada.trim().isEmpty()){

            editTextEditarMovimentoData.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoData.requestFocus();
            return;

        }else if(ConteudoDaDataSaida.trim().isEmpty()){

            editTextEditarMovimentoSair.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoSair.requestFocus();
            return;

        }

        String dataEntrada = editTextEditarMovimentoData.getText().toString();
        String dataSaida = editTextEditarMovimentoSair.getText().toString();
        String data = mDisplayDate.getText().toString();

        // save the data
        MovimentoModel movimentoModel = new MovimentoModel();

        movimentoModel.setHoraEntrada(dataEntrada);
        movimentoModel.setHoraSaida(dataSaida);
        movimentoModel.setData(data);






        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void CancelarEditarMovimento(View view){
        finish();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, CovidContentProvider.ENDERECO_MOVIMENTO, BdTabelaMovimento.TODOS, null, null, BdTabelaMovimento.CAMPO_HORA_ENTRADA);
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}