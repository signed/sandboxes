package gui;

import domain.Oracle;
import storage.OracleSerializer;

public class DelphiTempel {
	
	public DelphiTempel(Oracle oracle) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Oracle oracle = new OracleSerializer().load();
		DelphiTempel delphiTempel = new DelphiTempel(oracle);	
		delphiTempel.collectMoney();		
	}

	private void collectMoney() {
		
	}

}
