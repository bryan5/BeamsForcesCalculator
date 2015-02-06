package edu.self.bryan.beams_forcescalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String CONFIGURATIONS_TABLE = "CONFIGURATIONS";
    private static final String COL_ID = "ID";
    private static final String COL_ELASTICITY = "ELASTICITY";
    private static final String COL_INERTIA = "INERTIA";
    private static final String COL_LENGTH = "LENGTH";
    private static final String COL_FORCE = "FORCE";
    private static final String COL_P_LENGTH = "PARTIAL";
    private static final String COL_UNIFORM_LOAD = "UNIFORM";

    public Database(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s REAL NOT NULL, %s REAL NOT NULL, %s REAL NOT NULL, %s REAL NOT NULL, %s REAL NOT NULL, %s REAL NOT NULL)",
                                    CONFIGURATIONS_TABLE, COL_ID, COL_ELASTICITY, COL_INERTIA, COL_LENGTH, COL_FORCE, COL_P_LENGTH, COL_UNIFORM_LOAD);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeCantileverValues(CantileverBeam beam, int id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(CONFIGURATIONS_TABLE, null, null);

        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_ELASTICITY, beam.elasticity);
        values.put(COL_INERTIA, beam.inertia);
        values.put(COL_LENGTH, beam.lengthTotal);
        values.put(COL_FORCE, beam.force);
        values.put(COL_P_LENGTH, beam.partialLength);
        values.put(COL_UNIFORM_LOAD, beam.uniformLoad);

        database.insert(CONFIGURATIONS_TABLE, null, values);
        database.close();
    }

    public void getCantileverValues(CantileverBeam beam, int id) {
        SQLiteDatabase database = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s WHERE ID = " + Integer.toString(id), CONFIGURATIONS_TABLE);

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            beam.elasticity = cursor.getDouble(1);
            beam.inertia = cursor.getDouble(2);
            beam.lengthTotal = cursor.getDouble(3);
            beam.force = cursor.getDouble(4);
            beam.partialLength = cursor.getDouble(5);
            beam.uniformLoad = cursor.getDouble(6);
        }

        database.close();
    }

    public void storeSimpleSupportsValues(SimpleSupportsBeam beam, int id) {
        SQLiteDatabase database = getWritableDatabase();

        database.delete(CONFIGURATIONS_TABLE, null, null);

        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_ELASTICITY, beam.elasticity);
        values.put(COL_INERTIA, beam.inertia);
        values.put(COL_LENGTH, beam.lengthTotal);
        values.put(COL_FORCE, beam.force);
        values.put(COL_P_LENGTH, 0);
        values.put(COL_UNIFORM_LOAD, beam.uniformLoad);

        database.insert(CONFIGURATIONS_TABLE, null, values);
        database.close();
    }

    public void getSimpleSupportsValues(SimpleSupportsBeam beam, int id) {
        SQLiteDatabase database = getReadableDatabase();

        String sql = String.format("SELECT * FROM %s WHERE ID = " + Integer.toString(id), CONFIGURATIONS_TABLE);

        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            beam.elasticity = cursor.getDouble(1);
            beam.inertia = cursor.getDouble(2);
            beam.lengthTotal = cursor.getDouble(3);
            beam.force = cursor.getDouble(4);
            beam.uniformLoad = cursor.getDouble(6);
        }

        database.close();
    }

}
