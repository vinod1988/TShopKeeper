package com.yh.shopkeeper.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import android.os.Environment;

public class FileUtils {
	private static String SDCardRoot=Environment.getExternalStorageDirectory()+"/";
		
	static public boolean isFileExist(String filename,String path)
	{
		File file = new File(SDCardRoot + path +File.separator +filename);
		return file.exists();
	}

	static public File createDir(String dirPath)
	{
		
		File dir=new File(SDCardRoot+dirPath+File.separator);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		return dir;
	}
	
	static public File createFileInSD(String filename,String dir)
	{
		File file =new File(SDCardRoot+dir+File.separator+filename);
		try {
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			return file;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
	}
	
	
	static public File write2SDFromInputStream(String filename,String path ,InputStream input)
	{
		OutputStream output = null;
		File file =null;
		try{
			File file2 = createDir(path);
			System.out.println(file2);
			file = createFileInSD(filename,path);
			output=new FileOutputStream(file);
			byte[] buffer=new byte[4*1024];
			int count=-1;
			while((count=input.read(buffer))!=-1)
			{
				output.write(buffer, 0, count);
			}
			output.flush();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				input.close();
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}
	
	static  public String getFileNameFromUrl(String url){
		return url.substring(url.lastIndexOf("/"));
		
	} 
}
