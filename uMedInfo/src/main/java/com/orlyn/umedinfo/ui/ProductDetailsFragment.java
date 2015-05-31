package com.orlyn.umedinfo.ui;

import com.orlyn.umedinfo.R;
import com.orlyn.umedinfo.model.Product;
import com.orlyn.umedinfo.model.ProductDao;
import com.squareup.picasso.Picasso;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailsFragment extends Fragment {
	
	private String SPLID;
	private ProductDao productDao;
	private ProgressDialog progressWait;

	private ImageView product_detail_img;
	private TextView product_detail_name;
	private TextView product_detail_form;
	private TextView product_detail_route;		
		
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		productDao = new ProductDao();
		
		View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);
		
		product_detail_img = (ImageView) rootView.findViewById(R.id.product_detail_img);
		product_detail_name = (TextView)rootView.findViewById(R.id.product_detail_name);
		product_detail_form = (TextView)rootView.findViewById(R.id.product_detail_form);
		product_detail_route = (TextView)rootView.findViewById(R.id.product_detail_route);		
		
	
		return rootView;
	}
	
	public void loadProduct(){
		progressWait = ProgressDialog.show(this.getActivity(), "In progress","Searching \""+this.SPLID+"\"", true);
		productDao.findBySPLID(SPLID, new ProductDao.ProductFindBySPLIDListener(){

			@Override
			public void ProductFindBySPLIDSuccess(Product product) {
				if(product!=null){
					product_detail_name.setText(product.getName());
					product_detail_form.setText(product.getForm());
					product_detail_route.setText(product.getRoute());
					
					String firstImage = product.getFirstImage();
					if(firstImage!=null && firstImage.isEmpty()==false){
						Picasso.with(ProductDetailsFragment.this.getActivity()).load(firstImage).error(R.drawable.no_image_available).placeholder(R.drawable.circ_load_80).fit().into(product_detail_img);
					}					
				}
				

				progressWait.dismiss();
			}

			@Override
			public void ProductFindBySPLIDFail(String resultMessage) {
				progressWait.dismiss();
			}
			
		});		
	}
	
	public void findAndLoadSPLID(String SPLID){
		this.SPLID = SPLID;
		this.loadProduct();
	}

}
