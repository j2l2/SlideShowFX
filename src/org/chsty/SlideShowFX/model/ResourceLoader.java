package org.chsty.SlideShowFX.model;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class ResourceLoader extends Thread implements ImageLoader {
	double width = 800;
	double height = 600;
	BlockingQueue<Image> images = new ArrayBlockingQueue<>(2);
	Image eof = new WritableImage(1, 1);
	boolean cancelled = false;
	String[] resources = { "pics/bugs-0.jpg", "pics/bugs-1.jpg",
			"pics/cooper.jpg", "pics/tomatoes.jpg" };

	public void cancel() throws InterruptedException {

		cancelled = true;
		interrupt();
		join();
	}

	public Image getNextImage() {

		try {
			Image res = images.take();

			if (res != eof)
				return res;
		} catch (InterruptedException ex) {
			Logger.getLogger(ResourceLoader.class.getName()).log(Level.SEVERE,
					null, ex);
		}
		return null;
	}

	@Override
	public void run() {

		System.out.println("scanning");
		try {
			int id = 0;
			while (true) {
				String path = resources[id];

				InputStream is = getClass().getResourceAsStream(path);
				if (is != null) {
					System.out.println("Load: " + path);
					Image image = new Image(is, width, height, true, true);

					if (!image.isError())
						images.put(image);
				}

				id++;
				if (id >= resources.length)
					id = 0;
			}
		} catch (InterruptedException x) {
		} finally {
			if (!cancelled) {
				try {
					images.put(eof);
				} catch (InterruptedException ex) {
				}
			}
		}
	}
}
