public class Theta {

	double weight;
	double [] mu_vec;
	Matrix cov_mat;
    Matrix inv_mat;
    double det;


	public Theta(double w, double[] mu, Matrix c) throws NoSquareException {
		this.weight = w;
		this.mu_vec = mu;
		this.cov_mat = c;
        this.inv_mat = MatrixMathematics.inverse(c);
        this.det = MatrixMathematics.determinant(c);
	}

    public Theta(){

    }

    public double getDet() {
        return det;
    }

    public void setDet(double det) {
        this.det = det;
    }

    public void set_Det(Matrix m) throws NoSquareException {this.det= MatrixMathematics.determinant(m);}

    public Matrix getInv_mat(){return inv_mat;}

    public void setInv_mat(Matrix m){ this.inv_mat = m;}

    public void setInv_mat() throws NoSquareException {this.inv_mat= MatrixMathematics.inverse(this.cov_mat);}

    public double getWeight() {
        return weight;
    }

    public void setMu_vec(double[] mu_vec) {
        this.mu_vec = mu_vec;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double[] getMu_vec() {
        return mu_vec;
    }

    public Matrix getCov_mat() {
        return cov_mat;
    }

    public void setCov_mat(Matrix cov_mat) {
        this.cov_mat = cov_mat;
    }
}



