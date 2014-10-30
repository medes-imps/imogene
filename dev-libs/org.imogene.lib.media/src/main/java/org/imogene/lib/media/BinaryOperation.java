package org.imogene.lib.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.imogene.lib.common.binary.Binary;
import org.imogene.lib.common.binary.file.BinaryFileManager;

/**
 * Binary post treatment tasks to create flv files for video to be played in a browser or thumbnail images to be
 * embedded in forms.
 * 
 * @author MEDES-IMPS
 * 
 */
public class BinaryOperation {

	private MediaConverter videoConverter;
	private MediaConverter photoConverter;
	private MediaConverter audioConverter;

	/**
	 * @param binary
	 */
	public void operate(Binary binary) {
		/* binary file conversion to flv */
		if (binary.getContentType().contains("video")) {
			File localFile = BinaryFileManager.getInstance().buildFilePath(binary);
			File binaryPath = BinaryFileManager.getInstance().buildFlvFilePath(binary);
			File flvFile = new File(binaryPath, "flv/" + localFile.getName() + ".flv");
			if (!binary.getContentType().contains("x-flash-video")) {
				videoConverter.convert(localFile, new File(binaryPath + "flv/" + localFile.getName() + ".flv"),
						binary.getContentType());
			} else {
				try {
					FileInputStream fis = new FileInputStream(localFile);
					FileOutputStream fos = new FileOutputStream(flvFile);
					IOUtils.copy(fis, fos);
					fis.close();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		/* image file thumbnail */
		if (binary.getContentType().contains("image")) {
			File localFile = BinaryFileManager.getInstance().buildFilePath(binary);
			File thumbnail = BinaryFileManager.getInstance().buildThumbFilePath(binary);
			photoConverter.convert(localFile, thumbnail, binary.getContentType());
		}
		// audio file
		if (binary.getContentType().contains("audio")) {
			File localFile = BinaryFileManager.getInstance().buildFilePath(binary);
			File mp3File = BinaryFileManager.getInstance().buildMp3FilePath(binary);
			if (!binary.getContentType().contains("mp3")) {
				audioConverter.convert(localFile, mp3File, binary.getContentType());
			} else {
				try {
					FileInputStream fis = new FileInputStream(localFile);
					FileOutputStream fos = new FileOutputStream(mp3File);
					IOUtils.copy(fis, fos);
					fis.close();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setPhotoConverter(MediaConverter photoConverter) {
		this.photoConverter = photoConverter;
	}

	public void setVideoConverter(MediaConverter videoConverter) {
		this.videoConverter = videoConverter;
	}

	public void setAudioConverter(MediaConverter audioConverter) {
		this.audioConverter = audioConverter;
	}

}
