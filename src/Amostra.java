import java.util.Arrays;

public class Amostra {
	
	@Override
	public String toString() {
		return "{" + primeiro + "}";
	}


	No primeiro, ultimo;
	int comp;
	int[] dominio;

	Amostra(){
		primeiro=null;
		ultimo=null;
		comp=0;
		dominio=null;
	}

	public void add(double[] v){
		/*Adiciona um vector a amostra sem qualquer tipo de ordem
		 * e actualiza a lista dos domonios.*/
		No a=new No(v);
		if(primeiro==null){ // caso a amostra esteja vazia
			primeiro=a;
			ultimo=a;
			}
		else{
			ultimo.next=a;
			ultimo=a;
			}
		comp++;
		
	}
	
	public int comp(){
	  return comp; 
  }
	
	public double[] element(int p){
		No aux=primeiro;
		int i=0;
		while (i!=p){
			i++;
			aux=aux.next;
		}
		return aux.vec;
		
	}
	
   public void join(Amostra a){
		if(a==null || a.primeiro==null){
			return; // caso trivial: devolve a propria amostra
		}
		else{
			if(primeiro==null){ // caso em q a primeira amostra � vazia
				primeiro=a.primeiro;
				ultimo=a.ultimo;
				comp=a.comp;
				dominio=a.dominio;
			}
			else{
				ultimo.next=a.primeiro;
				ultimo=a.ultimo;
				comp+=a.comp;
				int i=0;
				while(i<dominio.length){ // actualiza��o do array dos domínios
					dominio[i]=Math.max(dominio[i], a.dominio[i]);
					i++;
				}
			}
		}
   }
  
   
  // public double[][] toMatrix(){
	//	int i=0;
		//No aux=primeiro;
		//int[][] matrix=new int[comp][primeiro.vec.length];
		//while(i<comp){
			//int j=0;
			//while(j<primeiro.vec.length){
				//matrix[i][j]=aux.vec[j];
				//j++;
			//}
			//aux=aux.next;
			//i++;
		//}
		//return matrix;
	//}

   
   public static void imprimeMatrix(int [][] m){
	   for (int i=0; i<m.length;i++){
		   for (int j=0; j<m[0].length; j++){
			   System.out.println(m[i][j]+ "");
		   }
		   System.out.println("");
		   }
	   }
   
   
   /*Testes!*/
	public static void main(String[] args) {
		Amostra a=new Amostra();
		Amostra b=new Amostra();
		int[] v1={1,2,3};
		int[] v2={2,1,4};
		int[] v3={4,5,7};
		a.add(v1);
		a.add(v2);
		b.add(v3);
		System.out.println(a.comp());
		System.out.println(a.toString());
		//a.join(b);
//		System.out.println(a.comp());
//		System.out.println(a.element(2));
		//System.out.println(a.toString());
		//Aux.printMatrix(a.toMatrix());
		//System.out.println(Arrays.toString(a.dominio));
		//Amostra b=new Amostra();
		//Amostra c=new Amostra();
		//a.join(null);
		//int[] v3={1,4,3};
		//int[] v4={1,1,1};
		//b.add(v3);
		//b.add(v4);
		//a.join(b);
		//a.join(c);
		//a.join(null);
		//System.out.println(" ");
		//Aux.printMatrix(a.toMatrix());
		//System.out.println(Arrays.toString(a.domain));
		
	}
}

   
