package com.example.app_mensajes_firebase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private Button btLogin, btReg;
    private ImageButton btGmail;
    private EditText etMail, etPassword;

    private FirebaseAuth auth;

    private final int GOOGLE_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Realizamos las autenticaciones y las altas de usuario

        //Variables del layout login
        btReg = findViewById(R.id.btReg);
        btGmail = findViewById(R.id.btGmail);
        btLogin = findViewById(R.id.btLogin);
        etMail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPass);

        //Varibles  de autenficiacion
        auth = FirebaseAuth.getInstance();


        loginConfg();
    }

    //----------------------------------------AUTENTICACION CON EMAIL Y CONTRASEÑA-----------------------------------------------
    //Metodo de configuracion de la autentificacion del logeo a nuestra aplicacion
    private void loginConfg() {

        //Registrar con email y contraseña, verificamos que los campos no esten vacios y si la operacion(task) es exitosa entraremos en la pantalla principal (Home)
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                    auth.createUserWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    logUserInfo(task.getResult().getUser().getEmail(), ProviderType.BASIC);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error de autentificacion", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Aqui programamos el boton de entrar o log-in , si esque el usuario ya existe

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                    auth.signInWithEmailAndPassword(etMail.getText().toString(), etPassword.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    logUserInfo(task.getResult().getUser().getEmail(), ProviderType.BASIC);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Error de autentificacion", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // ---------------------Autenticacion con Gmail-------------------------------------


        btGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                GoogleSignInClient gsc = GoogleSignIn.getClient(getApplicationContext(), gso);
                gsc.signOut();
                startActivityForResult(gsc.getSignInIntent(), GOOGLE_SIGN_IN);
            }
        });
    }

    //Con este método de navegacion a la pantalla principal de la app, a la que le parasemos los datos del usuario(email y proveedor)
    public void logUserInfo(String email, ProviderType provider) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("provider", provider.name());

        startActivity(intent);
        finish();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount gsa = (GoogleSignInAccount) task.getResult(ApiException.class);
                if (gsa != null) {

                    AuthCredential credential = GoogleAuthProvider.getCredential(gsa.getIdToken(), null);

                    FirebaseAuth.getInstance().signInWithCredential(credential);

                    if (task.isSuccessful()) {
                        logUserInfo(gsa.getEmail(), ProviderType.GOOGLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Error de autentificacion con Google", Toast.LENGTH_SHORT).show();
                    }

                }

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}