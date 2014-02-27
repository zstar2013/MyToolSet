package com.mytools.common;

public class StringUtils {
	public enum CharType {
		DELIMITER, // ����ĸ��ֹ�ַ������磬���������ȵȡ��� ����U0000-U0080��
		NUM, // 2�ֽ����֣�������
		LETTER, // gb2312�еģ�����:���£ã�2�ֽ��ַ�ͬʱ���� 1�ֽ��ܱ�ʾ�� basic latin and latin-1
		OTHER,// �����ַ�
		CHINESE; // ������
		
	}
	
	public static boolean isChineseChar(char c){
		return checkType(c)==CharType.CHINESE;
	}

	/**
	 * �ж�����char���ͱ������ַ�����
	 * 
	 * @param c
	 *            char���ͱ���
	 * @return CharType �ַ�����
	 */
	public static CharType checkType(char c) {
		CharType ct = null;
		// ���ģ���������0x4e00-0x9fbb
		if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			ct = CharType.CHINESE;
		}
		// Halfwidth and Fullwidth Forms�� ��������0xff00-0xffef
		else if ((c >= 0xff00) && (c <= 0xffef)) { // 2�ֽ�Ӣ����
			if (((c >= 0xff21) && (c <= 0xff3a))
					|| ((c >= 0xff41) && (c <= 0xff5a))) {
				ct = CharType.LETTER;
			}
			// 2�ֽ�����
			else if ((c >= 0xff10) && (c <= 0xff19)) {
				ct = CharType.NUM;
			}
			// �����ַ���������Ϊ�Ǳ�����
			else
				ct = CharType.DELIMITER;
		}
		// basic latin���������� 0000-007f
		else if ((c >= 0x0021) && (c <= 0x007e)) { // 1�ֽ�����
			if ((c >= 0x0030) && (c <= 0x0039)) {
				ct = CharType.NUM;
			} // 1�ֽ��ַ�
			else if (((c >= 0x0041) && (c <= 0x005a))
					|| ((c >= 0x0061) && (c <= 0x007a))) {
				ct = CharType.LETTER;
			}
			// �����ַ���������Ϊ�Ǳ����� else ct = CharType.DELIMITER; }
			// latin-1����������0080-00ff
			else if ((c >= 0x00a1) && (c <= 0x00ff)) {
				if ((c >= 0x00c0) && (c <= 0x00ff)) {
					ct = CharType.LETTER;
				} else
					ct = CharType.DELIMITER;
			} else
				ct = CharType.OTHER;
			return ct;
		}
		return ct;
	}
}
