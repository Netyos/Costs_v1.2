package com.example.admin.costs_v12;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/*
 * Displays the monthly costs.
 */
public class MonthlyCosts extends Activity  {

    TextView tvProduct, tvTransit, tvnEntertainment, tvKomm, tvOther, tvCommon, tvMonthTitle;

    Categories.DBHelper dbHelper;
    SQLiteDatabase db;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.monthlycosts);

        tvProduct = (TextView) findViewById(R.id.tvProduct);
        tvTransit = (TextView) findViewById(R.id.tvTransit);
        tvnEntertainment = (TextView) findViewById(R.id.tvnEntertainment);
        tvKomm = (TextView) findViewById(R.id.tvKomm);
        tvOther = (TextView) findViewById(R.id.tvOther);
        tvCommon = (TextView) findViewById(R.id.tvCommon);
        tvMonthTitle = (TextView) findViewById(R.id.tvMonthTitle);

        tvMonthTitle.setText("Расходы за " + MainActivity.sMonth);

        dbHelper = new Categories.DBHelper(this);
        db = dbHelper.getWritableDatabase();

        String[] column = new String[] { "sum(product) as product", "sum(transit) as transit",
                "sum(entertaiment) as entertaiment", "sum(komm) as komm", "sum(other) as other",
                "sum(common) as common"};

        String selection = "date LIKE '%" + MainActivity.sMonth + "%'";
        Cursor c = db.query("mytable", column, selection, null, null, null, null);
        c.moveToFirst();

        int productColIndex = c.getColumnIndex("product");
        int transitColIndex = c.getColumnIndex("transit");
        int entertaimentColIndex = c.getColumnIndex("entertaiment");
        int kommColIndex = c.getColumnIndex("komm");
        int otherColIndex = c.getColumnIndex("other");
        int commonColIndex = c.getColumnIndex("common");

        tvProduct.setText(c.getString(productColIndex));
        tvTransit.setText(c.getString(transitColIndex));
        tvnEntertainment.setText(c.getString(entertaimentColIndex));
        tvKomm.setText(c.getString(kommColIndex));
        tvOther.setText(c.getString(otherColIndex));
        tvCommon.setText(c.getString(commonColIndex));

        c.close();
        dbHelper.close();
    }
}
