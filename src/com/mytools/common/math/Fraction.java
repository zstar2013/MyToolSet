package com.mytools.common.math;

public class Fraction {
	
	public static void main(String[] args) {  
        // �����������������  
        Fraction fraction1 = new Fraction(0.25);  
        Fraction fraction2 = new Fraction(2, 8);  
          
        // �������  
        Fraction fraction3 = fraction1.add(fraction2);  
        System.out.println(fraction1 + "+" + fraction2 + "=" + fraction3);  
  
        // ��������5��Ԫ�صķ������������  
        Fraction[] fractions = { new Fraction(1, 3), new Fraction(1, 5),  
                new Fraction(1, 2), new Fraction(1, 4), new Fraction(1, 10) };  
                  
        // ���������򣬲�ʹ�ö����۰���ҷ���������ָ�����������  
        java.util.Arrays.sort(fractions);  
  
        // ���������������±�λ��  
        System.out.println("�����ķ����ǵ�"  
                + java.util.Arrays.binarySearch(fractions, fraction3) + "����");  
    }  
	 
    // ����  
    private int numerator;  
      
    // ��ĸ  
    private int denominator;  
  
    // Ĭ�Ϲ��캯��  
    public Fraction() {  
    }  
  
    // �ù��캯���Է��Ӻͷ�ĸ���г�ʼ��  
    public Fraction(int n, int d) {  
        setFraction(n, d);  
    }  
  
    // �ù��캯��֧�ֽ�˫������ת��Ϊ��Ӧ�ķ���  
    public Fraction(double d) {  
        convertToFraction(d);  
    }  
  
    // �ú������Խ�˫������ת��Ϊ��Ӧ�ķ���  
    private void convertToFraction(double d) {  
        int decimalCount = 1;  
          
        // ��˫���������ַ�����  
        int dLen = String.valueOf(d).length();  
          
        // ���ϵĽ�˫�������۳�10��ֱ��ת��Ϊ����Ϊֹ  
        for (int i = 0; i < dLen; i++) {  
            d = d * 10;  
            decimalCount *= 10;  
  
        }  
          
        // ����Ϊ���յ������˻����  
        numerator = (int) d;  
          
        // ��ĸΪ10���۳˽��  
        denominator = decimalCount;  
          
        // Լ��  
        reduct();  
    }  
  
    // ���ӵ����ú���������Լ��  
    public void setNumerator(int n) {  
        numerator = n;  
        reduct();  
    }  
  
    // ���ӵĶ�ȡ����  
    public int getNumerator() {  
        return numerator;  
    }  
  
    // ��ĸ�����ú���������Լ��  
    public void setDenominator(int d) {  
        // ����ĸ�Ƿ�Ϊ0  
        if (d == 0)  
            denominator = 1;  //��������쳣  
        else  
            denominator = d;  
        reduct();  
    }  
  
    // ��ĸ�Ķ�ȡ����  
    public int getDenominator() {  
        return denominator;  
    }  
  
    // ���������ú���������Լ��  
    public void setFraction(int n, int d) {  
        setNumerator(n);  
        setDenominator(d);  
        reduct();  
    }  
  
    // ��ʽ���ַ���Ϣ���  
    public String toString() {  
        // ���Ϊ���������򽫸�����ǰ��ʾ�����Ӻͷ�ĸ����Ϊ������������Ӻͷ�ĸ��ȡ����  
        if (numerator * denominator < 0)  
            return "-" + Math.abs(numerator) + "/" + Math.abs(denominator);  
        else  
            return Math.abs(numerator) + "/" + Math.abs(denominator);  
    }  
  
    // ���ö�ӦС��ֵ�Ƿ���ͬ���жϷ����Ƿ���ͬ  
    public boolean equals(Fraction f) {  
        if (numerator == f.numerator && denominator == f.denominator)  
            return true;  
        else  
            return false;  
    }  
  
    // ���ö�ӦС��ֵ�Ĵ�С���жϷ����Ĵ�С  
    public boolean isGreater(Fraction f) {  
        if ((double) numerator / denominator > (double) f.numerator  
                / f.denominator)  
            return true;  
        else  
            return false;  
    }  
  
    // Լ�ֺ���  
    private void reduct() {  
        // ����Ӻͷ�ĸ����Сֵ  
        int minValue = Math.min(Math.abs(numerator), Math.abs(denominator));  
          
        // ��С�ڷ��Ӻͷ�ĸ����Сֵ����ֵ���Ӵ�Сȥ�����Ӻͷ�ĸ������ܹ�ͬʱ�����������Դ���ֵԼ�ֲ��˳�  
        for (int i = minValue; i >= 1; i--) {  
            if (numerator % i == 0 && denominator % i == 0) {  
                numerator = numerator / i;  
                denominator = denominator / i;  
                break;  
            }  
        }  
    }  
  
    // ���ط�����ӵĽ��������Լ��  
    public Fraction add(Fraction f) {  
        Fraction fraction = new Fraction();  
        fraction.numerator = numerator * f.denominator + f.numerator  
                * denominator;  
        fraction.denominator = denominator * f.denominator;  
        fraction.reduct();  
        return fraction;  
    }  
  
    // ���ط�������Ľ��������Լ��  
    public Fraction minus(Fraction f) {  
        Fraction fraction = new Fraction();  
        fraction.numerator = numerator * f.denominator - f.numerator  
                * denominator;  
        fraction.denominator = denominator * f.denominator;  
        fraction.reduct();  
        return fraction;  
    }  
  
    // ���ط�����˵Ľ��������Լ��  
    public Fraction multiply(Fraction f) {  
        Fraction fraction = new Fraction();  
        fraction.numerator = numerator * f.numerator;  
        fraction.denominator = denominator * f.denominator;  
        fraction.reduct();  
        return fraction;  
    }  
  
    // ���ط�������Ľ��������Լ��  
    public Fraction divide(Fraction f) {  
        Fraction fraction = new Fraction();  
        fraction.numerator = numerator * f.denominator;  
        fraction.denominator = denominator * f.numerator;  
        fraction.reduct();  
        return fraction;  
    }  
  
    // ֧����������������ıȽϣ���ʵ�ַ������ϵĲ��ҹ���  
    public int compareTo(Object o) {  
        Fraction f = (Fraction) o;  
          
        // ���ö�ӦС���Ĵ�С���ȽϷ����Ĵ�С  
        if ((double) numerator / denominator > (double) f.numerator  
                / f.denominator)  
            return 1;  
        else if ((double) numerator / denominator < (double) f.numerator  
                / f.denominator)  
            return -1;  
        else  
            return 0;  
    }  
  
    // ���ݶ�ӦС���Ƿ���ͬ���жϷ����Ƿ���ͬ  
    public boolean equals(Object obj) {  
        Fraction f = (Fraction) obj;  
        if (Math.abs((double) numerator / denominator - (double) f.numerator  
                / f.denominator) < 0.00001)  
            return true;  
        return false;  
    }  
  
    // ��ͬ��ֵ�ķ���������ͬ�Ĺ�ϣ��  
    public int hashcode() {  
        String str = String.valueOf((double) numerator / denominator);  
        return str.hashCode();  
    }  
}
