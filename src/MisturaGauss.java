import java.util.ArrayList;
import java.util.Random;


public class MisturaGauss { 

    public ArrayList<Theta> params; // a mistura gaussiana é uma arraylist com parametros theta
    public pi aux_pi; // usa funçoes auxiliares da classe pi


    public MisturaGauss(int M,int N) throws NoSquareException { 
    	// cria uma mistura de M gaussianas em que os parametros da gaussiana tem dimensao N
        //Construtor
        params = new ArrayList<>(M); // cria uma nova arraylist params com dimensao M
        for(int i = 0;i<M;i++){ // percorre a arraylist
            params.add(new Theta()); // vai adicionando os Theta à arraylist params
        }
        generate_params(N); // gera parametros aleatorios
       

    }

    public void generate_params(int N) throws NoSquareException { // função que gera parametros aleatorios de dimensao N
        int M = params.size(); // M é a dimensao da arraylist params
        Random rand = new Random(); // gera parametros aleatorios
        double[][] c = new double[N][N]; // cria uma nova matriz c NxN
        for (int i = 0;i<M;i++){ // percorre a lista de parametros até M
            params.get(i).setWeight((double) 1/(double) M);
            // devolve o theta (parametro) na posicao i e define o peso com condiçao inicial 1/M


        }
        for(int i = 0; i < N; i++) { // percorre a matriz cov até N e define-a com condiçao inicial sendo uma matriz identidade
            c[i][i] = 1;
        }
        for(int j = 0;j<M;j++) { // percorre a lista de parametros até M
            params.get(j).setCov_mat(new Matrix(c)); 
            //busca o parametro na posiçao j e define a matriz Cov_mat como sendo uma nova matriz c
            params.get(j).setInv_mat(MatrixMathematics.inverse(new Matrix(c)));
            //busca o parametro na posicao j e define a Inv mat como sendo a inversa da nova matriz c
            params.get(j).set_Det((new Matrix(c)));
            //busca o parametro na posicao j e define o Det da nova matriz c
        }
        for(int k = 0;k<M;k++) {//percorre a lista de parametros até M
            double[] mu = new double[N]; // cria um novo vetor mu com dimensao N
            for (int i = 0; i < mu.length; i++) {// percorre o vetor mu até ao length
                double randomNum = Math.abs(rand.nextDouble());
                mu[i] = randomNum;
            }
            double norm = 0;
            for (int i = 0; i < N; i++) {
                norm = norm + mu[i] * mu[i];
            }
            norm = Math.sqrt(norm);
            for (int i = 0; i < N; i++) {
                mu[i] = mu[i] / norm; // vetor mu na posicao i ja tem o valor normalizado
            }
            params.get(k).setMu_vec(mu);  // busca o parametro k na lista de parametros e define o Mu_vec sendo o mu.
        }

    }

    public void build(Amostra a) throws NoSquareException, IllegalDimensionException { // recebe uma amostra a????????
        int M = a.length(); // define M sendo o tamanho da amostra
        int N = params.size(); // define N sendo a dimensao dos params
        aux_pi = new pi(M,N); //cria uma matriz MxN
        System.out.print("Building");
        for (int j = 0;j<N;j++){ 
            //System.out.print(j);
            for (int i=0;i<M;i++){
                //System.out.println(i);
                double v = pi_j(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet());
                aux_pi.setValueAt(j,i,v);
            }
        }
        System.out.print("Done");
    }


    public double prob(double v[],Matrix inv_cov,double det ) throws NoSquareException, IllegalDimensionException {
        double res=0;
        for (int k = 0; k < params.size(); k++) { // percorre a lista de params e calcula a probabilidade para cada parametro
            double s = probj(k,v,inv_cov,det); // calcula o probj na posicao k
            res= res + probj(k,v,inv_cov,det); // somatorio de todos os probj
        }
        return res;
    }

    public double pi_j(int j, double[] v,Matrix inv_cov,double det) throws IllegalDimensionException, NoSquareException {
        double t = probj(j,v,inv_cov,det)/prob(v,inv_cov,det);
        return t;
    }

