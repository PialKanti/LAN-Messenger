package com.logInOutSound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class LogInAlert extends Thread {
	Clip clip;
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		Long start=System.currentTimeMillis();
		Long end=start+1*1000;
			try {
				URL url=LogInAlert.class.getResource("/com/LogInOutSound/Skype_Log_In_Sound.wav");
				AudioInputStream audioClip1= AudioSystem.getAudioInputStream(url);
				clip=AudioSystem.getClip();
				clip.open(audioClip1);
				while (start<end) {
					clip.start();
				}
				clip.close();
				stop();
			}
			catch (LineUnavailableException e) {
				e.printStackTrace();
				System.out.println("Line Unavailable Exception");
				e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Can't open the file");
				e.getMessage();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
				System.out.println("audio file is not supported.");
				e.getMessage();
			}
	
      }
	
}
