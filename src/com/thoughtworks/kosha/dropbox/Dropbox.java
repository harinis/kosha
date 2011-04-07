package com.thoughtworks.kosha.dropbox;

import java.io.File;

import com.dropbox.client.DropboxAPI;
import com.dropbox.client.DropboxAPI.Config;
import com.thoughtworks.kosha.data.Session;

public class Dropbox {
    // Replace this with your consumer key and secret assigned by Dropbox.
    // Note that this is a really insecure way to do this, and you shouldn't
    // ship code which contains your key & secret in such an obvious way.
    // Obfuscation is good.
    final static private String CONSUMER_KEY = "4cnvu2bi0xk0jiw";
    final static private String CONSUMER_SECRET = "fvz6kv0etjj1625";

    private static final String DROPBOX_SERVER = "api.dropbox.com";
    private static final String DROPBOX_CONTENT_SERVER = "api-content.dropbox.com";
    private static final int DROPBOX_PORT = 80;

    static final String ROOT = "dropbox/";
    static final String DBPATH = "music-classes/";

    DropboxAPI api;
    Config config;
    String username = "harinis@thoughtworks.com";
    String password = "koshaproj";

    public Dropbox() {
	this.api = new DropboxAPI();
	buildConfig();
    }

    public void recordHistory(Session session) {
	connect();
	String path = DBPATH + session.getDate();
	api.createFolder(ROOT, path);
	for (File file : session.getArtifacts()) {
	    api.putFile(ROOT, path, file);
	}
	session.deleteFiles();
    }

    private void connect() {
	config = api.authenticate(config, username, password);
	int success = config.authStatus;

	if (success != DropboxAPI.STATUS_SUCCESS) {
	    throw new DropboxException("Error in authentication");
	}

	if (api.accountInfo().isError()) {
	    throw new DropboxException("Error in account Info");
	}
    }

    private void buildConfig() {
	config = api.getConfig(null, false);
	// TODO On a production app which you distribute, your consumer
	// key and secret should be obfuscated somehow.
	config.consumerKey = CONSUMER_KEY;
	config.consumerSecret = CONSUMER_SECRET;
	config.server = DROPBOX_SERVER;
	config.contentServer = DROPBOX_CONTENT_SERVER;
	config.port = DROPBOX_PORT;
    }

}