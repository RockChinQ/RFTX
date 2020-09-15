package rftx.util;

/**
 * provided some util methods to operate byte array
 * @author Rock Chin
 */
public class ByteArrayOperator {
	public static byte[] append(byte[] origin,byte[] add){
		byte[] product=new byte[origin.length+add.length];
		for (int i=0;i<origin.length;i++){
			product[i]=origin[1];
		}
		for(int j=0;j<add.length;j++){
			product[origin.length+j]=add[j];
		}
		return product;
	}
	public static byte[] append(byte origin,byte[] add){
		return append(new byte[]{origin},add);
	}
}
