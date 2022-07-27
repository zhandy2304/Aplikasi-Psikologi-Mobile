package com.example.psikologi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class Login_Dokter extends AppCompatActivity {

    Button BTNlogin;
    TextView TVlogin_user;
    EditText TVEmail;
    EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dokter);

        BTNlogin = findViewById(R.id.idBTNlogin);
        TVlogin_user = findViewById(R.id.idTVlogin);
        TVEmail  = findViewById(R.id.idETemail);
        Password = findViewById(R.id.Password);

        TVlogin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login_Dokter.this, Login.class);
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

                email = TVEmail.getText().toString();
                password = Password.getText().toString();


                kirimData(email, password);

            }

        });
    }
    void kirimData(String email, String password){
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_4/login_psikeater.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Login Psikiater")
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

                                SessionManager sesi = new SessionManager(Login_Dokter.this);

                               sesi.createLoginSession(ja.getString("password"));
                                sesi.setNama(ja.getString("nama"));
                                sesi.settempatpraktek(ja.getString("tempat_praktek"));
                                sesi.setalumni(ja.getString("alumni"));
                                sesi.setpengalaman(ja.getString("pengalaman"));
                                sesi.setharga(ja.getString("harga"));


                                Toast.makeText(Login_Dokter.this,"Login Berhasil, Selamat Datang "+ name, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login_Dokter.this, Navigation_Bar_psikeater.class);

                                startActivity(intent);
                                finish();
                            }
                            else{
                                String pesan = response.getString("result");
                                Toast.makeText(Login_Dokter.this, "Login gagal, " + pesan, Toast.LENGTH_SHORT).show();
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
        String emailInput = TVEmail.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            TVEmail.setError("Email tidak boleh kosong");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            TVEmail.setError("Silahkan masukkan email yang benar");
            return false;
        } else {
            TVEmail.setError(null);
            return true;
        }
    }

}