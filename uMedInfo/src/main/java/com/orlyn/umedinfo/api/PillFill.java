package com.orlyn.umedinfo.api;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

import com.google.gson.JsonElement;

public interface PillFill {

	@GET("/service/v1/products")
	public void products(@QueryMap Map<String, String> parameters,Callback<JsonElement> calback);

	@GET("/service/v1/product/{splid}")
	public void product(@Path("splid") String splid,Callback<JsonElement> calback);
}
