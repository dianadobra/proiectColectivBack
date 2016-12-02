package com.webcbg.eppione.companysettings.service;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	// where to be save the file
	@Value("${path}")
	private String path;

	@Value("${path-delimiter}")
	private String pathDelim;

	public String getFilePath(final MultipartFile file, final String fileType, final Long fileTypeId) {
		return fileType + pathDelim + fileTypeId + "_" + file.getOriginalFilename();
	}

	public String uploadFile(final MultipartFile file, final String nameOfFile) throws IOException {

		BufferedOutputStream stream = null;
		try {
			final byte[] bytes = file.getBytes();
			final String fileName = path + pathDelim + nameOfFile;
			final File file1 = new File(fileName);
			file1.getParentFile().mkdirs();
			stream = new BufferedOutputStream(new FileOutputStream(file1));

			stream.write(bytes);
			stream.flush();
			return fileName;
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	/**
	 * Get image based on the provided path
	 *
	 * @param path
	 *            relative path
	 * @return
	 * @throws IOException
	 */
	public byte[] getImage(final String path) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final BufferedImage image = ImageIO.read(new File(path));

		ImageIO.write(image, "jpg", baos);
		baos.flush();
		final byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	public byte[] getDocument(final String cpdPath) throws IOException {
		final Path path = Paths.get(cpdPath);
		final byte[] data = Files.readAllBytes(path);
		return data;
	}

	public boolean deleteFile(final String path) {
		final File file = new File(path);
		return file.delete();
	}
}
