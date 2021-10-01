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

public class Raf extends AppCompatActivity {

    ActionBar actionBar;
    LinearLayout mainLayout;
    Button pretrazibtn;
    Button nazadbtn;
    EditText sifra_rafa;
    Connection con;
    String sifre[];
    String nazivi[];
    String kolicine[];
    int i = 0;
    int brojac = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raf);
        pretrazibtn = (Button)findViewById(R.id.nadjiraf);
        nazadbtn = (Button)findViewById(R.id.nazad2);
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

    public void nadjiRaf(View v){
        TableLayout tl = (TableLayout)findViewById(R.id.table_main2);
        tl.removeAllViews();
        mainLayout = (LinearLayout)findViewById(R.id.raf);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
        sifra_rafa = (EditText)findViewById(R.id.unosrafa);
        sifra_rafa.toString();
        sifre = new String[100];
        nazivi = new String[100];
        kolicine = new String[100];
        try{
            con = connectionClass(ConnectionClass.un, ConnectionClass.pass,ConnectionClass.db,ConnectionClass.ip);
            if(con != null) {
                PreparedStatement statement0 = con.prepareStatement("exec getStorageBinMaterials '" + sifra_rafa.getText().toString() + "'");
                ResultSet rs0 = statement0.executeQuery();
                //Log.d("konekcija", "uspesna");
                if (!rs0.next()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Raf " + sifra_rafa.getText() + " nije pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "Raf " + sifra_rafa.getText() + " je pronadjen.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    PreparedStatement statement = con.prepareStatement("exec getStorageBinMaterials '" + sifra_rafa.getText().toString() + "'");
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        sifre[i] = rs.getString("SifraArtika");
                        nazivi[i] = rs.getString("NazivArtikla");
                        kolicine[i] = rs.getString("Kolicina");

                        i++;
                        brojac++;
                    }
                    String[][] stringovi = new String[brojac][3];
                    for (int j = 0; j < 3; j++) {
                        switch (j) {
                            case 0:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = sifre[k];

                                }
                                break;
                            case 1:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = nazivi[k];

                                }
                                break;
                            case 2:
                                for (int k = 0; k < brojac; k++) {
                                    stringovi[k][j] = kolicine[k];

                                }
                                break;
                        }
                    }

                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < brojac; k++) {
                            //Log.d("POLJA", "polje je " + stringovi[k][j]);
                        }
                    }
                    TableLayout stk = (TableLayout) findViewById(R.id.table_main2);
                    TableRow tbrow0 = new TableRow(this);
                    tbrow0.setBackgroundColor(Color.parseColor("#002D62"));
                    tbrow0.setPadding(2, 5, 2, 2);
                    TextView tv0 = new TextView(this);
                    tv0.setTextSize(10);
                    tv0.setText("ŠIFRA");
                    tv0.setTextColor(Color.WHITE);
                    tv0.setGravity(Gravity.RIGHT);
                    tv0.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv0);
                    TextView tv1 = new TextView(this);
                    tv1.setTextSize(10);
                    tv1.setText("NAZIV");
                    tv1.setTextColor(Color.WHITE);
                    tv1.setGravity(Gravity.CENTER);
                    tv1.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv1);
                    TextView tv2 = new TextView(this);
                    tv2.setTextSize(10);
                    tv2.setText("KOLICINA");
                    tv2.setTextColor(Color.WHITE);
                    tv2.setGravity(Gravity.LEFT);
                    tv2.setPadding(3, 1, 3, 1);
                    tbrow0.addView(tv2);
                    tbrow0.setGravity(Gravity.CENTER);
                    stk.addView(tbrow0);

                    for (int j = 0; j < brojac; j++) {
                        TableRow tbrow = new TableRow(this);
                        tbrow.setBackgroundColor(Color.parseColor("#FFEF00"));
                        for (int k = 0; k < 3; k++) {

                            TextView t1v = new TextView(this);
                            t1v.setPadding(3, 1, 3, 1);
                            t1v.setText(stringovi[j][k]);
                            t1v.setTextSize(9);
                            //t1v.setVerticalFadingEdgeEnabled(true);

                            //t1v.setBackgroundColor(Color.rgb(236, 93, 85));

                            t1v.setBackgroundColor(Color.parseColor("#FFEF00"));


                            t1v.setTextColor(Color.BLACK);
                            t1v.setGravity(Gravity.CENTER);
                            tbrow.addView(t1v);
                            tbrow.setPadding(2, 5, 2, 2);
                            tbrow.setGravity(Gravity.CENTER);
                            //tbrow.setMinimumWidth(650);
                            tbrow.setBackgroundResource(R.drawable.border);
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