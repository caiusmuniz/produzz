package br.com.produzz.facebook;

import java.io.File;
import java.util.Arrays;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.Ad;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdCreative;
import com.facebook.ads.sdk.AdImage;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Targeting;
import com.facebook.ads.sdk.TargetingGeoLocation;
import com.facebook.ads.sdk.APIException;

public class BasicExample {
	public static final String ACCESS_TOKEN = ExampleConfig.ACCESS_TOKEN;
	public static final Long ACCOUNT_ID = ExampleConfig.ACCOUNT_ID;
	public static final String APP_SECRET = ExampleConfig.APP_SECRET;
	public static final File imageFile = new File(ExampleConfig.IMAGE_FILE);

	public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(true);

	public static void main(String[] args) {
		try {
			Targeting targeting = new Targeting().setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(Arrays.asList("US")));
			AdAccount account = new AdAccount(ACCOUNT_ID, context);

			// Creation
			Campaign campaign = account.createCampaign()
					.setName("Java SDK Test Campaign")
					.setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
					.setSpendCap(10000L)
					.setStatus(Campaign.EnumStatus.VALUE_PAUSED)
					.execute();

			System.out.println(campaign);

			AdSet adset = account.createAdSet()
					.setName("Java SDK Test AdSet")
					.setCampaignId(campaign.getFieldId())
					.setStatus(AdSet.EnumStatus.VALUE_PAUSED)
					.setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
					.setDailyBudget(500L)
					.setBidAmount(100L)
					.setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_IMPRESSIONS)
					.setTargeting(targeting)
					.setRedownload(true)
					.execute();

			System.out.println(adset);

			AdImage image = account.createAdImage()
					.addUploadFile("file", imageFile)
					.execute();

			AdCreative creative = account.createAdCreative()
					.setTitle("Java SDK Test Creative")
					.setBody("Java SDK Test Creative")
					.setImageHash(image.getFieldHash())
					.setLinkUrl("www.facebook.com")
					.setObjectUrl("www.facebook.com")
					.execute();

			Ad ad = account.createAd()
					.setName("Java SDK Test ad")
					.setAdsetId(Long.parseLong(adset.getId()))
					.setCreative(creative)
					.setStatus("PAUSED")
					.setBidAmount(100L)
					.setRedownload(true)
					.execute();

			System.out.println("Creation done!");

			// Get after creation
			campaign.fetch(); // fetch() is just a shortcut for *.get().requestAllFields().execute()
			adset.fetch();
			ad.fetch();
			System.out.println(campaign);
			System.out.println(adset);
			System.out.println(ad);
			System.out.println("Get after creation done!");

			// call edge to get adsets
			for (AdSet as : campaign.getAdSets().requestAllFields().execute()) {
				for (Ad a : as.getAds().requestAllFields().execute()) {
					System.out.println(a);
				}

				System.out.println(as);
			}

			System.out.println("Get from edge done!");

			// Get with static methods
			System.out.println(Campaign.fetchById(campaign.getFieldId(), context));
			System.out.println(AdSet.fetchById(adset.getFieldId(), context));
			System.out.println("Get with static methods done!");

			// Update
			campaign.update()
				.setName("Updated Java SDK Test Campaign")
				.execute();

			adset.update()
				.setName("Updated Java SDK Test AdSet")
				.execute();

			System.out.println("Update done!");

			// Get after update
			campaign.fetch();
			adset.fetch();
			System.out.println(campaign);
			System.out.println(adset);
			System.out.println("Get after update done!");

			// Delete
			campaign.delete().execute();
			adset.delete().execute();
			System.out.println("Deletion done!");

			// Get after deletion
			campaign.fetch();
			adset.fetch();
			System.out.println(campaign);
			System.out.println(adset);

			for (AdSet as : campaign.getAdSets().requestAllFields().execute()) {
				System.out.println(as);
			}

			System.out.println("Get after deletion done!");

		} catch (final APIException e) {
			e.printStackTrace();
		}
	}
}
