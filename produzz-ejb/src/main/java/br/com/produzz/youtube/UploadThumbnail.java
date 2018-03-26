package br.com.produzz.youtube;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import com.google.api.services.youtube.model.ThumbnailSetResponse;

public class UploadThumbnail {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadThumbnail.class);

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    /**
     * Define a global variable that specifies the MIME type of the image being uploaded.
     */
    private static final String IMAGE_FILE_FORMAT = "image/*";

    /**
     * Prompt the user to specify a video ID and the path for a thumbnail
     * image. Then call the API to set the image as the thumbnail for the video.
     */
    public static void main(String[] args) {
        try {
			// Authorize the request.
			Credential credential = null;

	    		try {
    				credential = Auth.renovar(
    						Auth.autorizar("uploadThumbnail").getRefreshToken());

	    		} catch (final Exception e) {
	    			System.exit(0);
	    		}

            // Prompt the user to enter the video ID of the video being updated.
            String videoId = getVideoIdFromUser();
            System.out.println("You chose " + videoId + " to upload a thumbnail.");

            // Prompt the user to specify the location of the thumbnail image.
            File imageFile = getImageFromUser();
            System.out.println("You chose " + imageFile + " to upload.");

//	    		uploadThumbnail(credential, videoId, new BufferedInputStream(new FileInputStream(imageFile)));

	    } catch (final Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
	}

	public static void uploadThumbnail(final Credential credential, final String videoId, final byte[] content) {
		LOGGER.info("uploadThumbnail(" + credential + ", " + videoId + ", " + content + ")");

		try {
            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
            			.setApplicationName("Plataforma-Produzz")
            			.build();

            // Create an object that contains the thumbnail image file's
            // contents.
            InputStreamContent mediaContent = new InputStreamContent(
                    IMAGE_FILE_FORMAT, getVideo(content));
            mediaContent.setLength(content.length);

            // Create an API request that specifies that the mediaContent
            // object is the thumbnail of the specified video.
            Set thumbnailSet = youtube.thumbnails().set(videoId, mediaContent);

            // Set the upload type and add an event listener.
            MediaHttpUploader uploader = thumbnailSet.getMediaHttpUploader();

            // Indicate whether direct media upload is enabled. A value of
            // "True" indicates that direct media upload is enabled and that
            // the entire media content will be uploaded in a single request.
            // A value of "False," which is the default, indicates that the
            // request will use the resumable media upload protocol, which
            // supports the ability to resume an upload operation after a
            // network interruption or other transmission failure, saving
            // time and bandwidth in the event of network failures.
            uploader.setDirectUploadEnabled(false);

            // Set the upload state for the thumbnail image.
            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                @Override
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        // This value is set before the initiation request is
                        // sent.
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            System.out.println("Upload percentage: " + uploader.getProgress());
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            // Upload the image and set it as the specified video's thumbnail.
            ThumbnailSetResponse setResponse = thumbnailSet.execute();

            // Print the URL for the updated video's thumbnail image.
            System.out.println("\n================== Uploaded Thumbnail ==================\n");
            System.out.println("  - Url.: " + setResponse.getItems().get(0).getDefault().getUrl());
            System.out.println("  - Size: " + content.length);

        } catch (final GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (final IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /*
     * Prompts the user to enter a YouTube video ID and return the user input.
     */
    private static String getVideoIdFromUser() throws IOException {
        String inputVideoId = "";

        System.out.print("Please enter a video Id to update: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        inputVideoId = bReader.readLine();

        if (inputVideoId.length() < 1) {
            // Exit if the user does not specify a video ID.
            System.out.print("Video Id can't be empty!");
            System.exit(1);
        }

        return inputVideoId;
    }

    /*
     * Prompt the user to enter the path for the thumbnail image being uploaded.
     */
    private static File getImageFromUser() throws IOException {
        String path = "";

        System.out.print("Please enter the path of the image file to upload: ");
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        path = bReader.readLine();

        if (path.length() < 1) {
            // Exit if the user does not provide a path to the image file.
            System.out.print("Path can not be empty!");
            System.exit(1);
        }

        return new File(path);
    }

    private static InputStream getVideo(final byte[] content) {
		System.out.println("Video=" + content + ", " + content.length);
		return new ByteArrayInputStream(content, 0, content.length);
	}
}
