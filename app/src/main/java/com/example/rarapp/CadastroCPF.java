package com.example.rarapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class CadastroCPF extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_cpf);
            TextView nomeUser = findViewById(R.id.textView_nomeUser);
            EditText cpf = findViewById(R.id.editText_CPF);

            MaskEditTextChangedListener maskCPF = new MaskEditTextChangedListener("###.###.###-##", cpf);

            cpf.addTextChangedListener(maskCPF);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
            String nome = extras.getString("nome");
            nomeUser.setText(String.valueOf(nome));
            }
        }

        public void Voltar (View view) {

            Intent intent = new Intent(this, CadastroNome.class);
            startActivity(intent);
        }

        public void ProximoFull (View view) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String nome = extras.getString("nome");
                EditText etcpf = findViewById(R.id.editText_CPF);
                String[] output = etcpf.getText().toString().split(Pattern.quote("."));
                String cpfconc = output[0] + output[1] + output[2];
                String[] output2 = cpfconc.split(Pattern.quote("-"));
                String cpfconc2 = output2[0] + output2[1];
                int cpf = Integer.valueOf(cpfconc2);

                String nomet = nome;
                int cpft = cpf;

                Intent i = new Intent(CadastroCPF.this, CadastroFull.class);
                i.putExtra("nomet", nomet);
                i.putExtra("cpft", cpft);
                startActivity(i);
            }
        }
    }
