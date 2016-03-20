

import uk.ac.ic.doc.jpair.pairing.BigInt;
/*
 * This class stores the data of computation of on line mode
 * */
public class OnLineMsg {
	private BigInt N;
	private BigInt AuthS;
	private BigInt AuthC;
	public OnLineMsg(String x){
		String temp[]=x.split("-");
		this.N=new BigInt(temp[0]);
		this.AuthS=new BigInt(temp[1]);
	}
	
	public OnLineMsg(BigInt inputN, BigInt inputAuthS){
		N = inputN;
		AuthS=inputAuthS;
	}
	
	public BigInt getN(){
		return this.N;
	}
	public BigInt getAuthS(){
		return this.AuthS;
	}
	public String toString(){
		return this.AuthC.toString();
	}
	public String toMsg(){
		return this.AuthC.toString();
	}
}
