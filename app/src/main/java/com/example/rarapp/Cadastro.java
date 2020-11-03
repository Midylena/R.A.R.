package com.example.rarapp;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;

        import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
    }

    public void voltar (View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Comecar (View view) {

        Intent intent = new Intent(this, CadastroNome.class);
        startActivity(intent);
    }
}
