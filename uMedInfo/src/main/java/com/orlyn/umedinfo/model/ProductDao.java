package com.orlyn.umedinfo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orlyn.umedinfo.R;
import com.orlyn.umedinfo.api.PillFill;

public class ProductDao {
	ProductFindByTermListener productFindByTermListener;
	ProductFindBySPLIDListener productFindBySPLIDListener;
	RequestInterceptor requestInterceptor = new RequestInterceptor(){
		@Override
		public void intercept(RequestFacade request) {
			//get apikey from pill fill from
			request.addHeader("api_key", "----filpill api key--------");
		}
	};
	RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://developer.pillfill.com").setRequestInterceptor(requestInterceptor).build();
	
	public void findByTerm(String term, ProductFindByTermListener productFindByTermListener){
		
		Map<String, String> queryParam = new HashMap();
		queryParam.put("term", term);
		queryParam.put("type", "name");
		queryParam.put("page", "0");
		
		this.productFindByTermListener = productFindByTermListener;
		PillFill pillFIll = restAdapter.create(PillFill.class);
		pillFIll.products(queryParam, new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				ProductDao.this.productFindByTermListener.ProductFindByTermResultFail("Failure Product");
			}

			@Override
			public void success(JsonElement resultJE, Response arg1) {

				ArrayList<Product> products = new ArrayList<Product>();

				JsonArray productsJA = resultJE.getAsJsonArray();
				if(productsJA!=null && productsJA.size()>0){
					for(JsonElement productJE : productsJA) {
						
						 JsonObject productJO = productJE.getAsJsonObject();
						 JsonObject productInfoJO = productJO.get("product").getAsJsonObject();
						 String splId = productJO.get("splId").getAsString();
						 Product product = new Product();

						 product.setSplId(productJO.get("splId").getAsString());
						 product.setName(productInfoJO.get("name").getAsString());
						 product.setForm(productInfoJO.get("form").getAsString());
						 product.setManufacturer(productJO.get("manufacturer").getAsString());
						 product.setRoute(productInfoJO.get("route").getAsString());
						 JsonArray pkgJA = productInfoJO.get("pkg").getAsJsonArray();
						 
						 boolean foundImg = false;
						 for(JsonElement pkgJE : pkgJA) {
							 JsonArray splLabelImageJA = pkgJE.getAsJsonObject().get("splLabelImage").getAsJsonArray();
							 for(JsonElement splLabelImageJE : splLabelImageJA) {
								 foundImg=true;
								 
								 String imgURL = String.format("http://dailymed.nlm.nih.gov/dailymed/image.cfm?setid=%s&name=%s", 
										 splId.toLowerCase(Locale.ENGLISH), 
										 splLabelImageJE.getAsString());
								 product.setFirstImage(imgURL);
								 
								 
								 if(foundImg) break;
							 }
							 if(foundImg) break;
						 }
						 products.add(product);
					}
				}

				ProductDao.this.productFindByTermListener.ProductFindByTermResultSuccess(products);
			}

			
			
		});
	}
	
	public void findBySPLID(String splid, ProductFindBySPLIDListener productFindBySPLIDListener){
		final String splId = splid;
		this.productFindBySPLIDListener = productFindBySPLIDListener;
		PillFill pillFIll = restAdapter.create(PillFill.class);
		pillFIll.product(splId, new Callback<JsonElement>() {

			@Override
			public void failure(RetrofitError arg0) {
				ProductDao.this.productFindBySPLIDListener.ProductFindBySPLIDFail("Failure Product");
			}

			@Override
			public void success(JsonElement resultJE, Response arg1) {
				
				Product product=new Product();
				product.setSplId(splId);
				
				boolean doneProduct = false;
				
				JsonArray rootsJA = resultJE.getAsJsonArray();
				if(rootsJA!=null){
					for(JsonElement rootJE:rootsJA){
						JsonObject rootJO = rootJE.getAsJsonObject();
						JsonObject splEntryJO = rootJO.get("splEntry").getAsJsonObject();
						JsonObject productsJO = splEntryJO.get("products").getAsJsonObject();
						JsonArray productJA = productsJO.get("product").getAsJsonArray();
						for(JsonElement productJE:productJA){
							doneProduct=true;
							
							JsonObject productJO = productJE.getAsJsonObject();
							
							product.setName(productJO.get("name").getAsString());
							product.setForm(productJO.get("form").getAsString());
							product.setRoute(productJO.get("route").getAsString());
							
							JsonArray pkgJA = productJO.get("pkg").getAsJsonArray();
							boolean foundImg = false;
							for(JsonElement pkgJE : pkgJA) {
								 JsonArray splLabelImageJA = pkgJE.getAsJsonObject().get("splLabelImage").getAsJsonArray();
								 for(JsonElement splLabelImageJE : splLabelImageJA) {
									 foundImg=true;
									 
									 String imgURL = String.format("http://dailymed.nlm.nih.gov/dailymed/image.cfm?setid=%s&name=%s", 
											 splId.toLowerCase(Locale.ENGLISH), 
											 splLabelImageJE.getAsString());

									 product.setFirstImage(imgURL);
									 
									 
									 if(foundImg) break;
								 }
								 if(foundImg) break;
							}
							 
							if(doneProduct) break;
						}
						
						if(doneProduct) break;
					}
					
				}
				
				ProductDao.this.productFindBySPLIDListener.ProductFindBySPLIDSuccess(product);
			}
			
		});
	}
	
	public interface ProductFindByTermListener{
		public void ProductFindByTermResultSuccess(ArrayList<Product> products);
		public void ProductFindByTermResultFail(String resultMessage);
	}
	
	public interface ProductFindBySPLIDListener{
		public void ProductFindBySPLIDSuccess(Product product);
		public void ProductFindBySPLIDFail(String resultMessage);
	}
}
