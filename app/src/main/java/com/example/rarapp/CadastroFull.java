package com.example.rarapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroFull extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_full);

        radioGroup = findViewById(R.id.radioGroup);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
        public void ProximoFull (View view) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String nomer = extras.getString("nomet");
                int cpfr = extras.getInt("cpft");

                EditText email = findViewById(R.id.editText_email);
                EditText rg = findViewById(R.id.editText_RG);
                EditText tel = findViewById(R.id.editText_Tel);
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                EditText data = findViewById(R.id.editText_Data);


                String emailr = email.getText().toString();
                int rgr = Integer.parseInt(String.valueOf(rg.getText()));
                int telr = Integer.parseInt(String.valueOf(tel.getText()));
                int sexor = Integer.parseInt(String.valueOf(radioButton.getText()));
                String datar = data.getText().toString();


                String nomet = nomer;
                String emailt = emailr;
                int cpft = cpfr;
                int rgt = rgr;
                int telt = telr;
                int sexot = sexor;
                String datat = datar;

                Intent i = new Intent(CadastroFull.this, CadastroSenha.class);
                i.putExtra("nomet", nomet);
                i.putExtra("emailt", emailt);
                i.putExtra("cpft", cpft);
                i.putExtra("rgt", rgt);
                i.putExtra("telt", telt);
                i.putExtra("sexot", sexot);
                i.putExtra("datat", datat);
                startActivity(i);
            }
        }
    }
