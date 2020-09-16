package rftx.util;

/**
 * provided some util methods to operate byte array
 * @author Rock Chin
 */
public class ByteArrayOperator {
	public static byte[] append(byte[] origin,byte[] add){
		byte[] product=new byte[origin.length+add.length];
		for (int i=0;i<origin.length;i++){
			product[i]=origin[i];
		}
		for(int j=0;j<add.length;j++){
			product[origin.length+j]=add[j];
		}
		return product;
	}
	public static byte[] append(byte origin,byte[] add){
		return append(new byte[]{origin},add);
	}
	public static byte[] subArray(byte[] origin,int start,int end){
		byte[] product=new byte[end-start];
		int index=0;
		for(int i=start;i<end;i++){
			product[index++]=origin[i];
		}
		return product;
	}
}
