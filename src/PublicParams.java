

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import uk.ac.ic.doc.jpair.api.Field;
import uk.ac.ic.doc.jpair.api.Pairing;
import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.EllipticCurve;
import uk.ac.ic.doc.jpair.pairing.Fp;
import uk.ac.ic.doc.jpair.pairing.Point;
import uk.ac.ic.doc.jpair.pairing.TatePairing;
/*
 * this class models the public parameters
 * */
public class PublicParams{//store public params
	private static Pairing e; // pairing map e
	private EllipticCurve G1;//G1
	private EllipticCurve G2;//G2
	private static Point P;//generator P (Point)
	private Point Ppub;//Ppub
	public PublicParams(Pairing e,Point P,Point Ppub){
		this.e=e;
		this.G1=e.getCurve();
		this.G2=e.getCurve2();
		this.P=P;
		this.Ppub=Ppub;
	}
	public PublicParams(String x){//set the public parameters
		String temp[]=x.split("-");
		BigInt Prime=new BigInt(temp[0]);
		BigInt G1A=new BigInt(temp[1]);
		BigInt G1B=new BigInt(temp[2]);
		BigInt G2A=new BigInt(temp[3]);
		BigInt G2B=new BigInt(temp[4]);
		BigInt r=new BigInt(temp[5]);
		BigInt cof=new BigInt(temp[6]);
		this.P=new Point(new BigInt(temp[7]),new BigInt(temp[8]));
		this.Ppub=new Point(new BigInt(temp[9]),new BigInt(temp[10]));
		Fp field = new Fp(Prime);
		EllipticCurve ec = new EllipticCurve (field,G1A,G1B);
		this.e = new TatePairing(ec,r,cof);
		this.G1=this.e.getCurve();
		this.G2=this.e.getCurve2();
	}
	public String toString(){//toString
		String temp="";
		temp+="Prime:"+this.e.getGt().getP()+"\n";
		temp+="G1:\n";
		temp+=" A:"+this.G1.getA()+"\n";
		temp+=" B:"+this.G1.getB()+"\n";
		/*temp+="G2:\n";
		temp+=" A:"+this.G2.getA()+"\n";
		temp+=" B:"+this.G2.getB()+"\n";*/
		temp+="P:\n";
		temp+=" X: "+this.P.getX()+"\n";
		temp+=" Y: "+this.P.getY()+"\n";
		temp+="Ppub:\n";
		temp+=" X: "+this.Ppub.getX()+"\n";
		temp+=" Y: "+this.Ppub.getY()+"\n";
		return temp;
	}
	public EllipticCurve getG1(){
		return this.G1;
	}
	public EllipticCurve getG2(){
		return this.G2;
	}
	public Pairing getE(){
		return this.e;
	}
	public Point getP(){
		return this.P;
	}
	public Point getPpub(){
		return this.Ppub;
	}
	public BigInt getOrder(){
		return this.e.getGroupOrder();
	}//get function end
	public static BigInt f1(String ID,Point QID1){//Hash to value
		String data=ID;
		data+=QID1.getX().toString()+QID1.getY().toString();
		byte[] temp=null;
		try {
			temp = Hash.hashToLength(data.getBytes("UTF-8"),512);//┏糷ㄏノSHA-512
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		BigInt n =stringToInt(temp);
		return n.mod(e.getGroupOrder());
	}
	public static BigInt f2(String ID,Point U1,Point U2,Point V,BigInt N,Point K){//Hash to value
		String data = null;
		data=ID;
		data+=U1.getX().toString()+U1.getY().toString();
		data+=U2.getX().toString()+U2.getY().toString();
		data+=V.getX().toString()+V.getY().toString();
		data+=N.toString();
		data+=K.getX().toString()+K.getY().toString();
		byte[] temp=null;
		try {
			temp = Hash.hashToLength(data.getBytes("UTF-8"),512);//┏糷ㄏノSHA-512
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		BigInt n =stringToInt(temp);
		return n.mod(e.getGroupOrder());
	}
	public static BigInt f3(String ID,Point U1,Point U2,Point V,BigInt N,Point K,BigInt Auths){//Hash to value
		String data = null;
		data=ID;
		data+=U1.getX().toString()+U1.getY().toString();
		data+=U2.getX().toString()+U2.getY().toString();
		data+=V.getX().toString()+V.getY().toString();
		data+=N.toString();
		data+=K.getX().toString()+K.getY().toString();
		data+=Auths.toString();
		byte[] temp=null;
		try {
			temp = Hash.hashToLength(data.getBytes("UTF-8"),512);//┏糷ㄏノSHA-512
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		BigInt n =stringToInt(temp);
		return n.mod(e.getGroupOrder());
	}
	public static BigInt f4(String ID,Point U1,Point U2,Point V,BigInt N,Point K,BigInt Auths,BigInt Authc){//Hash to value
		String data = null;
		data=ID;
		data+=U1.getX().toString()+U1.getY().toString();
		data+=U2.getX().toString()+U2.getY().toString();
		data+=V.getX().toString()+V.getY().toString();
		data+=N.toString();
		data+=K.getX().toString()+K.getY().toString();
		data+=Auths.toString();
		data+=Authc.toString();
		byte[] temp=null;
		try {
			temp = Hash.hashToLength(data.getBytes("UTF-8"),512);//┏糷ㄏノSHA-512
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		BigInt n =stringToInt(temp);
		return n.mod(e.getGroupOrder());
	}
	public static Point H1(String ID){//H1(ID)
		BigInt r=HashToValue(ID);
		return e.getCurve().multiply(P, r);
	}
	public static Point H2(Point U1,Point U2){
		String data = "";
		data =U1.getX().toString()+U1.getY().toString();
		data+=U2.getX().toString()+U2.getY().toString();
		return H1(data);
	}
	private static BigInt HashToValue(String x){
		String data=x;
		byte[] temp=null;
		try {
			temp = Hash.hashToLength(data.getBytes("UTF-8"),512);//┏糷ㄏノSHA-512
		} catch (UnsupportedEncodingException e1) {e1.printStackTrace();}
		BigInt n =stringToInt(temp);
		return n.mod(e.getGroupOrder());
	}
	private static BigInt stringToInt(byte[] bytes){//盢ヴ種﹃锣传Θ计
		//и盢hash–ゅ锣传Θ2秈 8bit计 肚
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes){
		    int val = b;
		    for (int i = 0; i < 8; i++){
		        binary.append((val & 128) == 0 ? 0 : 1);
		        val <<= 1;
		    }
		}
		return new BigInt(binary.toString(),2);
	}
}
//////////////////////////////////////////hash class start
class Hash {
	static byte[] hashToLength(byte[] toHash, int byteLength){
		if (byteLength<=0)
			throw new IllegalArgumentException("Invalid hash length");
		String hashAlgorithm="SHA-512";
		int bitLength =byteLength*8;
		int round;
		if(bitLength<=512){
			round=1;
		}
		else{
			round=1+(bitLength-1)/512;
		}
		byte[] out = new byte[byteLength];
		byte[][] temp = new byte[round][];
	    try {
	        MessageDigest hash = MessageDigest.getInstance(hashAlgorithm);
	        for(int i=0;i<round;i++){
	        	temp[i]=hash.digest(toHash);
	        	toHash=temp[i];
	        }
	    } catch (NoSuchAlgorithmException e) {
	    	System.exit(-1);
	    }
	    int startIndex=0;
	    for(int i=0;i<round;i++){
	    	if(byteLength>=temp[i].length){
	    		System.arraycopy(temp[i], 0, out, startIndex, temp[i].length);
	    		startIndex+=temp[i].length;
	    		byteLength-=temp[i].length;
	    	}
	    	else{
	    		System.arraycopy(temp[i], 0, out, startIndex, byteLength);
	    	}
	    }
	    return out;
	}
	static BigInt hashToField(byte[] toHash,Field field){
		int byteLength =1+(field.getP().bitLength()-1)/8;
		byte[] ba = Hash.hashToLength(toHash, byteLength);
		BigInt b =new BigInt(1,ba);
		while(b.compareTo(field.getP())>=0){
			b=b.shiftRight(1);
		}
		return b;
	}
	static Point hashToPoint(byte[] toHash,EllipticCurve ec){
		BigInt b =Hash.hashToField(toHash, ec.getField());
		Point P =ec.getPoint(b);
		while(P==null){
			b=b.add(BigInt.ONE);
			P=ec.getPoint(b);
		}
		return P;
	}
	static Point hashToPoint(byte[] toHash,EllipticCurve ec,BigInt cofactor){
		Point P =Hash.hashToPoint(toHash, ec);
		P=ec.multiply(P, cofactor);
		return P;
	}
	static byte[] xorTwoByteArrays(byte[] ba1,byte[] ba2){
		byte [] result =new byte[ba1.length];
		for(int i=0;i<ba1.length;i++){
			 result[i]=(byte) (ba1[i]^ba2[i]);
		}
		return result;
	}
}
