package it.polito.dma.ponyexpressandroid;



import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/*  Fragment para seccion perfil */ 

public class MapaFragment extends Fragment {
     
    public MapaFragment(){}
    
    static final LatLng TORINO = new LatLng(45.0706029, 7.6867102);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap Mymap;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	View v = inflater.inflate(R.layout.fragment_map, container, false);
    	
        Mymap=((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();  
        
        Marker torino = Mymap.addMarker(new MarkerOptions()
        
                .position(TORINO)
                .title("AUTO 1")
                .snippet("Batteria:")
//                .icon(BitmapDescriptorFactory
//                    .fromResource(R.drawable.launcher_small))
                    );
        Marker ino = Mymap.addMarker(new MarkerOptions()
        
        .position(KIEL)
        .title("AUTO 1")
        .snippet("Batteria:")
//        .icon(BitmapDescriptorFactory
//            .fromResource(R.drawable.launcher_small))
            );
           

            // Move the camera instantly to hamburg with a zoom of 15.
            Mymap.moveCamera(CameraUpdateFactory.newLatLngZoom(TORINO, 15));

            // Zoom in, animating the camera.
            Mymap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        
        
        
        
        return v;
    }
}