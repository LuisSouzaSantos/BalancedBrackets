package br.com.ftt.ec6.balancedBrackets;

public class Bracket {
	
	private String symbol;
	private boolean open;
	
	public Bracket(String symbol, boolean open) {
		this.symbol = symbol;
		this.open = open;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	

	
}
