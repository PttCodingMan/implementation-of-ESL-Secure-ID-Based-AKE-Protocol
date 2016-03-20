

import java.security.SecureRandom;

import uk.ac.ic.doc.jpair.api.FieldElement;
import uk.ac.ic.doc.jpair.api.Pairing;
import uk.ac.ic.doc.jpair.pairing.BigInt;
import uk.ac.ic.doc.jpair.pairing.PairingFactory;
import uk.ac.ic.doc.jpair.pairing.Point;
import uk.ac.ic.doc.jpair.pairing.Predefined;

public class PairingDemo {
	
	private String ID = "Truth";
	private PublicParams publicParams;
	private BigInt ServerPrivateKey;
	private ClientData ClientData;
	public PairingDemo(){
		//Server init
		//Pairing e =PairingFactory.ssTate(256, 512, new SecureRandom());
		
		//Pairing e = Predefined.ssTate();
		Pairing e = Predefined.ssTate2();
		
		ServerPrivateKey=getRandomNumber(e);
		Point P=e.RandomPointInG1(new SecureRandom());//randomly choose generator P
		Point Ppub = e.getCurve().multiply(P, ServerPrivateKey);//compute Ppub = P * s
		publicParams=new PublicParams(e,P,Ppub);//store public params
		System.out.println("complete.");
		
		//Server extrat key
		BigInt DID1;
		Point DID2;
		Point QID1;
		BigInt l=getRandomNumber(publicParams.getE());
		QID1=publicParams.getE().getCurve().multiply(publicParams.getP(),l);
		BigInt h=PublicParams.f1(ID,QID1);
		DID1=l.add(h.multiply(ServerPrivateKey));
		Point QID2=PublicParams.H1(ID);
		DID2=publicParams.getE().getCurve().multiply(QID2, ServerPrivateKey);
		ClientPrivateKey ClientPrivateKey = new ClientPrivateKey(DID1, DID2, QID1);		
		
		//Client pre-compute phase
		PreComputation PreComputation = new PreComputation(ID);
		BigInt r=getRandomNumber(publicParams.getE());
		Point U1=publicParams.getE().getCurve().multiply(publicParams.getP(), r);
		Point U2=publicParams.getE().getCurve().multiply(PreComputation.getQID2(), r);
		Point W=PublicParams.H2(U1,U2);
		Point tempV=publicParams.getE().getCurve2().multiply(W, r.add(ClientPrivateKey.getDID1()));
		Point V=publicParams.getE().getCurve2().add(tempV, ClientPrivateKey.getDID2());
		PreComputation.setParams(U1, U2, V, ClientPrivateKey.getQID1(),r);
		
		ClientData = new ClientData(ID, ClientPrivateKey);
		ClientData.setOffLineParams(ID);
		MAKE_1 MAKE_1_result = MAKE_1(PreComputation);
		
		
		OnLineMsg onLineMsg=new OnLineMsg(MAKE_1_result.toMsg());
		
		Point Kc=publicParams.getE().getCurve().multiply(ClientPrivateKey.getDID2(), PreComputation.getR());
		BigInt AuthStemp=PublicParams.f2(PreComputation.getID(),PreComputation.getU1(),PreComputation.getU2(),PreComputation.getV(),onLineMsg.getN(), Kc);
		
		BigInt AuthC = null;
		if(!AuthStemp.equals(onLineMsg.getAuthS())){
			System.out.println("Client verify fail!");
			return;
		}
		System.out.println("Server to Client verify pass!");
		AuthC=PublicParams.f3(PreComputation.getID(),PreComputation.getU1(),PreComputation.getU2(),PreComputation.getV(), onLineMsg.getN(), Kc, onLineMsg.getAuthS());
		BigInt ClientSessionKey=PublicParams.f4(PreComputation.getID(),PreComputation.getU1(),PreComputation.getU2(),PreComputation.getV(), onLineMsg.getN(), Kc, onLineMsg.getAuthS(), AuthC);
		
		System.out.println("Client session key result: " + ClientSessionKey);
		
		BigInt AuthCtemp=PublicParams.f3(PreComputation.getID(),PreComputation.getU1(),PreComputation.getU2(),PreComputation.getV(), ClientData.getN(), ClientData.getKs(), ClientData.getAuthS());
		if(!AuthC.equals(AuthCtemp)){
			System.out.println("Client to Server authentication fail!");
			return;
		}
		
		System.out.println("Client to Server authentication pass!");
		BigInt ServerSessionKey=PublicParams.f4(PreComputation.getID(),PreComputation.getU1(),PreComputation.getU2(),PreComputation.getV(), ClientData.getN(), ClientData.getKs(), ClientData.getAuthS(), AuthC);
		System.out.println("Server session key result: " + ServerSessionKey);
		
	}
	public MAKE_1 MAKE_1(PreComputation inputPreComputation){//the first part of MAKE
		PreComputation clientOffLine=inputPreComputation;

		Point W=PublicParams.H2(clientOffLine.getU1(), clientOffLine.getU2());
		BigInt h=PublicParams.f1(clientOffLine.getID(), clientOffLine.getQID1());
		Point QID2=PublicParams.H1(clientOffLine.getID());
		FieldElement ePV=publicParams.getE().compute(publicParams.getP(), clientOffLine.getV());
		Point U1_QID1=publicParams.getE().getCurve().add(clientOffLine.getU1(), clientOffLine.getQID1());
		FieldElement one=publicParams.getE().compute(U1_QID1,W);
		Point hw=publicParams.getE().getCurve2().multiply(W, h);
		Point hw_QID2=publicParams.getE().getCurve2().add(hw,QID2);//
		FieldElement two=publicParams.getE().compute(publicParams.getPpub(),hw_QID2);
		FieldElement oneAndTwo = publicParams.getE().getGt().multiply(one,two);
		
		System.out.println(ePV.toString());
		System.out.println(oneAndTwo.toString());
		if(ePV.equals(oneAndTwo)){
			System.out.println("Server pairing verify pass!");
			BigInt N=getRandomNumber(publicParams.getE());
			Point Ks=publicParams.getE().getCurve().multiply(clientOffLine.getU2(), ServerPrivateKey);
			BigInt AuthS=PublicParams.f2(clientOffLine.getID(), clientOffLine.getU1(),clientOffLine.getU2(),clientOffLine.getV(), N, Ks);
			ClientData.setN_Ks_AuthS(N, Ks, AuthS);
			MAKE_1 temp=new MAKE_1(N,AuthS);
			return temp;
		}
		System.out.println("Pairing verify fail!");
		return null;
	}
	private BigInt getRandomNumber(Pairing e){//get a random number
		BigInt x = null;
		try{
			x = new BigInt(e.getGroupOrder().bitLength(),new SecureRandom());
		}catch(Exception e1) {return null;}
		return x.mod(e.getGroupOrder());
	}
	public Point PreComputation(String ID){
		return PublicParams.H1(ID);
	}
	public static void main(String[] args) {
		
		PairingDemo Demo = new PairingDemo();
		
	}

}
