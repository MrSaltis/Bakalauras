package com.example.bakalauras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Activity_PridetiRestorana extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_INT = "LogIn";
    int LogIn;
    EditText ed_pavadinimas, ed_Xcoord, ed_Ycoord, ed_adresas, ed_telNr, ed_pastas, ed_url, ed_darboLaikas, ed_aprasymas;
    ImageView image;
    Spinner sp_tipas, sp_miestas;
    String Miestas, Tipas;
    Button btn_paveiksliukas, btn_prideti;
    Bitmap bitmap;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__prideti_restorana);

        Intent intent = getIntent();
        LogIn = intent.getIntExtra(MainActivity.EXTRA_INT, -1);

        sp_miestas = (Spinner) findViewById(R.id.spinner_miestas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Miestai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_miestas.setAdapter(adapter);
        sp_miestas.setOnItemSelectedListener(this);

        sp_tipas = (Spinner) findViewById(R.id.spinner_tipas);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Tipai, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipas.setAdapter(adapter2);
        sp_tipas.setOnItemSelectedListener(this);




        btn_prideti = (Button) findViewById(R.id.button_sukurti);
        btn_prideti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_pavadinimas = (EditText) findViewById(R.id.ed_pavadinimas);
                ed_adresas = (EditText) findViewById(R.id.ed_Adresas);
                ed_Ycoord = (EditText) findViewById(R.id.ed_Ycoord);
                ed_Xcoord = (EditText) findViewById(R.id.ed_Xcoord);
                ed_telNr = (EditText) findViewById(R.id.ed_Tel);
                ed_url = (EditText) findViewById(R.id.ed_Url);
                ed_darboLaikas = (EditText) findViewById(R.id.ed_DarboLaikas);
                ed_aprasymas = (EditText) findViewById(R.id.ed_Aprasymas);
                ed_pastas = (EditText) findViewById(R.id.ed_Pastas);

                Restoranas restoranas;

                restoranas = new Restoranas(-1, ed_pavadinimas.getText().toString(), Miestas, Integer.parseInt(ed_Xcoord.getText().toString()), Integer.parseInt(ed_Ycoord.getText().toString()),
                        Tipas, ed_adresas.getText().toString(), Integer.parseInt(ed_telNr.getText().toString()), ed_pastas.getText().toString(), ed_darboLaikas.getText().toString(),
                        ed_url.getText().toString(), ed_aprasymas.getText().toString(), bitmap);
/*
                try{
                    restoranas = new Restoranas(-1, ed_pavadinimas.getText().toString(), Miestas, Integer.parseInt(ed_Xcoord.getText().toString()), Integer.parseInt(ed_Ycoord.getText().toString()),
                            Tipas, ed_adresas.getText().toString(), Integer.parseInt(ed_telNr.getText().toString()), ed_pastas.getText().toString(), ed_darboLaikas.getText().toString(),
                            ed_url.getText().toString(), ed_aprasymas.getText().toString(), bitmap);
                }catch (Exception e){
                    restoranas = new Restoranas(-1, "error", "error", 1, 1,
                            "error", "error", 111, "error", "error",
                            "error", "error", bitmap);
                }*/

                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_PridetiRestorana.this);
                boolean sucess = dataBaseHelper.addRestoranas(restoranas);

                Toast.makeText(Activity_PridetiRestorana.this, "sucess= " + sucess, Toast.LENGTH_SHORT).show();
            }
        });



        image = (ImageView) findViewById(R.id.image_restoranas);

        btn_paveiksliukas = (Button) findViewById(R.id.button_Paveiksliukas);
        btn_paveiksliukas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });






    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this, "Prieiga nesuteikta!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image.setImageURI(data.getData());

            Uri targetUri = data.getData();
            try {
                 bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner_miestas){
            Miestas = parent.getItemAtPosition(position).toString();
        } else if (parent.getId() == R.id.spinner_tipas){
            Tipas = parent.getItemAtPosition(position).toString();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_PridetiRestorana.this);
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
                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_PridetiRestorana.this);
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
            case R.id.menu_suvestine:
                OpenActivity_Administravimas();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void OpenActivity_Administravimas(){
        Intent intent = new Intent(this, Activity_Administravimas.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }



}
