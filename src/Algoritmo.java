import java.sql.*;

/**
 * Created by trind on 08/05/2016.
 */
public class Algoritmo {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://godel.math.ist.utl.pt/proj";

    //  Database credentials
    static final String USER = "amc";
    static final String PASS = "amc2016";
    Amostra a = new Amostra();
    MisturaGauss m ;

    public Algoritmo(){

    }


    public  void read() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * from  Table1;";
            //sql = "SELECT * from  Teste where PatientID=3;";
            //sql = "SELECT * from  Teste where PatientID<3;";
            ResultSet rs = stmt.executeQuery(sql);
            int n_cols = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                double[] v = new double[n_cols];
                for (int i = 0; i < v.length; i++) {
                    v[i] = rs.getFloat(i + 1);
                }
                a.add(v);

            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");

    }

    public static void main(String args[]) throws NoSquareException, IllegalDimensionException {
        Algoritmo alg = new Algoritmo();
        alg.read();
        alg.m = new MisturaGauss(3,alg.a.element(0).length);
        for (int i = 0;i<10;i++){
            for (int j = 0;j<3;j++){
                alg.m.actualiza_pesos(j,alg.a);
                System.out.println("Pesos");
                alg.m.actualiza_medias(j,alg.a);
                System.out.println("Media");
                alg.m.actualiza_covariancia(j,alg.a);
                System.out.println("Cov");
            }
        }
    }

}
