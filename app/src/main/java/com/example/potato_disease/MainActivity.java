package com.example.potato_disease;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.potato_disease.ml.Model;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.TensorFlowLite;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient mFusedLocationClient;
    LinearLayout fertilizer_cal;
    LinearLayout weather_btn;
    LinearLayout chatai;
    Button camera;
    Bitmap bitmap;
    public double lat;
    public double longi;
    int PERMISSION_ID = 44;
    int imageSize = 224;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Log.d("msg", "just checking");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fertilizer_cal=findViewById(R.id.fertilizer_calculator);
        weather_btn=findViewById(R.id.weather_btn);
        camera=findViewById(R.id.camera);
        chatai=findViewById(R.id.chatai);
        photo=findViewById(R.id.imageView10);
        getLastLocation();
//        Fertilizer btn clicked
        fertilizer_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,fertilizer.class);
                startActivity(i);

            }
        });

//        Weather btn click

        weather_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions()){
                    Intent intent = new Intent(MainActivity.this, Weather.class);
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longitude", longi);
                            startActivity(intent);
                }
                else
                    Toast.makeText(MainActivity.this,"Location permission is not provided",Toast.LENGTH_SHORT);
                Log.d("check", "inside userLocation");

            }
        });

//        Open camera

            camera.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {

                        ImagePicker.with(MainActivity.this)
                                .start();
//                        Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(open_camera, 12);

                }
            });

//            CHAT AI
        chatai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openai=new Intent(MainActivity.this,ChatAI.class);
                startActivity(openai);

            }
        });





    }
    void getPermission()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},11);
        }
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            lat=location.getLatitude();
                            longi=location.getLongitude();
                            Intent intent = new Intent(MainActivity.this, Weather.class);
                            intent.putExtra("latitude", lat);
                            intent.putExtra("longitude", longi);
//                            startActivity(intent);
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat= mLastLocation.getLatitude();
            longi=mLastLocation.getLongitude();
            Intent intent = new Intent(MainActivity.this, Weather.class);
            intent.putExtra("latitude", lat);
            intent.putExtra("longitude", longi);
//            startActivity(intent);


        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
            else  if(grantResults.length>1 && grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                getPermission();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri=data.getData();
//        Toast.makeText(MainActivity.this, "URI "+uri, Toast.LENGTH_SHORT).show();
//        photo.setImageURI(uri);
        try {
            ContentResolver contentResolver = getContentResolver();
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
            bitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false);
            classifyImage(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        photo.setImageBitmap(bitmap);

           }

    private void classifyImage(Bitmap bitmap) {
        try {
            Model model = Model.newInstance(MainActivity.this);

// Assuming your model expects input images to be 224x224 pixels and uses FLOAT32 data type
            int inputWidth = 224;
            int inputHeight = 224;
            int numChannels = 3;  // Assuming it's an RGB image

//            bitmap = Bitmap.createScaledBitmap(bitmap, inputWidth, inputHeight, true);

// Create an input tensor with the correct shape
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1,224, 224, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * inputWidth * inputHeight * numChannels);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[inputWidth * inputHeight];
            bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

// Iterate over each pixel and extract R, G, and B values. Normalize them to the [0, 1] range and add to the byte buffer.
            int pixel = 0;
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Potato___Early_blight", "Potato___Late_blight", "Potato___healthy", "random"};
// Get the class label associated with the predicted index
            String predictedClassLabel = classes[maxPos];

// Display the result
            Toast.makeText(MainActivity.this, "Predicted class: " + predictedClassLabel+"   "+maxConfidence, Toast.LENGTH_SHORT).show();
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }
}