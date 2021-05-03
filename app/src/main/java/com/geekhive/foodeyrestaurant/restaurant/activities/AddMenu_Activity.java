package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.addmenu.AddMenu;
import com.geekhive.foodeyrestaurant.restaurant.beans.addmenu.CategoryList;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import static com.geekhive.foodeyrestaurant.restaurant.utils.util.IMAGE_DIRECTORY_NAME;
import static com.geekhive.foodeyrestaurant.restaurant.utils.util.REQUEST_FOR_STORAGE_PERMISSION;
import static com.geekhive.foodeyrestaurant.restaurant.utils.util.REQUEST_TAKE_PHOTO;
import static com.geekhive.foodeyrestaurant.restaurant.utils.util.SELECT_FILE;

public class AddMenu_Activity extends AppCompatActivity implements OnResponseListner {

    String[] type = {"Veg", "Non Veg"};
    String[] category = {"Biriyani", "South Indian", "Chinese", "North Indian", "Tandoori"};
    String[] foodTime = {"Breakfast", "Lunch", "Snack", "Dinner"};

    EditText et_AddName, et_mrp, et_specialPrice;

    ImageView et_addImage, et_imageDisplay;

    Spinner spnfoodTime, spnCategory, spnType;

    private Uri picUri;
    File f;
    private int STORAGE_PERMISSION_CODE = 109;
    DialogInterface dialog;
    private String filePath = "";
    private String uriSting;
    String mFileNameGallery;
    String fileName;
    Bitmap bitmap;
    Bitmap bitmapFromGallery;
    Uri fileUri;

