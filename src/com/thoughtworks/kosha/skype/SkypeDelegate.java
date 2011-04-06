package com.thoughtworks.kosha.skype;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import com.skype.Call;
import com.skype.CallListener;
import com.skype.CallStatusChangedListener;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.Skype;
import com.skype.SkypeException;
import com.skype.Call.Status;
import com.skype.connector.Connector;
import com.skype.connector.ConnectorException;
import com.thoughtworks.kosha.data.Session;

public class SkypeDelegate {

    private Session session;

    public SkypeDelegate() throws SkypeException, IOException {
	session = new Session(new File("data/student.wav"), new File("data/teacher.wav"), new File("data/tags.txt"),
		new File("data/chat.txt"));
	Skype.addCallListener(createCallListener());

    }

    CallListener createCallListener() {
	return new CallListener() {
	    public void callReceived(Call call) throws SkypeException {
	    }

	    public void callMaked(Call call) throws SkypeException {
		try {
		    final ChatMessageListener chatListener = getChatListener();
		    Skype.addChatMessageListener(chatListener);
		    call.addCallStatusChangedListener(new CallStatusChangedListener() {
			public void statusChanged(Status status) throws SkypeException {
			    if (status == Status.FINISHED) {
				Skype.removeChatMessageListener(chatListener);
			    }
			}
		    });
		    getConnector().execute(
			    "ALTER CALL " + call.getId() + " SET_CAPTURE_MIC FILE=\""
				    + session.getStudentTrack().getAbsolutePath() + "\"");
		    getConnector().execute(
			    "ALTER CALL " + call.getId() + " SET_OUTPUT FILE=\""
				    + session.getTeacherTrack().getAbsolutePath() + "\"");
		} catch (ConnectorException e) {
		    e.printStackTrace();
		}
	    }
	};
    }

    private ChatMessageListener getChatListener() {
	return new ChatMessageListener() {

	    public void chatMessageSent(ChatMessage sentChatMessage) throws SkypeException {
	    }

	    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
		try {
		    session.getConversationFile().createNewFile();
		    PrintWriter writer = new PrintWriter(new FileWriter(session.getConversationFile(),true));
		    writer.println(receivedChatMessage.getContent());
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}

	    }
	};
    }

    Connector getConnector() {
	return Connector.getInstance();
    }

    public Session getSession() {
        return session;
    }
    
    
}
