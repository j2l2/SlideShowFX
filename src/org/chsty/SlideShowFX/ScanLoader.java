package org.chsty.SlideShowFX;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;

import javafx.scene.image.Image;

import com.sun.istack.internal.logging.Logger;

public class ScanLoader extends Thread implements FileVisitor<Path>{
	String rootPath = "/Users/lliu/Pictures";
	boolean complete;
	private int height = 720;
	private int width = 580;
	private BlockingQueue<Image> images = new ArrayBlockingQueue(5);
	
	ScanLoader() {
		super();
	}
	@Override
	public void run() {
		System.out.println("scanning");
		try {
			Files.walkFileTree(Paths.get(rootPath), this);
			System.out.println("complete");
		} catch (IOException e) {
			Logger.getLogger(this.getClass().getName(), this.getClass())
            .log(Level.SEVERE, null, e);
		} finally {
			complete = true;
		}
	}
	
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
//		SlideShowImage ssi = new SlideShowImage(file);
		try {
			Image image = new Image(file.toUri().toString(), width, height, true, true, false);
			if (!image.isError())
				images.put(image);
		} catch (InterruptedException e) {
			 Logger.getLogger(this.getClass().getName(), this.getClass())
             .log(Level.SEVERE, null, e);
         return FileVisitResult.TERMINATE;
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc)
			throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc)
			throws IOException {
		// TODO Auto-generated method stub
		return FileVisitResult.CONTINUE;
	}

}
