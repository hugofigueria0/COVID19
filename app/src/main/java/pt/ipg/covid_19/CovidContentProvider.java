package pt.ipg.covid_19;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class CovidContentProvider extends ContentProvider {
    private static final String AUTHORITY = "pt.ipg.covid";
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

    private static final int URI_INFECTADO = 200;
    private static final int URI_ID_INFECTADO= 201;


    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item/";

    private BdPessoasOpenHelper openHelper;


    @Override
    public boolean onCreate() {
        return false;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
