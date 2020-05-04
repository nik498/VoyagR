package ca.qc.bdeb.voyagr.Vue;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.data.Feature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import org.json.JSONException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import ca.qc.bdeb.voyagr.Modele.Modele;
import ca.qc.bdeb.voyagr.Modele.Pays;
import ca.qc.bdeb.voyagr.R;

/**
 * Cette classe est la classe principale du programme.
 * Elle affiche la carte du monde et permet de la déplacer et la zoomer. Elle ajoute la layer qui donne les
 * frontières des pays afin de définir sur quel pays a cliqué un utilisateur. C'est à partir des interactions
 * de l'utilisateur avec cette classe que tout le reste du programme fonctionne.
 */
public class CarteDuMonde extends FragmentActivity implements OnMapReadyCallback {

    //TODO:Localisation absolument nécessaire pour cliquer sur des pays
    protected @Nullable
    GoogleMap mMap;
    private Button btnAfficherMenu;
    private Modele modele = new Modele();
    protected String langueCarte;
    private Parametres parametres;

    private List<Pays> listePays;
    private Locale localeActuelle;

    /**
     * Permet de lancer l'activity Google Maps
     *
     * @param savedInstanceState état
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte_du_monde);

        if (Modele.getListePays().size() <= 4) {
            modele.lireFichier(CarteDuMonde.this);
        }

        localeActuelle = getResources().getConfiguration().locale;
        if(localeActuelle.getISO3Language() .equals("deu")){
            Modele.setLangueFrancais(false);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.location_map);

        try {
            mapFragment.getMapAsync(this);

        }catch (NullPointerException ex){
        }



        creerComposante();
        afficherMenu();

        Modele.lirePaysFavori(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * Javadoc par défaut d'une MapsActivity
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        trouverLocationUtilisateur();

        mMap.setMapType(modele.getTypeCarte());
        activerFonctionGoogleMaps();

        try {
            GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.frontieres, getApplicationContext());
            layer.addLayerToMap();

            layer.getDefaultPolygonStyle().setVisible(false);//NE PAS ENLEVER et remettre a false
            layer.setOnFeatureClickListener(new GeoJsonLayer.GeoJsonOnFeatureClickListener() {
                @Override
                public void onFeatureClick(Feature feature) {
                    modele.lireFichierPays(CarteDuMonde.this, feature.getProperty("name"));
                    Modele.setPaysClique(feature.getProperty("name"));
                    Modele.setVientDeLaCarte(true);
                    startActivity(new Intent(CarteDuMonde.this, InfoPays.class));
                }
            });


        } catch (IOException ex) {
            Log.e("IOException", ex.getLocalizedMessage());
            System.out.println("erreur avec le nom d'un pays cliqué");
        } catch (JSONException ex) {
            Log.e("JSONException", ex.getLocalizedMessage());
            System.out.println("erreur avec le nom d'un pays cliqué");

        }

    }

    /**
     * Cette méthode permet à l'utilisateur de se recentrer sur sa position, de zoomer la carte et de faire tourner la carte.
     */
    private void activerFonctionGoogleMaps() {
        UiSettings reglageCarte;
        reglageCarte = mMap.getUiSettings();

        reglageCarte.setMyLocationButtonEnabled(true);
        reglageCarte.setRotateGesturesEnabled(true);
        reglageCarte.setZoomControlsEnabled(true);
    }

    /**
     * Cette méthode permet de créer le bouton qui affiche le menu.
     */
    private void creerComposante() {
        btnAfficherMenu = (Button) findViewById(R.id.btnMenu);

    }

    /**
     * Cette méthode fait afficher le menu lorsque l'utilisateur clique sur le bouton du menu.
     */
    private void afficherMenu() {
        btnAfficherMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CarteDuMonde.this, Menu.class));
            }
        });
    }

    protected void changerTypeCarte(int type) {
        mMap.setMapType(type);
    }

    /**
     * Cette méthode permet de déplacer la caméra du google maps à la position actuelle de l'émulateur/telephone
     */
    private void trouverLocationUtilisateur() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 0));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(5), 2000, null);
    }
}
