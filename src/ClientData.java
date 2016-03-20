

import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.Point;

/*
 * This class stores one client's Data
 * */
public class ClientData {
	private String ID;
	private PreComputation offLine;
	private BigInt N;
	private Point Ks;
	private BigInt AuthS;
	private BigInt SessionKey;
	public ClientData(String ID,ClientPrivateKey privateKey){
		this.ID=ID;
	}
	public String getID(){
		return this.ID;
	}
	public PreComputation getOffLine(){
		return this.offLine;
	}
	public BigInt getN(){
		return this.N;
	}
	public void setN_Ks_AuthS(BigInt N,Point Ks,BigInt AuthS){
		this.N=N;
		this.Ks=Ks;
		this.AuthS=AuthS;
	}
	public Point getKs(){
		return this.Ks;
	}
	public BigInt getAuthS(){
		return this.AuthS;
	}
	public void setSessionKey(BigInt x){
		this.SessionKey=x;
	}
	public BigInt getSessionKey(){
		return this.SessionKey;
	}
	public void setOffLineParams(String x){
		this.offLine=new PreComputation(x);
	}
}
