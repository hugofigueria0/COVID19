package pt.ipg.covid_19;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CovidContentProvider extends ContentProvider {
    private static final String AUTHORITY = "pt.ipg.covid_19";
    private static final String PESSOAS = "pessoas";
    private static final String MOVIMENTO = "movimento";
    private static final String INFECTADO = "infectado";

    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_PESSOAS= Uri.withAppendedPath(ENDERECO_BASE, PESSOAS);
    public static final Uri ENDERECO_MOVIMENTO = Uri.withAppendedPath(ENDERECO_BASE, MOVIMENTO);
    public static final Uri ENDERECO_INFECTADO = Uri.withAppendedPath(ENDERECO_BASE, INFECTADO);

    private static final int URI_PESSOAS = 100;
    private static final int URI_ID_PESSOAS = 101;

    private static final int URI_MOVIMENTO = 200;
    private static final int URI_ID_MOVIMENTO = 201;

    private static final int URI_INFECTADO = 300;
    private static final int URI_ID_INFECTADO= 301;


    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item/";

    private BdPessoasOpenHelper openHelper;

    private UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, PESSOAS, URI_PESSOAS);
        uriMatcher.addURI(AUTHORITY, PESSOAS + "/#", URI_ID_PESSOAS);

        uriMatcher.addURI(AUTHORITY, MOVIMENTO, URI_MOVIMENTO);
        uriMatcher.addURI(AUTHORITY, MOVIMENTO + "/#", URI_ID_MOVIMENTO);

        uriMatcher.addURI(AUTHORITY, INFECTADO, URI_INFECTADO);
        uriMatcher.addURI(AUTHORITY, INFECTADO + "/#", URI_ID_INFECTADO);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new BdPessoasOpenHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = openHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_PESSOAS:
                return new BdTabelaPessoas(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_PESSOAS:
                return new BdTabelaPessoas(bd).query(projection, BdTabelaPessoas._ID + "=?", new String[] { id }, null, null, sortOrder);

            case URI_MOVIMENTO:
                return new BdTabelaMovimento(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_MOVIMENTO:
                return new BdTabelaMovimento(bd).query(projection, BdTabelaMovimento._ID + "=?", new String[] { id }, null, null, sortOrder);

            case URI_INFECTADO:
                return new BdTabelaInfectados(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_INFECTADO:
                return new BdTabelaInfectados(bd).query(projection, BdTabelaInfectados._ID + "=?", new String[] { id }, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException("Endereço query inválido: " + uri.getPath());
        }


    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int codigoUri = getUriMatcher().match(uri);

        switch (codigoUri) {
            case URI_PESSOAS:
                return CURSOR_DIR + PESSOAS;
            case URI_ID_PESSOAS:
                return CURSOR_ITEM + PESSOAS;
            case URI_MOVIMENTO:
                return CURSOR_DIR + MOVIMENTO;
            case URI_ID_MOVIMENTO:
                return CURSOR_ITEM + MOVIMENTO;
            case URI_INFECTADO:
                return CURSOR_DIR + INFECTADO;
            case URI_ID_INFECTADO:
                return CURSOR_ITEM + INFECTADO;
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        long id;

        switch (getUriMatcher().match(uri)) {
            case URI_PESSOAS:
                id = (new BdTabelaPessoas(bd).insert(values));
                break;

            case URI_MOVIMENTO:
                id = (new BdTabelaMovimento(bd).insert(values));
                break;

            case URI_INFECTADO:
                id = (new BdTabelaInfectados(bd).insert(values));
                break;

            default:
                throw new UnsupportedOperationException("Endereço insert inválido: " + uri.getPath());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo: " + uri.getPath());
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_PESSOAS:
                return new BdTabelaPessoas(bd).delete(BdTabelaPessoas._ID + "=?", new String[]{id});

            case URI_ID_MOVIMENTO:
                return new BdTabelaMovimento(bd).delete(BdTabelaMovimento._ID + "=?", new String[] { id });

            case URI_ID_INFECTADO:
                return new BdTabelaInfectados(bd).delete(BdTabelaMovimento._ID + "=?", new String[] { id });

            default:
                throw new UnsupportedOperationException("Endereço delete inválido: " + uri.getPath());
        }
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_PESSOAS:
                return new BdTabelaPessoas(bd).update(values, BdTabelaPessoas._ID + "=?", new String[] { id });

            case URI_ID_MOVIMENTO:
                return new BdTabelaMovimento(bd).update(values,BdTabelaMovimento._ID + "=?", new String[] { id });

                case URI_ID_INFECTADO:
                return new BdTabelaInfectados(bd).update(values,BdTabelaInfectados._ID + "=?", new String[] { id });

            default:
                throw new UnsupportedOperationException("Endereço de update inválido: " + uri.getPath());
        }
    }
}
