

import uk.ac.ic.doc.jpair.pairing.BigInt;
/*
 * this class stores the first msg send to client
 * */
public class MAKE_1 {
	private BigInt N;
	private BigInt AuthS;
	public MAKE_1(BigInt N,BigInt AuthS){
		this.N=N;
		this.AuthS=AuthS;
	}
	public String toString(){
		String temp="";
		temp+="N:\n"+this.N;
		temp+="AuthS:\n"+this.AuthS;
		return temp;
	}
	public String toMsg(){
		String temp="";
		temp+=this.N+"-";
		temp+=this.AuthS;
		return temp;
	}
}
