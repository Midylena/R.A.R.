package com.example.rarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroNome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_nome);
    }

    public void Voltar (View view) {

        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void ProximoCPF (View view) {

        EditText etnome = findViewById(R.id.editText_Nome);
        String nome = etnome.getText().toString();

        Intent i = new Intent(CadastroNome.this, CadastroCPF.class);
        i.putExtra("nome", nome);
        startActivity(i);
    }
}
