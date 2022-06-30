package org.eam.sharedgdrive.application;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReturnAllGDriveFiles {
	/**
	 * Retrieve a list of File resources.
	 *
	 * @param service Drive API service instance.
	 * @return List of File resources.
	 */
	protected static List<File> retrieveAllFiles(Drive service) throws IOException {
		List<File> result = new ArrayList<>();
		Files.List request = service.files().list().setFields("nextPageToken, files(id, name, permissions(*))");
		int i = 0;
		do {
			try {
				FileList files = request.execute();
				System.out.printf(++i + "\r");
				result.addAll(files.getFiles());
				request.setPageToken(files.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null && request.getPageToken().length() > 0);

		return result;
	}
}
