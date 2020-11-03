package com.example.rarapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EsqueceuASenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esqueceu_a_senha);
    }

    public void voltar (View view) {

        Intent intent = new Intent(this, CadastroFull.class);
        startActivity(intent);
    }

}
