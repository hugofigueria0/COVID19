package pt.ipg.covid_19;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

public class PessoasModel {

    private long id = -1;
    private String nome;
    private String tipoPessoa;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public ContentValues getContentValues(){
        ContentValues valores = new ContentValues();
        valores.put(BdTabelaPessoas.CAMPO_NOME, nome);
        valores.put(BdTabelaPessoas.CAMPO_TIPO_PESSOA, tipoPessoa);


        return valores;
    }

    public static PessoasModel fromCursor(Cursor cursor){

        long id = cursor.getLong(
                cursor.getColumnIndex(BdTabelaPessoas._ID)
        );

        String pessoa = cursor.getString(
                cursor.getColumnIndex(BdTabelaPessoas.CAMPO_NOME)
        );

        String tipoPessoa = cursor.getString(
                cursor.getColumnIndex(BdTabelaPessoas.CAMPO_TIPO_PESSOA)
        );



        PessoasModel pessoasModel = new PessoasModel();

        pessoasModel.setId(id);
        pessoasModel.setNome(pessoa);
        pessoasModel.setTipoPessoa(tipoPessoa);

        return pessoasModel;
    }
}
