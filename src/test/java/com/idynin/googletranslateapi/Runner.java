package test.java.com.idynin.googletranslateapi;

import java.util.Scanner;

import main.java.com.idynin.googletranslateapi.Language;

public class Runner {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Set target language:");
		Language target = Language.getBestLanguage(s.nextLine());
		System.out.println("Got: " + target);
		while (true) {
			System.out.println("Set target language:");
			target = Language.getBestLanguage(s.nextLine());
			System.out.println("Got: " + target);
		}
	}
}
