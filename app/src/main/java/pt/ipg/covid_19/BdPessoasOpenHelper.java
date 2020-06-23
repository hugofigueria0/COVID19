package pt.ipg.covid_19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class BdPessoasOpenHelper extends SQLiteOpenHelper {

    public static final String NOME_BASE_DADOS = "livros.db";
    private static final int VERSAO_BASE_DADOS = 1;
    private static final boolean DESENVOLVIMENTO = true;


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

        if (DESENVOLVIMENTO) {
            seedData(db);
        }

    }

    private void seedData(SQLiteDatabase db) {

        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(db);

        PessoasModel pessoasModel = new PessoasModel();

        pessoasModel.setNome("Hugo Amaral");
        pessoasModel.setTipoPessoa("Visitante");
        long idPessoa2 = tabelaPessoas.insert(Converte.pessoasToContentValues(pessoasModel));

        pessoasModel.setNome("Tia Maria");
        pessoasModel.setTipoPessoa("Trabalhador");
        long idpessoa = tabelaPessoas.insert(Converte.pessoasToContentValues(pessoasModel));

        BdTabelaMovimento tabelaMovimento = new BdTabelaMovimento(db);

        MovimentoModel movimentoModel = new MovimentoModel();

        movimentoModel.setId_pessoa(idpessoa);
        movimentoModel.setHoraEntrada("17:20");
        movimentoModel.setHoraSaida("18:30");
        movimentoModel.setData("26/05/2020");
        tabelaMovimento.insert(Converte.movimentosToContentValues(movimentoModel));

        movimentoModel.setId_pessoa(idPessoa2);
        movimentoModel.setHoraEntrada("15:20");
        movimentoModel.setHoraSaida("16:30");
        movimentoModel.setData("20/04/2020");
        tabelaMovimento.insert(Converte.movimentosToContentValues(movimentoModel));

        BdTabelaInfectados tabelaInfectados = new BdTabelaInfectados(db);

        InfectadoModel infectadoModel = new InfectadoModel();

        infectadoModel.setId_pessoa(idpessoa);
        infectadoModel.setInfectado("Positivo");
        tabelaInfectados.insert(Converte.infectadosToContentValues(infectadoModel));

        infectadoModel.setId_pessoa(idPessoa2);
        infectadoModel.setInfectado("Negativo");
        tabelaInfectados.insert(Converte.infectadosToContentValues(infectadoModel));

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
