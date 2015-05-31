package com.orlyn.umedinfo.ui;

import com.orlyn.umedinfo.R;
import com.orlyn.umedinfo.R.id;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductViewHolder extends ViewHolder {
	
	protected TextView vName;
	protected TextView vForm;
	protected TextView vRoute;
	protected TextView vManufacturer;
	protected TextView vQuantity;
	protected ImageView vDrugImg;
	

	public ProductViewHolder(View v) {
		super(v);
		vName = (TextView) v.findViewById(R.id.product_name);
		vForm = (TextView) v.findViewById(R.id.product_form);
		vRoute = (TextView) v.findViewById(R.id.product_route);
		vManufacturer = (TextView) v.findViewById(R.id.product_manufacturer);
		vQuantity = (TextView) v.findViewById(R.id.product_quantity); 
		vDrugImg = (ImageView)v.findViewById(R.id.product_img); 
	}

}
