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
import com.thoughtworks.kosha.data.KoshaException;
import com.thoughtworks.kosha.data.Session;

public class SkypeDelegate {

    private Session currentSession;
    private List<Session> sessions = new ArrayList<Session>();
    private ChatMessageListener chatListener;
    private CallInfoListener callInfoListener;
    private Call currentCall;

    public SkypeDelegate() {
	try {
	    chatListener = getChatListener();
	    Skype.addCallListener(createCallListener());
	} catch (SkypeException e) {
	    throw new KoshaException("Unable to create skype delegate", e);
	}
    }

    private CallListener createCallListener() {
	return new CallListener() {

	    public void callReceived(Call call) throws SkypeException {
		callMaked(call);
	    }

	    public void callMaked(Call call) throws SkypeException {
		currentCall = call;
		Skype.addChatMessageListener(chatListener);
		currentSession = Session.getInstance();
		sessions.add(currentSession);

		call.addCallStatusChangedListener(getCallStatusListener());
		SkypeCommands.startMicCapture(call.getId(), currentSession.getStudentTrack().getAbsoluteFile());
		SkypeCommands.startSpeakerCapture(call.getId(), currentSession.getTeacherTrack().getAbsoluteFile());
	    }

	};
    }

    private CallStatusChangedListener getCallStatusListener() {
	return new CallStatusChangedListener() {
	    public void statusChanged(Status status) throws SkypeException {
		notifyCallInfoListener(status);
		if (status == Status.FINISHED) {
		    Skype.removeChatMessageListener(chatListener);
		}
	    }
	};
    }

    private void notifyCallInfoListener(Status status) {
	try {
	    callInfoListener.notify(new CallDetails(currentCall.getPartnerDisplayName(), status));
	} catch (SkypeException e) {
	    throw new KoshaException("Unable to notify call information listener", e);
	}
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
	for (int i = 1; i < sessions.size(); i++) {
	    outputSession.concat(sessions.get(i));
	}
	return outputSession;
    }

    public void addCallListener(CallInfoListener callInfoListener) {
	this.callInfoListener = callInfoListener;
    }

}
