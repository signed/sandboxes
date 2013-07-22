package gui;

import domain.Oracle;
import storage.OracleSerializer;

public class DelphiTemple {

    public static class RuntimeAnalyzer{
        public void analyze(){
            String property = System.getProperty("java.class.path");
            String[] elements = property.split(":");
            StringBuilder builder = new StringBuilder();

            for (String element : elements) {
                builder.append(element).append("\n");
            }
            System.out.println(builder.toString());

        }
    }

    public static void main(String[] args) {
        new RuntimeAnalyzer().analyze();
        Oracle oracle = new OracleSerializer().load();
        DelphiTemple delphiTemple = new DelphiTemple(oracle);
        delphiTemple.collectMoney();
    }

	public DelphiTemple(Oracle oracle) {
		//nothing to do
	}

	private void collectMoney() {
		
	}

}
