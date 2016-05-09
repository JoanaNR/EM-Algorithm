
public class pi {//funcoes de matrizes auxiliares

    public double[][] pi_mat;
    double s_val;//pq e q definimos aqui o s_val e nao apenas dentro do sum_vals?

    public pi(int N,int M){//recebe inteiro N e M e devolve uma matriz NxM
        pi_mat = new double[N][M];
    }

    public void setValueAt(int i,int j,double v){//adiciona o valor v na posiçao i,j da matriz
        pi_mat[i][j] = v;
    }

    public double sum_cols(int j){//soma as entradas da coluna j
        double res = 0;
        for(int i=0;i<pi_mat.length;i++){
            res = res + pi_mat[i][j];
        }
        return res;
    }

    public void sum_vals(){//soma todas as entradas da matrix (adicionando coluna a coluna)
        double res = 0;
        for (int j = 0;j<pi_mat[0].length;j++){
            res = res + sum_cols(j);
        }
        s_val = res;
    }


}
