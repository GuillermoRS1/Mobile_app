package com.example.infowindow;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_AZURE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_MAGENTA;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ORANGE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_ROSE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_YELLOW;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.Project_Guillermo.Ruiz.Senso_71235.R;
import com.Project_Guillermo.Ruiz.Senso_71235.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Spinner spinner;
    private String item, idItem, json, json2;
    Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (myMarker == null) {
                    myMarker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title("Your marker"));
                    drawCircle(latLng);
                } else {
                    myMarker.setPosition(latLng);
                }
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item = spinner.getSelectedItem().toString();

                try {
                    InputStream types = getAssets().open("place_types.json");
                    int size2 = types.available();
                    byte[] buffer2 = new byte[size2];
                    types.read(buffer2);
                    types.close();

                    json2 = new String(buffer2, "UTF-8");
                    JSONArray jsonTypes = new JSONArray(json2);

                    for(int x = 0; x < jsonTypes.length(); x++) {
                        JSONObject type = jsonTypes.getJSONObject(x);
                        if (type.getString("name").equals(item)) {
                            idItem = type.getString("id");
                            googleMap.clear();
                            addMarkers();
                        }
                    }
                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Intent intent = new Intent(this, MarkerDetailActivity.class);
        intent.putExtra("NAME", marker.getTitle());
        intent.putExtra("LATITUDE", marker.getPosition().latitude);
        intent.putExtra("LONGITUDE", marker.getPosition().longitude);

        startActivity(intent);
    }

    public void addMarkers() {
        try {
            InputStream places = getAssets().open("places.json");
            int size = places.available();
            byte[] buffer = new byte[size];
            places.read(buffer);
            places.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonPlaces = new JSONArray(json);

            for(int i = 0; i < jsonPlaces.length(); i++) {
                JSONObject place = jsonPlaces.getJSONObject(i);
                float lati = Float.parseFloat(place.getString("latitude"));
                float longi = Float.parseFloat(place.getString("longitude"));

                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("1")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_AZURE)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("2")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("3")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("4")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_CYAN)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("5")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_MAGENTA)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("6")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_ORANGE)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("7")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_RED)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("8")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_ROSE)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("9")) {
                        float hue = 36;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("10")) {
                        float hue = 96;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("11")) {
                        float hue = 2;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("12")) {
                        float hue = 147;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("13")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_VIOLET)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("14")) {
                        float hue = 90;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(hue)));
                    }
                }
                if (place.getString("place_type_id").equals(idItem)) {
                    if (place.getString("place_type_id").equals("15")) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lati, longi))
                                .title(place.getString("name"))
                                .snippet("Gaelic name: " + place.getString("gaelic_name"))
                                .icon(BitmapDescriptorFactory.defaultMarker(HUE_YELLOW)));
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void drawCircle(LatLng point){
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(point);
        circleOptions.radius(10000);
        circleOptions.strokeColor(R.color.lightBlue);
        circleOptions.fillColor(R.color.lightBlue);
        circleOptions.strokeWidth(2);
        mMap.addCircle(circleOptions);

    }

}