package com.orlyn.umedinfo.helper;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;
	
	public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }
	 
	@Override
	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
        Bitmap imgBitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            imgBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		return imgBitmap;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if(bmImage!=null && result!=null){
			Bitmap scaledImg = Bitmap.createBitmap(result,0,0,80,80);
			bmImage.setImageBitmap(scaledImg);
		}
	}

}
