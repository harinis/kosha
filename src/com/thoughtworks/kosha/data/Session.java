package com.thoughtworks.kosha.data;

import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.FileUtils;

public class Session {
    File studentTrack;
    File teacherTrack;
    File tagsFile;
    File conversationFile;
    Date date;

    private Session(File studentTrack, File teacherTrack, File tagsFile, File conversationFile, Date date)
	    throws IOException {
	this.studentTrack = studentTrack;
	this.teacherTrack = teacherTrack;
	this.tagsFile = tagsFile;
	this.conversationFile = conversationFile;
	this.date = date;
	createFiles();
    }

    void createFiles() throws IOException {
	new File("data").mkdir();
	studentTrack.createNewFile();
	teacherTrack.createNewFile();
	tagsFile.createNewFile();
	conversationFile.createNewFile();
    }

    public static Session getInstance() {
	try {
	    Date today = new Date();
	    String timeStamp = new SimpleDateFormat("HH-mm-ss").format(today);
	    return new Session(buildFilename("student", timeStamp, ".wav"),
		    buildFilename("teacher", timeStamp, ".wav"), buildFilename("tags", timeStamp, ".txt"),
		    buildFilename("chat", timeStamp, ".txt"), today);
	} catch (IOException e) {
	    throw new KoshaException("Session creation failed", e);
	}
    }

    private static File buildFilename(String fileName, String timeStamp, String extn) {
	return new File("data/" + fileName + "-" + timeStamp + extn);
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

    public void setTagsFile(String tags) {
	try {
	    FileUtils.writeStringToFile(tagsFile, tags);
	} catch (IOException e) {
	    throw new KoshaException("Unable to append notes", e);
	}
    }

    public void deleteFiles() {
	studentTrack.delete();
	teacherTrack.delete();
	tagsFile.delete();
	conversationFile.delete();
    }

    @Override
    public String toString() {
	return "Session [studentTrack=" + studentTrack.getAbsolutePath() + ", teacherTrack="
		+ teacherTrack.getAbsolutePath() + ", tagsFile=" + tagsFile.getAbsolutePath() + ", conversationFile="
		+ conversationFile.getAbsolutePath() + ", date=" + date + "]";
    }

    public void concat(Session session) {
	studentTrack = concatAudioFiles(studentTrack, session.getStudentTrack());
	teacherTrack = concatAudioFiles(teacherTrack, session.getTeacherTrack());
	appendConversations(session);
    }

    private void appendConversations(Session session) {
	try {
	    String conv1 = FileUtils.readFileToString(conversationFile);
	    String conv2 = FileUtils.readFileToString(session.conversationFile);
	    FileUtils.writeStringToFile(conversationFile, conv1 + conv2);
	} catch (IOException e) {
	    throw new KoshaException("Unable to append conversations", e);
	}
    }

    private File concatAudioFiles(File file1, File file2) {
	File outputFile = new File("temp.wav");
	try {
	    if (file1.length() != 0 && file2.length() != 0) {
		AudioInputStream clip1 = AudioSystem.getAudioInputStream(file1);
		AudioInputStream clip2 = AudioSystem.getAudioInputStream(file2);
		AudioInputStream appendedFiles = new AudioInputStream(new SequenceInputStream(clip1, clip2),
			clip1.getFormat(), clip1.getFrameLength() + clip2.getFrameLength());
		AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, outputFile);
		clip1.close();
		clip2.close();
		appendedFiles.close();
		file1.delete();
		file2.delete();
		outputFile.renameTo(file1);
		return file1;
	    }
	    return file1;
	} catch (Exception e) {
	    throw new KoshaException("Unable to concat files", e);
	}
    }

}
