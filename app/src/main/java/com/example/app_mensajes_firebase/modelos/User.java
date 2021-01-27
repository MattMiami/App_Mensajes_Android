package com.example.app_mensajes_firebase.modelos;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String uid, email, nombre, telefono, provider;
    private Uri photoUrl;
    private List<String> mensajesRecibidos = new ArrayList<String>();
    private List<String> mensajesEnviados = new ArrayList<String>();

    public User() {
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getMensajesRecibidos() {
        return mensajesRecibidos;
    }

    public void setMensajesRecibidos(List<String> mensajesRecibidos) {
        this.mensajesRecibidos = mensajesRecibidos;
    }

    public List<String> getMensajesEnviados() {
        return mensajesEnviados;
    }

    public void setMensajesEnviados(List<String> mensajesEnviados) {
        this.mensajesEnviados = mensajesEnviados;
    }
}
