package br.com.metronic.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@PropertySource("classpath:/file.properties")
public class FileSaver {

	@Autowired
	private Environment env;
	
	public String write(String baseFolder, MultipartFile file) {
		String saveDirectory = getRootPath() + baseFolder + "/";
		try {
			file.transferTo(new File(saveDirectory + file.getOriginalFilename()));
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public File load(String file) {
		return null;
	}
	
	private String getRootPath() {
		return env.getProperty("root.path");
	}

}
