import java.util.ArrayList;

public class MisturaGauss {

    private ArrayList<Theta> params;

    public MisturaGauss(int M){
        //Construtor
        params = new ArrayList<>(M);
        //TODO Code for generate random parameters

    }

    public double prob( double v[] ) throws NoSquareException, IllegalDimensionException {
        int M = v.length;
        double res=0;
        for (int k = 0; k <= M; k++) {
            res+=probj(k,v);
        }
        return res;
    }

    public double probj(int j, double v[]) throws NoSquareException, IllegalDimensionException {
        int d = v.length; // A dimensao do vector e a dimensao do espaco
        double w=params.get(j).getWeight();
        Matrix cov = params.get(j).getCov_mat();
        Matrix mu = converte(params.get(j).getMu_vec());
        Matrix v_mat = converte(v);
        double base= Math.pow(2*Math.pow(Math.PI, d)*MatrixMathematics.determinant(params.get(j).getCov_mat()),-0.5);
        Matrix x_transposed=MatrixMathematics.transpose(MatrixMathematics.subtract(v_mat,mu));
        Matrix x  = MatrixMathematics.subtract(v_mat,mu);
        Matrix inv_cov=MatrixMathematics.inverse(cov);
        Matrix l4=MatrixMathematics.multiply(x,inv_cov);
        Matrix l5=MatrixMathematics.multiply(l4,x_transposed);
        double exponent = mat_to_number(l5);
        double res=w*base*Math.pow(Math.E,-0.5*exponent);
        return res;
    }

    public double mat_to_number(Matrix m){
        //Converte uma matriz 1x1 para um numero
        if(m.getNcols()==1 && m.getNrows()==1){
            return m.getValueAt(0,0);
        }
        return 0;
    }

    public Matrix converte(double vec []){
        //Converte um vector coluna numa Matrix
        int M = vec.length;
        Matrix m=new Matrix(M,1);
        for (int i = 0; i < M; i++) {
            m.setValueAt(i, 1,vec[i]);
        }
        return m;
    }


    public double pi_j(int j, double[] v) throws IllegalDimensionException, NoSquareException {
        return probj(j,v)/prob(v);
    }


    //M-step

    public double actualiza_pesos( int i, Amostra a) throws IllegalDimensionException, NoSquareException {
        int K = params.size(); //O tamanho da lista dos parametros é o numero de gaussianas
        double res=0;
        for (int j=0; j<a.length(); j++){
            res = res + pi_j(i,a.element(i));
        }
        //params.get(i).setWeight(res/K);
        return res/K;
    }

    public double[] actualiza_medias (int i, Amostra a) throws IllegalDimensionException, NoSquareException {
        double[] res = new double[a.element(0).length]; // O vector de media tem a dimensao dos dados
        Matrix mu = converte(params.get(i).getMu_vec());
        Matrix P = mu; //Usado para poder actualizar
        for (int j=0; j<a.length(); j++){
            Matrix aux = converte(a.element(j));
            double pj = probj(i,a.element(j));
            Matrix aux1 = aux.multiplyByConstant(pj);
            Matrix aux2 = aux1.multiplyByConstant(prob(a.element(j)));
            P = MatrixMathematics.add(aux2,P);
        }
        for (int k=0;k<res.length;k++){
            res[k] = P.getValueAt(k,0);
        }
        //params.get(i).setMu_vec(res);
        return res;
    }


    public Matrix actualiza_covariancia (int j, Amostra a) throws IllegalDimensionException, NoSquareException {
        int N = a.element(0).length; //A matriz de covariancia tem e NxN, sendo N a dimensao dos vectores
        Matrix res = new Matrix(N,N);
        for(int i = 0;i<a.length();i++){
            Matrix x_mat = converte(a.element(i));
            Matrix mu_mat = converte(params.get(j).getMu_vec());
            Matrix x1 = MatrixMathematics.subtract(x_mat,mu_mat);
            Matrix x2 = MatrixMathematics.transpose(x1);
            Matrix aux = MatrixMathematics.multiply(x1,x2);
            Matrix aux1 = aux.multiplyByConstant(pi_j(j,a.element(i)));
            res = aux1.multiplyByConstant(1/pi_j(j,a.element(i)));
        }
        //params.get(j).setCov_mat(res);
        return res;
    }
}
		
		