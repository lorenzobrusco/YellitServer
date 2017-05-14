package tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BuilderImage {

	/**
	 * 
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage  Build(byte[] bytes) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(bytes));
	}	
}
