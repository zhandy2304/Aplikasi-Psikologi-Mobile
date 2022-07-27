package com.example.psikologi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    Button BTNlogin;
    TextView TVdaftar;
    TextView TVlogin_dokter;
    EditText Username;
    EditText Password;
    String name,password;
    //private FirebaseAuth mAuth;
    //SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // mAuth = FirebaseAuth.getInstance();
        BTNlogin = findViewById(R.id.idBTNlogin);
        TVdaftar = findViewById(R.id.idTVdaftar);
        Username = findViewById(R.id.idETemail);
        Password = findViewById(R.id.Password);
        TVlogin_dokter = findViewById(R.id.idTVlogin_dokter);

        //sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);
        TVdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this, Daftar.class);
                startActivity(intent);
            }
        });

        TVlogin_dokter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this, Login_Dokter.class);
                startActivity(intent);
            }
        });

        BTNlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail()){
                    return;
                }

                if (TextUtils.isEmpty(Password.getText().toString())){
                    Password.setError("Password tidak boleh kosong");
                    return;
                }

                String email, password;

                email = Username.getText().toString();
                password = Password.getText().toString();

                String encrypted = "";
                String sourceStr = password;

                try {
                    encrypted = AESUtils.encrypt(sourceStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                kirimData(email, encrypted);

            }
        });
    }
    void kirimData(String email, String password){
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_4/login.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Login User")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekUser",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            Log.d("status",""+status);
                            if(status){
                                JSONObject ja = response.getJSONObject("result");
                                String name = ja.getString("nama");

                                SessionManager sesi = new SessionManager(Login.this);

                                sesi.createLoginSession(ja.getString("password"));
                                sesi.setNama(ja.getString("nama"));
                                sesi.setEmail(ja.getString("email"));
                                sesi.setUmur(ja.getString("umur"));
                                sesi.setJenisKelamin(ja.getString("jenis_kelamin"));
                                sesi.setPhoto(ja.getString("foto"));
                                sesi.setIduser(ja.getString("id_user"));


                                Toast.makeText(Login.this, "Login Berhasil, Selamat Datang "+ name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, Navigation_Bar.class);

                                startActivity(intent);
                                finish();
                            }
                            else{
                                String pesan = response.getString("result");
                                Toast.makeText(Login.this, "Login gagal, " + pesan, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }
    private boolean validateEmail(){

        // Extract input from EditText
        String emailInput = Username.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            Username.setError("Email tidak boleh kosong");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Username.setError("Silahkan masukkan email yang benar");
            return false;
        } else {
            Username.setError(null);
            return true;
        }
    }

}