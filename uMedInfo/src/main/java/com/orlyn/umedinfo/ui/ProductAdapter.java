package com.orlyn.umedinfo.ui;


import java.util.List;

import com.orlyn.umedinfo.R;
import com.orlyn.umedinfo.R.layout;
import com.orlyn.umedinfo.helper.DownloadImageTask;
import com.orlyn.umedinfo.model.Product;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductAdapter extends Adapter<ProductViewHolder> {
	
	private List<Product> productList;
	private ProductListeners productListeners;
	private Activity parentActivity;
	
	
	public Product getItemAt(int position){
		if(this.getItemCount()>0){
			return productList.get(position);
		}else{
			return null;
		}
	}
	
	public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

	@Override
	public int getItemCount() {
		return this.productList.size();
	}

	@Override
	public void onBindViewHolder(ProductViewHolder productViewHolder, int i) {
		Product product = productList.get(i);
		
		productViewHolder.vName.setText(product.getName());
		productViewHolder.vForm.setText(product.getForm());
		productViewHolder.vRoute.setText(product.getRoute());
		productViewHolder.vManufacturer.setText(product.getManufacturer());
		productViewHolder.vQuantity.setText(product.getQuantity());
		String firstImage = product.getFirstImage();
		if(firstImage!=null && firstImage.isEmpty()==false){
			Picasso.with(parentActivity).load(product.getFirstImage()).error(R.drawable.no_image_available).placeholder(R.drawable.circ_load_80).fit().into(productViewHolder.vDrugImg);
		}
		
		
		productViewHolder.itemView.setTag(product.getSplId());
	}

	@Override
	public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		Product product = productList.get(i);
		View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_view, viewGroup, false);
		parentActivity = (Activity)viewGroup.getContext();

		if(parentActivity instanceof ProductAdapter.ProductListeners){
			productListeners = (ProductListeners) parentActivity;
			
		}
		
		itemView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				productListeners.OnClickProduct(v);
			}
		});
		
		return new ProductViewHolder(itemView);
	}

	public interface ProductListeners{
		public void OnClickProduct(View v);
	}
}
