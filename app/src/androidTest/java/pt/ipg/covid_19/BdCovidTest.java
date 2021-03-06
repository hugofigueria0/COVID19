package pt.ipg.covid_19;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BdCovidTest {

    @Before
    @After
    public void apagaBaseDados() {
        getTargetContext().deleteDatabase(BdPessoasOpenHelper.NOME_BASE_DADOS);
    }

    @Test
    public void consegueAbrirBaseDados() {
        // Context of the app under test.
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getReadableDatabase();
        assertTrue(bdCovid.isOpen());
        bdCovid.close();
    }

    private Context getTargetContext() {
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    // PESSOAS

    private long inserePessoas(BdTabelaPessoas tabelaPessoas, PessoasModel pessoas) {
        long id = tabelaPessoas.insert(Converte.pessoasToContentValues(pessoas));
        assertNotEquals(-1, id);

        return id;
    }

    private long inserePessoas(BdTabelaPessoas tabelaPessoas, String nome, String tipoPessoa) {

        PessoasModel pessoasModel = new PessoasModel();
        pessoasModel.setNome(nome);
        pessoasModel.setTipoPessoa(tipoPessoa);

        return inserePessoas(tabelaPessoas, pessoasModel);
    }

    @Test
    public void consegueInserirPessoas() {

        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdCovid = openHelper.getWritableDatabase();

        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdCovid);

        inserePessoas(tabelaPessoas, "Ricardo", "Visitante");

        bdCovid.close();
    }

    @Test
    public void consegueLerPessoas() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdPessoas = openHelper.getWritableDatabase();

        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdPessoas);

        Cursor cursor = tabelaPessoas.query(BdTabelaPessoas.TODOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        inserePessoas(tabelaPessoas, "Luis", "Visitante");

        cursor = tabelaPessoas.query(BdTabelaPessoas.TODOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdPessoas.close();
    }

    @Test
    public void consegueAlterarPessoas() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdPessoas = openHelper.getWritableDatabase();

        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdPessoas);

        PessoasModel pessoasModel = new PessoasModel();
        pessoasModel.setNome("Hug");
        pessoasModel.setTipoPessoa("Visitant");

        long id = inserePessoas(tabelaPessoas, pessoasModel);

        pessoasModel.setNome("Hugo");
        pessoasModel.setTipoPessoa("Visitante");

        int registosAfetados = tabelaPessoas.update(Converte.pessoasToContentValues(pessoasModel), BdTabelaPessoas._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosAfetados);

        bdPessoas.close();
    }

    @Test
    public void consegueEliminarPessoas() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdPessoas = openHelper.getWritableDatabase();

        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdPessoas);

        long id = inserePessoas(tabelaPessoas, "Ricardo", "Trabalhador");

        int registosEliminados = tabelaPessoas.delete(BdTabelaPessoas._ID + "=?", new String[]{String.valueOf(id)});
        assertEquals(1, registosEliminados);

        bdPessoas.close();
    }

    //MOVIMENTO

    private long insereMovimento(SQLiteDatabase bdMovimento, String horaEntrada, String horaSaida, String Data, String pessoas, String tipoPessoas) {
        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdMovimento);

        long idPessoas = inserePessoas(tabelaPessoas, pessoas, tipoPessoas);

        MovimentoModel movimentoModel = new MovimentoModel();
        movimentoModel.setHoraEntrada(horaEntrada);
        movimentoModel.setHoraSaida(horaSaida);
        movimentoModel.setData(Data);
        movimentoModel.setId_pessoa(idPessoas);

        BdTabelaMovimento tabelaMovimento = new BdTabelaMovimento(bdMovimento);
        long id = tabelaMovimento.insert(Converte.movimentosToContentValues(movimentoModel));
        assertNotEquals(-1, id);

        return  id;
    }

    @Test
    public void consegueInserirMovimentos() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdMovimento = openHelper.getWritableDatabase();

        insereMovimento(bdMovimento, "15:20", "16:20","24/10/2020","Ricardo", "Visitante");

        bdMovimento.close();
    }

    @Test
    public void consegueLerMovimentos() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdMovimento = openHelper.getWritableDatabase();

        BdTabelaMovimento tabelaMovimento = new BdTabelaMovimento(bdMovimento);

        Cursor cursor = tabelaMovimento.query(BdTabelaMovimento.TODOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereMovimento(bdMovimento, "16:20", "17:50","20/05/2020","Hugo","Empregado");

        cursor = tabelaMovimento.query(BdTabelaMovimento.TODOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdMovimento.close();
    }

    @Test
    public void consegueAlterarMovimento() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdMovimento = openHelper.getWritableDatabase();

        long idMovimento = insereMovimento(bdMovimento, "10:20","12:00","20/05/2020","Francisco","Visitante");

        BdTabelaMovimento tabelaMovimento = new BdTabelaMovimento(bdMovimento);

        Cursor cursor = tabelaMovimento.query(BdTabelaMovimento.TODOS, BdTabelaMovimento.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(idMovimento) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        MovimentoModel movimentoModel = Converte.cursorToMovimento(cursor);
        cursor.close();

        assertEquals("10:20", movimentoModel.getHoraEntrada());
        assertEquals("12:00", movimentoModel.getHoraSaida());

        movimentoModel.setHoraEntrada("11:20");
        movimentoModel.setHoraSaida("12:20");

        int registosAfetados = tabelaMovimento.update(Converte.movimentosToContentValues(movimentoModel), BdTabelaMovimento.CAMPO_ID_COMPLETO + "=?", new String[]{String.valueOf(movimentoModel.getId())});
        assertEquals(1, registosAfetados);

        bdMovimento.close();
    }



    //Infectados

    private long insereInfectado(SQLiteDatabase bdInfectado, String infectado, String pessoas, String tipoPessoas) {
        BdTabelaPessoas tabelaPessoas = new BdTabelaPessoas(bdInfectado);

        long idPessoas = inserePessoas(tabelaPessoas, pessoas, tipoPessoas);

        InfectadoModel infectadoModel = new InfectadoModel();
        infectadoModel.setInfectado(infectado);
        infectadoModel.setId_pessoa(idPessoas);

        BdTabelaInfectados tabelaInfectados = new BdTabelaInfectados(bdInfectado);

        long id = tabelaInfectados.insert(Converte.infectadosToContentValues(infectadoModel));
        assertNotEquals(-1, id);

        return  id;
    }

    @Test
    public void consegueInserirInfectado() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdInfectado = openHelper.getWritableDatabase();

        insereInfectado(bdInfectado, "Nao","Ricardo", "Visitante");

        bdInfectado.close();
    }

    @Test
    public void consegueLerInfectados() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdInfectado = openHelper.getWritableDatabase();

        BdTabelaInfectados tabelaInfectados = new BdTabelaInfectados(bdInfectado);

        Cursor cursor = tabelaInfectados.query(BdTabelaInfectados.TODOS, null, null, null, null, null);
        int registos = cursor.getCount();
        cursor.close();

        insereInfectado(bdInfectado, "Sim", "Hugo", "Empregado");

        cursor = tabelaInfectados.query(BdTabelaInfectados.TODOS, null, null, null, null, null);
        assertEquals(registos + 1, cursor.getCount());
        cursor.close();

        bdInfectado.close();
    }

    @Test
    public void consegueAlterarInfectado() {
        Context appContext = getTargetContext();

        BdPessoasOpenHelper openHelper = new BdPessoasOpenHelper(appContext);
        SQLiteDatabase bdInfectado = openHelper.getWritableDatabase();

        long idInfectado = insereInfectado(bdInfectado, "Positivo", "Amaral","Visitante");

        BdTabelaInfectados tabelaInfectados = new BdTabelaInfectados(bdInfectado);

        Cursor cursor = tabelaInfectados.query(BdTabelaInfectados.TODOS, BdTabelaInfectados.CAMPO_ID_COMPLETO + "=?", new String[]{ String.valueOf(idInfectado) }, null, null, null);
        assertEquals(1, cursor.getCount());

        assertTrue(cursor.moveToNext());
        InfectadoModel infectadoModel = Converte.cursorToInfectado(cursor);
        cursor.close();

        assertEquals("Positivo", infectadoModel.getInfectado());

        infectadoModel.setInfectado("Negativo");

        int registosAfetados = tabelaInfectados.update(Converte.infectadosToContentValues(infectadoModel), BdTabelaInfectados.CAMPO_ID_COMPLETO + "=?", new String[]{String.valueOf(infectadoModel.getId())});
        assertEquals(1, registosAfetados);

        bdInfectado.close();
    }



}
