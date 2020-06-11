package pt.ipg.covid_19;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

}
