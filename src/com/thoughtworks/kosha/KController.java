package com.thoughtworks.kosha;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

import com.thoughtworks.kosha.data.Session;
import com.thoughtworks.kosha.dropbox.Dropbox;
import com.thoughtworks.kosha.skype.CallDetails;
import com.thoughtworks.kosha.skype.CallInfoListener;
import com.thoughtworks.kosha.skype.SkypeDelegate;

public class KController {

    private final IKView view;
    private SkypeDelegate skypeDelegate;

    public KController(IKView view) {
	this.view = view;
	initSkype();
	view.addArchiveListener(getArchiveListener());
    }

    private void initSkype() {
	this.skypeDelegate = new SkypeDelegate();
	skypeDelegate.addCallListener(getCallInfoListener());
    }

    private CallInfoListener getCallInfoListener() {
	return new CallInfoListener() {
	    public void notify(final CallDetails details) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
			view.setCallStatus(details.caller + " : " + details.status.toString());
		    }
		});
	    }
	};
    }

    private SelectionListener getArchiveListener() {
	return new SelectionAdapter() {
	    public void widgetSelected(SelectionEvent e) {
		Display.getDefault().asyncExec(new Runnable() {
		    public void run() {
			Session currSession = skypeDelegate.getConsolidatedSession();
			currSession.setTagsFile(view.getNotes().trim());
			new Dropbox().recordHistory(currSession);
			view.clearNotes();
		    }
		});
	    }
	};
    }

}
