package com.springposter.service;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springposter.entity.Post;
import com.springposter.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	public List<Post> getSomePosts(int offset) {
		return postRepository.findPosts(offset);
	}

	public void add(Post p) {
		postRepository.save(p);
	}

	public void clear() {
		File file = new File("images/");
		for(File f : file.listFiles())
			f.delete();
		postRepository.deleteAllInBatch();
	}

	public String checkName(String name){
		String imgName = name;
		if(postRepository.findByimageName(imgName) != null){
			String old = name.substring(0, name.indexOf("."));
			imgName = name.replace(old, old + new Random().nextInt(1000));
		}
		return imgName;
	}
	
	public void removeOne(long id){
		
		try{
			
			Post p = postRepository.findOne(id);
			File file = new File("images/" + p.getImageName());
			file.delete();
			postRepository.delete(p);
			
		}
		catch (NullPointerException e) {}
	}

	public void resizeAndSave(MultipartFile img, String name) throws IOException {
		File upl = new File("images/" + name);
		upl.createNewFile();
		FileOutputStream fout = new FileOutputStream(upl);
		BufferedImage original = ImageIO.read(img.getInputStream());
		
		if(original.getWidth() > 600 && !img.getContentType().contains("gif")){
			Image org = original.getScaledInstance(600, -1, Image.SCALE_SMOOTH);
			BufferedImage newImg = new BufferedImage(600, org.getHeight(null), original.getType());
			Graphics2D g = newImg.createGraphics();
			g.drawImage(org, 0, 0, 600, org.getHeight(null), null);
			g.dispose();
			ImageIO.write(newImg, img.getContentType().replace("image/", ""), upl);
		}
		
		else 
			fout.write(img.getBytes());
		
		fout.close();
	}


}
