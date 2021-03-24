package br.com.ftt.ec6.balancedBrackets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Start {
	
	private final static List<String> OPEN_BRACKET_LIST = new ArrayList<String>(Arrays.asList("{", "[", "("));
	private final static List<String> CLOSE_BRACKET_LIST = new ArrayList<String>(Arrays.asList("}", "]", ")"));
	private static Map<String, String> OPEN_CLOSE_BRACKET_LIST = new HashMap<String, String>();
	private static List<String> openBracket = new ArrayList<String>();
	
	public static void main(String[] args){
		
		if(args.length <= 0 || args[0].isEmpty()) {
			System.out.println("ERROR: arg not found");
			System.out.println("java -jar balancedBrackets.jar pathFile.txt");
			return;
		}
		
		loadopenCloseBracketList();
		
		File file = new File(args[0]);
		String fileInTextFormat ="";
		try {
			fileInTextFormat = readFile(file);
		} catch (IOException e1) {
			System.out.println("Arquivo não encontrado");
			System.out.println("Encerrando programa........");
			return;
		}
		
		char[] charArray = fileInTextFormat.toCharArray();
		
		File newFile;
		try {
			for (char c : charArray) {
				if(isOpenBracket(c)) {
					stack(c, true);
				}else {
					stack(c, false);
				}
			}
			newFile = new File(args[0]+"-check.txt");
			System.out.println("Arquivo válido");
		}catch(Exception e) {
			newFile = new File(args[0]+"-check-failed.txt");
			System.out.println("Arquivo inválido");
		}
		
		file.renameTo(newFile);
	}
	
	public static String readFile(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String allString ="";
		String line;
		while((line = br.readLine()) != null){
			allString = allString+line;
		}
		br.close();
		return allString;
	}
	
	
	public static boolean isOpenBracket(char c) {
		return OPEN_BRACKET_LIST.contains(String.valueOf(c));
	}
	
	public static void stack(char c, boolean stackUp) throws Exception {
		if(stackUp) {
			openBracket.add(Character.toString(c));
		}else {
			int openBracketListSize = openBracket.size();
			
			if(openBracketListSize <= 0) {throw new Exception("INVALID_FILE");}
			
			String lastOpenBracket = openBracket.get(openBracketListSize-1);
			
			if(isOppositeBracket(lastOpenBracket, Character.toString(c))) {
				openBracket.remove(openBracketListSize-1);
			}else {
				throw new Exception("INVALID_FILE");
			}
		}
	}
	
	public static boolean isOppositeBracket(String openBracket, String closeBracket) {
		String closeBracketAccordingList = OPEN_CLOSE_BRACKET_LIST.get(openBracket);
		return closeBracketAccordingList.equals(closeBracket);
	}
	
	public static void loadopenCloseBracketList() {		
		for (int i = 0; i < OPEN_BRACKET_LIST.size(); i++) {
			OPEN_CLOSE_BRACKET_LIST.put(OPEN_BRACKET_LIST.get(i), CLOSE_BRACKET_LIST.get(i));
		}
	}

}