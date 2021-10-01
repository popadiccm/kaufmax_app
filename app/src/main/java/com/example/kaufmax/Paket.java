package com.example.kaufmax;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

public class Paket extends AppCompatActivity {

    ActionBar actionBar;
    LinearLayout mainLayout;
    Button pretrazibtn;
    Button nazadbtn;
    EditText sifra_paketa;
    Connection con;
    String sifre[];
    String nazivi[];
    String kolicine[];
    String rbr[];
    String kodovi[];
    String kutije[];
    String ERP_br;
    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);
        pretrazibtn = (Button)findViewById(R.id.nadjipaket);
        nazadbtn = (Button)findViewById(R.id.nazad3);
        pretrazibtn.setBackgroundColor(Color.rgb(255, 239, 0));
        nazadbtn.setBackgroundColor(Color.rgb(255, 239, 0));
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Meni.class);
        startActivity(intent);
        //super.onBackPressed();
    }

    public void nadjiPaket(View v){
        TableLayout tl = (TableLayout)findViewById(R.id.table_main3);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.paket);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        sifra_paketa = (EditText)findViewById(R.id.unospaketa);
        sifra_paketa.toString();
        sifre = new String[100];
        nazivi = new String[100];
        kolicine = new String[100];
        rbr = new String[100];
        kutije = new String[100];
        kodovi = new String[100];
        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("exec getBoxesForDocument " + sifra_paketa.getText().toString());
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Paket " + sifra_paketa.getText() + " nije pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Paket " + sifra_paketa.getText() + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("exec getBoxesForDocument " + sifra_paketa.getText().toString());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {

                        rbr[i] = rs.getString("RBR");
                        sifre[i] = rs.getString("Šifra");
                        nazivi[i] = rs.getString("Naziv");
                        kodovi[i] = rs.getString("BarKod");
                        kolicine[i] = rs.getString("kolicina");
                        kutije[i] = rs.getString("Kutija");
                        ERP_br = rs.getString("ERPDocumentNumber");
                        i++;
                        brojac++;
                    }

                    String[][] stringovi = new String[brojac][6];
                    for (int j = 0; j < 6; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = rbr[k];
                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = sifre[k];
                                }
                                break;
                            case 2:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = nazivi[k];
                                }
                                break;
                            case 3:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kodovi[k];
                                }
                                break;
                            case 4:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine[k];
                                }
                                break;
                            case 5:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kutije[k];
                                }
                                break;
                        }

                    }

                    for (int j = 0; j < 6; j++) {
                        for (int k = 0; k < brojac; k++) {
                            //Log.d("POLJA", "polje je " + stringovi[k][j]);
                        }
                    }



                    /*

                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.parseColor("#f1f1f1"));
                    tbrow0.setPadding(2, 5, 2, 2);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(8);
                    tv0.setText("SIFRA");
                    tv0.setTextColor(Color.BLACK);
                    tv0.setGravity(Gravity.RIGHT);
                    tv0.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv0);
                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(8);
                    tv1.setText("NAZIV");
                    tv1.setTextColor(Color.BLACK);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv1);
                    TextView tv2 = new TextView(this);
                    tv2.setTextSize(8);
                    tv2.setText("KOLICINA");
                    tv2.setTextColor(Color.BLACK);
                    tv2.setGravity(Gravity.LEFT);
                    tv2.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv2);
                    tbrow0.setGravity(Gravity.CENTER);
                    stk.addView(tbrow0);

                     */
                    TableLayout stk = (TableLayout) findViewById(R.id.table_main3);
                    TableRow trow = new TableRow(this);
                    trow.setBackgroundColor(Color.parseColor("#002D62"));
                    trow.setPadding(2, 0, 2, 4);
                    trow.setWeightSum(1);
                    trow.setGravity(Gravity.CENTER);
                    trow.setMinimumWidth(650);
                    trow.setBackgroundResource(R.drawable.border);
                    TextView t0v = new TextView(this);
                    t0v.setPadding(3, 0, 3, 1);
                    t0v.setTextSize(12);
                    t0v.setTextColor(Color.WHITE);
                    t0v.setGravity(Gravity.CENTER);
                    t0v.setBackgroundColor(Color.parseColor("#002D62"));

                    TextView t3v = new TextView(this);
                    t3v.setPadding(3, 0, 3, 1);
                    t3v.setTextSize(12);
                    t3v.setTextColor(Color.WHITE);
                    t3v.setGravity(Gravity.CENTER);
                    t3v.setBackgroundColor(Color.parseColor("#002D62"));

                    TextView t4v = new TextView(this);
                    t4v.setPadding(3, 0, 3, 1);
                    t4v.setTextSize(12);
                    t4v.setTextColor(Color.WHITE);
                    t4v.setGravity(Gravity.CENTER);
                    t4v.setBackgroundColor(Color.parseColor("#002D62"));

                    t0v.setText("  ");
                    trow.addView(t0v);
                    String s0 = "ERPDocumentNumber:   " + ERP_br;
                    t3v.setText(s0);
                    trow.addView(t3v);
                    t4v.setText("  ");
                    trow.addView(t4v);
                    stk.addView(trow);


                    TableRow trow5 = new TableRow(this);
                    trow5.setBackgroundColor(Color.parseColor("#002D62"));
                    trow5.setPadding(2, 4, 2, 4);
                    trow5.setWeightSum(1);
                    trow5.setGravity(Gravity.CENTER);
                    trow5.setMinimumWidth(650);
                    trow5.setBackgroundResource(R.drawable.border);

                    TextView t5v = new TextView(this);
                    t5v.setPadding(5, 0, 3, 1);
                    t5v.setTextSize(10);
                    t5v.setTextColor(Color.WHITE);
                    t5v.setGravity(Gravity.LEFT);
                    t5v.setBackgroundColor(Color.parseColor("#002D62"));

                    TextView t6v = new TextView(this);
                    t6v.setPadding(3, 0, 3, 1);
                    t6v.setTextSize(10);
                    t6v.setTextColor(Color.WHITE);
                    t6v.setGravity(Gravity.CENTER);
                    t6v.setBackgroundColor(Color.parseColor("#002D62"));

                    TextView t7v = new TextView(this);
                    t7v.setPadding(0, 0, 3, 1);
                    t7v.setTextSize(10);
                    t7v.setTextColor(Color.WHITE);
                    t7v.setGravity(Gravity.LEFT);
                    t7v.setBackgroundColor(Color.parseColor("#002D62"));


                    t5v.setText("KUTIJA");
                    trow5.addView(t5v);
                    t6v.setText("ŠIFRA                          BARKOD");
                    trow5.addView(t6v);
                    t7v.setText("KOLIČINA");
                    trow5.addView(t7v);
                    stk.addView(trow5);






                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setBackgroundColor(Color.parseColor("#FFEF00"));
                        TableRow tbrow0 = new TableRow(this);
                        tbrow0.setBackgroundColor(Color.parseColor("#FFEF00"));
                        for (int k = 0; k < 6; k++) {
                            TextView t1v = new TextView(this);
                            t1v.setPadding(3, 0, 3, 1);
                            t1v.setTextSize(10);
                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            t1v.setBackgroundColor(Color.parseColor("#FFEF00"));
                            TextView t2v = new TextView(this);
                            t2v.setPadding(3, 1, 3, 0);
                            t2v.setTextSize(10);
                            t2v.setTextColor(Color.BLACK);
                            t2v.setGravity(Gravity.CENTER);
                            t2v.setBackgroundColor(Color.parseColor("#FFEF00"));

                            tbrow.setPadding(2, 0, 2, 4);
                            tbrow.setWeightSum(1);
                            tbrow.setGravity(Gravity.CENTER);

                            tbrow.setBackgroundResource(R.drawable.border);
                            tbrow0.setPadding(2, 5, 2, 0);
                            tbrow0.setWeightSum(1);
                            tbrow0.setGravity(Gravity.CENTER);
                            tbrow0.setBackgroundResource(R.drawable.border);
                            switch(k){
                                case 0:
                                    t2v.setText(" ");
                                    t1v.setText(stringovi[j][5]);
                                    tbrow.addView(t1v);
                                    tbrow0.addView(t2v);
                                    break;
                                case 1:
                                    t2v.setText(stringovi[j][2]);
                                    t2v.setTextSize(10);
                                    t1v.setTextSize(10);
                                    String s = stringovi[j][k] + "              " + stringovi[j][k+2];
                                    t1v.setText(s);
                                    tbrow.addView(t1v);
                                    tbrow0.addView(t2v);
                                    break;
                                case 2:
                                case 3:
                                case 4:
                                    break;
                                case 5:
                                    t2v.setText(" ");
                                    t1v.setText(stringovi[j][4]);
                                    tbrow.addView(t1v);
                                    tbrow0.addView(t2v);
                                    break;
                            }
                            /*
                            if(k == 2){
                                t2v.setText(stringovi[j][k]);
                                tbrow0.addView(t2v);
                            }else if(k == 5){
                                t1v.setText(stringovi[j][k]);
                                tbrow.addView(t1v);
                            }else{
                                t2v.setText("  ");
                                t1v.setText(stringovi[j][k]);
                                tbrow.addView(t1v);
                                tbrow0.addView(t2v);
                            }*/
                        }
                        stk.addView(tbrow0);
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