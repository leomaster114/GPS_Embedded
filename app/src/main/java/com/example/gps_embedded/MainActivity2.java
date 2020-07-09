package com.example.gps_embedded;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity2 extends AppCompatActivity implements OnMapReadyCallback {
    public String lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PositionAsync(this).execute("http://btlnhung.000webhostapp.com/server.php");
        Log.d("TAG", "onCreate: "+PositionAsync.getLatitude()+"-"+lng);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap googleMap1 = googleMap;
        float lattitude = Float.parseFloat("20.909");
        float longtitude = Float.parseFloat("105.333");
        //hiển thị dữ liệu
        LatLng myposition = new LatLng(lattitude, longtitude);
        Geocoder geocoder = new Geocoder(MainActivity2.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(lattitude, longtitude, 1);
            String cityName = addresses.get(0).getAddressLine(0);

            googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 10));
            googleMap1.addMarker(new MarkerOptions()
                    .position(myposition)
                    .title(cityName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class PositionAsync extends AsyncTask<String, DataObject, DataObject> {
        private Activity mActivity;
        public static String lat,lng;
        public PositionAsync(Activity mActivity) {
            this.mActivity = mActivity;
        }

        @Override
        protected DataObject doInBackground(String... strings) {
            String UrlWebservice = strings[0];// lấy địa chỉ Webservice được truyền vào
            String JsonString = new Commons().getJSONStringFromURL(UrlWebservice);
            DataObject dataObject = new Gson().fromJson(JsonString, new TypeToken<DataObject>() {
            }.getType());
            Log.d("TAG", "doInBackground: " + dataObject.getPositionObject());
            publishProgress(dataObject);
            return null;
        }

        @Override
        protected void onProgressUpdate(DataObject... values) {
            super.onProgressUpdate(values);
            PositionObject positionObject = values[0].getPositionObject();
            lat = positionObject.getLat();
            lng = positionObject.getLng();
        }
        public static String getLatitude(){
            return lat;
        }
    }
}
