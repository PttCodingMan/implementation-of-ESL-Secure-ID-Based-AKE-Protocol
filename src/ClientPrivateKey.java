

import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.Point;

public class ClientPrivateKey {
	/*
	 * this class models the private key of client
	 * */
	private BigInt DID1;
	private Point DID2;
	private Point QID1;
	public ClientPrivateKey(BigInt DID1,Point DID2,Point QID1){
		this.DID1=DID1;
		this.DID2=DID2;
		this.QID1=QID1;
	}
	public ClientPrivateKey(String x){
		String temp[]=x.split("-");
		this.DID1=new BigInt(temp[0]);
		this.DID2=new Point(new BigInt(temp[1]),new BigInt(temp[2]));
		this.QID1=new Point(new BigInt(temp[3]),new BigInt(temp[4]));
	}
	public BigInt getDID1(){
		return this.DID1;
	}
	public Point getDID2(){
		return this.DID2;
	}
	public Point getQID1(){
		return this.QID1;
	}
	public String toString(){
		/*
		 * this method is used to construct the key format to display
		 * */
		String temp ="";
		temp+="DID1:\n"+this.DID1+"\n";
		temp+="DID2:\n X:\n"+this.DID2.getX()+"\n";
		temp+=" Y:\n"+this.DID2.getY()+"\n";
		temp+="QID1:\n X:\n"+this.QID1.getX()+"\n";
		temp+=" Y:\n"+this.QID1.getY()+"\n";
		return temp;
	}
}
