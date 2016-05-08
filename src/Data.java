import java.util.ArrayList;
import java.util.Arrays;


public class Data {
	
	static String DB_URL = "jdbc:mysql://godel.math.ist.utl.pt/proj";
	static String USER = "amc";
	static String PASS = "amc2016";

	public static void main(String[] args) throws IllegalDimensionException, NoSquareException {
		ListTimes DB = new ListTimes(DB_URL, USER, PASS);
		
		
		Amostra amostra = DB.getAmostra("Table1");
		double g;
		g = mix.probj(0, amostra.element(3));

		System.out.println(Arrays.toString(amostra.element(3)));
	}

}
