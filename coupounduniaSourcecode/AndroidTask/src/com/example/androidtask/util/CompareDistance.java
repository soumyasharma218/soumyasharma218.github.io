package com.example.androidtask.util;

import java.util.Comparator;

import com.example.androidtask.model.DataValue;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

public class CompareDistance implements Comparator<DataValue> {
    LatLng currentLoc;

    public CompareDistance(LatLng current){
        currentLoc = current;
    }
    @Override
    public int compare(final DataValue place1, final DataValue place2) {
    	try{
    		
    		if(currentLoc!=null){
        double lat1 = Double.parseDouble(place1.getLatitude());
        double lon1 = Double.parseDouble(place1.getLongitude());
        double lat2 = Double.parseDouble(place2.getLatitude());;
        double lon2 = Double.parseDouble(place2.getLongitude());

        double distanceToPlace1 = distance(currentLoc.latitude, currentLoc.longitude, lat1, lon1);
        double distanceToPlace2 = distance(currentLoc.latitude, currentLoc.longitude, lat2, lon2);
        return (int) (distanceToPlace1 - distanceToPlace2);}else{    		return 0;
        }}
    	
    	catch(Exception e){
    		
    		
    		return 0;
    	}
    }

    public double distance(double fromLat, double fromLon, double toLat, double toLon) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = toLat - fromLat;
        double deltaLon = toLon - fromLon;
        double angle = 2 * Math.asin( Math.sqrt(
                Math.pow(Math.sin(deltaLat/2), 2) +
                        Math.cos(fromLat) * Math.cos(toLat) *
                                Math.pow(Math.sin(deltaLon/2), 2) ) );
        return radius * angle;
    }
}
