package com.example.rarapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CadastroSenha extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_senha);

        TextView info = findViewById(R.id.textView_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nomed = extras.getString("nomet");
            String emaild = extras.getString("emailt");
            String cpfd = extras.getString("cpft");
            String rgd = extras.getString("rgt");
            String teld = extras.getString("teld");
            String sexod = extras.getString("sexot");
            String datad = extras.getString("datat");
            info.setText(nomed+emaild+cpfd+rgd+teld+sexod+datad);
        }
    }
}
