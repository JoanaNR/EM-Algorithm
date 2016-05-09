public class Theta {

	double weight;
	double [] mu_vec;
	Matrix cov_mat;
	Matrix inv_mat;
    	double det;


	public Theta(double w, double[] mu, Matrix c) throws NoSquareException {
		//Theta definido pelos valores dos pesos w, vector de medias mu e matriz de covarriancas c
		this.weight = w;
		this.mu_vec = mu;
		this.cov_mat = c;
        	this.inv_mat = MatrixMathematics.inverse(c);//calculo da inversa da matriz das convariancias c
        	this.det = MatrixMathematics.determinant(c);//calculo do determinante da matriz das convariancias c
	}

    public Theta(){//metodo construtor dos parametros Theta

    }

    public double getDet() {//devolve o determinante da matriz
        return det;
    }

    public void setDet(double det) {//acede determinante da matriz das covariancias c
        this.det = det;
    }

    public void set_Det(Matrix m) throws NoSquareException {this.det= MatrixMathematics.determinant(m);}
//acede determinante da matriz m
    public Matrix getInv_mat(){return inv_mat;}
//acede à inversa da matriz das covariancias c
    public void setInv_mat(Matrix m){ this.inv_mat = m;}
//define a matriz inversa com m
    public void setInv_mat() throws NoSquareException {this.inv_mat= MatrixMathematics.inverse(this.cov_mat);}
//É IGUAL à anterior mas a receber coisas diferentes - nao percebemos bem a diferença entre elas
    public double getWeight() {
        return weight;
    }
	//acede aos pesos
    public void setWeight(double weight) {
        this.weight = weight;
    }
	//define aos pesos
public double[] getMu_vec() {
        return mu_vec;
    }
	//acede as medias
    public void setMu_vec(double[] mu_vec) {
        this.mu_vec = mu_vec;
    }
    //define as medias
   public Matrix getCov_mat() {
        return cov_mat;
    }
//acede as covariancias
    public void setCov_mat(Matrix cov_mat) {
        this.cov_mat = cov_mat;
    }
}
//define as covariancias


//Temos algumas duvidas em perceber a diferença e utilidade das funcoes sete get para os diferentes paramentros
