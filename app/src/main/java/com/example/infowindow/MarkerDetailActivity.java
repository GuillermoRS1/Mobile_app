package com.example.infowindow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Project_Guillermo.Ruiz.Senso_71235.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MarkerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String json, json2;

        Button back = (Button) findViewById(R.id.btmBack);
        String nam = getIntent().getStringExtra("NAME");
        String id="", name="", gaelicName="", latitude="", longitude="", typeName="";

        try {
            InputStream places = getAssets().open("places.json");
            int size = places.available();
            byte[] buffer = new byte[size];
            places.read(buffer);
            places.close();
            InputStream types = getAssets().open("place_types.json");
            int size2 = types.available();
            byte[] buffer2 = new byte[size2];
            types.read(buffer2);
            types.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonPlaces = new JSONArray(json);
            json2 = new String(buffer2, "UTF-8");
            JSONArray jsonTypes = new JSONArray(json2);
            // id, name, Gaelic name, type, GPS coordinates
            for(int i = 0; i < jsonPlaces.length(); i++) {
                for(int x = 0; x < jsonTypes.length(); x++) {
                    JSONObject place = jsonPlaces.getJSONObject(i);
                    JSONObject type = jsonPlaces.getJSONObject(x);
                    if (place.getString("name").equals(nam)) {
                        id = place.getString("id");
                        name = place.getString("name");
                        gaelicName = place.getString("gaelic_name");
                        String typeID = place.getString("place_type_id");
                        latitude = place.getString("latitude");
                        longitude = place.getString("longitude");
                        if (type.getString("id").equals(typeID)) {
                            typeName = type.getString("name");
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        TextView id_value = (TextView) findViewById(R.id.id);
        TextView name_value = (TextView) findViewById(R.id.name);
        TextView gaelic_value = (TextView) findViewById(R.id.gaelic);
        TextView type_value = (TextView) findViewById(R.id.type);
        TextView lati_value = (TextView) findViewById(R.id.latitude);
        TextView longi_value = (TextView) findViewById(R.id.longitude);
        id_value.setText("The id is:" + id);
        name_value.setText("The name is:" + name);
        gaelic_value.setText("The gaelic name is:" + gaelicName);
        type_value.setText("The type is:" + typeName);
        lati_value.setText("The latitude value is:" + latitude);
        longi_value.setText("The longitude value is:" + longitude);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}