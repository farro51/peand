package it.polito.dma.ponyexpressandroid.response;

import it.polito.dma.ponyexpressandroid.PickUpActivity;
import it.polito.dma.ponyexpressandroid.RequestListFragment;

import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.PersistentCookieStore;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

public class PickUpResponseHander {

	private Context appContext;
	private PickUpActivity pUp;


	public PickUpResponseHander(Context c) {
		appContext = c;

	}

	public void onSuccess(JSONObject dato , String type) {
		    
			Toast.makeText(appContext,"Success, Code send it!!", Toast.LENGTH_SHORT).show();
			//if(type.contains("d")){
    			pUp = (PickUpActivity) appContext;
				pUp.makeSignature(); //call method signature
			//}else{
				 //ListFragment lf = new RequestListFragment();

			//}
			
	}
	public void onFailure(HttpResponseException e, String response){
		int code = e.getStatusCode();
		if(code == 404 || code == 400 || code == 503){
			Toast.makeText(appContext,response, Toast.LENGTH_SHORT).show();
		}
		else{
			Toast.makeText(appContext,"Error accessing the server", Toast.LENGTH_SHORT).show();
		}
	}
}