package com.orlyn.umedinfo.ui;

import java.util.ArrayList;

import com.orlyn.umedinfo.R;
import com.orlyn.umedinfo.model.Product;
import com.orlyn.umedinfo.model.ProductDao;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductListFragment extends Fragment {

	private View rootView;
	private RecyclerView productList;
	private String productTerm;
	private ProductDao productDao;
	private ProgressDialog progressWait;
	private ProductListFragmentListners productListFragmentListners;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		productDao = new ProductDao();
		rootView = inflater.inflate(R.layout.fragment_product_list, container, false);
		productList = (RecyclerView)rootView.findViewById(R.id.product_list);
		productList.setLayoutManager(new LinearLayoutManager(getActivity()));
		
		
		
		return rootView;
	}
	
	public void loadProduct(){
		this.clearProductList();
		progressWait = ProgressDialog.show(this.getActivity(), "In progress","Searching \""+this.productTerm+"\"", true);
		productDao.findByTerm(this.productTerm, new ProductDao.ProductFindByTermListener() {
			@Override
			public void ProductFindByTermResultSuccess(ArrayList<Product> products) {
				ProductListFragment.this.setProductList(products);
				progressWait.dismiss();
				
				if(ProductListFragment.this.productListFragmentListners!=null){
					ProductListFragment.this.productListFragmentListners.doneLoad();
				}
			}
			
			@Override
			public void ProductFindByTermResultFail(String resultMessage) {
				System.out.println("error term result");
				progressWait.dismiss();
				if(ProductListFragment.this.productListFragmentListners!=null){
					ProductListFragment.this.productListFragmentListners.doneLoad();
				}
			}
		});
	}
	
	public void findAndLoadTerm(String productTerm){
		this.productTerm = productTerm;
		this.loadProduct();
	}
	
	public void setProductList(ArrayList<Product> products){
		productList.setAdapter(new ProductAdapter(products));	
	}
	
	public void clearProductList(){
		productList.setAdapter(null);
	}
	
	public Product getProductByListIndex(int index){
		if(productList==null){
			return null;
		}
		
		ProductAdapter productAdapter = (ProductAdapter)productList.getAdapter();
		if(productAdapter==null){
			return null;
		}else{
			return productAdapter.getItemAt(index);
		}
	}
	
	public void addProductListFragmentListners(ProductListFragmentListners productListFragmentListners){
		this.productListFragmentListners = productListFragmentListners;
	}

	public interface ProductListFragmentListners{
		public void doneLoad();
	}
}
