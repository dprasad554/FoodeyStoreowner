package com.geekhive.foodeyrestaurant.cakes.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.utils.ConnectionDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.geekhive.foodeyrestaurant.cakes.utils.util.IMAGE_DIRECTORY_NAME;
import static com.geekhive.foodeyrestaurant.cakes.utils.util.REQUEST_FOR_STORAGE_PERMISSION;
import static com.geekhive.foodeyrestaurant.cakes.utils.util.REQUEST_TAKE_PHOTO_PROFILE_PIC;
import static com.geekhive.foodeyrestaurant.cakes.utils.util.SELECT_FILE_PROFILE_PIC;


public class CakeAddMenu_Activity extends AppCompatActivity {

    String[] select = {"gm", "kg", "ltr", "box", "unit"};

    EditText et_AddName;

    ImageView et_addImage, et_imageDisplay;

    Spinner spnSelect;
    DialogInterface dialog;
    private String filepathprofilepic = "";
    ConnectionDetector mDetector;
    Bitmap bitmap;
    Uri fileUri;
    String fileNameProfilepic;
    Bitmap bitmapFromGallery;
    private String uriSting;
    String mFileNameGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_activity_add_menu);
        setValues();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menuAdd));

        et_AddName = findViewById(R.id.et_AddName);
        et_addImage = findViewById(R.id.et_addImage);
        et_imageDisplay = findViewById(R.id.et_imageDisplay);
        spnSelect = findViewById(R.id.spnSelect);

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,select);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnSelect.setAdapter(typeAdapter);
        spnSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_imageDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage(SELECT_FILE_PROFILE_PIC, REQUEST_TAKE_PHOTO_PROFILE_PIC);

                }
            }
        });
    }
    private void setValues() {
        mDetector = new ConnectionDetector(this);
    }
    public void doSubmit(View view) {
    }

    private boolean mayRequestGalleryImages() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        requestPermissions(
                new String[]{Manifest.permission
                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                REQUEST_FOR_STORAGE_PERMISSION);
        return false;
    }

    private void selectImage(final int selectfile, final int takephoto) {
        View view = getLayoutInflater().inflate(R.layout.cake_take_image_popup, null);
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);
        final TextView chooseFromGallery = (TextView) view.findViewById(R.id.choose_from_gallery);
        final TextView tekePhoto = (TextView) view.findViewById(R.id.take_photo);
        final TextView select_photo = (TextView) view.findViewById(R.id.select_photo);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        new Runnable() {
            public void run() {
            }
        }.run();
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), selectfile);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        tekePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile(takephoto);
                    } catch (IOException ex) {
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = null;
                        try {
                            photoURI = FileProvider.getUriForFile(CakeAddMenu_Activity.this, getApplicationContext().getPackageName() + ".provider", createImageFile(takephoto));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, takephoto);
                    }
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.show();
    }
    private File createImageFile(int phot) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "studeEMI"+timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (phot == SELECT_FILE_PROFILE_PIC || phot == REQUEST_TAKE_PHOTO_PROFILE_PIC){
            filepathprofilepic = storageDir.getAbsolutePath() + "/" + imageFileName;
            File file = new File(filepathprofilepic);
            return file;
        }else {
            return null;
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 100) {
            if (mDetector.isConnectingToInternet()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
                String[] fileTemp = fileUri.getPath().split("/");
                /*fileName = fileTemp[fileTemp.length - 1];
                filePath = fileUri.getPath();*/
                compressImage(fileUri.getPath(), 1);

                //vI_ad_image_display.setImageBitmap(bitmap);

                return;
            }
            SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
        } else if (requestCode == SELECT_FILE_PROFILE_PIC) {
            onSelectFromGalleryResult(data, SELECT_FILE_PROFILE_PIC);
            if (mDetector.isConnectingToInternet()) {
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                et_imageDisplay.setImageBitmap(bitmapFromGallery);
                //CallService();
            } else {
                SnackBar.makeText((Context) this, (int) R.string.no_internet, SnackBar.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO_PROFILE_PIC&& resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filepathprofilepic.split("/");
            fileNameProfilepic = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filepathprofilepic);
            filepathprofilepic = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                et_imageDisplay.setImageBitmap(BitmapFactory.decodeStream(ims));
              // CallService();

            } catch (FileNotFoundException e) {
                return;
            }

            MediaScannerConnection.scanFile(this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }
    private void onSelectFromGalleryResult(Intent data, int resultImage) {

        Uri selectedImageUri = data.getData();
        String selectedImagePath = getRealPathFromURI(selectedImageUri);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        int scale = 1;
        while ((options.outWidth / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION && (options.outHeight / scale) / 2 >= ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
            scale *= 2;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        compressImage(selectedImagePath, 2);
        Bitmap bm = BitmapFactory.decodeFile(uriSting, options);
        fetchImageName(selectedImagePath, resultImage);
    }
    private void fetchImageName(String selectedImagePath, int id) {
        mFileNameGallery = "";
        StringTokenizer st = new StringTokenizer(selectedImagePath, "/");
        while (st.hasMoreTokens()) {
            mFileNameGallery = st.nextToken().toString();
        }
        if (id == SELECT_FILE_PROFILE_PIC){
            filepathprofilepic = selectedImagePath;
            fileNameProfilepic = mFileNameGallery;
        }

        selectedImagePath = selectedImagePath;
    }
    public String compressImage(String imageUri, int flag) {
        String filename;
        OutputStream outputStream;
        FileNotFoundException e;
        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);
        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = 612.0f / 816.0f;
        if (((float) actualHeight) > 816.0f || ((float) actualWidth) > 612.0f) {
            if (imgRatio < maxRatio) {
                actualWidth = (int) (((float) actualWidth) * (816.0f / ((float) actualHeight)));
                actualHeight = (int) 816.0f;
            } else if (imgRatio > maxRatio) {
                actualHeight = (int) (((float) actualHeight) * (612.0f / ((float) actualWidth)));
                actualWidth = (int) 612.0f;
            } else {
                actualHeight = (int) 816.0f;
                actualWidth = (int) 612.0f;
            }
        }
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16384];
        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
            bitmapFromGallery = bmp;
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception2) {
            exception2.printStackTrace();
        }
        float ratioX = ((float) actualWidth) / ((float) options.outWidth);
        float ratioY = ((float) actualHeight) / ((float) options.outHeight);
        float middleX = ((float) actualWidth) / 2.0f;
        float middleY = ((float) actualHeight) / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - ((float) (bmp.getWidth() / 2)), middleY - ((float) (bmp.getHeight() / 2)), new Paint(2));
        try {
            int orientation = new ExifInterface(filePath).getAttributeInt("Orientation", 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90.0f);
            } else if (orientation == 3) {
                matrix.postRotate(180.0f);
            } else if (orientation == 8) {
                matrix.postRotate(270.0f);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (flag == 1) {
            filename = imageUri;
        } else {
            filename = getFilename();
        }
        try {
            OutputStream fileOutputStream = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            outputStream = fileOutputStream;
        } catch (FileNotFoundException e4) {
            e = e4;
            e.printStackTrace();
            return filename;
        }
        return filename;
    }
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
        }
        while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
            inSampleSize++;
        }
        return inSampleSize;
    }
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        return uriSting;
    }
    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file
            // path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            String path = cursor.getString(idx);
            cursor.close();
            return path;
        }
    }
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_data"));
    }
}
