package com.example.psikologi2;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class Daftar extends AppCompatActivity {

    Button BTNdaftar;
    TextView TVmasuk;
    EditText Nama;
    EditText Email;
    EditText Password;
    String email,password,nama;
   //FirebaseAuth mAuth;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=_1234567890!])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{4,}" +                // at least 4 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        //mAuth = FirebaseAuth.getInstance();
        BTNdaftar   = findViewById(R.id.idBTNdaftar);
        TVmasuk     = findViewById(R.id.idTVmasuk);
        Nama        = findViewById(R.id.nama);
        Email       = findViewById(R.id.email);
        Password    = findViewById(R.id.password);

        TVmasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Daftar.this, Login.class);
                startActivity(intent);
            }
        });


        BTNdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Nama.getText().toString())){
                    Nama.setError("Nama tidak boleh kosong");
                    return;
                }
                if (!validateEmail()){
                    return;
                }
                if (!validatePassword()){
                    return;
                }

                String nama, email, password;

                email = Email.getText().toString();
                nama = Nama.getText().toString();
                password = Password.getText().toString();

                String encrypted = "";
                String sourceStr = password;
                try {
                    encrypted = AESUtils.encrypt(sourceStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                kirimData(email, encrypted, nama);
            }
        });
    }
    void kirimData(String email, String password, String nama){
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_4/register.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .addBodyParameter("nama",""+nama)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        progressDialog.dismiss();
                        Log.d("cekTambah",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(Daftar.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                Toast.makeText(Daftar.this, "Registrasi berhasil, silahkan login!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Daftar.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Daftar.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
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
    private boolean validatePassword() {
        String passwordInput = Password.getText().toString().trim();
        // if password field is empty
        // it will display error message "Field can not be empty"
        if (passwordInput.isEmpty()) {
            Password.setError("Password tidak bleh kosong");
            return false;
        }

        // if password does not matches to the pattern
        // it will display an error message "Password is too weak"
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            Password.setError("Password sangat lemah");
            return false;
        }

        else {
            Password.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){

        // Extract input from EditText
        String emailInput = Email.getText().toString().trim();

        // if the email input field is empty
        if (emailInput.isEmpty()) {
            Email.setError("Email tidak boleh kosong");
            return false;
        }

        // Matching the input email to a predefined email pattern
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            Email.setError("Silahkan masukkan email yang benar");
            return false;
        } else {
            Email.setError(null);
            return true;
        }
    }

}