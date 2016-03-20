

import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.Point;
/*
 * This class models the data of pre-computation
 * */
public class PreComputation {
	private String ID;
	private Point U1;
	private Point U2;
	private Point V;
	private Point QID1;
	private Point QID2;
	private BigInt r;
	public PreComputation(String ID){
		this.ID=ID;
		this.QID2=PublicParams.H1(ID);
	}
	public void setParams(Point U1,Point U2,Point V,Point QID1,BigInt r){
		this.U1=U1;
		this.U2=U2;
		this.V=V;
		this.QID1=QID1;
		this.r=r;
	}
	public BigInt getR(){
		return this.r;
	}
	public String getID(){
		return this.ID;
	}
	public Point getU1(){
		return this.U1;
	}
	public Point getU2(){
		return this.U2;
	}
	public Point getV(){
		return this.V;
	}
	public Point getQID1(){
		return this.QID1;
	}
	public Point getQID2(){
		return this.QID2;
	}
	public String toString(){
		/*
		 * This method is used to return the string for display
		 * */
		String temp="";
		temp+="ID: "+this.ID+"\n";
		temp+="U1:\n X:\n"+this.U1.getX()+"\n";
		temp+=" Y:\n"+this.U1.getY()+"\n";
		temp+="U2:\n X:\n"+this.U2.getX()+"\n";
		temp+=" Y:\n"+this.U2.getY()+"\n";
		temp+="V:\n X:\n"+this.V.getX()+"\n";
		temp+=" Y:\n"+this.V.getY()+"\n";
		temp+="QID1:\n X:\n"+this.QID1.getX()+"\n";
		temp+=" Y:\n"+this.QID1.getY()+"\n";
		return temp;
	}
	public String toMsg(){
		/*
		 * This method will collect the data into a one single message
		 * */
		String temp="";
		temp+=this.ID+"-";
		temp+=this.U1.getX()+"-";
		temp+=this.U1.getY()+"-";
		temp+=this.U2.getX()+"-";
		temp+=this.U2.getY()+"-";
		temp+=this.V.getX()+"-";
		temp+=this.V.getY()+"-";
		temp+=this.QID1.getX()+"-";
		temp+=this.QID1.getY();
		return temp;
	}
}
