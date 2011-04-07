package com.thoughtworks.kosha.skype;

import java.io.File;

import com.skype.connector.Connector;
import com.skype.connector.ConnectorException;

public class SkypeCommands {

    public static void startMicCapture(String callId, File file) {
	try {
	    String command = "ALTER CALL " + callId + " SET_CAPTURE_MIC FILE=\"" + file.getAbsolutePath() + "\"";
	    getConnector().execute(command);
	} catch (ConnectorException e) {
	    throw new SkypeException("Unable to capture mic audio", e);
	}
    }

    public static void startSpeakerCapture(String callId, File file) {
	try {
	    String command = "ALTER CALL " + callId + " SET_OUTPUT FILE=\"" + file.getAbsolutePath() + "\"";
	    getConnector().execute(command);
	} catch (ConnectorException e) {
	    throw new SkypeException("Unable to capture speaker audio", e);
	}
    }

    static Connector getConnector() {
	return Connector.getInstance();
    }

}
