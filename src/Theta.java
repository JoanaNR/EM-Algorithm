public class Theta {

	double weight;
	double [] mu_vec;
	Matrix cov_mat;


	public Theta(double w, double[] mu, Matrix c){
		this.weight = w;
		this.mu_vec = mu;
		this.cov_mat = c;
	}

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



