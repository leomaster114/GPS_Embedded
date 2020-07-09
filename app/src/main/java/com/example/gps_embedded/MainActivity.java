package com.example.gps_embedded;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestQueue = Volley.newRequestQueue(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final GoogleMap googleMap1 = googleMap;
        String url = "http://btlnhung.000webhostapp.com/server.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // lấy dữ liệu từ server
                            JSONObject latlng = response.getJSONObject("data");
                            String lat = latlng.getString("lat");
                            String lng = latlng.getString("lng");
                            String time = latlng.getString("time");
                            float lattitude = Float.parseFloat(lat);
                            float longtitude = Float.parseFloat(lng);
                            //hiển thị dữ liệu
                            LatLng myposition = new LatLng(lattitude, longtitude);
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(lattitude, longtitude, 1);
                                String cityName = addresses.get(0).getAddressLine(0);

                                googleMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition, 10));
                                googleMap1.addMarker(new MarkerOptions()
                                        .position(myposition)
                                        .title(cityName));
                                Toast.makeText(MainActivity.this,"Vị trí: "+cityName+","+lat+","+lng,Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}