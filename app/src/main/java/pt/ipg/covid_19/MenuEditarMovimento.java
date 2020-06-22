package pt.ipg.covid_19;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MenuEditarMovimento extends AppCompatActivity {

    private EditText editTextEditarMovimentoData, editTextEditarMovimentoSair;

    private static final String TAG = "MenuEditarMovimento";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editar_movimento);

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



        if (ConteudoDaDataEntrada.trim().isEmpty()){

            editTextEditarMovimentoData.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoData.requestFocus();
            return;

        }else if(ConteudoDaDataSaida.trim().isEmpty()){

            editTextEditarMovimentoSair.setError(getString(R.string.FaltaData));
            editTextEditarMovimentoSair.requestFocus();
            return;

        }


        Toast.makeText(this, R.string.Sucesso, Toast.LENGTH_LONG).show();
        finish();
    }

    public void CancelarEditarMovimento(View view){
        finish();
    }
}