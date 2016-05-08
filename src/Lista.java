
public class Lista {
	public double w; //pesos
    public double [] med; // media
    public Matrix cov; //matriz da covariancia
    
    public Lista (){
    }
    
    public Lista theta ( double w, double [] med, Matrix cov){
    	Lista par=new Lista ();
    	this.w=w;
    	this.med=med;
    	this.cov=cov;
    return par;
    }
    
    public double acede_pesos (){
    	return w;
    }
    public double[] acede_media (){
    	return med;
    }
    public Matrix acede_cov (){
    	return cov;
    }
}
