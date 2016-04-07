package fatec.com.digital_library.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {
	public void resize(String inputImagePath, String outputImagePath) {
		int scaledWidth = 300;
		int scaledHeight = 400;
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage;
		try {
			inputImage = ImageIO.read(inputFile);
			BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
			g2d.dispose();

			String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

			ImageIO.write(outputImage, formatName, new File(outputImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
