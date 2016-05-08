import java.util.ArrayList;
import java.util.Random;


public class MisturaGauss {

    private ArrayList<Theta> params;

    public MisturaGauss(int M,int N) throws NoSquareException {
        //Construtor
        params = new ArrayList<>(M);
        for(int i = 0;i<M;i++){
            params.add(new Theta());
        }
        generate_params(N);
        //TODO Code for generate random parameters

    }

    public void generate_params(int N) throws NoSquareException {
        int M = params.size();
        Random rand = new Random();
        double[][] c = new double[N][N];
        for (int i = 0;i<M;i++){
            params.get(i).setWeight((double) 1/(double) M);

        }
        for(int i = 0; i < N; i++) {
            c[i][i] = 1;
        }
        for(int j = 0;j<M;j++) {
            params.get(j).setCov_mat(new Matrix(c));
            params.get(j).setInv_mat(MatrixMathematics.inverse(new Matrix(c)));
        }
        double[] mu = new double[N];
        for(int k = 0;k<M;k++) {
            for (int i = 0; i < mu.length; i++) {
                double randomNum = Math.abs(rand.nextDouble());
                mu[i] = randomNum;
            }
            double norm = 0;
            for (int i = 0; i < N; i++) {
                norm = norm + mu[i] * mu[i];
            }
            norm = Math.sqrt(norm);
            for (int i = 0; i < N; i++) {
                mu[i] = mu[i] / norm;
            }
            params.get(k).setMu_vec(mu);
        }


    }


    public double prob( double v[],Matrix inv_cov ) throws NoSquareException, IllegalDimensionException {
        //int M = v.length;
        double res=0;
        for (int k = 0; k < params.size(); k++) {
            res+=probj(k,v,inv_cov);
        }
        return res;
    }

    public double probj(int j, double v[],Matrix inv_cov) throws NoSquareException, IllegalDimensionException {
        int d = v.length; // A dimensao do vector e a dimensao do espaco
        double w=params.get(j).getWeight();
        Matrix mu = converte(params.get(j).getMu_vec());
        Matrix v_mat = converte(v);
        double base= Math.pow(2*Math.pow(Math.PI, d)*MatrixMathematics.determinant(params.get(j).getCov_mat()),-0.5);
        Matrix x_transposed=MatrixMathematics.transpose(MatrixMathematics.subtract(v_mat,mu));
        Matrix x  = MatrixMathematics.subtract(v_mat,mu);
        Matrix l4=MatrixMathematics.multiply(x_transposed,inv_cov);
        Matrix l5=MatrixMathematics.multiply(l4,x);
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
            m.setValueAt(i, 0,vec[i]);
        }
        return m;
    }


    public double pi_j(int j, double[] v,Matrix inv_cov) throws IllegalDimensionException, NoSquareException {
        Matrix inv = MatrixMathematics.inverse(params.get(j).getCov_mat());
        return probj(j,v,inv)/prob(v,inv_cov);
    }


    //M-step

    public double actualiza_pesos( int j, Amostra a) throws IllegalDimensionException, NoSquareException {
        int K = params.size(); //O tamanho da lista dos parametros é o numero de gaussianas
        double res=0;
        for (int i=0; i<a.length(); i++){
            res = res + pi_j(j,a.element(i),params.get(j).getInv_mat());
            System.out.println(i);
        }
        //params.get(i).setWeight(res/K);
        return res/K;
    }

    public double[] actualiza_medias (int i, Amostra a) throws IllegalDimensionException, NoSquareException {
        double[] res = new double[a.element(0).length]; // O vector de media tem a dimensao dos dados
        Matrix mu = converte(params.get(i).getMu_vec());
        Matrix P = mu; //Usado para poder actualizar
        for (int j=0; j<params.size(); j++){
            Matrix aux = converte(a.element(j));
            double pj = probj(i,a.element(j),params.get(j).getInv_mat());
            Matrix aux1 = aux.multiplyByConstant(pj);
            Matrix aux2 = aux1.multiplyByConstant(prob(a.element(j),params.get(j).getInv_mat()));
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
        for(int i = 0;i<params.size();i++){
            Matrix x_mat = converte(a.element(i));
            Matrix mu_mat = converte(params.get(j).getMu_vec());
            Matrix x1 = MatrixMathematics.subtract(x_mat,mu_mat);
            Matrix x2 = MatrixMathematics.transpose(x1);
            Matrix aux = MatrixMathematics.multiply(x1,x2);
            Matrix aux1 = aux.multiplyByConstant(pi_j(j,a.element(i),params.get(j).getInv_mat()));
            res = aux1.multiplyByConstant(1/pi_j(j,a.element(i),params.get(j).getInv_mat()));
        }
        //params.get(j).setCov_mat(res);
        return res;
    }
}
		
		