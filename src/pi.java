
public class pi {

    public double[][] pi_mat;
    double s_val;

    public pi(int M,int N){
        pi_mat = new double[N][M];
    }

    public void setValueAt(int i,int j,double v){
        pi_mat[i][j] = v;
    }

    public double sum_cols(int j){
        double res = 0;
        for(int i=0;i<pi_mat.length;i++){
            res = res + pi_mat[i][j];
        }
        return res;
    }

    public void sum_vals(){
        double res = 0;
        for (int j = 0;j<pi_mat[0].length;j++){
            res = res + sum_cols(j);
        }
        s_val = res;
    }


}
