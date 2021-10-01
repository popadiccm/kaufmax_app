package com.example.kaufmax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Artikal extends AppCompatActivity {

    LinearLayout mainLayout;
    ActionBar actionBar;
    Button pretrazibtn;
    Button nazadbtn;
    EditText sifra_artikla;
    Connection con;
    String sifra;
    String rafovi[];
    String naziv;
    String kolicine[];
    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikal);
        pretrazibtn = (Button)findViewById(R.id.nadjiartikal);
        nazadbtn = (Button)findViewById(R.id.nazad1);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffef00"));
        pretrazibtn.setBackgroundColor(Color.rgb(255, 239, 0));
        nazadbtn.setBackgroundColor(Color.rgb(255, 239, 0));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Meni.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    /**
     * @param v
     */
    public void nadjiArtikal(View v){
        TableLayout tl = (TableLayout)findViewById(R.id.table_main);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.artikal);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        sifra_artikla = (EditText)findViewById(R.id.unosartikla);
        sifra_artikla.toString();
        rafovi = new String[100];
        kolicine = new String[100];
        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("exec getMaterialsStorageBin '" + sifra_artikla.getText().toString() + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Artikal " + sifra_artikla.getText() + " nije pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Artikal " + sifra_artikla.getText() + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("exec getMaterialsStorageBin '" + sifra_artikla.getText().toString() + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        sifra = rs.getString("SifraArtikla");
                        rafovi[i] = rs.getString("Raf");
                        naziv = rs.getString("NazivArtikla");
                        kolicine[i] = rs.getString("Kolicina");

                        i++;
                        brojac++;
                    }
                    String[][] stringovi = new String[brojac][2];
                    for (int j = 0; j < 2; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = rafovi[k];

                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine[k];

                                }
                                break;
                        }

                    }

                    for (int j = 0; j < 2; j++) {
                        for (int k = 0; k < brojac; k++) {
                            //Log.d("POLJA", "polje je " + stringovi[k][j]);
                        }
                    }
                    TableLayout stk = (TableLayout) findViewById(R.id.table_main);
                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.parseColor("#002D62"));
                    tbrow0.setPadding(2, 0, 2, 6);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(12);
                    String s = sifra + "      " + naziv;
                    tv0.setText(s);
                    tv0.setTextColor(Color.WHITE);
                    tv0.setGravity(Gravity.CENTER);
                    tv0.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv0);
                    stk.addView(tbrow0);

                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setBackgroundColor(Color.parseColor("#FFEF00"));
                        for (int k = 0; k < 2; k++) {

                            TextView t1v = new TextView(this);
                            t1v.setPadding(3, 1, 3, 1);
                            t1v.setTextSize(12);
                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.LEFT);

                            tbrow.setPadding(2, 5, 2, 5);
                            tbrow.setGravity(Gravity.CENTER);
                            tbrow.setBackgroundColor(Color.parseColor("#FFEF00"));
                            switch (k){
                                case 0:
                                    String p = "  raf: " + stringovi[j][k] + "             količina: " + stringovi[j][k+1];
                                    t1v.setText(p);
                                    tbrow.addView(t1v);
                                    break;
                                case 1:
                                    break;
                            }
                        }

                        tbrow.setBackgroundColor(Color.parseColor("#FFEF00"));
                        stk.addView(tbrow);

                    }
                    i = 0;
                    brojac = 0;
                }
            }
        }catch (Exception ex){
            Log.e("error", ex.getMessage());
        }
    }

    public void Nazad(View v){
        Intent intent = new Intent(this, Meni.class);
        startActivity(intent);
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(String user, String password, String database, String server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String connectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionURL = "jdbc:jtds:sqlserver://" + server + ":1433;databaseName=" + database + ";user=" + user + ";password=" + password + ";";

            connection = DriverManager.getConnection(connectionURL);
        }catch(Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }
        return connection;
    }
}