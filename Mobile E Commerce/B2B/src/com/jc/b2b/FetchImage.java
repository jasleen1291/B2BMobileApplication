package com.jc.b2b;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.jc.b2b.utils.B2BUtils;

public class FetchImage extends IntentService {
	private File cacheDir;
	private String CACHE_FOLDER="cache";

	public FetchImage() {
		super("FetchImage");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
		
	}

	@Override
	public void onCreate() {
		
		    super.onCreate();
		    String tmpLocation = 
		    	Environment.getExternalStoragePublicDirectory(
		    	        Environment.DIRECTORY_PICTURES
		    	    ).getPath() + File.separator +CACHE_FOLDER ;
		       
		    cacheDir = new File(tmpLocation);
		    if(!cacheDir.exists()){
		        cacheDir.mkdirs();
		    }
		
		super.onCreate();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		String remoteUrl = arg0.getExtras().getString("url");
	    String location;
	    String filename = 
	        remoteUrl.substring(
	        remoteUrl.lastIndexOf(File.separator)+1);
	    
	    File tmp = new File(cacheDir.getPath() 
	            + File.separator +filename);
	    if(tmp.exists()){
	        location = tmp.getAbsolutePath();
	        notifyFinished(location, filename);
	        stopSelf();
	        return;
	    }
	    try{
	        URL url = new URL(remoteUrl);
	        HttpURLConnection httpCon = 
	            (HttpURLConnection)url.openConnection();
	        if(httpCon.getResponseCode() != 200)
	            throw new Exception("Failed to connect");
	        InputStream is = httpCon.getInputStream();
	        FileOutputStream fos = new FileOutputStream(tmp);
	        writeStream(is, fos);
	        fos.flush(); fos.close();
	        is.close();
	        location = tmp.getAbsolutePath();
	        notifyFinished(location, filename);
	    }catch(Exception e){
	        Log.e("Service","Failed!",e);
	    }
		
	}
	private void writeStream(InputStream is, FileOutputStream fos) {
		byte buf[]=new byte[1024];
		  int len;
		  try {
			while((len=is.read(buf))>0)
			  fos.write(buf,0,len);
		
		  fos.close();
		  is.close();
		  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void notifyFinished(String location, String remoteUrl){
	    Intent i = new Intent(B2BUtils.IMAGE_INTENT);
	    i.putExtra("imagepath", location);
	    i.putExtra("picture", remoteUrl);
	    //Log.d("img", location);
	    FetchImage.this.sendBroadcast(i);
	}
}
