package pt.ipg.covid_19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BdPessoasOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "livros.db";
    private static final int VERSAO_BASE_DADOS = 1;


    public BdPessoasOpenHelper(@Nullable Context context) {
        super(context, NOME_BASE_DADOS, null, VERSAO_BASE_DADOS);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(db);
        tabelaPessoas.cria();

        BdTabelaMovimento tabelaMovimento = new BdTabelaMovimento(db);
        tabelaMovimento.cria();

        BdTabelaInfectados tabelaInfectados = new BdTabelaInfectados(db);
        tabelaInfectados.cria();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
