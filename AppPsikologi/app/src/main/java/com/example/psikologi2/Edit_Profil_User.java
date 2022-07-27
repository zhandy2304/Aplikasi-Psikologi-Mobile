package com.example.psikologi2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Edit_Profil_User extends AppCompatActivity {

    TextView EditFoto, id;
    EditText NamaUser,Email, Umur, Jenis_Kelamin, idUser;
    ImageView FotoUser;
    String nama, email, umur, jenis_kelamin, id_user, foto_user;
    Button BTNUpdate;
    ProgressDialog progressDialog;


    String pilihan;
    private static final int PHOTO_REQUEST_GALLERY = 1;//gallery
    static final int REQUEST_TAKE_PHOTO = 1;
    final int CODE_GALLERY_REQUEST = 999;
    String rPilihan[]= {"Take a photo", "From Album"};
    public final String APP_TAG = "MyApp";
    public String photoFileName = "photo.jpg";
    File photoFile;

    Bitmap bitMap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NamaUser    = findViewById(R.id.idETnama);
        Email       = findViewById(R.id.idETemail);
        Umur        = findViewById(R.id.idETUmur);
        Jenis_Kelamin    = findViewById(R.id.idETJenisKelamin);
        EditFoto    = findViewById(R.id.idTVEditFoto);
        FotoUser    = findViewById(R.id.idCIVfoto);
        BTNUpdate   = findViewById(R.id.idBTNUpdate);
        idUser      = findViewById(R.id.idETIdUser);

        SessionManager sessionManager = new SessionManager(Edit_Profil_User.this);

        NamaUser.setText(sessionManager.getNama());
        Email.setText(sessionManager.getEmail());
        Umur.setText(sessionManager.getUmur());
        idUser.setText(sessionManager.getIdUser());
        Jenis_Kelamin.setText(sessionManager.getJenisKelamin());

        progressDialog = new ProgressDialog(this);

        EditFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitMap != null) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Edit_Profil_User.this);
                    alertDialogBuilder.setMessage("Do yo want to take photo again?");

                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Toast.makeText(context,"You clicked yes button",Toast.LENGTH_LONG).show();
                            //call fuction of TakePhoto
                            TakePhoto();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                } else {
                    TakePhoto();
                }
            }
        });

        BTNUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Edit_Profil_User.this)
                        .setMessage("Are you sure to update this data?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.setMessage("Updating Data...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();

                                id_user         = idUser.getText().toString();
                                nama            = NamaUser.getText().toString();
                                email           = Email.getText().toString();
                                umur            = Umur.getText().toString();
                                jenis_kelamin   = Jenis_Kelamin.getText().toString();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        validatingData();
                                    }
                                },1000);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();

            }
        });
    }

    public  void TakePhoto(){
        // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Profil_User.this);
        builder.setTitle("Select");
        builder.setItems(rPilihan, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                pilihan = String.valueOf(rPilihan[which]);

                if (pilihan.equals("Take a photo")) {
                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Create a File reference to access to future access
                    photoFile = getPhotoFileUri(photoFileName);

                    // wrap File object into a content provider
                    // required for API >= 24
                    // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
                    String authorities = getPackageName() + ".fileprovider";
                    Uri fileProvider = FileProvider.getUriForFile(Edit_Profil_User.this, authorities, photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

                    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
                    // So as long as the result is not null, it's safe to use the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Start the image capture intent to take photo
                        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                    }
                } else {
                    ActivityCompat.requestPermissions(Edit_Profil_User.this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
                }
            }
        });
        builder.show();
    }

    //permission
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"), CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(), "You don't have permission to access gallery!", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        //set photo size
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_TAKE_PHOTO) {

            if (resultCode == Activity.RESULT_OK) {
                bitMap = decodeSampledBitmapFromFile(String.valueOf(photoFile), 1000, 700);
                FotoUser.setImageBitmap(bitMap);
            } else { // Result was a failure
                Toast.makeText(Edit_Profil_User.this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }

        } else {

            if (intent != null) {
                Uri photoUri = intent.getData();
                // Do something with the photo based on Uri
                bitMap = null;
                try {
                    //InputStream inputStream = getContentResolver().openInputStream(photoUri);
                    //bitMap = BitmapFactory.decodeStream(inputStream);

                    //btnImage.setVisibility(View.VISIBLE);
                    bitMap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Load the selected image into a preview
                FotoUser.setImageBitmap(bitMap);
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    //get data photo
    public File getPhotoFileUri(String fileName)  {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }
            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
            return file;
        }
        return null;
    }
    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    void validatingData(){
        if(nama.equals("") || email.equals("") || umur.equals("") || jenis_kelamin.equals("")){
            progressDialog.dismiss();
            Toast.makeText(Edit_Profil_User.this, "Data Cannot Empty", Toast.LENGTH_SHORT).show();
        } else {
            updateData();
        }
    }

    void updateData(){
        AndroidNetworking.post("https://tkjalpha19.com/mobile/api_kelompok_4/uas_updateData.php")
                .addBodyParameter("id_user", "" + id_user)
                .addBodyParameter("nama", "" + nama)
                .addBodyParameter("email", "" + email)
                .addBodyParameter("umur", "" + umur)
                .addBodyParameter("jenis_kelamin", "" + jenis_kelamin)
                .setPriority(Priority.MEDIUM)
                .setTag("Update Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("cekUpdate", "" + response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(Edit_Profil_User.this, "" + pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);

                            if (status) {
                                new AlertDialog.Builder(Edit_Profil_User.this)
                                        .setMessage("Data has been updated !")
                                        .setCancelable(false)
                                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Edit_Profil_User.this, MainActivity.class);
                                                startActivity(i);
                                                Edit_Profil_User.this.finish();
                                            }
                                        })
                                        .show();
                            } else {
                                new AlertDialog.Builder(Edit_Profil_User.this)
                                        .setMessage("Failed Updating Data !")
                                        .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = getIntent();
                                                setResult(RESULT_CANCELED, i);
                                                Edit_Profil_User.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d("Cannot update your data",""+anError.getErrorBody());
                    }
                });
    }


}
