package br.com.produzz.facebook;

import java.util.Arrays;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.AdSet;
import com.facebook.ads.sdk.Campaign;
import com.facebook.ads.sdk.Targeting;
import com.facebook.ads.sdk.TargetingGeoLocation;

public class TargetingExample {
	public static final String ACCESS_TOKEN = ExampleConfig.ACCESS_TOKEN;
	public static final Long ACCOUNT_ID = ExampleConfig.ACCOUNT_ID;
	public static final String APP_SECRET = ExampleConfig.APP_SECRET;
	public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(true);

	public static void main(String[] args) {
		try {
			AdAccount account = new AdAccount(ACCOUNT_ID, context);

			Targeting targeting = new Targeting()
					.setFieldGeoLocations(new TargetingGeoLocation().setFieldCountries(Arrays.asList("US")))
					.setFieldAgeMin(18L)
					.setFieldAgeMax(30L)
					.setFieldUserOs(Arrays.asList("Android", "iOS"));

			Campaign campaign = account.createCampaign()
					.setName("Java SDK Test Campaign")
					.setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
					.setSpendCap(10000L)
					.setStatus(Campaign.EnumStatus.VALUE_PAUSED)
					.execute();

			AdSet adset = account.createAdSet()
					.setName("Java SDK Test AdSet")
					.setCampaignId(campaign.getFieldId())
					.setStatus(AdSet.EnumStatus.VALUE_PAUSED)
					.setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
					.setDailyBudget(1000L)
					.setBidAmount(100L)
					.setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_IMPRESSIONS)
					.setTargeting(targeting)
					.execute();

		} catch (final APIException e) {
			e.printStackTrace();
		}
	}
}
