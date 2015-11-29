package com.example.androidtask.adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidtask.R;
import com.example.androidtask.model.Categories;
import com.example.androidtask.model.DataValue;
import com.fedorvlasov.lazylist.ImageLoader;

public class NearbyListAdapter extends ArrayAdapter<DataValue> {
	  private final Context context;
	  private  ArrayList<DataValue> values;
	  View rowView ;
	  TextView name,offer,distance;
	  ImageView coverpic;
	  private Bitmap bmp; LinearLayout catLayout;
List<Categories> listct;
	  public NearbyListAdapter(Context context, ArrayList<DataValue> values ) {
	    super(context, -1, values);
	    this.context = context;
	    this.values = values;
	  }
@Override
public void notifyDataSetChanged() {
	// TODO Auto-generated method stub
	super.notifyDataSetChanged();
}
	  @SuppressLint("ResourceAsColor") @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    init(parent);
	       
	  name.setText(values.get(position).getBrandName());
	  offer.setText(values.get(position).getNumCoupons()+" offers");
	  
	  Double dist= Double.parseDouble(values.get(position).getDistance());
	  dist=(double) ((Math.round(dist))/1000);
	   distance.setText(dist+" km "+values.get(position).getNeighbourhoodName());
	   listct=values.get(position).getCategories();
	  
	   String IMG_URL=values.get(position).getLogoURL();
	   ImageLoader imageLoader=new ImageLoader(context);
	 
	   imageLoader.DisplayImage(IMG_URL, coverpic);	   
	  
	   for (int i = 0; i < listct.size(); i++) {    
		  /* View view=new View(context);
		   view.setBackgroundDrawable(background)
		   
		   
		   
		   */
		   
		  
		   String name = listct.get(i).getName();
		   ImageView bullet = new ImageView(context);
		   bullet.setBackgroundResource(R.drawable.bullet);
		   TextView tv = new TextView(context);
		   tv.setTextColor(R.color.headingtext)	;	   tv.setText(name);

		
		   catLayout.addView(bullet);
		   catLayout.addView(tv);
		 }
	   
	   /*
	   new AsyncTask<Void, Void, Void>() {                  
	        @Override
	        protected Void doInBackground(Void... params) {
	            try {
	                InputStream in = new URL(IMG_URL).openStream();
	                bmp = BitmapFactory.decodeStream(in);
	            } catch (Exception e) {
	               // log error
	            }
	            return null;
	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            if (bmp != null)
	                coverpic.setImageBitmap(bmp);
	        }

	   }.execute();*/
	    return rowView;
	  }

public void init(ViewGroup parent){
	LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 rowView = inflater.inflate(R.layout.offerrowlayout, parent, false);
name = (TextView) rowView.findViewById(R.id.name);
offer = (TextView) rowView.findViewById(R.id.offer);
distance = (TextView)rowView.findViewById(R.id.distance);
coverpic = (ImageView) rowView.findViewById(R.id.imagecovr);

catLayout = (LinearLayout)rowView.findViewById(R.id.catagorieslayout);}
} 