    public double probj(int j, double v[],Matrix inv_cov,double det) throws NoSquareException, IllegalDimensionException {
        int d = v.length; // A dimensao do vector e a dimensao do espaco
        double w=params.get(j).getWeight(); // vai buscar o peso na posiçao j
        Matrix mu = converte(params.get(j).getMu_vec()); // converte de vetor para matriz o mu na posiçao j da arraylist params
        Matrix v_mat = converte(v); // converte de vetor para matriz o vetor v
        double base= 1/Math.sqrt(Math.pow(2*Math.PI, d)*det); // define a expressao que antecede a exponencial na 1ª pagina do encunciado
        Matrix x = MatrixMathematics.subtract(v_mat,mu); // define a subtracao entre (x-mu) em matriz
        Matrix x_transposed=MatrixMathematics.transpose(x); // x transposto
        Matrix l4=MatrixMathematics.multiply(x_transposed,inv_cov); // multiplicação da x transposta com a matriz inversa das covariancias
        Matrix l5=MatrixMathematics.multiply(l4,x);// produto de matrizes de l4 com x
        Matrix f = l5.multiplyByConstant(-0.5); // multiplicação da l5 pela constante 0.5
        double exponent = mat_to_number(f); //o resultado é uma matriz 1x1 que convertemos para numero
        double res=w*base*Math.exp(exponent); // o resultado sao os pesos * função normal (gj)
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





    //M-step

    public double actualiza_pesos( int j, Amostra a) throws IllegalDimensionException, NoSquareException {
         //O tamanho da lista dos parametros é o numero de gaussianas
        int L = a.length();
        double res =0;
        for (int i=0; i<a.length(); i++){
            res = res + pi_j(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet()); 
            // numerador da formula da atualizaçao dos pesos
            //
            // System.out.println(pi_j(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet()));
            //System.out.println(i);
        }
        //System.out.println(i);
        params.get(j).setWeight(res/L); // atualiza os pesos
        return res/L;
    }


// EXPLICAR ESTA
    public double[] actualiza_medias (int j, Amostra a) throws IllegalDimensionException, NoSquareException {
        double[] res = new double[a.element(0).length]; // O vector de media tem a dimensao dos dados
        Matrix mu = converte(params.get(j).getMu_vec()); // converte o vetor das medias para matriz
        Matrix P = mu; //Usado para poder actualizar
        for (int i=0; i<a.length(); i++){
            //System.out.println(i + "medias");
            Matrix aux = converte(a.element(i)); // converte o vector da amostra numa matriz
            double pj = probj(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet());
            Matrix aux1 = aux.multiplyByConstant(pj);//
            Matrix aux2 = aux1.multiplyByConstant(prob(a.element(i),params.get(j).getInv_mat(),params.get(j).getDet()));
            
            P = MatrixMathematics.add(aux2,P);
        }
        for (int k=0;k<res.length;k++){
            res[k] = P.getValueAt(k,0);
        }
        params.get(j).setMu_vec(res); // atualiza as medias
        return res;
    }


    public Matrix actualiza_covariancia (int j, Amostra a) throws IllegalDimensionException, NoSquareException {
        int N = a.element(0).length; //A matriz de covariancia tem e NxN, sendo N a dimensao dos vectores
        Matrix res = new Matrix(N,N);
        for(int i = 0;i<a.length();i++){
            //System.out.println(i + "covs");
            Matrix x_mat = converte(a.element(i));
            Matrix mu_mat = converte(params.get(j).getMu_vec());
            Matrix x1 = MatrixMathematics.subtract(x_mat,mu_mat);
            Matrix x2 = MatrixMathematics.transpose(x1);
            Matrix aux = MatrixMathematics.multiply(x1,x2);
            Matrix aux1 = aux.multiplyByConstant(pi_j(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet()));
            res = aux1.multiplyByConstant(1/pi_j(j,a.element(i),params.get(j).getInv_mat(),params.get(j).getDet()));
        }
        params.get(j).setCov_mat(res);
        return res;
    }
}
		
		
