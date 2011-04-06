package com.thoughtworks.kosha.data;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Session {
    File studentTrack;
    File teacherTrack;
    File tagsFile;
    File conversationFile;
    Date date;

    public Session(File studentTrack, File teacherTrack, File tagsFile, File conversationFile) throws IOException {
	this.studentTrack = studentTrack;
	this.teacherTrack = teacherTrack;
	this.tagsFile = tagsFile;
	this.conversationFile = conversationFile;
	this.date = new Date();
	studentTrack.createNewFile();
	teacherTrack.createNewFile();
	tagsFile.createNewFile();
	conversationFile.createNewFile();
    }

    public Set<File> getArtifacts() {
	return new HashSet<File>(Arrays.asList(studentTrack, teacherTrack, tagsFile, conversationFile));
    }

    public String getDate() {
	return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public File getStudentTrack() {
	return studentTrack;
    }

    public File getTeacherTrack() {
	return teacherTrack;
    }

    public File getTagsFile() {
	return tagsFile;
    }

    public File getConversationFile() {
	return conversationFile;
    }

    @Override
    public String toString() {
	return "Session [studentTrack=" + studentTrack.getAbsolutePath() + ", teacherTrack="
		+ teacherTrack.getAbsolutePath() + ", tagsFile=" + tagsFile.getAbsolutePath() + ", conversationFile="
		+ conversationFile.getAbsolutePath() + ", date=" + date + "]";
    }

}
