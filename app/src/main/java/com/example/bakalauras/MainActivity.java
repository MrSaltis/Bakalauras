package com.example.bakalauras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView1;
    private TextView textView2;
    private EditText editText1;
    private EditText editText2;
    public static final String EXTRA_INT = "com.example.bakalauras.EXTRA_INT";
    public int LogIn = -1;
    List<Vartotojas> vartotojas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = getIntent();
        //LogIn = intent.getIntExtra(MainActivity.EXTRA_INT, -1);


        button = (Button) findViewById(R.id.button_prisijungti);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1 = (EditText) findViewById(R.id.pris_vardas);
                editText2 = (EditText) findViewById(R.id.pris_slaptazodis);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                Boolean user = dataBaseHelper.getUser(editText1.getText().toString(), editText2.getText().toString());
                if (user)
                {
                    vartotojas = dataBaseHelper.getUserFullbyName(editText1.getText().toString());
                    LogIn = vartotojas.get(0).getID();
                    OpenActivity();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Blogas vartotojo vardas ar slaptažodis",Toast.LENGTH_SHORT).show();
                }
            }
        });

        textView1 = (TextView) findViewById(R.id.text_svečias);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogIn = -1;
                OpenActivity();
            }
        });

        textView2 = (TextView) findViewById(R.id.text_registruotis);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenActivity2();
            }
        });
    }

    public void OpenActivity(){
        Intent intent = new Intent(this, Activity_Restoranai.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }

    public void OpenActivity2(){
        Intent intent = new Intent(this, Activity_Registruotis.class);
        intent.putExtra(EXTRA_INT, LogIn);
        startActivity(intent);
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.exit(0);
        return super.onOptionsItemSelected(item);
    }*/
}
