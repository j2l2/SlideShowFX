package org.chsty.SlideShowFX.model;

import javafx.scene.image.Image;

public interface ImageLoader {
	public void start();

	public void cancel() throws InterruptedException;

	public Image getNextImage();
}
