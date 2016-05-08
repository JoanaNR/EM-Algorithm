import java.util.Arrays;

class No {
		/*Cada nó tem um vector correspondente e aponta para o próximo nó na lista*/
		double[] vec;
		No next;
		
		No(double[] v){
			vec=new double[v.length];
			int i=0;
			while(i<v.length){ // temos de adicionar arrays como objectos diferentes
				vec[i]=v[i];
				i++;
			}
			next=null;
		}

		@Override
		public String toString() {
			if(next!=null)
				return Arrays.toString(vec)+ "";
			else
				return "";
		}
			


}


