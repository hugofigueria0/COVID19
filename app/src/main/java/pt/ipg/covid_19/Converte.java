package pt.ipg.covid_19;

import android.content.ContentValues;
import android.database.Cursor;

public class Converte {

    // TABELA PESSOAS
    public static ContentValues pessoasToContentValues(PessoasModel pessoasModel) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelaPessoas.CAMPO_NOME, pessoasModel.getNome());
        valores.put(BdTabelaPessoas.CAMPO_TIPO_PESSOA, pessoasModel.getTipoPessoa());

        return valores;
    }

    public static PessoasModel contentValuesToPessoas(ContentValues valores) {
        PessoasModel pessoasModel = new PessoasModel();

        pessoasModel.setId(valores.getAsLong(BdTabelaPessoas._ID));
        pessoasModel.setNome(valores.getAsString(BdTabelaPessoas.CAMPO_NOME));
        pessoasModel.setTipoPessoa(valores.getAsString(BdTabelaPessoas.CAMPO_TIPO_PESSOA));

        return pessoasModel;
    }


    // TABELA Movimentos

    public static ContentValues movimentosToContentValues(MovimentoModel movimentoModel) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelaMovimento.CAMPO_HORA_ENTRADA, movimentoModel.getHoraEntrada());
        valores.put(BdTabelaMovimento.CAMPO_HORA_SAIDA, movimentoModel.getHoraSaida());
        valores.put(BdTabelaMovimento.CAMPO_DATA, movimentoModel.getData());

        valores.put(BdTabelaMovimento.CAMPO_ID_PESSOA, movimentoModel.getId_pessoa());


        return valores;
    }

    public static MovimentoModel contentValuesToMovimentos(ContentValues valores) {

        MovimentoModel movimentoModel = new MovimentoModel();

        movimentoModel.setId(valores.getAsLong(BdTabelaMovimento._ID));
        movimentoModel.setHoraEntrada(valores.getAsString(BdTabelaMovimento.CAMPO_HORA_ENTRADA));
        movimentoModel.setHoraSaida(valores.getAsString(BdTabelaMovimento.CAMPO_HORA_SAIDA));
        movimentoModel.setData(valores.getAsString(BdTabelaMovimento.CAMPO_DATA));

        movimentoModel.setId_pessoa(valores.getAsLong(BdTabelaMovimento.CAMPO_ID_PESSOA));
        movimentoModel.setNome_pessoa(valores.getAsString(BdTabelaMovimento.CAMPO_PESSOA));

        return movimentoModel;
    }

    //TABELA INFECTADOS

    public static ContentValues infectadosToContentValues(InfectadoModel infectadoModel) {
        ContentValues valores = new ContentValues();

        valores.put(BdTabelaInfectados.CAMPO_INFECTADO, infectadoModel.getInfectado());

        valores.put(BdTabelaInfectados.CAMPO_ID_PESSOA, infectadoModel.getId_pessoa());

        return valores;
    }

    public static InfectadoModel contentValuesToInfectados(ContentValues valores) {
        InfectadoModel infectadoModel = new InfectadoModel();

        infectadoModel.setId(valores.getAsLong(BdTabelaInfectados._ID));
        infectadoModel.setInfectado(valores.getAsString(BdTabelaInfectados.CAMPO_INFECTADO));

        infectadoModel.setId_pessoa(valores.getAsLong(BdTabelaInfectados.CAMPO_ID_PESSOA));
        infectadoModel.setNome_pessoa(valores.getAsString(BdTabelaInfectados.CAMPO_PESSOA));

        return infectadoModel;
    }



}