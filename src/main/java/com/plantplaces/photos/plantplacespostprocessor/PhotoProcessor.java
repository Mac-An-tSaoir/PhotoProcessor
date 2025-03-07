package com.plantplaces.photos.plantplacespostprocessor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

@Component
public class PhotoProcessor {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	//@JmsListener(destination="photos")
	@KafkaListener(topics="photoIn", groupId="plantphotos")
	public void processPhoto(String path) {
		try {
			System.out.println("Path is: "+path);
			File file = new File(path);
			Path photoPath = Paths.get(path).getParent();
			Path fileName = Paths.get(path).getFileName();
			String thumbNailFullPath = photoPath + File.separator + "thumbnail" + File.separator + fileName;
			File thumbNailFile = new File(thumbNailFullPath);
			String watermarkPath = photoPath + File.separator + "watermark.png";
			File watermarkFile = new File(watermarkPath);
		
			BufferedImage watermark = ImageIO.read(watermarkFile);
			Thumbnails.of(file).scale(1).watermark(Positions.BOTTOM_CENTER, watermark, 0.9f).toFile(file);
			Thumbnails.of(file).size(100,100).toFile(thumbNailFile);
			kafkaTemplate.send("photoOut", path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			kafkaTemplate.send("photoException", path);
		}
		
		
	}

}
