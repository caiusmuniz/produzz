package br.com.produzz.facebook;

import com.facebook.ads.sdk.APIContext;
import com.facebook.ads.sdk.APIException;
import com.facebook.ads.sdk.AdAccount;
import com.facebook.ads.sdk.Campaign;

public class QuickStartExample {
	public static final String ACCESS_TOKEN = ExampleConfig.ACCESS_TOKEN;
	public static final Long ACCOUNT_ID = ExampleConfig.ACCOUNT_ID;
	public static final String APP_SECRET = ExampleConfig.APP_SECRET;
	public static final APIContext context = new APIContext(ACCESS_TOKEN, APP_SECRET).enableDebug(true);

	public static void main(String[] args) {
		try {
			AdAccount account = new AdAccount(ACCOUNT_ID, context);
			Campaign campaign = account.createCampaign()
					.setName("Java SDK Test Campaign")
					.setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
					.setSpendCap(10000L)
					.setStatus(Campaign.EnumStatus.VALUE_PAUSED)
					.execute();

			System.out.println(campaign.fetch());

		} catch (final APIException e) {
			e.printStackTrace();
		}
	}
}
