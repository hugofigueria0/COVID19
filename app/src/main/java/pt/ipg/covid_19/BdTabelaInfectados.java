package pt.ipg.covid_19;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Arrays;

public class BdTabelaInfectados implements BaseColumns {

    public static final String NOME_TABELA ="infectados";
    public static final String CAMPO_INFECTADO = "infectado";

    //Chave Estrangeira
    public static final String CAMPO_ID_PESSOA = "id_pessoa";
    public static final String CAMPO_PESSOA = "pessoa";

    public static final String CAMPO_ID_COMPLETO =  NOME_TABELA + "." + _ID;
    public static final String CAMPO_INFECTADOCOMPLETO = NOME_TABELA + "." + CAMPO_INFECTADO;

    //Chave Estrangeira
    public static final String CAMPO_ID_PESSOA_COMPLETO = NOME_TABELA + "." + CAMPO_ID_PESSOA;
    public static final String CAMPO_PESSOA_COMPLETO = BdTabelaPessoas.CAMPO_NOME_COMPLETO + " AS " + CAMPO_PESSOA;

    public static final String[] TODOS = {CAMPO_ID_COMPLETO, CAMPO_INFECTADOCOMPLETO, CAMPO_ID_PESSOA_COMPLETO, CAMPO_PESSOA_COMPLETO};




    private SQLiteDatabase db;

    public BdTabelaInfectados (SQLiteDatabase db){this.db = db;}

    public void cria(){
        db.execSQL("CREATE TABLE " + NOME_TABELA + " ("+
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_INFECTADO + " TEXT NOT NULL," +
                CAMPO_PESSOA + " TEXT NOT NULL," +
                CAMPO_ID_PESSOA + " TEXT NOT NULL," +
                "FOREIGN KEY (" + CAMPO_ID_PESSOA + ") REFERENCES " +
                BdTabelaPessoas.NOME_TABELA + "("+ BdTabelaPessoas._ID + ")" +
                ")"
        );
    }

    public long insert(ContentValues values){ return  db.insert(NOME_TABELA, null, values);}

    public Cursor query(String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy){

        if (!Arrays.asList(columns).contains(CAMPO_PESSOA_COMPLETO)) {
            return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

        String campos = TextUtils.join(",", columns);

        String sql = "SELECT " + campos;
        sql += " FROM " + NOME_TABELA + " INNER JOIN " + BdTabelaPessoas.NOME_TABELA;
        sql += " ON " + CAMPO_ID_PESSOA_COMPLETO + "=" + BdTabelaPessoas.CAMPO_ID_COMPLETO;

        if (selection != null) {
            sql += " WHERE " + selection;
        }

        if (groupBy != null) {
            sql += " GROUP BY " + groupBy;

            if (having != null) {
                sql += " HAVING " + having;
            }
        }

        if (orderBy != null) {
            sql += " ORDER BY " + orderBy;
        }

        return db.rawQuery(sql, selectionArgs);

    }

    public int update( ContentValues values, String whereClause, String[] whereArgs){
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete (String whereClause, String[] whereArgs){
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
