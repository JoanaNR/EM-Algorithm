import java.util.ArrayList;
import java.util.Iterator;

public class MisturaGauss extends theta {
	private int n;
	private ArrayList<theta> t;
	
	int M;
	int d;
	int K;
	int p;
	int q;
	Theta theta = new Theta();
	Amostra a=new Amostra();
	Matrix b=new Matrix(p,q);
	// confirmar parametros
	public void mix(int n, ArrayList<Lista> t){
		this.n=n;
		this.t=t;
	}
	public double Prob( double v[] ){
		for (int k = 0; k <= M; k++) {
			double res=0;
			return res+=Probj(k,v);
		}
	}
		
	public double Probj(int i, double v[]){
		double w=theta.busca(i).acede_pesos();
		double base= Math.pow(2*Math.pow(Math.PI, d)*MatrixMathematics.determinant(theta.busca(i).acede_cov()),-0.5);
		Matrix l1=MatrixMathematics.transpose(subtract(converte(v(i)),converte(theta.busca(i).acede_media())));
		Matrix l2=MatrixMathematics.inverse(theta.busca(i).acede_cov());
		Matrix l3=MatrixMathematics.subtract(converte(v(i)),converte(theta.busca(i).acede_media())); //x??
		Matrix l4=MatrixMathematics.multiply(l1,l2);
		Matrix l5=MatrixMathematics.multiply(l4,l3);
		double res=w*base*Math.pow(Math.E,Matrix.multiplyByConstant(-0.5)); //l5
	}
	
	public Matrix converte(double vec []){
		Matrix m=new Matrix(M,1);
		for (int i = 0; i < M; i++) {
			m.setValueAt(i, 1,vec[i]);
			}
		return m;
	}
			
	public Gauss gau(int i){
		return mixtu[i];//Para dar apenas uma gaussiana (a i-esima), retira-se o elemento i da lista mixtu
	}
	
	
    public double pi(int i, double v[]){ // expoente (m)??
    	double res = Probj(i,v)/prob();
    }
	
	
    //M-step
    
		public double pesos ( int i, double v[]){
		double res=0;
		for (int j=1; j<=K; j++){
		res+=pi(i,v);
		return res/K; //MultiplybyConstant K-1
		}
	}
	
	public double medias (int i, double v[]){
		Matrix res1= new Matrix (M,d);
		Matrix res2=new Matrix (M,d);
		for (int j=1; j<=K; j++)
			res1+=MatrixMathematics.multiply(converte(pi(i,v)),converte(v)); //
		for (int u=1; u<=K; u++)
			res2+=converte(pi(i,v));
			return res1/res2; // multiply res -1
		}
		
	}
	public double covas (int i, double v[]){
	double l3=0;
	for (int j=1; j<=K; j++){
			Matrix l1= MatrixMathematics.multiply(converte(pi(i,v)),MatrixMathematics.subtract(converte(v(i)),converte(theta.busca(i).acede_media())));
			Matrix l2=MatrixMathematics.multiple (l1,MatrixMathematics.transpose(MatrixMathematics.subtract(converte(v(i)),converte(theta.busca(i).acede_media())));)
	}
		for (int u=1; u<=K; u++)
			double l3+=pi(i,v);
		
		return l2/l3;	// multiply
		}
	}
		
		