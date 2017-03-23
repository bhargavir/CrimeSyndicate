package bhargavi.com.crimesyndicate;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bhargavi.com.crimesyndicate.model.Crime;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, Callback<List<Crime>> {

    private GoogleMap mMap;
    private List<Crime> listOfCrimes;
    private HashMap<String, List<Crime>> districtHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadInitialCrimeData();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sf = new LatLng(37.7749295, -122.4194155);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sf, 12));
        mMap.setMinZoomPreference(5.0f);
        mMap.setMaxZoomPreference(20.0f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (districtHashMap.containsKey(query.toUpperCase())) {
                    List<Crime> crimes = districtHashMap.get(query.toUpperCase());
                    mMap.clear();
                    BitmapDescriptor bitmapDescriptor = getMarkerIcon(getMarkerColor(crimes));
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();

                    for (Crime crime : crimes) {
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(crime.getLocation().getLatitude()), Double.parseDouble(crime.getLocation().getLongitude())))
                                .icon(bitmapDescriptor)
                                .title(crime.getCategory()));
                        builder.include(marker.getPosition());
                    }

                    LatLngBounds bounds = builder.build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 15);
                    mMap.animateCamera(cameraUpdate);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    private void loadInitialCrimeData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://data.sfgov.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CrimeAPI crimeAPI = retrofit.create(CrimeAPI.class);

        Call<List<Crime>> call = crimeAPI.getCrimes();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Crime>> call, Response<List<Crime>> response) {
        Log.d("BHAGS", "response received");

        listOfCrimes = response.body();
        for (Crime crime : listOfCrimes) {
            if (districtHashMap.containsKey(crime.getPddistrict())) {
                List<Crime> existingList = districtHashMap.get(crime.getPddistrict());
                existingList.add(crime);
                districtHashMap.put(crime.getPddistrict(), existingList);
            } else {
                List<Crime> newList = new ArrayList<>();
                newList.add(crime);
                districtHashMap.put(crime.getPddistrict(), newList);
            }
        }

        placeMarkersOnMap(districtHashMap);
    }

    @Override
    public void onFailure(Call<List<Crime>> call, Throwable t) {
        Log.d("BHAGS", "error" + t.getLocalizedMessage());
    }

    private void placeMarkersOnMap(HashMap<String, List<Crime>> hashMap) {
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            mMap.addMarker(new MarkerOptions()
                    .position(getAvgLatLng((List<Crime>) pair.getValue()))
                    .icon(getMarkerIcon(getMarkerColor((List<Crime>) pair.getValue())))
                    .title((String) pair.getKey()));
        }
    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    private LatLng getAvgLatLng(List<Crime> crimes) {
        double lat = 0.0;
        double lng = 0.0;
        for (Crime crime : crimes) {
            lat += Double.parseDouble(crime.getLocation().getLatitude());
            lng += Double.parseDouble(crime.getLocation().getLongitude());
        }
        return new LatLng(lat / crimes.size(), lng / crimes.size());
    }

    private String getMarkerColor(List<Crime> crimes) {
        double value = (crimes.size() * 1.0 / listOfCrimes.size());
        if (value <= 0.025) {
            return "#a6ff00";
        } else if (value <= 0.05) {
            return "#b9c800";
        } else if (value <= 0.075) {
            return "#c5a300";
        } else if (value <= 0.1) {
            return "#d27f00";
        } else if (value <= 0.125) {
            return "#d86d00";
        } else if (value <= 0.15) {
            return "#e54800";
        } else if (value <= 0.175) {
            return "#eb3600";
        }
        return "#ff0000";
    }
}
