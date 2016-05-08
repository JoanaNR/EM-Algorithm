import java.util.ArrayList;

public class Amostra {
	public ArrayList<double[]> a;

	public Amostra(){
		a = new ArrayList<>();
	}

	public void add(double [] x){
		a.add(x);
	}

	public int length(){
		return a.size();
	}

	public double[] element(int i){
		return a.get(i);
	}

	public void join(Amostra b){
		for (int i = 0;i<b.length();i++){
			a.add(b.element(i));
		}
	}

}

