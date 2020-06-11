package pt.ipg.covid_19;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BdTabelaPessoas implements BaseColumns {

    public static final String NOME_TABELA ="pessoas";

    public static final String CAMPO_NOME = "nome";
    public static final String CAMPO_TIPO_PESSOA = "tipoPessoa";

    public static final String CAMPO_ID_COMPLETO =  NOME_TABELA + "." + _ID;
    public static final String CAMPO_NOME_COMPLETO = NOME_TABELA + "." + CAMPO_NOME;
    public static final String CAMPO_TIPO_PESSOA_COMPLETO = NOME_TABELA + "." + CAMPO_TIPO_PESSOA;
    public static final String[] TODOS = {CAMPO_ID_COMPLETO, CAMPO_NOME_COMPLETO, CAMPO_TIPO_PESSOA_COMPLETO};



    private SQLiteDatabase db;

    public BdTabelaPessoas (SQLiteDatabase db){this.db = db;}

    public void cria(){
        db.execSQL("CREATE TABLE " + NOME_TABELA + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_NOME + " TEXT NOT NULL," +
                CAMPO_TIPO_PESSOA + " TEXT NOT NULL" +
                ")"
        );
    }

    public long insert(ContentValues values){ return  db.insert(NOME_TABELA, null, values);}

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy){

        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having,  orderBy);

    }

    public int update( ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete (String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
