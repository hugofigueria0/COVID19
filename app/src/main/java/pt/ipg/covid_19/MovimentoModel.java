package pt.ipg.covid_19;

import android.content.ContentValues;
import android.database.Cursor;

public class MovimentoModel {

    private long id = -1;
    private String horaEntrada;
    private String horaSaida;
    private String data;
    private long id_pessoa = -1;
    private String nome_pessoa = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.horaSaida = horaSaida;
    }

    public String getData() {
        return data;
    }

    public long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();
        valores.put(BdTabelaMovimento.CAMPO_HORA_ENTRADA, horaEntrada);
        valores.put(BdTabelaMovimento.CAMPO_HORA_SAIDA, horaSaida);
        valores.put(BdTabelaMovimento.CAMPO_DATA, data);


        return valores;
    }

    public static MovimentoModel fromCursor(Cursor cursor){

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTabelaMovimento._ID)
        );

        String DataEntrada = cursor.getString(
                cursor.getColumnIndex(BdTabelaMovimento.CAMPO_HORA_ENTRADA)
        );

        String DataSaida = cursor.getString(
                cursor.getColumnIndex(BdTabelaMovimento.CAMPO_HORA_SAIDA)
        );

        String Data = cursor.getString(
                cursor.getColumnIndex(BdTabelaMovimento.CAMPO_DATA)
        );
        String Nome = cursor.getString( cursor.getColumnIndex(BdTabelaMovimento.CAMPO_PESSOA));


        long idPessoa = cursor.getLong(
                cursor.getColumnIndex(BdTabelaMovimento.CAMPO_ID_PESSOA)
        );


        MovimentoModel movimentoModel = new MovimentoModel();

        movimentoModel.setId(id);
        movimentoModel.setHoraEntrada(DataEntrada);
        movimentoModel.setHoraSaida(DataSaida);
        movimentoModel.setData(Data);
        movimentoModel.setId_pessoa(idPessoa);
        movimentoModel.setNome_pessoa(Nome);


        return movimentoModel;
    }
}