    ConnectionDetector mDetector;
    ArrayList<String> categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.menuAdd));
        mDetector = new ConnectionDetector(this);

        et_AddName = findViewById(R.id.et_AddName);
        et_mrp = findViewById(R.id.et_mrp);
        et_specialPrice = findViewById(R.id.et_specialPrice);

        et_addImage = findViewById(R.id.et_addImage);
        et_imageDisplay = findViewById(R.id.et_imageDisplay);

        spnfoodTime = findViewById(R.id.spnfoodTime);
        spnCategory = findViewById(R.id.spnCategory);
        spnType = findViewById(R.id.spnType);

        ArrayAdapter typeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnType.setAdapter(typeAdapter);
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategory.setAdapter(categoryAdapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        ArrayAdapter foodTimeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, foodTime);
        foodTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnfoodTime.setAdapter(foodTimeAdapter);
        spnfoodTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage();
                }
            }
        });

        et_imageDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mayRequestGalleryImages()) {
                    return;
                } else {
                    selectImage();
                }
            }
        });

        CallService();

    }

    public void doSubmit(View view) {
        if (!(et_AddName.getText().toString().equals("")) || !(et_AddName.getText().toString().isEmpty())) {
            if (!(et_mrp.getText().toString().equals("")) || !(et_mrp.getText().toString().isEmpty())) {

                    if (!filePath.equals("")){
                        if (this.mDetector.isConnectingToInternet()) {
                            if (!(et_specialPrice.getText().toString().equals("")) || !(et_specialPrice.getText().toString().isEmpty())) {
                                new WebServices(this).AddMenu(WebServices.Foodey_Services,
                                        WebServices.ApiType.addMenu, Prefs.getUserId(this), et_AddName.getText().toString(),
                                        spnType.getSelectedItem().toString(), spnCategory.getSelectedItem().toString(),
                                        spnfoodTime.getSelectedItem().toString(), et_mrp.getText().toString(),
                                        et_specialPrice.getText().toString(), fileName, filePath);
                            } else {
                                new WebServices(this).AddMenu(WebServices.Foodey_Services,
                                        WebServices.ApiType.addMenu, Prefs.getUserId(this), et_AddName.getText().toString(),
                                        spnType.getSelectedItem().toString(), spnCategory.getSelectedItem().toString(),
                                        spnfoodTime.getSelectedItem().toString(), et_mrp.getText().toString(),
                                        "", fileName, filePath);
                        }
                    }
                }else {
                    et_specialPrice.setError("Please enter special price");
                }
            }else {
                et_mrp.setError("Please enter mrp");
            }
        } else {
            et_AddName.setError("Please enter email id");
        }
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CategoryList(WebServices.Foodey_Services,
                    WebServices.ApiType.categoryList);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.addMenu) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                AddMenu addMenu = (AddMenu) response;
                if (!isSucces || addMenu == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    SnackBar.makeText(this, addMenu.getMessage(), SnackBar.LENGTH_SHORT).show();
                    startActivity(new Intent(this, AddMenu_Activity.class));
                    AddMenu_Activity.this.finish();
                }
            }
        }  if (apiType == WebServices.ApiType.categoryList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                CategoryList categoryList  = (CategoryList) response;
                categoryName = new ArrayList<>();
                for (int i = 0; i < categoryList.getCategory().size(); i++) {
                    String countryName = categoryList.getCategory().get(i).getCategoryType();
                    categoryName.add(countryName);
                }

                ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryName);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnCategory.setAdapter(categoryAdapter);
                spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }

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
    /**
     * This method calls if user if a user has denied a permission and selected the
     * Don't ask again option in the permission request dialog,
     * or if a device policy prohibits the permission
     */
    private void showPermissionRationaleSnackBar() {

        Snackbar.make(findViewById(android.R.id.content),
                "Please Grant Permissions",
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(AddMenu_Activity.this,
                                new String[]{Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                REQUEST_FOR_STORAGE_PERMISSION);
                    }
                }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FOR_STORAGE_PERMISSION:

                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                        //selectImage();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale
                                (this, Manifest.permission.CAMERA)) {

                            showPermissionRationaleSnackBar();

                        } else {
                            Toast.makeText(this, "Go to settings and enable permission", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                break;

        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
        }

    }

    /**
     * This method shows Dialogue to select image from camera or Gallery
     */
    private void selectImage() {
        View view = getLayoutInflater().inflate(R.layout.take_image_popup, null);
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view);
        final TextView chooseFromGallery = (TextView) view.findViewById(R.id.choose_from_gallery);
        final TextView tekePhoto = (TextView) view.findViewById(R.id.take_photo);
        final TextView select_photo = (TextView) view.findViewById(R.id.select_photo);
        final TextView cancel = (TextView) view.findViewById(R.id.cancel);
        chooseFromGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        return;
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = null;
                        try {
                            photoURI = FileProvider.getUriForFile(AddMenu_Activity.this, getApplicationContext().getPackageName() + ".provider", createImageFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
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

    /**
     * This method uses to create image file to store the image in phone gallery
     */
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "profile"+timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        filePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(filePath);
        return file;
    }

    /**
     * This method uses to decode file and
     * fetch the image
     */
    private void onSelectFromGalleryResult(Intent data) {

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
        fetchImageName(selectedImagePath);
    }

    /**
     * This method uses to get real path of selected
     * gallery image
     *
     * @param contentURI - uri path of image
     */
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


    /**
     * This method uses to get file path and
     * file name
     *
     * @param selectedImagePath - image path
     */
    private void fetchImageName(String selectedImagePath) {
        mFileNameGallery = "";
        StringTokenizer st = new StringTokenizer(selectedImagePath, "/");
        while (st.hasMoreTokens()) {
            mFileNameGallery = st.nextToken().toString();
        }
        filePath = selectedImagePath;
        fileName = mFileNameGallery;
        selectedImagePath = selectedImagePath;
    }

    /**
     * This method uses to compress
     * the image without losing the quantity of image
     *
     * @param imageUri - image Uri path
     * @param flag     - scale type
     */
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

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    /**
     * This method uses to create and
     * get file path
     */
    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_DIRECTORY_NAME);
        if (!file.exists()) {
            file.mkdirs();
        }
        uriSting = file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg";
        return uriSting;
    }

    /**
     * This method uses to get path uri
     * from internal storage
     *
     * @param contentURI - image uri
     */
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        }
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex("_data"));
    }

    /**
     * This method uses to calculate the size of image
     * from bitmap factory
     *
     * @param options   - Bitmap Factory options
     * @param reqWidth  - Width of image
     * @param reqHeight - height of image
     */
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


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (requestCode == 100) {

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeFile(fileUri.getPath(), options);
            String[] fileTemp = fileUri.getPath().split("/");
            fileName = fileTemp[fileTemp.length - 1];
            filePath = fileUri.getPath();
            compressImage(fileUri.getPath(), 1);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

            //profile_image.setImageBitmap(bitmap);

            return;
        } else if (requestCode == SELECT_FILE) {
            onSelectFromGalleryResult(data);
            et_imageDisplay.setImageBitmap(bitmapFromGallery);
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            // Show the thumbnail on ImageView

            String[] fileTemp = filePath.split("/");
            fileName = fileTemp[fileTemp.length - 1];
            Uri imageUri = Uri.parse(filePath);
            filePath = imageUri.getPath();
            File file = new File(imageUri.getPath());
            try {
                InputStream ims = new FileInputStream(file);
//                vIAmpTemp.setVisibility(View.GONE);
//                vLAmpLay.setVisibility(View.GONE);

                try {

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 1;
                    bitmap = BitmapFactory.decodeFile(imageUri.getPath(), options);
                    ExifInterface ei = new ExifInterface(filePath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bitmap;
                    }


                    et_imageDisplay.setImageBitmap(rotatedBitmap);
                } catch (Exception e){
                    Log.e("Take Photo", e.getMessage());
                }

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
}
