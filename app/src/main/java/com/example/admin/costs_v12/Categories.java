package com.example.admin.costs_v12;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
 * Creates a database and displays daily costs.
 */
public class Categories extends Activity implements View.OnClickListener {

    TextView titleCat;

    DBHelper dbHelper;
    SQLiteDatabase db;

    String[] column = null;
    String selection = null;
    String[] selectionArgs = null;

    Button buttonSum;
    EditText editProduct, editTransit, editEntertainment, editKomm, editOther, editCommon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories);

        buttonSum = (Button) findViewById(R.id.buttonSum);
        buttonSum.setOnClickListener(this);


        titleCat = (TextView) findViewById(R.id.titleCat);

        titleCat.setText("Расходы на " + MainActivity.sDate);

        editProduct = (EditText) findViewById(R.id.editProduct);
        editTransit = (EditText) findViewById(R.id.editTransit);
        editEntertainment = (EditText) findViewById(R.id.editEntertainment);
        editKomm = (EditText) findViewById(R.id.editKomm);
        editOther = (EditText) findViewById(R.id.editOther);
        editCommon = (EditText) findViewById(R.id.editCommon);

        dbHelper = new DBHelper(this);

        boolean exist = Exists(MainActivity.sDate);

        if (exist == true) {

            db = dbHelper.getWritableDatabase();

            column = new String[] { "product", "transit", "entertaiment", "komm",
                    "other", "common"};
            selection = "date = ?";
            selectionArgs = new String[] { MainActivity.sDate };
            Cursor c = db.query("mytable", column, selection, selectionArgs, null,
                    null, null, null);
            c.moveToFirst();

            int productColIndex = c.getColumnIndex("product");
            int transitColIndex = c.getColumnIndex("transit");
            int entertaimentColIndex = c.getColumnIndex("entertaiment");
            int kommColIndex = c.getColumnIndex("komm");
            int otherColIndex = c.getColumnIndex("other");
            int commonColIndex = c.getColumnIndex("common");

            editProduct.setText(c.getString(productColIndex));
            editTransit.setText(c.getString(transitColIndex));
            editEntertainment.setText(c.getString(entertaimentColIndex));
            editKomm.setText(c.getString(kommColIndex));
            editOther.setText(c.getString(otherColIndex));
            editCommon.setText(c.getString(commonColIndex));

            c.close();
            dbHelper.close();
        }
        else {
            editProduct.setText("0");
            editTransit.setText("0");
            editEntertainment.setText("0");
            editKomm.setText("0");
            editOther.setText("0");
        }
    }

    public boolean Exists(String date) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c =  db.rawQuery("select 1 from mytable where date=?", new String[] { date });
        boolean exists = (c.getCount() > 0);
        c.close();
        return exists;
    }

    public void onClick(View v) {

        ContentValues cv = new ContentValues();

        String product = editProduct.getText().toString();
        String transit = editTransit.getText().toString();
        String entertaiment = editEntertainment.getText().toString();
        String komm = editKomm.getText().toString();
        String other = editOther.getText().toString();

        int cat1 = Integer.parseInt(product);
        int cat2 = Integer.parseInt(transit);
        int cat3 = Integer.parseInt(entertaiment);
        int cat4 = Integer.parseInt(komm);
        int cat5 = Integer.parseInt(other);
        int com = cat1 + cat2 + cat3 + cat4 + cat5;

        String common = "" + com;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (v.getId()) {
            case R.id.buttonSum:

                boolean exist = Exists(MainActivity.sDate);

                if(exist == true) {
                    cv.put("product", product);
                    cv.put("transit", transit);
                    cv.put("entertaiment", entertaiment);
                    cv.put("komm", komm);
                    cv.put("other", other);
                    cv.put("common", common);

                    int updCount = db.update("mytable", cv, "date = ?",
                            new String[] { MainActivity.sDate });

                    column = new String[] {"common"};
                    selection = "date = ?";
                    selectionArgs = new String[] { MainActivity.sDate };
                    Cursor c = db.query("mytable", column, selection, selectionArgs, null,
                            null, null, null);
                    c.moveToFirst();

                    int commonColIndex = c.getColumnIndex("common");
                    editCommon.setText(c.getString(commonColIndex));
                    c.close();
                    break;
                }

                else {
                    cv.put("date", MainActivity.sDate);
                    cv.put("product", product);
                    cv.put("transit", transit);
                    cv.put("entertaiment", entertaiment);
                    cv.put("komm", komm);
                    cv.put("other", other);
                    cv.put("common", common);

                    long rowID = (db.insert("mytable", null, cv));

                    column = new String[] {"common"};
                    selection = "date = ?";
                    selectionArgs = new String[] { MainActivity.sDate };
                    Cursor c = db.query("mytable", column, selection, selectionArgs, null,
                            null, null, null);
                    c.moveToFirst();

                    int commonColIndex = c.getColumnIndex("common");
                    editCommon.setText(c.getString(commonColIndex));
                    c.close();
                    break;
                }

        }
        dbHelper.close();
    }

    public static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table mytable ("

                    + "id integer primary key autoincrement,"
                    + "date text,"
                    + "product integer,"
                    + "transit integer,"
                    + "entertaiment integer,"
                    + "komm integer,"
                    + "other integer,"
                    + "common integer" + ");");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
