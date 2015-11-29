package com.example.androidtask.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.androidtask.R;
import com.example.androidtask.adapter.NearbyListAdapter;
import com.example.androidtask.model.Categories;
import com.example.androidtask.model.DataValue;
import com.example.androidtask.network.VolleySingleton;
import com.example.androidtask.util.CompareDistance;
import com.example.androidtask.util.Constant;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

public class NearByFragment extends Fragment implements LocationListener {

	public static final String ARG_SECTION_NUMBER = "placeholder_text";

	JsonObjectRequest jsonObjReq;
	ListView listView;
	/*ProgressDialog pDialog;*/
	List<String> list;
	String url;
	TextView locationnearby;
	LatLng current;
	ArrayList<DataValue> listvalue;

	NearbyListAdapter nearbyListAdapter;
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
Context context;
	private Location mLastLocation;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	 private LocationManager locationManager;
	  private String provider;
	
	// Location updates intervals in sec
	
	public NearByFragment() {
		super();
		listvalue = new ArrayList<DataValue>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myInflatedView = inflater.inflate(R.layout.nearby, container,
				false);
		locationnearby = (TextView) myInflatedView
				.findViewById(R.id.mylocation);
		

		return myInflatedView;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		fragmentInit();
		 // Get the location manager
	    locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    
	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      setaddress(location);
	      onLocationChanged(location);
	      
	    } else {
	      System.out.println("Location not available");
	    }
		context= getActivity().getApplicationContext();
/*if (checkPlayServices()) {
			System.out.println("test nerwok ok");
			
			buildGoogleApiClient();
			displayLocation();
		}*/
		final ProgressDialog pDialog1  = new ProgressDialog(getActivity());
		pDialog1.setMessage("Loading...");
		pDialog1.show();
		pDialog1.setCancelable(false);
		
 
	
