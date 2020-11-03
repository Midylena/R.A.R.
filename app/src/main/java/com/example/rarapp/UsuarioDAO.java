package com.example.rarapp;

import android.provider.ContactsContract;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class UsuarioDAO {

    private static final String URL = "http://192.168.0.132:8080/Mechachieve/services/UsuarioDAO?wsdl";
    private static final String NAMESPACE = "http://mechachiveWS.banco.com.br";

    private static final String INSERIR = "inserirUsuario";
    private static final String EXCLUIR = "excluirUsuario";
    private static final String ATUALIZAR = "atualizarUsuario";
    private static final String BUSCAR_TODOS = "buscarTodosUsuarios";
    private static final String BUSCAR_POR_ID = "buscarUsuarioPorID";
    private static final String LOGAR = "Logar";
    private static final String BUSCAR_ID_LOGIN = "buscarIDLogin";


    public boolean inserirUsuario(Usuario usuario){

        SoapObject inserirUsuario = new SoapObject(NAMESPACE,  INSERIR);

        SoapObject usr = new SoapObject(NAMESPACE, "usuario");

        usr.addProperty("nome", usuario.getNome());
        usr.addProperty("email", usuario.getEmail());
        usr.addProperty("senha", usuario.getSenha());
        usr.addProperty("cpf", usuario.getCpf());
        usr.addProperty("rg", usuario.getRg());
        usr.addProperty("telefone", usuario.getTelefone());
        usr.addProperty("sexo", usuario.getSexo());
        usr.addProperty("dtNascimento", usuario.getDt_nasc());


        inserirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(inserirUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
            try {
                http.call("urn:" + INSERIR, envelope);

                SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

                return Boolean.parseBoolean(resposta.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
    }

    public boolean excluirUsuario(Usuario usuario){

        SoapObject excluirUsuario = new SoapObject(NAMESPACE,  EXCLUIR);

        SoapObject usr = new SoapObject(NAMESPACE, "usuario");

        usr.addProperty("email", usuario.getEmail());
        usr.addProperty("id", usuario.getId());
        usr.addProperty("nome", usuario.getNome());
        usr.addProperty("senha", usuario.getSenha());

        excluirUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(excluirUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + EXCLUIR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarUsuario(Usuario usuario){

        SoapObject atulaizarUsuario = new SoapObject(NAMESPACE,  ATUALIZAR);

        SoapObject usr = new SoapObject(NAMESPACE, "usuario");

        usr.addProperty("email", usuario.getEmail());
        usr.addProperty("id", usuario.getId());
        usr.addProperty("nome", usuario.getNome());
        usr.addProperty("senha", usuario.getSenha());

        atulaizarUsuario.addSoapObject(usr);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(atulaizarUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + ATUALIZAR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return Boolean.parseBoolean(resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Usuario> buscarTodosUsuarios(){
        ArrayList<Usuario> lista = new ArrayList<Usuario>();

        SoapObject buscarUsuario = new SoapObject(NAMESPACE,  BUSCAR_TODOS);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuario);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + BUSCAR_TODOS, envelope);

            Vector<SoapObject> resposta = (Vector<SoapObject>) envelope.getResponse();

            for (SoapObject soapObject : resposta) {

                Usuario usr = new Usuario();
                usr.setId(Integer.parseInt(soapObject.getProperty("id").toString()));
                usr.setNome(soapObject.getProperty("nome").toString());
                usr.setEmail(soapObject.getProperty("email").toString());
                usr.setSenha(soapObject.getProperty("senha").toString());

                lista.add(usr);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return lista;
    }

    public String Logar(String email, String senha){

        String NOK = "Erro";

        SoapObject Logar = new SoapObject(NAMESPACE,  LOGAR);
        Logar.addProperty("email", email);
        Logar.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(Logar);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + LOGAR, envelope);

            SoapPrimitive resposta = (SoapPrimitive) envelope.getResponse();

            return (resposta.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return NOK;
        }
    }

    public Usuario buscarIDLogin(String email, String senha){
        Usuario usr = null;

        SoapObject buscarIDLogin = new SoapObject(NAMESPACE,  BUSCAR_ID_LOGIN);
        buscarIDLogin.addProperty("email", email);
        buscarIDLogin.addProperty("senha", senha);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarIDLogin);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + BUSCAR_ID_LOGIN, envelope);

            SoapObject resposta = (SoapObject) envelope.getResponse();


            usr = new Usuario();
            usr.setId(Integer.parseInt(resposta.getProperty("id").toString()));
            usr.setEmail(resposta.getProperty("email").toString());
            usr.setSenha(resposta.getProperty("senha").toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return usr;
    }

    public Usuario buscarUsuarioPorID(int id){
        Usuario usr = null;

        SoapObject buscarUsuarioPorID = new SoapObject(NAMESPACE,  BUSCAR_POR_ID);
        buscarUsuarioPorID.addProperty("id", id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(buscarUsuarioPorID);

        envelope.implicitTypes = true;

        HttpTransportSE http = new HttpTransportSE(URL);
        try {
            http.call("urn:" + BUSCAR_POR_ID, envelope);

            SoapObject resposta = (SoapObject) envelope.getResponse();

                usr = new Usuario();

                usr.setId(Integer.parseInt(resposta.getProperty("id").toString()));
                usr.setNome(resposta.getProperty("nome").toString());
                usr.setEmail(resposta.getProperty("email").toString());
                usr.setSenha(resposta.getProperty("senha").toString());


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return usr;
    }
}
