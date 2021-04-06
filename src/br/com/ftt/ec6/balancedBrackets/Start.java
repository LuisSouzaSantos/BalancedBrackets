package br.com.ftt.ec6.balancedBrackets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Start {
	
	private final static String[] OPEN_BRACKET_LIST = {"{", "[", "("};
	private final static String[] CLOSE_BRACKET_LIST = {"}", "]", ")"};
	
	private static Set<Bracket> OPEN_BRACKET_SET_LIST = new HashSet<Bracket>();
	private static Set<Bracket> CLOSE_BRACKET_SET_LIST = new HashSet<Bracket>();
	
	private static Map<Bracket, Bracket> OPEN_CLOSE_BRACKET_LIST = new HashMap<Bracket, Bracket>();
	private static List<String> openBracketStack = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception{
		
		if(args.length <= 0 || args[0].isEmpty()) {
			System.out.println("ERROR: arg not found");
			System.out.println("java -jar balancedBrackets.jar pathFile.txt");
			return;
		}
		
		loadOpenAndCloseBracketList(OPEN_BRACKET_LIST, CLOSE_BRACKET_LIST);
		
		File file = new File(args[0]);
		String textFile  ="";
		try {
			textFile = readFile(file);
		} catch (IOException e1) {
			System.out.println("Arquivo não encontrado");
			System.out.println("Encerrando programa........");
			return;
		}
		
		String text = formatText(textFile);
		
		char[] charArray = text.toCharArray();
		
		File newFile;
		try {
			for (char c : charArray) {
				if(isValidBracket(c)) {
					if(isOpenBracket(c)) {
						stack(c, true);
					}else {
						stack(c, false);
					}
				}
			}
			
			if(openBracketStack.size() > 0) { throw new Exception("INVALID_FILE"); }
			
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
	
	public static void stack(char c, boolean stackUp) throws Exception {
		if(stackUp) {
			openBracketStack.add(Character.toString(c));
		}else {
			int openBracketListSize = openBracketStack.size();
			
			if(openBracketListSize <= 0) {throw new Exception("INVALID_FILE");}
			
			String lastOpenBracket = openBracketStack.get(openBracketListSize-1);
			
			if(isOppositeBracket(lastOpenBracket, Character.toString(c))) {
				openBracketStack.remove(openBracketListSize-1);
			}else {
				throw new Exception("INVALID_FILE");
			}
		}
	}
	
	public static String formatText(String text) {
		return text.replaceAll("\\s+","");
	}
	
	public static boolean isValidBracket(char c) {
		return isOpenBracket(c) || isCloseBracket(c);
	}
	
	public static boolean isOpenBracket(char c) {
		String symbol = String.valueOf(c);
		return OPEN_BRACKET_SET_LIST.stream()
					.anyMatch(bracket -> bracket.getSymbol().equals(symbol));
	}
	
	public static boolean isCloseBracket(char c) {
		String symbol = String.valueOf(c);
		return CLOSE_BRACKET_SET_LIST.stream()
					.anyMatch(bracket -> bracket.getSymbol().equals(symbol));
	}
	
	public static boolean isOppositeBracket(String openBracket, String closeBracket) {
		Bracket closeBracketObject = null;
		
		for (Map.Entry keyAndValue : OPEN_CLOSE_BRACKET_LIST.entrySet()) {
			Bracket bracket = (Bracket) keyAndValue.getKey();
		
			if(bracket.getSymbol().equals(openBracket)) {
				closeBracketObject = (Bracket) keyAndValue.getValue();
			}
		}
		
		if(closeBracketObject == null) { return false; }
		
		return closeBracketObject.getSymbol().equals(closeBracket);
	}
	
	public static void loadOpenAndCloseBracketList(String[] openBrackets, String[] closeBrackets) throws Exception {
		
		if(openBrackets.length != closeBrackets.length) {
			throw new Exception("Open or close bracket list is wrong");
		}
		
		for (int i = 0; i < openBrackets.length; i++) {
			OPEN_CLOSE_BRACKET_LIST.put(new Bracket(openBrackets[i], true), new Bracket(closeBrackets[i], false));
		}
		
		for (String string : openBrackets) {
			OPEN_BRACKET_SET_LIST.add(new Bracket(string, true));
		}
		
		for (String string : closeBrackets) {
			CLOSE_BRACKET_SET_LIST.add(new Bracket(string, false));
		}
	}

}