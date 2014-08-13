package it.polito.dma.ponyexpressandroid.response;

	import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpResponseException;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.PersistentCookieStore;

import it.polito.dma.ponyexpressandroid.MainActivity;
import it.polito.dma.ponyexpressandroid.LoginActivity;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.library.CookiesManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

	public class LogOutResponseHander {

		private Context appContext;


		public LogOutResponseHander(Context c) {
			appContext = c;

		}

		public void onSuccess(JSONObject user) {
			
				Toast.makeText(appContext,"Logout Success!!", Toast.LENGTH_SHORT).show();
				PersistentCookieStore myCookieStore = new PersistentCookieStore(appContext);
				myCookieStore.clear();
	    		((Activity) appContext).finish();
				
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