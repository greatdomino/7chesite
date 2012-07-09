package com.dongwang.common.util;

import java.security.*;
import javax.crypto.*;

public class DESPlus {
	private static String strDefaultKey = "national";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * ��byte����ת��Ϊ��ʾ16����ֵ���ַ����� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת������
	 * 
	 * @param arrB
	 *            ��Ҫת����byte����
	 * @return ת������ַ���
	 * @throws Exception
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 */
	public static String byteArr2HexStr(byte[] arrB) {
		int iLen = arrB.length;
		// ÿ��byte�������ַ����ܱ�ʾ�������ַ����ĳ��������鳤�ȵ�����
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * ����ʾ16����ֵ���ַ���ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
	 * ��Ϊ�����ת������
	 * 
	 * @param strIn
	 *            ��Ҫת�����ַ���
	 * @return ת�����byte����
	 */
	public static byte[] hexStr2ByteArr(String strIn) {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		// �����ַ���ʾһ���ֽڣ������ֽ����鳤�����ַ������ȳ���2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
	 * 
	 * @throws Exception
	 */
	public DESPlus() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * ָ����Կ���췽��
	 * 
	 * @param strKey
	 *            ָ������Կ
	 * @throws Exception
	 */
	public DESPlus(String strKey) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key;
		try {
			key = getKey(strKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �����ֽ�����
	 */
	public byte[] encrypt(byte[] arrB) {
		byte[] s = null;
		try {
			s = encryptCipher.doFinal(arrB);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * ��������strIn���ܣ����ؼ��ܺ��������
	 */
	public String encrypt(String strIn) {
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**
	 * �����ֽ�����
	 */
	public byte[] decrypt(byte[] arrB) {
		byte[] s = null;
		try {
			s = decryptCipher.doFinal(arrB);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * ���� strIn
	 */
	public String decrypt(String strIn) {
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	
	private Key getKey(byte[] arrBTmp) {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];
		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	public static void main(String[] args) {
		try {
			String test = "1";
			DESPlus des = new DESPlus("wwwdongwangcom"); // �Զ�����Կ
			System.out.println("����ǰ���ַ���" + test);
			System.out.println("���ܺ���ַ���" + des.encrypt(test));
			System.out.println("���ܺ���ַ���" + des.decrypt(des.encrypt(test)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
