package com.plantplaces.photos.plantplacespostprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class PhotoProcessor {
	
	@JmsListener(destination="photos")
	public void processPhoto(String path) {
		System.out.println("Path is: "+path);
		File file = new File(path);
		Path thumbNailPath = Paths.get(path).getParent();
		Path fileName = Paths.get(path).getFileName();
		String thumbNailFullPath = thumbNailPath + File.separator + "thumbnail" + File.separator + fileName;
		File thumbNailFile = new File(thumbNailFullPath);
		try {
			Thumbnails.of(file).size(100,100).toFile(thumbNailFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
