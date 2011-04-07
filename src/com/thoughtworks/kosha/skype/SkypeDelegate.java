package com.thoughtworks.kosha.skype;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.skype.Call;
import com.skype.Call.Status;
import com.skype.CallListener;
import com.skype.CallStatusChangedListener;
import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.Skype;
import com.skype.SkypeException;
import com.thoughtworks.kosha.data.Session;

public class SkypeDelegate {

    private Session currentSession;
    private List<Session> sessions = new ArrayList<Session>();

    public SkypeDelegate() throws SkypeException, IOException {
	Skype.addCallListener(createCallListener());
    }

    CallListener createCallListener() {
	return new CallListener() {
	    public void callReceived(Call call) throws SkypeException {
		callMaked(call);
	    }

	    public void callMaked(Call call) throws SkypeException {
		currentSession = Session.getInstance();
		sessions.add(currentSession);
		final ChatMessageListener chatListener = getChatListener();
		Skype.addChatMessageListener(chatListener);
		call.addCallStatusChangedListener(new CallStatusChangedListener() {
		    public void statusChanged(Status status) throws SkypeException {
			if (status == Status.FINISHED) {
			    Skype.removeChatMessageListener(chatListener);
			}
		    }
		});
		SkypeCommands.startMicCapture(call.getId(), currentSession.getStudentTrack().getAbsoluteFile());
		SkypeCommands.startSpeakerCapture(call.getId(), currentSession.getTeacherTrack().getAbsoluteFile());
	    }
	};
    }

    private ChatMessageListener getChatListener() {
	return new ChatMessageListener() {

	    public void chatMessageSent(ChatMessage sentChatMessage) throws SkypeException {
	    }

	    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
		try {
		    currentSession.getConversationFile().createNewFile();
		    PrintWriter writer = new PrintWriter(new FileWriter(currentSession.getConversationFile(), true));
		    writer.println(receivedChatMessage.getContent());
		    writer.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}

	    }
	};
    }

    public Session getSession() {
	return currentSession;
    }

    public Session getConsolidatedSession() {
	Session outputSession = sessions.get(0);
	for (int i=1; i< sessions.size(); i++) {
	    outputSession.concat(sessions.get(i));	    
	}
	return outputSession;
    }
    
    public void addStatusChangedListener(StatusChangedListener listener) {
	
    }

}
