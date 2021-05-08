package com.example.bakalauras;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.List;

public class Activity_RestoranoRedagavimas extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText red_pavadinimas, red_Xcoord, red_Ycoord, red_adresas, red_telNr, red_pastas, red_url, red_darboLaikas, red_aprasymas;
    ImageView image;
    Spinner sp_tipas, sp_miestas;
    String Miestas, Tipas;
    Button btn_paveiksliukas, btn_redaguoti;
    Bitmap bitmap;
    List<Restoranas> restoranai;
    int restoranoid;
    public static final String EXTRA_RESTORANAS = "RESTORANAS";

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__restorano_redagavimas);

        Intent intent = getIntent();
        restoranoid = intent.getIntExtra(Activity_Restoranai.EXTRA_RESTORANAS, -1);

        sp_miestas = (Spinner) findViewById(R.id.spinner_red_miestas);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Miestai, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_miestas.setAdapter(adapter);
        sp_miestas.setOnItemSelectedListener(this);

        sp_tipas = (Spinner) findViewById(R.id.spinner_red_tipas);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.Tipai, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipas.setAdapter(adapter2);
        sp_tipas.setOnItemSelectedListener(this);

        dataBaseHelper = new DataBaseHelper(Activity_RestoranoRedagavimas.this);
        restoranai = dataBaseHelper.getRestoranasSingle(restoranoid);

        red_pavadinimas = (EditText) findViewById(R.id.red_pavadinimas);
        red_adresas = (EditText) findViewById(R.id.red_Adresas);
        red_Ycoord = (EditText) findViewById(R.id.red_Ycoord);
        red_Xcoord = (EditText) findViewById(R.id.red_Xcoord);
        red_telNr = (EditText) findViewById(R.id.red_Tel);
        red_url = (EditText) findViewById(R.id.red_Url);
        red_darboLaikas = (EditText) findViewById(R.id.red_DarboLaikas);
        red_aprasymas = (EditText) findViewById(R.id.red_Aprasymas);
        red_pastas = (EditText) findViewById(R.id.red_Pastas);
        image = (ImageView) findViewById(R.id.image_red_restoranas);

        red_pavadinimas.setText(restoranai.get(0).getPavadinimas());
        //textView2.setText(restoranai.get(0).getTipas());
        red_adresas.setText(restoranai.get(0).getAdresas());
        red_darboLaikas.setText(restoranai.get(0).getDarboLaikas());
        red_url.setText(restoranai.get(0).getURL());
        red_pastas.setText(restoranai.get(0).getElPastas());
        red_Xcoord.setText(Double.toString(restoranai.get(0).getXcoord()));
        red_Ycoord.setText(Double.toString(restoranai.get(0).getYcoord()));
        red_telNr.setText(Integer.toString(restoranai.get(0).getTelNr()));
        red_aprasymas.setText(restoranai.get(0).getAprasymas());
        //textView9.setText(restoranai.get(0).getMiestas());
        image.setImageBitmap(restoranai.get(0).getPaveiksliukas());
        bitmap = restoranai.get(0).getPaveiksliukas();




        btn_redaguoti = (Button) findViewById(R.id.button_redaguoti);
        btn_redaguoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                red_pavadinimas = (EditText) findViewById(R.id.red_pavadinimas);
                red_adresas = (EditText) findViewById(R.id.red_Adresas);
                red_Ycoord = (EditText) findViewById(R.id.red_Ycoord);
                red_Xcoord = (EditText) findViewById(R.id.red_Xcoord);
                red_telNr = (EditText) findViewById(R.id.red_Tel);
                red_url = (EditText) findViewById(R.id.red_Url);
                red_darboLaikas = (EditText) findViewById(R.id.red_DarboLaikas);
                red_aprasymas = (EditText) findViewById(R.id.red_Aprasymas);
                red_pastas = (EditText) findViewById(R.id.red_Pastas);


                Restoranas restoranas;

                restoranas = new Restoranas(restoranai.get(0).getRestoranoID(), red_pavadinimas.getText().toString(),
                        Miestas, Double.parseDouble(red_Xcoord.getText().toString()), Double.parseDouble(red_Ycoord.getText().toString()),
                        Tipas, red_adresas.getText().toString(), Integer.parseInt(red_telNr.getText().toString()), red_pastas.getText().toString(),
                        red_darboLaikas.getText().toString(), red_url.getText().toString(), red_aprasymas.getText().toString(), bitmap);


                DataBaseHelper dataBaseHelper = new DataBaseHelper(Activity_RestoranoRedagavimas.this);
                boolean sucess = dataBaseHelper.updateRestoranas(restoranas);

                Toast.makeText(Activity_RestoranoRedagavimas.this, "sucess= " + sucess, Toast.LENGTH_SHORT).show();

            }
        });


        btn_paveiksliukas = (Button) findViewById(R.id.button_red_Paveiksliukas);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner_red_miestas){
            Miestas = parent.getItemAtPosition(position).toString();
        }else if (parent.getId() == R.id.spinner_red_tipas){
            Tipas = parent.getItemAtPosition(position).toString();
        }
        /*switch(parent.getId()) {
            case R.id.spinner_red_miestas:
                Miestas = parent.getItemAtPosition(position).toString();
            case R.id.spinner_red_tipas:
                Tipas = parent.getItemAtPosition(position).toString();
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}