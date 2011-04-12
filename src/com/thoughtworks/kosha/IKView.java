package com.thoughtworks.kosha;

import org.eclipse.swt.events.SelectionListener;

public interface IKView {

    public void setCallStatus(String status);
    
    public void addArchiveListener(SelectionListener listener);
    
    public String getNotes();
    
    public void clearNotes();
}
