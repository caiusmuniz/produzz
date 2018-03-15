package br.com.produzz.youtube;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

public class Auth {
	private static final Logger LOGGER = LoggerFactory.getLogger(Auth.class);

    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    /**
     * List of scopes needed to run youtube upload.
     */
    private static final List<String> SCOPES = Lists.newArrayList(
			"https://www.googleapis.com/auth/youtube",
			"https://www.googleapis.com/auth/youtube.upload",
			"https://www.googleapis.com/auth/youtubepartner",
			"https://www.googleapis.com/auth/yt-analytics.readonly",
			"https://www.googleapis.com/auth/youtube.readonly",
			"https://www.googleapis.com/auth/userinfo.profile",
			"https://www.googleapis.com/auth/userinfo.email");

    /**
     * Authorizes the installed application to access user's protected data.
     * @param credentialDatastore name of the credential datastore to cache OAuth tokens
     */
    public static Credential autorizar(String credentialDatastore) throws IOException {
    		LOGGER.info("autorizar(" + credentialDatastore + ")");

    		// Load client secrets.
        Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/youtube/client_secrets.json"));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

        // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
        DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        		.setAccessType("offline")
        		.setApprovalPrompt("force")
        		.setCredentialDataStore(datastore).build();

        // Build the local server and bind it to port 8080
        LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();

        // Authorize.
        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }

    public static Credential renovar(final String refreshToken) throws Exception {
		LOGGER.info("renovar(" + refreshToken + ")");

		// Load client secrets.
		Reader clientSecretReader = new InputStreamReader(Auth.class.getResourceAsStream("/youtube/client_secrets.json"));
		GoogleClientSecrets clientSecrets =  GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

		GoogleTokenResponse refresh = new GoogleRefreshTokenRequest(
				new NetHttpTransport(),
				JacksonFactory.getDefaultInstance(),
				refreshToken,
				clientSecrets.getDetails().getClientId(),
				clientSecrets.getDetails().getClientSecret()).execute();

		JsonObject credential = new JsonObject();
		credential.addProperty("type", "authorized_user");
		credential.addProperty("client_id", clientSecrets.getDetails().getClientId());
		credential.addProperty("client_secret", clientSecrets.getDetails().getClientSecret());
		credential.addProperty("refresh_token", refreshToken);

		return new GoogleCredential()
				.fromStream(new ByteArrayInputStream(credential.toString().getBytes()))
        			.setAccessToken(refresh.getAccessToken())
        			.setExpiresInSeconds(refresh.getExpiresInSeconds())
        			.setRefreshToken(refresh.getAccessToken());
    }
}
