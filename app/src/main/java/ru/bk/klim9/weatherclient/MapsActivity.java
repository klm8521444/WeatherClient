package ru.bk.klim9.weatherclient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{


    String[] dataArray;
    GoogleApiClient mGoogleApiClient;
    GoogleMap mMap = null;
    private double myLat = 0;
    private double myLng = 0;

    TextView latValue, lonValue, id1Value, mainValue, descriptionValue, iconValue, baseValue, id2Value, tempValue;
    TextView pressureValue, humidityValue, tempMinValue, tempMaxValue, nameValue, visibilityValue, codValue, speedValue;
    TextView degValue, cloudsAllValue, dtValue, typeValue, sysIdValue, messageValue, countryValue, sunriseValue, sunsetValue;

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataArray = getIntent().getStringArrayExtra("array");

        img = (ImageView) findViewById(R.id.imageView);

        String url = "http://api.openweathermap.org/img/w/" + dataArray[5] + ".png";

        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(img);





        setUpTable();

        doubleCoordinates();

    }

    public void setUpTable() {

        latValue = (TextView) findViewById(R.id.latValue);
        lonValue = (TextView) findViewById(R.id.lonValue);
        id1Value = (TextView) findViewById(R.id.id1Value);
        mainValue = (TextView) findViewById(R.id.mainValue);
        descriptionValue = (TextView) findViewById(R.id.descriptionValue);
        iconValue = (TextView) findViewById(R.id.iconValue);
        baseValue = (TextView) findViewById(R.id.baseValue);
        id2Value = (TextView) findViewById(R.id.id2Value);
        tempValue = (TextView) findViewById(R.id.tempValue);
        pressureValue = (TextView) findViewById(R.id.pressureValue);
        humidityValue = (TextView) findViewById(R.id.humidityValue);
        tempMinValue = (TextView) findViewById(R.id.tempMinValue);
        tempMaxValue = (TextView) findViewById(R.id.tempMaxValue);
        nameValue = (TextView) findViewById(R.id.nameValue);
        visibilityValue = (TextView) findViewById(R.id.visibilityValue);
        codValue = (TextView) findViewById(R.id.codValue);
        speedValue = (TextView) findViewById(R.id.speedValue);
        degValue = (TextView) findViewById(R.id.degValue);
        cloudsAllValue = (TextView) findViewById(R.id.cloudsAllValue);
        dtValue = (TextView) findViewById(R.id.dtValue);
        typeValue = (TextView) findViewById(R.id.typeValue);
        sysIdValue = (TextView) findViewById(R.id.sysIdValue);
        messageValue = (TextView) findViewById(R.id.messageValue);
        countryValue = (TextView) findViewById(R.id.countryValue);
        sunriseValue = (TextView) findViewById(R.id.sunriseValue);
        sunsetValue = (TextView) findViewById(R.id.sunsetValue);

        latValue.setText(dataArray[1]);
        lonValue.setText(dataArray[0]);
        id1Value.setText(dataArray[2]);
        mainValue.setText(dataArray[3]);
        descriptionValue.setText(dataArray[4]);
        iconValue.setText(dataArray[5]);
        baseValue.setText(dataArray[6]);
        id2Value.setText(dataArray[23]);
        tempValue.setText(dataArray[7]);
        pressureValue.setText(dataArray[8]);
        humidityValue.setText(dataArray[9]);
        tempMinValue.setText(dataArray[10]);
        tempMaxValue.setText(dataArray[11]);
        nameValue.setText(dataArray[24]);
        visibilityValue.setText(dataArray[12]);
        codValue.setText(dataArray[25]);
        speedValue.setText(dataArray[13]);
        degValue.setText(dataArray[14]);
        cloudsAllValue.setText(dataArray[15]);
        dtValue.setText(dataArray[16]);
        typeValue.setText(dataArray[17]);
        sysIdValue.setText(dataArray[18]);
        messageValue.setText(dataArray[19]);
        countryValue.setText(dataArray[20]);
        sunriseValue.setText(dataArray[21]);
        sunsetValue.setText(dataArray[22]);


    }

    public void doubleCoordinates() {

        try {
            myLat = Double.parseDouble(dataArray[1]);
            myLng = Double.parseDouble(dataArray[0]);
        } catch (NumberFormatException e) {
            Toast.makeText(MapsActivity.this, e.toString(), Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onConnected(Bundle bundle) {
        final LatLng myLocation = new LatLng(myLat, myLng);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("My location"));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(
                        myLocation,
                        5.0f)));

        disconnect();
    }

    private void disconnect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
