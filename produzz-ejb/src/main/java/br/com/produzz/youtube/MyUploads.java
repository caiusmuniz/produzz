package br.com.produzz.youtube;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

/**
 * Print a list of videos uploaded to the authenticated user's YouTube channel.
 */
public class MyUploads {
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadThumbnail.class);

    /**
     * Define a global instance of a Youtube object, which will be used
     * to make YouTube Data API requests.
     */
    private static YouTube youtube;

    /**
     * Authorize the user, call the youtube.channels.list method to retrieve
     * the playlist ID for the list of videos uploaded to the user's channel,
     * and then call the youtube.playlistItems.list method to retrieve the
     * list of videos in that playlist.
     *
     * @param args command line args (not used).
     */
    public static void main(String[] args) {
        try {
        		// Authorize the request.
        		Credential credential = null;

        		try {
        			credential = Auth.renovar(
        					Auth.autorizar("myUploads").getAccessToken());

        		} catch (final Exception e) {
        			System.exit(0);
        		}

        		myUploads(credential);

	    } catch (final Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
	}

	public static void myUploads(final Credential credential) {
		LOGGER.info("myUploads(" + credential + ")");

		try {
            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
            			.setApplicationName("Plataforma-Produzz")
            			.build();

            // Call the API's channels.list method to retrieve the
            // resource that represents the authenticated user's channel.
            // In the API response, only include channel information needed for
            // this use case. The channel's contentDetails part contains
            // playlist IDs relevant to the channel, including the ID for the
            // list that contains videos uploaded to the channel.
            YouTube.Channels.List channelRequest = youtube.channels().list("id,snippet,contentDetails,status");
            channelRequest.setMine(true);
            channelRequest.setFields("items(id,snippet/title,snippet/description,snippet/publishedAt,status/privacyStatus,contentDetails),nextPageToken,pageInfo");
            ChannelListResponse channelResult = channelRequest.execute();

            List<Channel> channelsList = channelResult.getItems();

            System.out.println("=============================================================");
            System.out.println("\t\tTotal Channels: " + channelsList.size());
            System.out.println("=============================================================\n");

            for (Channel canal : channelsList) {
                System.out.println(" channel id  = " + canal.getId());
                System.out.println(" titulo      = " + canal.getSnippet().getTitle());
                System.out.println(" description = " + canal.getSnippet().getDescription());
                System.out.println(" publicacao  = " + canal.getSnippet().getPublishedAt());
                System.out.println(" status      = " + canal.getStatus().getPrivacyStatus());
                //System.out.println("\n-------------------------------------------------------------\n");

                // The user's default channel is the first item in the list.
                // Extract the playlist ID for the channel's videos from the
                // API response.
                String uploadPlaylistId =
                			canal.getContentDetails().getRelatedPlaylists().getUploads();

                // Define a list to store items in the list of uploaded videos.
                List<PlaylistItem> playlistItemList = new ArrayList<PlaylistItem>();

                // Retrieve the playlist of the channel's uploaded videos.
                YouTube.PlaylistItems.List playlistItemRequest =
                        youtube.playlistItems().list("id,contentDetails,snippet,status");
                playlistItemRequest.setPlaylistId(uploadPlaylistId);

                // Only retrieve data used in this application, thereby making
                // the application more efficient. See:
                // https://developers.google.com/youtube/v3/getting-started#partial
                playlistItemRequest.setFields(
                        "items(contentDetails/videoId,snippet/title,snippet/description,snippet/publishedAt,status/privacyStatus),nextPageToken,pageInfo");

                String nextToken = "";

                // Call the API one or more times to retrieve all items in the
                // list. As long as the API response returns a nextPageToken,
                // there are still more items to retrieve.
                do {
                    playlistItemRequest.setPageToken(nextToken);
                    PlaylistItemListResponse playlistItemResult = playlistItemRequest.execute();

                    playlistItemList.addAll(playlistItemResult.getItems());

                    nextToken = playlistItemResult.getNextPageToken();
                } while (nextToken != null);

                // Prints information about the results.
                prettyPrint(playlistItemList.size(), playlistItemList.iterator());
            }

        } catch (final GoogleJsonResponseException e) {
            e.printStackTrace();
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());

        } catch (final Throwable t) {
            t.printStackTrace();
        }
    }

    /*
     * Print information about all of the items in the playlist.
     * @param size size of list
     * @param iterator of Playlist Items from uploaded Playlist
     */
    private static void prettyPrint(int size, Iterator<PlaylistItem> playlistEntries) {
        System.out.println("\n\t\tTotal Videos Uploaded: " + size);
        System.out.println("\n-------------------------------------------------------------\n");

        while (playlistEntries.hasNext()) {
            PlaylistItem playlistItem = playlistEntries.next();
            System.out.println(" video id    = " + playlistItem.getContentDetails().getVideoId());
            System.out.println(" titulo      = " + playlistItem.getSnippet().getTitle());
            System.out.println(" description = " + playlistItem.getSnippet().getDescription());
            System.out.println(" publicacao  = " + playlistItem.getSnippet().getPublishedAt());
            System.out.println(" status      = " + playlistItem.getStatus().getPrivacyStatus());
            System.out.println("\n-------------------------------------------------------------\n");
        }
    }
}
