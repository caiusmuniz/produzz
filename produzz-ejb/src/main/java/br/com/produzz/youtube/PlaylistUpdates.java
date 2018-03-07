package br.com.produzz.youtube;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.common.collect.Lists;

public class PlaylistUpdates {
	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	/** Global instance of YouTube object to make all API requests. */
	private static YouTube youtube;

	/*
	 * Global instance of the video id we want to post as the first PlaylistItem in our new playlist.
	 */
	private static String VIDEO_ID = "SZj6rAYkYOg";

	/**
	 * Authorizes user, creates a playlist, adds a playlistitem with a video to that new playlist.
	 * @param args command line args (not used).
	 */
	public static void main(String[] args ) {
		// General read/write scope for YouTube APIs.
	    List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");

	    try {
	    	// Authorization.
	    	Credential credential = Auth.autorizar(scopes, "playlist");

	    	// YouTube object used to make all API requests.
	    	youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	    			.setApplicationName("youtube-cmdline-playlistupdates-sample")
	    			.build();

	    	// Creates a new playlist in the authorized user's channel.
	    	String playlistId = insertPlaylist();

	    	// If a valid playlist was created, adds a new playlistitem with a video to that playlist.
	    	insertPlaylistItem(playlistId, VIDEO_ID);

	    } catch (final GoogleJsonResponseException e) {
	    	System.err.println("There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
	    	e.printStackTrace();

	    } catch (final IOException e) {
	    	System.err.println("IOException: " + e.getMessage());
	    	e.printStackTrace();

	    } catch (final Throwable t) {
	    	System.err.println("Throwable: " + t.getMessage());
	    	t.printStackTrace();
	    }
	}

	/**
	 * Creates YouTube Playlist and adds it to the authorized account.
	 */
	private static String insertPlaylist() throws IOException {
		/*
	     * We need to first create the parts of the Playlist before the playlist itself.  Here we are
	     * creating the PlaylistSnippet and adding the required data.
	     */
		PlaylistSnippet playlistSnippet = new PlaylistSnippet();
		playlistSnippet.setTitle("Test Playlist " + Calendar.getInstance().getTime());
		playlistSnippet.setDescription("A private playlist created with the YouTube API v3");

		// Here we set the privacy status (required).
		PlaylistStatus playlistStatus = new PlaylistStatus();
		playlistStatus.setPrivacyStatus("private");

		/*
	     * Now that we have all the required objects, we can create the Playlist itself and assign the
	     * snippet and status objects from above.
	     */
		Playlist youTubePlaylist = new Playlist();
		youTubePlaylist.setSnippet(playlistSnippet);
		youTubePlaylist.setStatus(playlistStatus);

	    /*
	     * This is the object that will actually do the insert request and return the result.  The
	     * first argument tells the API what to return when a successful insert has been executed.  In
	     * this case, we want the snippet and contentDetails info.  The second argument is the playlist
	     * we wish to insert.
	     */
		YouTube.Playlists.Insert playlistInsertCommand =
				youtube.playlists().insert("snippet,status", youTubePlaylist);
		Playlist playlistInserted = playlistInsertCommand.execute();

	    // Pretty print results.
		System.out.println("New Playlist name: " + playlistInserted.getSnippet().getTitle());
		System.out.println(" - Privacy: " + playlistInserted.getStatus().getPrivacyStatus());
		System.out.println(" - Description: " + playlistInserted.getSnippet().getDescription());
		System.out.println(" - Posted: " + playlistInserted.getSnippet().getPublishedAt());
		System.out.println(" - Channel: " + playlistInserted.getSnippet().getChannelId() + "\n");
		return playlistInserted.getId();
	}

	/**
	 * Creates YouTube PlaylistItem with specified video id and adds it to the specified playlist id
	 * for the authorized account.
	 * @param playlistId assign to newly created playlistitem
	 * @param videoId YouTube video id to add to playlistitem
	 */
	private static String insertPlaylistItem(String playlistId, String videoId) throws IOException {
		/*
	     * The Resource type (video,playlist,channel) needs to be set along with the resource id. In
	     * this case, we are setting the resource to a video id, since that makes sense for this
	     * playlist.
	     */
		ResourceId resourceId = new ResourceId();
		resourceId.setKind("youtube#video");
		resourceId.setVideoId(videoId);

	    /*
	     * Here we set all the information required for the snippet section.  We also assign the
	     * resource id from above to the snippet object.
	     */
		PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
		playlistItemSnippet.setTitle("First video in the test playlist");
		playlistItemSnippet.setPlaylistId(playlistId);
		playlistItemSnippet.setResourceId(resourceId);

	    /*
	     * Now that we have all the required objects, we can create the PlaylistItem itself and assign
	     * the snippet object from above.
	     */
		PlaylistItem playlistItem = new PlaylistItem();
		playlistItem.setSnippet(playlistItemSnippet);

	    /*
	     * This is the object that will actually do the insert request and return the result.  The
	     * first argument tells the API what to return when a successful insert has been executed.  In
	     * this case, we want the snippet and contentDetails info.  The second argument is the
	     * playlistitem we wish to insert.
	     */
		YouTube.PlaylistItems.Insert playlistItemsInsertCommand =
				youtube.playlistItems().insert("snippet,contentDetails", playlistItem);
		PlaylistItem returnedPlaylistItem = playlistItemsInsertCommand.execute();

		// Pretty print results.
		System.out.println("New PlaylistItem name: " + returnedPlaylistItem.getSnippet().getTitle());
		System.out.println(" - Video id: " + returnedPlaylistItem.getSnippet().getResourceId().getVideoId());
		System.out.println(" - Posted: " + returnedPlaylistItem.getSnippet().getPublishedAt());
		System.out.println(" - Channel: " + returnedPlaylistItem.getSnippet().getChannelId());
		return returnedPlaylistItem.getId();
	  }
}
