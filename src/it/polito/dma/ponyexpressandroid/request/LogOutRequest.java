package it.polito.dma.ponyexpressandroid.request;


import it.polito.dma.ponyexpressandroid.MainActivity;
import it.polito.dma.ponyexpressandroid.R;
import it.polito.dma.ponyexpressandroid.library.AlertDialogManager;
import it.polito.dma.ponyexpressandroid.library.UserFunctions;
import it.polito.dma.ponyexpressandroid.response.LogOutResponseHander;
import it.polito.dma.ponyexpressandroid.response.PickUpResponseHander;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;

import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

	/*Contacts the server to send the logOut request and contact LoginResponseHandler
	 * to manage the success or failure response from the server
	 * */
	public class LogOutRequest {

		private Context appContext;
		private String url;
		private AlertDialogManager alert;

		public LogOutRequest(Context c){
			appContext = c;
		
			url = appContext.getString(R.string.server_ip_address);
			alert = new AlertDialogManager();
		}

		public void logOut(String IdUser){

			RequestParams logOut = new RequestParams();
		
				logOut.put("id_user", IdUser);
						

			AsyncHttpClient client = new AsyncHttpClient();
			final ProgressDialog pd = ProgressDialog.show(appContext, "", "LogOut...", true, false);
			//client.post(appContext, "http://192.168.1.133/ponyexpress/resources/agents/logout.json",logOut ,new JsonHttpResponseHandler() {
			client.post(appContext, "http://"+url+"/ponyexpress/resources/agents/logout.json",logOut ,new JsonHttpResponseHandler() {
				
				private LogOutResponseHander logOutHandler;

				@Override
				public void onSuccess(JSONObject response) {
					pd.dismiss();
					logOutHandler = new LogOutResponseHander(appContext);
					logOutHandler.onSuccess(response);
				}

				@Override
				public void onFailure(Throwable e, String response) {
					pd.dismiss();
					
					//The device isn't connected to a network
					if(e.getClass() == HttpHostConnectException.class){
						alert.showAlertDialog(appContext, "Error", "Without Internet Connection", false);
						return;
					}

					//Server unreachable
					if(e.getClass() == ConnectException.class){
						alert.showAlertDialog(appContext, "Sorry!", "Server unreachable, please retry later", false);
						return;
					}

					logOutHandler = new LogOutResponseHander(appContext);
					logOutHandler.onFailure((HttpResponseException)e, response);

				}
			});
		}
	}