package com.example.app_mensajes_firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

//Proveedores
enum ProviderType {
    BASIC,
    GOOGLE

}

public class HomeActivity extends AppCompatActivity {

    private TextView tvEmail, tvProvider;
    private Button btLogout;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //En hombre activity realizamos las bajsa y modificaciones del usuario asi como la mensajeria.

        auth = FirebaseAuth.getInstance();



        //Info del usuario obtenida al inicio de sesion

        Bundle bundle = getIntent().getExtras();
        String email = bundle.getString("email");
        String prov = bundle.getString("provider");

        tvEmail = findViewById(R.id.etEmail);
        tvProvider = findViewById(R.id.etProvider);
        btLogout = findViewById(R.id.btLogout);


        //Guardar datos en preferencias
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email",email);
        editor.putString("provider", prov);
        editor.apply();

        homeConfg(email, prov);
    }

    private void  homeConfg(String email, String provider){

        tvEmail.setText(email);
        tvProvider.setText( provider);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Borrar los datos de las preferencias
                SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();


                auth.signOut();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

}