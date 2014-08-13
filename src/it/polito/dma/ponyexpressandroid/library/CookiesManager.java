package it.polito.dma.ponyexpressandroid.library;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.PersistentCookieStore;
import android.content.Context;

public class CookiesManager {
	private Context context;
	
	public CookiesManager(Context c){
		context = c;
	}
	
	public boolean isLoggedIn(){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		for (int i = 0; i < myCookieStore.getCookies().size(); i++) {
			if (myCookieStore.getCookies().get(i).getName().equals("id_user")) {
				return true;
			}
		}
		return false;
	}
	
	public String getIdUser() {
		 PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		 int cookiesSize = myCookieStore.getCookies().size();
		 String id_user ="";
			 for(int i = 0; i < cookiesSize; i++){
				if(myCookieStore.getCookies().get(i).getName().equals("id_user")){
					id_user=myCookieStore.getCookies().get(i).getValue().toString();
				}
			 }
		return id_user;
	}
	
	
	public String getQuestions() {
		 PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		 int cookiesSize = myCookieStore.getCookies().size();
		 String questions ="";
			 for(int i = 0; i < cookiesSize; i++){
				if(myCookieStore.getCookies().get(i).getName().equals("questions")){
					questions=myCookieStore.getCookies().get(i).getValue().toString();
				}
			 }
		return questions;
	}
	
	
	public JSONObject getSupermarket() {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		int cookiesSize = myCookieStore.getCookies().size();
	
		try {
			
			for (int i = 0; i < cookiesSize; i++) {
				
				if (myCookieStore.getCookies().get(i).getName().equals("supermarket")) {
					return new JSONObject(myCookieStore.getCookies().get(i).getValue());	
				}
			}
		} catch (JSONException e) {
			return null;
		}
		return null;
	
	}
	
	public void createCookie(String name, String value, String domain){
		PersistentCookieStore myCookieStore = new PersistentCookieStore(context);
		BasicClientCookie newCookie;
		newCookie = new BasicClientCookie(name, value);
		newCookie.setVersion(1);
		//newCookie.setDomain(domain);
		//newCookie.setPath("/~s181451");
		newCookie.setDomain(domain);
		newCookie.setPath("/Ponyexpress");
		myCookieStore.addCookie(newCookie);
	}
}
