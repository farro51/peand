package it.polito.dma.ponyexpressandroid;

import it.polito.dma.ponyexpressandroid.library.CookiesManager;
import it.polito.dma.ponyexpressandroid.request.GetListRequest;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

   public class RequestListFragment extends ListFragment  {
    
 	private SimpleAdapter sa;
 	private ArrayList<HashMap<String,String>> list;
 	private ArrayList<String> addressList;
	private ArrayList<String> serviceList; 
	private ArrayList<Double> latitudeList; 
	private ArrayList<Double> longitudeList; 

	   
	 public RequestListFragment() {
		 super();
		}
	 

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        
		View rootView = inflater.inflate(R.layout.fragment_request_list, container, false);
        list = new ArrayList<HashMap<String,String>>();
  		setServiceIdList(new ArrayList<String>());
  		setAddressList(new ArrayList<String>());
  		setLatitudeList(new ArrayList<Double>()); 
  		setLongitudeList(new ArrayList<Double>()); 
  		getUpdateList();  //Fullfill the list
          
          
        return rootView;
    }
    
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id){
		//super.onListItemClick(l, v, position, id);
		Intent i = new Intent(getActivity(), PickUpActivity.class);
		i.putExtra("street_name", getAddressList().get(position));
		i.putExtra("service_id", getServiceIdList().get(position));
		i.putExtra("id_user", getIdUser());
	    i.putExtra("latitude", getLatitudeList().get(position));
		i.putExtra("longitude", getLongitudeList().get(position));
		startActivity(i);
	}
    
    
     
    //Get the list of pickups and deliveries.
    private void getUpdateList() {
		GetListRequest getListR = new GetListRequest(getActivity(), this);
		getListR.getpickupList(getIdUser());
		
	}

    
   protected String getIdUser() {
		CookiesManager cookiesManager = new CookiesManager(getActivity().getApplicationContext());
		return cookiesManager.getIdUser();
	}
    
		
	public ArrayList<HashMap<String,String>> getList() {
		return list;
	}

	
	public void setList(ArrayList<HashMap<String,String>> list) {
		this.list = list;
	}

	public SimpleAdapter getSa() {
		return sa;
	}

	public void setSa(SimpleAdapter sa) {
		this.sa = sa;
	}
	
	
	public ArrayList<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(ArrayList<String> addressList) {
		this.addressList = addressList;
	}
	

	public ArrayList<String> getServiceIdList() {
		return serviceList;
	}

	public void setServiceIdList(ArrayList<String> serviceList) {
		this.serviceList = serviceList;
	}

	
	public ArrayList<Double> getLongitudeList() {
		return longitudeList;
	}

	public void setLongitudeList(ArrayList<Double> longitudeList) {
		this.longitudeList = longitudeList;
	}
	
	
	public ArrayList<Double> getLatitudeList() {
		return latitudeList;
	}

	public void setLatitudeList(ArrayList<Double> latitudeList) {
		this.latitudeList = latitudeList;
	}
	
	
	
	
}