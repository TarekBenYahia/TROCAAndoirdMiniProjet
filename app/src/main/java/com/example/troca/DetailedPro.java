package com.example.troca;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.troca.RetroFit.INodeJS;
import com.example.troca.RetroFit.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class DetailedPro extends AppCompatActivity {
    private ImageView mImage;
    private TextView mTitre, mDescription, mDate, mLieu,adressClient;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private Button AjoutC;
    INodeJS myApi3;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    SupportMapFragment smf;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_detailed_pro);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        AjoutC = (Button) findViewById(R.id.AjoutC);

        mTitre = findViewById(R.id.titre);
        mDescription = findViewById(R.id.description);
        mDate = findViewById(R.id.date);
        mLieu = findViewById(R.id.lieu);
        mImage = findViewById(R.id.imageA);
        adressClient = (TextView) findViewById(R.id.adressClient);

        Intent intent = getIntent();
        String title = intent.getStringExtra("titreAnnonce");
        String desc = intent.getStringExtra("descriptionAnnonce");
        final String date = intent.getStringExtra("dateAnnonce");
        String lieu = intent.getStringExtra("lieu");
        final String idPro = intent.getStringExtra("idPro");

        //maps

        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(this);
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getMyLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        Log.d("", lieu);
        if (intent != null) {
            mTitre.setText(title);
            mDescription.setText(desc);
            mDate.setText(date);
            mLieu.setText(lieu);
            Glide.with(DetailedPro.this).load(R.drawable.pro).into(mImage);
        }
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi3 = retrofit.create(INodeJS.class);

        AjoutC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailedPro.this);
                    builder.setTitle("Adresse");
                    builder.setMessage("Votre adresse est elle correcte?");
                    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                            String display = sharedPreferences.getString("display", "");
                            JSONObject p = null;
                            try {
                                p = new JSONObject(display);
                                String idC = p.getString("idClient");
                                DateTimeFormatter dtf = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                    LocalDate localDate = LocalDate.now();
                                    System.out.println(dtf.format(localDate));
                                    String adresse = adressClient.getText().toString();
                                    ajouterCommande(Integer.parseInt(idC), Integer.parseInt(idPro), dtf.format(localDate), adresse, 30);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alert =builder.create();
                    alert.show();

            }
        });
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Votre Position");
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(DetailedPro.this, Locale.getDefault());
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            String address = addresses.get(0).getAddressLine(0);
                            adressClient.setText(address);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void ajouterCommande(int idClient, int idPro, String date, String lieu, int prix) {
        compositeDisposable.add(myApi3.ajouterCommande(idClient,idPro,date,lieu,prix)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("enregistré")){
                            Toast.makeText(DetailedPro.this, "Commande ajoutée Avec Succès", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(DetailedPro.this, ""+s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

}