package com.thoughtworks.kosha.skype;

import com.skype.Call.Status;

public class CallDetails {
    public String caller;
    public Status status;

    public CallDetails(String partnerDisplayName, Status status) {
	caller = partnerDisplayName;
	this.status = status;
    }

    @Override
    public String toString() {
	return "CallDetails [caller=" + caller + ", status=" + status + "]";
    }

}
