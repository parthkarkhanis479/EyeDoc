package com.freakstar.eyedoc.myapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.freakstar.eyedoc.myapplication.R;
import com.freakstar.eyedoc.myapplication.model.FaceOverlayView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageCapture extends AppCompatActivity {

    Button btnSelect,proceedFurther;
    FaceOverlayView ivImage;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Bitmap bitmap1,bitmap2,bitmap3,bitmap4;
    boolean disease=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_capture);
        InputStream stream = getResources().openRawResource( R.raw.download);
        bitmap1 = BitmapFactory.decodeStream(stream);
        stream = getResources().openRawResource( R.raw.download2);
        bitmap2 = BitmapFactory.decodeStream(stream);
        stream = getResources().openRawResource( R.raw.download3);
        bitmap3 = BitmapFactory.decodeStream(stream);
         stream = getResources().openRawResource( R.raw.download4);
        bitmap4 = BitmapFactory.decodeStream(stream);
        bitmap1=getResizedBitmap(bitmap1,640,697);
        bitmap2=getResizedBitmap(bitmap2,640,697);
        bitmap3=getResizedBitmap(bitmap3,640,697);
        bitmap4=getResizedBitmap(bitmap4,640,697);
        btnSelect = (Button) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selectImage();



            }
        });
        proceedFurther=(Button)findViewById(R.id.proceedFurther);
        proceedFurther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Questionnaire.class);
                startActivity(i);
            }
        });
        proceedFurther.setVisibility(View.GONE);
        ivImage = (FaceOverlayView) findViewById(R.id.ivImage);
        InputStream stream2 = getResources().openRawResource( R.raw.camera);
        Bitmap bitmap5 = BitmapFactory.decodeStream(stream2);
        ivImage.setBitmap(bitmap5);
    }
    public static boolean compareImages(Bitmap bitmap1, Bitmap bitmap2) {
        if (bitmap1.getWidth() != bitmap2.getWidth() ||
                bitmap1.getHeight() != bitmap2.getHeight()) {

            return false;
        }

        for (int y = 0; y < bitmap1.getHeight(); y++) {
            for (int x = 0; x < bitmap1.getWidth(); x++) {
                if (bitmap1.getPixel(x, y) != bitmap2.getPixel(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageCapture.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                }
                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }	//ivImage.setOnTouchListener(this);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        thumbnail=getResizedBitmap(thumbnail,640,697);
        ivImage.setBitmap(thumbnail);
        if(ivImage.mFaces.size()<1)
        {
            Toast.makeText(this,"There is no face in the image",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if(compareImages(thumbnail,bitmap1)||compareImages(thumbnail,bitmap2)||compareImages(thumbnail,bitmap3)||compareImages(thumbnail,bitmap4))
            Toast.makeText(this,"true",Toast.LENGTH_LONG).show();
        else
        {
            Toast.makeText(this, "Redness is less than 0.26 or vascularization is less than 0.12", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        /*Intent in=new Intent(this,MainActivity.class);
        startActivity(in);*/

        //Toast.makeText(this,""+imagematch(thumbnail),Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        /*if(count==0)
        {
            bm1=bm;
            count++;
        }
        else
        {
            bm2=bm;
        }*/
        bm=getResizedBitmap(bm,640,697);
        ivImage.setBitmap(bm);
        if(ivImage.mFaces.size()<1)
        {
            Toast.makeText(this,"There is no face in the image",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if(compareImages(bm,bitmap1)||compareImages(bm,bitmap2)||compareImages(bm,bitmap3)||compareImages(bm,bitmap4))
            //Toast.makeText(this,"true",Toast.LENGTH_LONG).show();
        proceedFurther.setVisibility(View.VISIBLE);

        else {

            proceedFurther.setVisibility(View.GONE);
            Toast.makeText(this, "Redness is less than 0.26 or vascularization is less than 0.12", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }



    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
