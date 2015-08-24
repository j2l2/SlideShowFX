package org.chsty.SlideShowFX.model;

import java.nio.file.Path;

import javafx.scene.image.Image;

public class SlideShowImage {
	private Image image;
	int width = 720;
	int height = 580;

	public SlideShowImage() {
		this(null);
	}
	
	public SlideShowImage(Path imgFile) {
		this.image = new Image(imgFile.toUri().toString(), width, height, true, true, false);
	}
}
