package com.orlyn.umedinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.orlyn.umedinfo.model.Product;
import com.orlyn.umedinfo.model.ProductDao;
import com.orlyn.umedinfo.ui.ProductAdapter;
import com.orlyn.umedinfo.ui.ProductDetailsFragment;
import com.orlyn.umedinfo.ui.ProductListFragment;
import com.orlyn.umedinfo.ui.ProductListFragment.ProductListFragmentListners;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements ProductAdapter.ProductListeners, ProductListFragment.ProductListFragmentListners {
	
	private SearchView searchView;
	
	private ProductListFragment productlistFragment;
	private ProductDetailsFragment productdetailsFragment;
	
	private FrameLayout productlistLayout;
	private FrameLayout productdetailsLayout;
	
	private FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main);
		
		productlistLayout = (FrameLayout)this.findViewById(R.id.productlistLayout);
		productdetailsLayout = (FrameLayout)this.findViewById(R.id.productdetailsLayout);
		
		
		fragmentManager = this.getSupportFragmentManager();
		
		productlistFragment =  new ProductListFragment();
		productdetailsFragment = new ProductDetailsFragment();
		
		fragmentManager
		.beginTransaction()
		.add(R.id.productlistLayout, productlistFragment)
		.commit();
		
		fragmentManager
		.beginTransaction()
		.add(R.id.productdetailsLayout, productdetailsFragment)
		.commit();

		productlistFragment.addProductListFragmentListners(this);

		this.doLayoutByConfig(getResources().getConfiguration());

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem searchItem = menu.findItem(R.id.search);
		MenuItemCompat.expandActionView(searchItem);
		searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setQueryHint("Search Drugs");
		searchView.setOnQueryTextListener(new OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				searchView.clearFocus();
				MainActivity.this.searchDrugText(query);
				return false;
			}
			
		});
			
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public void OnClickProduct(View productView) {
		if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE){
			productdetailsFragment.findAndLoadSPLID((String)productView.getTag());
		}else{
			Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
			intent.putExtra("splid",(String) productView.getTag());
			this.startActivity(intent);
		}
	}

	
	
	private void searchDrugText(String drugText){
		productlistFragment.findAndLoadTerm(drugText);
	}
	
	
	
	public void onConfigurationChanged (Configuration newConfig){
		super.onConfigurationChanged(newConfig);		
		this.doLayoutByConfig(newConfig);		
	}
	
	private void doLayoutByConfig(Configuration config){
		LinearLayout.LayoutParams list_llp=null;

		//default settings to portrait
		if(config.orientation==Configuration.ORIENTATION_LANDSCAPE){

			list_llp = new LinearLayout.LayoutParams(0,LayoutParams.MATCH_PARENT,1);

			Product product = productlistFragment.getProductByListIndex(0);
			if(product==null){
				productdetailsLayout.setVisibility(View.GONE);
			}else{
				productdetailsLayout.setVisibility(View.VISIBLE);
				productdetailsFragment.findAndLoadSPLID(product.getSplId());
			}
			
		}else{
			list_llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			productdetailsLayout.setVisibility(View.GONE);
		}
		productlistLayout.setLayoutParams(list_llp);

	}

	@Override
	public void doneLoad() {		
		this.doLayoutByConfig(getResources().getConfiguration());
	}
	
	
	
}
