package com.example.rarapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cometchat.pro.constants.CometChatConstants;
import com.cometchat.pro.core.Call;
import com.cometchat.pro.core.CallManager;
import com.cometchat.pro.core.CallSettings;
import com.cometchat.pro.core.CometChat;
import com.cometchat.pro.exceptions.CometChatException;
import com.cometchat.pro.models.User;

public class ConectarRAR extends AppCompatActivity {

    boolean statusVideo;
    boolean statusAudio;
    boolean modocall;
    String gpio;
    Handler handler = new Handler();
    String url = "http://192.168.0.134:50/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conectar_rar);

        handler.postDelayed(atualizaStatus, 0);

        Button bmutar = findViewById(R.id.button_mutar);
        bmutar.setVisibility(View.INVISIBLE);

        Button dcamera = findViewById(R.id.button_dcamera);
        dcamera.setVisibility(View.INVISIBLE);

        Button mcamera = findViewById(R.id.button_mcamera);
        mcamera.setVisibility(View.INVISIBLE);

        Button endcall = findViewById(R.id.button_endcall);
        endcall.setVisibility(View.INVISIBLE);

        Button iatend = findViewById(R.id.button_iniciarAtendimento);
        iatend.setVisibility(View.VISIBLE);

    }

    protected void onDestroy(){
        // call the superclass method first
        super.onDestroy();

        CometChat.logout(new CometChat.CallbackListener<String>() {
            @Override
            public void onSuccess(String successMessage) {
                Log.d("TAG", "Logout completed successfully");
                Intent intent = new Intent(ConectarRAR.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(CometChatException e) {
                Log.d("TAG", "Logout failed with exception: " + e.getMessage());
                Toast.makeText(ConectarRAR.this, "Erro ao deslogar do app", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void ligaled(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            new SolicitaDados().execute(url + gpio);
        } else {
            Toast.makeText(ConectarRAR.this, "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
        }
    }

    private class SolicitaDados extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {
            return Conexao.getDados(url[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            if (resultado != null) {
                if(resultado.contains("low")){
                    gpio = "gpio/1";
                } else if(resultado.contains("high")){
                    gpio = "gpio/0";
                }

            }
        }
    }

    private Runnable atualizaStatus = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 2000);
        }
    };

        public void startCall(View view) {

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){
                new SolicitaDados().execute(url + gpio);
            } else {
                Toast.makeText(ConectarRAR.this, "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
            }

            Button bmutar = findViewById(R.id.button_mutar);
            bmutar.setVisibility(View.VISIBLE);

            Button dcamera = findViewById(R.id.button_dcamera);
            dcamera.setVisibility(View.VISIBLE);

            Button mcamera = findViewById(R.id.button_mcamera);
            mcamera.setVisibility(View.VISIBLE);

            Button endcall = findViewById(R.id.button_endcall);
            endcall.setVisibility(View.VISIBLE);

            Button iatend = findViewById(R.id.button_iniciarAtendimento);
            iatend.setVisibility(View.INVISIBLE);

            String sessionID = "26";
            RelativeLayout callView = findViewById(R.id.chamada);

            CallManager callManager = CallManager.getInstance();
            String audioMode = CometChatConstants.AUDIO_MODE_SPEAKER;
            callManager.setAudioMode(audioMode);
            callManager.pauseVideo(true);
            callManager.muteAudio(true);
            Log.d("TAG", String.valueOf(statusVideo) + statusAudio);
            callManager.switchCamera();

            CallSettings callSettings = new CallSettings.CallSettingsBuilder(this, callView)
                    .setSessionId(sessionID)
                    .enableDefaultLayout(false)
                    .showAudioModeButton(false)
                    .showEndCallButton(false)
                    .showPauseVideoButton(false)
                    .showMuteAudioButton(false)
                    .showSwitchCameraButton(false)
                    .setAudioOnlyCall(modocall)
                    .build();

            CometChat.startCall(callSettings, new CometChat.OngoingCallListener() {
                @Override
                public void onUserJoined(User user) {
                    Log.d("TAG", "onUserJoined: Name " + user.getName());
                }

                @Override
                public void onUserLeft(User user) {
                    Log.d("TAG", "onUserLeft: " + user.getName());
                }

                @Override
                public void onError(CometChatException e) {
                    Log.d("TAG", "onError: " + e.getMessage());
                }

                @Override
                public void onCallEnded(Call call) {
                    Log.d("TAG", "onCallEnded: " + call.toString());
                }
            });
        }

        public void endCall(View view) {

            Button bmutar = findViewById(R.id.button_mutar);
            bmutar.setVisibility(View.INVISIBLE);

            Button dcamera = findViewById(R.id.button_dcamera);
            dcamera.setVisibility(View.INVISIBLE);

            Button mcamera = findViewById(R.id.button_mcamera);
            mcamera.setVisibility(View.INVISIBLE);

            Button endcall = findViewById(R.id.button_endcall);
            endcall.setVisibility(View.INVISIBLE);

            Button iatend = findViewById(R.id.button_iniciarAtendimento);
            iatend.setVisibility(View.VISIBLE);

            CometChat.endCall("26", new CometChat.CallbackListener<Call>() {
                @Override
                public void onSuccess(Call call) {
                    // handle end call success
                    Intent intent = new Intent(ConectarRAR.this, TelaInicial.class);
                    startActivity(intent);
                }

                @Override
                public void onError(CometChatException e) {
                    // handled end call error
                    Log.d("TAG", "onCallEnded: " + e.toString());
                }

            });
        }

        public void mudarCamera(View view) {

            CallManager callManager = CallManager.getInstance();
            callManager.switchCamera();
        }

        public void pausarVideo(View view) {

            CallManager callManager = CallManager.getInstance();
            if(statusVideo == true) {
                callManager.pauseVideo(!statusVideo);
                statusVideo = false;
            } else if (statusVideo == false){
                callManager.pauseVideo(!statusVideo);
                statusVideo = true;
            }

        }

        public void mutar(View view) {

            CallManager callManager = CallManager.getInstance();
            if(statusAudio == true) {
                callManager.muteAudio(!statusAudio);
                statusAudio = false;
            } else if (statusAudio == false) {
                callManager.muteAudio(!statusAudio);
                statusAudio = true;
            }
        }

        public void tipoAtend(View view) {

            Switch tipoAtend = (Switch) findViewById(R.id.switch_TipoAtend);


            Boolean switchState = tipoAtend.isChecked();

            if (switchState == false) {
                modocall = true;

            } else {
                modocall = false;
            }
        }
    }
