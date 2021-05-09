package com.example.bakalauras;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Activity_RestoranoInformacija extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, liko_simboliu;
    ImageView imageView;
    EditText komentaras;
    //List<Restoranas> restoranai = new ArrayList<>();
    List<Restoranas> restoranai;
    List<Komentaras> komentarai;
    Komentaras komentarasClass;
    int restoranoid;
    public static final String EXTRA_RESTORANAS = "RESTORANAS";
    public static final String EXTRA_ZEMELAPIOTIPAS = "1";
    Button button, button2, button3, button4, b_prideti;
    int LogIn;
    public static final String EXTRA_INT = "LogIn";

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__restorano_informacija);

        Intent intent = getIntent();
        restoranoid = intent.getIntExtra(Activity_Restoranai.EXTRA_RESTORANAS, -1);
        LogIn = intent.getIntExtra(Activity_Restoranai.EXTRA_INT, -1);

        dataBaseHelper = new DataBaseHelper(Activity_RestoranoInformacija.this);
        restoranai = dataBaseHelper.getRestoranasSingle(restoranoid);

        textView1 = (TextView) findViewById(R.id.info_pavadinimas);
        textView2 = (TextView) findViewById(R.id.info_tipas);
        textView3 = (TextView) findViewById(R.id.info_adresas);
        textView4 = (TextView) findViewById(R.id.info_darboLaikas);
        textView5 = (TextView) findViewById(R.id.info_svetaine);
        textView6 = (TextView) findViewById(R.id.info_pastas);
        textView7 = (TextView) findViewById(R.id.info_telefonas);
        textView8 = (TextView) findViewById(R.id.info_aprasymas);
        textView9 = (TextView) findViewById(R.id.info_miestas);
        imageView = (ImageView) findViewById(R.id.InfoView);

        textView1.setText(restoranai.get(0).getPavadinimas());
        textView2.setText(restoranai.get(0).getTipas());
        textView3.setText(restoranai.get(0).getAdresas());
        textView4.setText(restoranai.get(0).getDarboLaikas());
        textView5.setText(restoranai.get(0).getURL());
        textView6.setText(restoranai.get(0).getElPastas());
        textView7.setText(Integer.toString(restoranai.get(0).getTelNr()));
        textView8.setText(restoranai.get(0).getAprasymas());
        textView9.setText(restoranai.get(0).getMiestas());
        imageView.setImageBitmap(restoranai.get(0).getPaveiksliukas());


        button = (Button) findViewById(R.id.button_rastiZemelapy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity_Map();
            }
        });

        button2 = (Button) findViewById(R.id.button_rodytiMeniu);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity_Meniu();
            }
        });

        button3 = (Button) findViewById(R.id.button_info_redaguoti);
        if(LogIn != 1){
            button3.setVisibility(View.GONE);
        }
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity_RestoranoRedagavimas();
            }
        });

        button4 = (Button) findViewById(R.id.button_info_delete);
        if(LogIn != 1){
            button4.setVisibility(View.GONE);
        }
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseHelper = new DataBaseHelper(Activity_RestoranoInformacija.this);
                dataBaseHelper.deleteRestoranas(restoranoid);
                Toast.makeText(Activity_RestoranoInformacija.this, "Restoranas ištrintas", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        recyclerView = findViewById(R.id.RecyclerView_komentarai);
        recyclerView.setHasFixedSize(false);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);

        komentarai = dataBaseHelper.getKomentaras(restoranoid);

        programAdapter = new KomentaraiAdapter(this, komentarai, recyclerView, LogIn);
        recyclerView.setAdapter(programAdapter);

        komentaras = (EditText) findViewById(R.id.Komentaras);
        liko_simboliu = (TextView) findViewById(R.id.likoSimboliu);

        komentaras.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {   //Convert the Text to String
                int simboliai = 300 - komentaras.length();
                liko_simboliu.setText(Integer.toString(simboliai));
                liko_simboliu.setTextColor(Color.parseColor("#000000"));
                if(komentaras.length() > 300){
                    liko_simboliu.setTextColor(Color.parseColor("#FF0000"));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
        });

        b_prideti = (Button) findViewById(R.id.button_komentaras_prideti);
        b_prideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LogIn == -1){
                    Toast.makeText(Activity_RestoranoInformacija.this, "Šiai funkcijai turite būti prisijungęs", Toast.LENGTH_SHORT).show();
                } else{
                    if(komentaras.length() <= 300 && komentaras.length() > 0){
                        komentarasClass = new Komentaras(-1, LogIn, restoranoid, komentaras.getText().toString());
                        dataBaseHelper = new DataBaseHelper(Activity_RestoranoInformacija.this);
                        dataBaseHelper.addKomentaras(komentarasClass);

                        Toast.makeText(Activity_RestoranoInformacija.this, "Komentaras pridėtas", Toast.LENGTH_SHORT).show();

                        komentarai = dataBaseHelper.getKomentaras(restoranoid);
                        programAdapter = new KomentaraiAdapter(Activity_RestoranoInformacija.this, komentarai, recyclerView, LogIn);
                        recyclerView.setAdapter(programAdapter);
                    } else {
                        Toast.makeText(Activity_RestoranoInformacija.this, "Komentaras perilgas arba tuščias", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        dataBaseHelper = new DataBaseHelper(Activity_RestoranoInformacija.this);
        List<Vartotojas> vartotojas = dataBaseHelper.getUserFullbyID(LogIn);

        if(LogIn != -1){
            if (vartotojas.get(0).getRole() == 1) {
                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            } else {
                getMenuInflater().inflate(R.menu.menu_main_vartotojas, menu);
                return true;
            }
        } else {
            getMenuInflater().inflate(R.menu.menu_main_vartotojas, menu);
            return true;
        }
    }

    //menuSelect
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_profilis:
                if(LogIn > -1) {
                    OpenActivity_Profilis();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_pridetiRestorana: {
                dataBaseHelper = new DataBaseHelper(Activity_RestoranoInformacija.this);
                List<Vartotojas> vartotojas = dataBaseHelper.getUserFullbyID(LogIn);
                if(vartotojas.get(0).getRole() == 1){
                    OpenActivity_PridetiRestorana();
                    return true;
                } else {
                    Toast.makeText(this, "Šiai paslaugai teisės neturite", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
            case R.id.menu_isimintiSarasas:
                if(LogIn > -1) {
                    OpenActivity_Isiminti();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_marsSarasas:
                if(LogIn > -1) {
                    OpenActivity_MarsSarasas();
                    return true;
                } else {
                    Toast.makeText(this,"Šiai funkcijai turite būti prisijungęs",Toast.LENGTH_SHORT).show();
                    return true;
                }
            case R.id.menu_atsijungti:
                OpenActivity_MainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void OpenActivity_RestoranoRedagavimas(){
        Intent intent = new Intent(this, Activity_RestoranoRedagavimas.class);
        intent.putExtra(EXTRA_RESTORANAS, restoranoid);
        startActivity(intent);
    }

    public void OpenActivity_Map(){
        Intent intent = new Intent(this, Activity_Zemelapis.class);
        intent.putExtra(EXTRA_RESTORANAS, restoranoid);
        intent.putExtra(EXTRA_ZEMELAPIOTIPAS, 1);
        startActivity(intent);
    }

    public void OpenActivity_Meniu(){
        Intent intent = new Intent(this, Activity_Meniu.class);
        intent.putExtra(EXTRA_RESTORANAS, restoranoid);
        startActivity(intent);
    }


    // menu bendras
    public void OpenActivity_Profilis(){
        Intent intent = new Intent(this, Activity_Profilis.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_MainActivity(){
        LogIn = -1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_Isiminti(){
        Intent intent = new Intent(this, Activity_IsimintuSarasas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_MarsSarasas(){
        Intent intent = new Intent(this, Activity_MarsrutoSarasas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity_PridetiRestorana(){
        Intent intent = new Intent(this, Activity_PridetiRestorana.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }






}
