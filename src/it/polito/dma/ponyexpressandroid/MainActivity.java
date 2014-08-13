package it.polito.dma.ponyexpressandroid;

import java.util.ArrayList;

import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import it.polito.dma.ponyexpressandroid.*;
import it.polito.dma.ponyexpressandroid.library.CookiesManager;
import it.polito.dma.ponyexpressandroid.library.GpsService;
import it.polito.dma.ponyexpressandroid.request.LogOutRequest;
import it.polito.dma.ponyexpressandroid.request.PickUpRequest;
import it.polito.dma.ponyexpressandroid.request.LoginRequest;

public class MainActivity extends Activity {
	    private String[] titulos;
	    private DrawerLayout NavDrawerLayout;
	    private ListView NavList;
        private ArrayList<Item_objct> NavItms;
        private TypedArray NavIcons;
	    private ActionBarDrawerToggle mDrawerToggle;
	    private CharSequence mDrawerTitle;
	    private CharSequence mTitle;
	   NavigationAdapter NavAdapter;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);		

			//Drawer Layout
			NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
			//Lista
	        NavList = (ListView) findViewById(R.id.lista);
	        //Declaramos el header el cual sera el layout de header.xml
	        //View header = getLayoutInflater().inflate(R.layout.header, null);
	        //Establecemos header
	       // NavList.addHeaderView(header);
			//Tomamos listado  de imgs desde drawable
	        NavIcons = getResources().obtainTypedArray(R.array.navigation_iconos);			
			//Tomamos listado  de titulos desde el string-array de los recursos @string/nav_options
	        titulos = getResources().getStringArray(R.array.nav_options);
	        //Listado de titulos de barra de navegacion
	        NavItms = new ArrayList<Item_objct>();
	        //Agregamos objetos Item_objct al array
	        //Profile	      
	        NavItms.add(new Item_objct(titulos[0], NavIcons.getResourceId(0, -1)));
	        //Request
	        NavItms.add(new Item_objct(titulos[1], NavIcons.getResourceId(1, -1)));
	        //Mapa
	        NavItms.add(new Item_objct(titulos[2], NavIcons.getResourceId(2, -1)));
	        //Help
	        NavItms.add(new Item_objct(titulos[3], NavIcons.getResourceId(3, -1)));
	        //Impostazione
	        NavItms.add(new Item_objct(titulos[4], NavIcons.getResourceId(4, -1)));
	        //Log out
	        NavItms.add(new Item_objct(titulos[5], NavIcons.getResourceId(5, -1)));
	       
	        //Declaramos y seteamos nuestro adaptador al cual le pasamos el array con los titulos	       
	        NavAdapter= new NavigationAdapter(this,NavItms);
	        NavList.setAdapter(NavAdapter);	
	        //Siempre vamos a mostrar el mismo titulo
	        mTitle = mDrawerTitle = getTitle();
	        
	        //Declaramos el mDrawerToggle y las imgs a utilizar
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                NavDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* Icono de navegacion*/
	                R.string.app_name,  /* "open drawer" description */
	                R.string.hello_world  /* "close drawer" description */
	                ) {

	            /** Called when a drawer has settled in a completely closed state. */
	            public void onDrawerClosed(View view) {
	            	Log.e("Chiuso", "!!");
	            }

	            /** Called when a drawer has settled in a completely open state. */
	            public void onDrawerOpened(View drawerView) {
	                Log.e("Aperto", "!!");
	            }
	        };	        
	        
	        // Establecemos que mDrawerToggle declarado anteriormente sea el DrawerListener
	        NavDrawerLayout.setDrawerListener(mDrawerToggle);
	        //Establecemos que el ActionBar muestre el Boton Home
	        getActionBar().setDisplayHomeAsUpEnabled(true);

	        //Establecemos la accion al clickear sobre cualquier item del menu.
	        //De la misma forma que hariamos en una app comun con un listview.
	        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
	            	MostrarFragment(position);
	            	
	            	//poner intent para llamar activity
	            }
	        });
	        
	      //Cuando la aplicacion cargue por defecto mostrar la opcion Home
	        MostrarFragment(0);
	        
	        
	       //Start the services
	        startService(new Intent(getBaseContext(), GpsService.class));
	        
	        
	}
	
	/*Pasando la posicion de la opcion en el menu nos mostrara el Fragment correspondiente*/
    private void MostrarFragment(int position) {
        // update the main content by replacing fragments
    	Intent i;
        Fragment frag = null;
        ListFragment lf = null;
     
        switch (position) {
        case 0:
            frag = new ProfileFragment();
            break;
        case 1:
            //fragment = new home();
            lf = new RequestListFragment();    
            
            break;
        case 2:
        	frag = new MapaFragment();      	
            break;
        case 3:
        	//Chiama Help
            i = new Intent(this, QlistActivity.class);
            startActivity(i);        	
            break;
        case 4:
           	//Chiama Impostazione
            i = new Intent(this, SettingsActivity.class);
            startActivity(i);         	
            break;
        case 5:
        	//Chiama Log Out
            		
    		LogOutRequest logOutR = new LogOutRequest(this);
    		logOutR.logOut(getIdUser());
    		//Paramos el servicio de gps
    		stopService(new Intent(getBaseContext(), GpsService.class));
    		finish();
            break;
        default:
        	//si no esta la opcion mostrara un toast y nos mandara a Home
        	//Toast.makeText(getApplicationContext(),"Opcion "+titulos[position-1]+"no disponible!", Toast.LENGTH_SHORT).show();
            //fragment = new MapFragment();
        	lf = new RequestListFragment();    

            position=1;
            break;
        }
        //Validamos si el fragment no es nulo
        if (frag != null||lf != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, frag==null?lf:frag).commit();
 
            // Actualizamos el contenido segun la opcion elegida
            NavList.setItemChecked(position, true);
            NavList.setSelection(position);
            //Cambiamos el titulo en donde decia "
            //setTitle(titulos[position-1]);
            //Cerramos el menu deslizable
            NavDrawerLayout.closeDrawer(NavList);
        } else {
            //Si el fragment es nulo mostramos un mensaje de error.
            Log.e("Error  ", "MostrarFragment"+position);
        }
    }
	  
    
    protected String getIdUser() {
		CookiesManager cookiesManager = new CookiesManager(getApplicationContext());
		return cookiesManager.getIdUser();
	}
    
    
    
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Log.e("mDrawerToggle pushed", "x");
          return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
}
