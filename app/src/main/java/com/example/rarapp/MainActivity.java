package com.example.rarapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.core.AppSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsuarioDAO dao = new UsuarioDAO();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //boolean resultado = dao.inserirUsuario(new Usuario(0,"Lucas","lucas.damaral@gmail.com","Super@lucas"));
        //Log.d("Exemplo", resultado + "");

        //ArrayList<Usuario> lista = dao.buscarTodosUsuarios();
        //Log.d("Exemplo", lista.toString());

        //Usuario usr = dao.buscarUsuarioPorID(1);
        //Log.d("Exemplo", usr.toString());

        //Usuario usr = dao.buscarIDLogin("1", "1234");
        //Log.d("Exemplo", usr.toString());
        //boolean resultado = dao.excluirUsuario(new Usuario(14,"Lucas","lucas.damaral@gmail.com","Super@lucas"));
        //Log.d("Exemplo", resultado + "");

        //boolean resultado = dao.atualizarUsuario(new Usuario(14,"Lucas D'Amaral Matheus","lucas.damaral@gmail.com","Pro@Player"));
        //Log.d("Exemplo", resultado + "");

        //boolean resultado = dao.excluirUsuarioPorID(12);
        //Log.d("Exemplo", resultado + "");

        //String resultado = dao.Logar("milenape.alves@gmail.com","Florzinh@00");
        //Log.d("Exemplo", resultado + "");

        String appID = "244870159fb6da5"; // Replace with your App ID
        String region = "us"; // Replace with your App Region ("eu" or "us")

        AppSettings appSettings=new AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(region).build();

        CometChat.init(this, appID,appSettings, new CometChat.CallbackListener<String>() {


            @Override
            public void onSuccess(String successMessage) {
                Log.d("TAG", "Initialization completed successfully");
            }
            @Override
            public void onError(CometChatException e) {
                Log.d("TAG", "Initialization failed with exception: " + e.getMessage());
            }
        });
    }


    public void EsqueceuASenha(View view){
        Intent intent = new Intent(this, EsqueceuASenha.class);
        startActivity(intent);
    }

    public void Cadastro(View view){
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }

    public void Login(View view) {

        UsuarioDAO dao = new UsuarioDAO();

        EditText etemail = findViewById(R.id.editText_EmaiUsuario);
        EditText etsenha = findViewById(R.id.editText_TextPassword);

        String email = etemail.getText().toString();
        String senha = etsenha.getText().toString();
        String resultado = dao.Logar(email, senha);
        Log.d("Exemplo", resultado + "");

        //Usuario usr = dao.buscarIDLogin("1", "1234");
        //Log.d("Exemplo", usr.toString());

        String UID = email; // Replace with the UID of the user to login
        String authKey = "62b4adc9f9d95a46a03c1b02da1658a540f0211b"; // Replace with your App Auth Key

        if (CometChat.getLoggedInUser() == null) {
            CometChat.login(UID, authKey, new CometChat.CallbackListener<User>() {

                @Override
                public void onSuccess(User user) {
                    Log.d("TAG", "Login Successful : " + user.toString());
                    if (resultado.equals("logado")) {
                        Intent intent = new Intent(MainActivity.this, TelaInicial.class);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Email ou Senha Inválidos", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onError(CometChatException e) {
                    Log.d("TAG", "Login failed with exception: " + e.getMessage());
                }
            });
        } else {
            // User already logged in
            Toast.makeText(MainActivity.this, "Você esta logade em outro dispositivo, se não for o caso, por favor entre em contato imediatamente",
                    Toast.LENGTH_LONG).show();
        }
    }
}