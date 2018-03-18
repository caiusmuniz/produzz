package br.com.produzz.youtube;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.InvideoPosition;
import com.google.api.services.youtube.model.InvideoPromotion;
import com.google.api.services.youtube.model.InvideoTiming;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PromotedItemId;
import com.google.common.collect.Lists;

public class AddFeaturedVideo {
	private static final Logger LOGGER = LoggerFactory.getLogger(AddFeaturedVideo.class);

	/**
	 * Global instance of Youtube object to make all API requests.
	 */
	private static YouTube youtube;

    /**
     * This is a very simple code sample that looks up a user's channel, then features the most recently
     * uploaded video in the bottom left hand corner of every single video in the channel.
     * @param args command line args (not used).
     */
    public static void main(String[] args) {
        try {
	    		// Authorize the request.
	    		Credential credential = null;
	
	    		try {
    				credential = Auth.renovar(
    						Auth.autorizar("addFeatured").getAccessToken());

    			} catch (final Exception e) {
    				System.exit(0);
    			}

	    		addFeatured(credential);

	    } catch (final Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
	}

	public static void addFeatured(final Credential credential) {
		LOGGER.info("addFeatured(" + credential + ")");

		try {
            // YouTube object used to make all API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
            			.setApplicationName("Plataforma-Produzz")
            			.build();

            // Fetch the user's channel. We also fetch the uploads playlist so we can use this later
            // to find the most recently uploaded video
            ChannelListResponse channelListResponse = youtube.channels()
            			.list("id,contentDetails")
                    .setMine(true)
                    .setFields("items(contentDetails/relatedPlaylists/uploads,id)")
                    .execute();

            // This assumes the user has a channel already. If the user does not have a channel, this should
            // throw a GoogleJsonResponseException explaining the issue
            Channel myChannel = channelListResponse.getItems().get(0);
            String channelId = myChannel.getId();
            String uploadsPlaylistId = myChannel.getContentDetails().getRelatedPlaylists().getUploads();

            // Fetch the most recently uploaded video
            PlaylistItemListResponse playlistItemListResponse = youtube.playlistItems()
            			.list("snippet")
                    .setPlaylistId(uploadsPlaylistId)
                    .setFields("items/snippet")
                    .execute();

            String featuredVideoId;

            if (playlistItemListResponse.getItems().isEmpty()) {
                // There are no videos on the channel. Therefore, we cannot feature a video. Exit.
                System.out.println("Channel contains no videos. Featuring a default video instead from the Google Developers channel.");
                featuredVideoId = "w4eiUiauo2w";

            } else {
                // The latest video should be the first video in the playlist response
                PlaylistItem featuredVideo = playlistItemListResponse.getItems().get(0);
                featuredVideoId = featuredVideo.getSnippet()
                        .getResourceId()
                        .getVideoId();

                System.out.println("Featuring video: " + featuredVideo.getSnippet().getTitle());
            }

            // Feature this video on the channel via the Invideo programming API
            // This describes the position of the video. Valid positions are bottomLeft, bottomRight, topLeft and
            // topRight
            InvideoPosition invideoPosition = new InvideoPosition();
            invideoPosition.setCornerPosition("bottomLeft");
            invideoPosition.setType("corner");

            // The allowed offsets are offsetFromEnd and offsetFromStart, with offsetMs being an offset in milliseconds
            InvideoTiming invideoTiming = new InvideoTiming();
            invideoTiming.setOffsetMs(BigInteger.valueOf(15000l));
            invideoTiming.setType("offsetFromEnd");

            // Represents the type of promotion. In this case, a video with a video ID
            PromotedItemId promotedItemId = new PromotedItemId();
            promotedItemId.setType("video");
            promotedItemId.setVideoId(featuredVideoId);

            // Construct the Invidideo promotion
            InvideoPromotion invideoPromotion = new InvideoPromotion();
            invideoPromotion.setPosition(invideoPosition);
            invideoPromotion.setDefaultTiming(invideoTiming);
            invideoPromotion.setItems((List)Lists.newArrayList(promotedItemId));

            // Now let's add the invideo promotion to the channel
            Channel channel = new Channel();
            channel.setId(channelId);
            channel.setInvideoPromotion(invideoPromotion);

            // Make the API call
            Channel updateChannelResponse = youtube.channels()
                    .update("invideoPromotion", channel)
                    .execute();

            // Print out returned results.
            System.out.println("\n================== Updated Channel Information ==================\n");
            System.out.println("\t- Channel ID: " + updateChannelResponse.getId());

            InvideoPromotion promotion = updateChannelResponse.getInvideoPromotion();
            System.out.println("\t- Invideo promotion video ID: " + promotion.getItems()
                    .get(0)
                    .getId());
            System.out.println("\t- Promotion position: " + promotion.getPosition().getCornerPosition());
            System.out.println("\t- Promotion timing: " + promotion.getDefaultTiming().getOffsetMs()
                    + " Offset: " + promotion.getDefaultTiming().getType());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
