package com.example.rarapp;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;

public class TelaInicial extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        Button bagend = findViewById(R.id.button_Agendar);
        bagend.setEnabled(false);

        Button braricon = findViewById(R.id.button_RarIcon);
        braricon.setEnabled(false);

        Button bcomofinc = findViewById(R.id.button_ComoFunc);
        bcomofinc.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nome = extras.getString("nome");

        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


    }
    @Override
    protected void onDestroy(){
        // call the superclass method first
        super.onDestroy();

        CometChat.logout(new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d("TAG", "Logout completed successfully");
                Intent intent = new Intent(TelaInicial.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d("TAG", "Logout failed with exception: " + e.getMessage());
                Toast.makeText(TelaInicial.this, "Erro ao deslogar do app", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ConectarRAR (View view) {

        Intent intent = new Intent(this, ConectarRAR.class);
        startActivity(intent);

    }

    public void Sair (View view) {

        CometChat.logout(new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d("TAG", "Logout completed successfully");
                Intent intent = new Intent(TelaInicial.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d("TAG", "Logout failed with exception: " + e.getMessage());
                Toast.makeText(TelaInicial.this, "Erro ao deslogar do app", Toast.LENGTH_LONG).show();
            }
        });
    }
}
