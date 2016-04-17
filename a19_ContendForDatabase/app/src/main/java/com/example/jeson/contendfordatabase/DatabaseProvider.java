package com.example.jeson.contendfordatabase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Jeson on 2016/4/17.
 * 内容提供者类
 */
public class DatabaseProvider extends ContentProvider{

    private static final int BOOK_DIR = 0;  //用于访问Book表中的所有数据

    private static final int BOOK_ITME = 1; //用于访问Book表中单条数据

    private static final int CATEGORY_DIR = 2;  //用于访问Category表中的所有数据

    private static final int CATEGORY_ITEM = 3; //用于访问Category表中的所有数据

    public static final String AUTHORITY = "com.example.jeson.databasetest.provider";

    private static UriMatcher uriMatcher;

    private MyDatabaseHelper myDatabaseHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR);
        uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITME);
        uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM);

    }

    /**
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection,
        String selection, String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITME:
                String booId = uri.getPathSegments().get(1);
                cursor = db.query("Book", projection,
                        "id = ?", new String[] {booId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category", projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection,
                        "id = ?", new String[] {categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public boolean onCreate(){
        myDatabaseHelper = new MyDatabaseHelper(getContext(), "BookStore.db", null, 2);
        return false;
    }

    public DatabaseProvider() {
        super();
    }

    /**
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //更新数据
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        int updateRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updateRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITME:
                String bookId = uri.getPathSegments().get(1);
                updateRows = db.update("Book", values, "id = ?", new String[] {bookId});
                break;
            case CATEGORY_DIR:
                updateRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updateRows = db.update("Category", values, "id = ?", new String[] {categoryId});
                break;
            default:
                break;
        }
        return updateRows;
    }

    /**
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //删除数据
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deletedRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITME:
                String bookId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Book", "id = ?", new String[] {bookId});
                break;
            case CATEGORY_DIR:
                deletedRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deletedRows = db.delete("Category", "id = ?", new String[] {categoryId});
                break;
            default:
                break;
        }
        return deletedRows;
    }

    /**
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //添加数据
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITME:
                long newBookId = db.insert("Book", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/book" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uriReturn = Uri.parse("content://" + AUTHORITY + "/category" + newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    /**
     *
     * @param uri
     * @return
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.jeson.contendfordatabase.provider.book";
            case BOOK_ITME:
                return "vnd.android.cursor.item/vnd.com.example.jeson.contendfordatabase.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.jeson.contendfordatabase.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.jeson.contendfordatabase.provider.category";
        }
        return null;
    }
}
