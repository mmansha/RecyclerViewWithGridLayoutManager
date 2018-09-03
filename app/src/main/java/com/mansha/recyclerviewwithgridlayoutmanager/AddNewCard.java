package com.mansha.recyclerviewwithgridlayoutmanager;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


public class AddNewCard extends AppCompatActivity {

    private static final int PICK_FROM_GALLERY = 2;
    private static String startingActivityName;
    private static String filepath = "MyfileStorage";
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);
        Toolbar toolbar = (Toolbar)findViewById(R.id.add_new_card_toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        startingActivityName = intent.getStringExtra("ActivityName");
        Log.d("AddNewCard", "Activity that started this activity = " + startingActivityName);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void getImage(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(
                    Intent.createChooser(intent,"Complete action using"),
                    PICK_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {}
    }

    public void saveToDB(View view){
        Boolean imageSaveStatus;
        String captionLabel;
        String captionImagePath = UUID.randomUUID().toString() + ".jpg";
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        EditText editText = findViewById(R.id.editText);
        captionLabel = editText.getText().toString();
        dbHelper.insertData(db, DBContract.CATEGORY_NAME, captionLabel, captionImagePath, null);
        imageSaveStatus = storeImage(bitmap, captionImagePath );
        Toast.makeText(this, "Image Saving Status: " + imageSaveStatus, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Boolean imageSaveStatus;
        ImageView imgview = (ImageView)findViewById(R.id.image_view);
        super.onActivityResult(requestCode, resultCode, data);

//        Log.i("AddNewCard", "Into get activity result. Request code = " + requestCode);
//        Log.i("AddNewCard","Data intent = " + (data == null?"Null":"Not Null"));

        if (data != null){
            Uri contentURI = data.getData();
            Log.i("AddNewCard", "Get path = " + contentURI.getPath());
            try {
                this.bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                //String path = saveImage(bitmap);

                imgview.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean storeImage(Bitmap bitmap, String filename){
        //filename ="test6.jpg";
       int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/dadAppData");
        Boolean result = dir.mkdirs();
        Log.d("AddNewCard", "Result of mkdir " + result);

        File sdStoragefile = new File(dir, filename);
//        Log.d("AddNewCard", "Filepath + filename: " + sdStoragefile.toString());
//        Log.d("AddNewCard", "Directory exists: " + sdStoragefile.exists());
//        Log.d("AddNewCard", "Media mounted state " + Environment.getExternalStorageState());

        try {
            Log.d("AddNewCard", "Filepath + filename: " + sdStoragefile);
            FileOutputStream fileOutputStream = new FileOutputStream(sdStoragefile);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException exception){
            Log.w("AddNewCard", "Error saving image file: " + exception.getMessage());
            return false;
        } catch (IOException exception){
            Log.w("AddNewCard", "Error saving image file: " + exception.getMessage());
            return false;
        }

        return true;



    }
}
