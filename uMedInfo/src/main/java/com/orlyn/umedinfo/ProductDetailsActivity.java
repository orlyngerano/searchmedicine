package com.orlyn.umedinfo;

import java.util.ArrayList;

import com.orlyn.umedinfo.model.Product;
import com.orlyn.umedinfo.model.ProductDao;
import com.orlyn.umedinfo.ui.ProductAdapter;
import com.orlyn.umedinfo.ui.ProductDetailsFragment;
import com.orlyn.umedinfo.ui.ProductListFragment;
import com.squareup.picasso.Picasso;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProductDetailsActivity extends ActionBarActivity {

	ProductDetailsFragment productdetailsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		
		this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = this.getIntent();
		String splid = (String) intent.getSerializableExtra("splid");
		
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		productdetailsFragment = (ProductDetailsFragment)fragmentManager.findFragmentById(R.id.productdetailsFragment);
		
		productdetailsFragment.findAndLoadSPLID(splid);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.product_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}else if (id == android.R.id.home) {
			finish();
			overridePendingTransition(R.anim.slide_lf_in, R.anim.slide_if_out);
			return true;
	    }
		return super.onOptionsItemSelected(item);
	}
}