		jsonObjReq = new JsonObjectRequest(Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						if(pDialog1.isShowing()){
					
							
				
							pDialog1.dismiss();
						//	pDialog.dismiss();
							
							
						}
					
						Log.d("App", response.toString());
						
						try {
							
							///Retriving the values from json object...........
							JSONObject subObj = response.getJSONObject("data");
						
							
							for (int i = 0; i < subObj.length(); i++) {
								// foreach element in the 'data' array
								
								JSONObject dataObj = subObj.optJSONObject(""
										+ i); // get the object from the array
								
								if (dataObj!=null) {

									DataValue dv = new DataValue();

									String SubFranchiseID = dataObj
											.getString("SubFranchiseID");
									dv.setSubFranchiseID(SubFranchiseID);
									String OutletID = dataObj
											.getString("OutletID");
									dv.setOutletID(OutletID);
									String OutletName = dataObj
											.getString("OutletName");
									dv.setOutletName(OutletName);
									String BrandID = dataObj
											.getString("BrandID");
									dv.setBrandID(BrandID);
									String Address = dataObj
											.getString("Address");
									dv.setAddress(Address);
									String NeighbourhoodID = dataObj
											.getString("NeighbourhoodID");
									dv.setNeighbourhoodID(NeighbourhoodID);
									String CityID = dataObj.getString("CityID");
									dv.setCityID(CityID);
									String Email = dataObj.getString("Email");
									dv.setEmail(Email);
									String Timings = dataObj
											.getString("Timings");
									dv.setTimings(Timings);
									String CityRank = dataObj
											.getString("CityRank");
									dv.setCityRank(CityRank);
									String Latitude = dataObj
											.getString("Latitude");
									dv.setLatitude(Latitude);

									String Longitude = dataObj
											.getString("Longitude");
									dv.setLongitude(Longitude);
									String Pincode = dataObj
											.getString("Pincode");
									dv.setPincode(Pincode);
									String Landmark = dataObj
											.getString("Landmark");
									dv.setLandmark(Landmark);
									String Streetname = dataObj
											.getString("Streetname");
									dv.setStreetname(Streetname);
									String BrandName = dataObj
											.getString("BrandName");
									dv.setBrandName(BrandName);
									String OutletURL = dataObj
											.getString("OutletURL");
									dv.setOutletURL(OutletURL);
									String NumCoupons = dataObj
											.getString("NumCoupons");
									dv.setNumCoupons(NumCoupons);
									String NeighbourhoodName = dataObj
											.getString("NeighbourhoodName");
									dv.setNeighbourhoodName(NeighbourhoodName);
									String PhoneNumber = dataObj
							.getString("PhoneNumber");
									dv.setPhoneNumber(PhoneNumber);
									String CityName = dataObj
											.getString("CityName");
									dv.setCityName(CityName);
									String Distance = dataObj
											.getString("Distance");
									dv.setDistance(Distance);
									JSONArray arr = dataObj.getJSONArray("Categories");
									List<Categories> listcat = new ArrayList<Categories>();

									for(int j = 0; j < arr.length(); j++){
										Categories categories= new Categories();
;								
										 String offlineCategoryID=arr.getJSONObject(j).getString("OfflineCategoryID");
                                        categories.setOfflineCategoryID(offlineCategoryID);
										 String name=arr.getJSONObject(j).getString("Name");
										 categories.setName(name);
											
										 String parentCategoryID=arr.getJSONObject(j).getString("ParentCategoryID");
										categories.setParentCategoryID(parentCategoryID);
										 String categoryType=arr.getJSONObject(j).getString("CategoryType");
										 categories.setCategoryType(categoryType);
									    
										
										 listcat.add(categories);
									
									
									}dv.setCategories(listcat);
									
								
									
									// dataObj.getJSONArray("Categories");
									String LogoURL = dataObj
											.getString("LogoURL");
									dv.setLogoURL(LogoURL);
									String CoverURL = dataObj
											.getString("CoverURL");
									dv.setCoverURL(CoverURL);

									listvalue.add(dv);

								}

								Collections.sort(listvalue, new CompareDistance(current));
								nearbyListAdapter.notifyDataSetChanged();
								
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} /*
						 *  JSONObject data = response.getJSONObject("data");
						 * 
						 * 
						 * } catch (JSONException e) { // TODO Auto-generated
						 * catch block e.printStackTrace(); } /* JSONObject data
						 * = response.getJSONObject("data");
						 *//*
							 * catch (JsonParseException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (JsonMappingException e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * catch (IOException e) { // TODO Auto-generated
							 * catch block e.printStackTrace(); }
							 */
						/*
						 * Gson gson = new Gson(); String res
						 * =response.toString(); // convert String into
						 * InputStream InputStream is = new
						 * ByteArrayInputStream(response.toString().getBytes());
						 * 
						 * Reader reader = new InputStreamReader(is);
						 * 
						 * 
						 * Type type = new TypeToken<Map<String,
						 * String>>(){}.getType(); Map<String, String> map =
						 * gson.fromJson("{'key1':'123','key2':'456'}", type);
						 * ListResponse responsedata = gson.fromJson(reader,
						 * ListResponse.class); for(int i=0;i<35;i++)
						 
						 */

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.d("App", "Error: " + error.getMessage());
						// hide the progress dialog
						pDialog1.hide();
					}
				});

		VolleySingleton.getInstance(getActivity().getApplicationContext())
				.addToRequestQueue(jsonObjReq);

	};

	public void fragmentInit() {
		list = new ArrayList<String>();
		
		listView = (ListView) getActivity().findViewById(R.id.offers);

		
	/*	pDialog = new ProgressDialog(getActivity());
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(false);
		pDialog.show();
		*/

		url = Constant.URL;
		nearbyListAdapter = new NearbyListAdapter(
				getActivity().getApplicationContext(), listvalue);
		listView.setAdapter(nearbyListAdapter);
	}

	/**
	 * Method to display the location on UI
	 * */
	
	/*private void displayLocation() {
		final Context context = getActivity().getApplicationContext();
		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
System.out.println(mLastLocation);
		if (mLastLocation != null) {
			double latitude = mLastLocation.getLatitude();
			double longitude = mLastLocation.getLongitude();
			current = new LatLng(latitude, longitude);
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(context, Locale.getDefault());

			try {
				addresses = geocoder.getFromLocation(latitude, longitude, 1);
				// Here 1 represent max location result to returned, by
				// documents it recommended 1 to 5

				String address = addresses.get(0).getAddressLine(0); // If any
																		// additional
																		// address
																		// line
																		// present
																		// than
																		// only,
																		// check
																		// with
																		// max
																		// available
																		// address
																		// lines
																		// by
																		// getMaxAddressLineIndex()

				String locality = address.replaceAll("\\P{L}", "");
			
				locationnearby.setText(locality);
				pDialog.dismiss();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //

			// lblLocation.setText(latitude + ", " + longitude);

		} else {

			// lblLocation
			// .setText("(Couldn't get the location. Make sure location is enabled on the device)");
		}
	}

	*//**
	 * Creating google api client object
	 * *//*
	protected synchronized void buildGoogleApiClient() {
		final Context context = getActivity().getApplicationContext();
		mGoogleApiClient = new GoogleApiClient.Builder(context)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	*//**
	 * Method to verify google play services on the device
	 * *//*
	private boolean checkPlayServices() {
		final Context context = getActivity().getApplicationContext();
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(context);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				GooglePlayServicesUtil.getErrorDialog(resultCode,
						getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
			} else {
				Toast.makeText(context, "This device is not supported.",
						Toast.LENGTH_LONG).show();
				getActivity().finish();
			}
			return false;
		}
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
		if (mGoogleApiClient != null) {
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		checkPlayServices();
	}

	*//**
	 * Google api callback methods
	 *//*
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.i("Api Connect",
				"Connection failed: ConnectionResult.getErrorCode() = "
						+ result.getErrorCode());
	}

	@Override
	public void onConnected(Bundle arg0) {

		// Once connected with google api, get the location
		displayLocation();
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		mGoogleApiClient.connect();
	}

*/	 /* Request updates at startup */
	  @Override
	public void onResume() {
	    super.onResume();
	    locationManager.requestLocationUpdates(provider, 100, 1, this);
	  }

	  /* Remove the locationlistener updates when Activity is paused */
	  @Override
	  public void onPause() {
	    super.onPause();
	    locationManager.removeUpdates(this);
	  }

	  @Override
	  public void onLocationChanged(Location location) {
	 /*   int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());*/
		  setaddress(location);
		  
		  
	  }

	  @Override
	  public void onStatusChanged(String provider, int status, Bundle extras) {
	    // TODO Auto-generated method stub

	  }

	  @Override
	  public void onProviderEnabled(String provider) {
	    Toast.makeText(context, "Enabled new provider " + provider,
	        Toast.LENGTH_SHORT).show();

	  }

	  @Override
	  public void onProviderDisabled(String provider) {
	    Toast.makeText(context, "Disabled provider " + provider,
	        Toast.LENGTH_SHORT).show();
	  }
public void setaddress(Location mLastLocation){
	
	if (mLastLocation != null) {
		double latitude = mLastLocation.getLatitude();
		double longitude = mLastLocation.getLongitude();
		current = new LatLng(latitude, longitude);
		 System.out.println(latitude);
		Geocoder geocoder;
		List<Address> addresses;
		System.out.println(getActivity().getApplicationContext()+" :( "+Locale.getDefault());
		geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			// Here 1 represent max location result to returned, by
			// documents it recommended 1 to 5

			String address = addresses.get(0).getAddressLine(0); // If any
																	// additional
																	// address
																	// line
																	// present
																	// than
																	// only,
																	// check
																	// with
																	// max
																	// available
																	// address
																	// lines
																	// by
																	// getMaxAddressLineIndex()

			String locality = address.replaceAll("\\P{L}", "");
		
			locationnearby.setText(locality);
		/*	pDialog.dismiss();*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //

		// lblLocation.setText(latitude + ", " + longitude);

	} else {

		// lblLocation
		// .setText("(Couldn't get the location. Make sure location is enabled on the device)");
	}

}}
