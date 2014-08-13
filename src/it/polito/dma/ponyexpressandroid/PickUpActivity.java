package it.polito.dma.ponyexpressandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import it.polito.dma.ponyexpressandroid.library.AlertDialogManager;
import it.polito.dma.ponyexpressandroid.request.PickUpRequest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PickUpActivity extends FragmentActivity {

	private AlertDialogManager alert;
	private Button btn_InsertCode;
	private EditText txt_InsertCode;
	private String idUser;
	private String street;
	private Double latitude; 
	private Double longitude; 
	private String typeP;
	private String codeS; 
	private LatLng STARTING_POINT;
    public static final int SIGNATURE_ACTIVITY = 1;
    private Context appContext;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_pick_up);
		Bundle receiveParams = getIntent().getExtras();
	    idUser= receiveParams.getString("id_user");
	    street= receiveParams.getString("street_name");
	    latitude= receiveParams.getDouble("latitude");
	    longitude= receiveParams.getDouble("longitude");
		typeP= receiveParams.getString("service_id");
	    
	    appContext = this;
		alert = new AlertDialogManager();
		
		 
		//Paint the map
		//paintLocation(latitude,longitude,street);
		
		btn_InsertCode = (Button) findViewById(R.id.btn_InsertCode);
		txt_InsertCode= (EditText) findViewById(R.id.txt_Code);	
			
		// Register Button Click event
		btn_InsertCode.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				//Catch the code
				codeS = txt_InsertCode.getText().toString();				
				//validate code
			 	if(validate(codeS)){
			 		//Call request
					PickUpRequest pickUpR = new PickUpRequest(appContext);
					pickUpR.sendCode(codeS,idUser,typeP);	
				}
			}
		});
	
	}
	
	
    //Call Signature
    public void makeSignature(){
    		Intent intent = new Intent(PickUpActivity.this, CaptureSignature.class); 
            startActivityForResult(intent,SIGNATURE_ACTIVITY);		
    }
    
    //Wait for result and call questionActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
        case SIGNATURE_ACTIVITY: 
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                String status  = bundle.getString("status");
                if(status.equalsIgnoreCase("done")){
                    Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                    
                    //llamamos a questions.
                    
                    Intent in = new Intent(this, SurveyActivity.class);
                    startActivity(in);     
        
                }
            }
            break;
        }

    }  
    
	
	//Validates the code
	private boolean validate(String code) {
			boolean valid = true;
			String CODE_PATTERN = "^[a-zA-Z0-9]+$";
			Pattern pattern;
			Matcher matcher;

			if (code.equals("")) {
				alert.showAlertDialog(appContext, "Error", "You must to insert a code", false);
				valid = false;
			} else {
				pattern = Pattern.compile(CODE_PATTERN);
				matcher = pattern.matcher(code);
				if (!matcher.matches()) {
					alert.showAlertDialog(appContext, "Error", "You must to insert a correct code", false);
					valid = false;
				}
			}
			
			return valid;

		}

	//Paint the path
	private void paintLocation(double latitude, double longitude, String name) {
			
			STARTING_POINT=new LatLng(latitude, longitude);
			GoogleMap map=((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		    map.setMyLocationEnabled(true);  // centra sulla mia pos.
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(STARTING_POINT, 5));
			map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
			map.addMarker(new MarkerOptions()
			.position(STARTING_POINT)
			.title(name)
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
			 );
			
		}
	
	
}
