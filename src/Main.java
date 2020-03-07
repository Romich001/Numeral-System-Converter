
import java.util.Scanner;

public class Main {

    static String number;
    static int targetRadix;
    static int sourceRadix;
    static boolean correctness = true;

    public static void main(String[] args) {

        getInput();          //input and check radix and number

        if (correctness) {      //if all input is correct then  convert number else  print "error"

            if (number.contains(".")) {         //if number has fraction part or not
                if (sourceRadix == 1 || targetRadix == 1) {  // ignore fraction part if radix == 1
                    number = number.substring(0, number.indexOf("."));
                    convertNotFractional();
                } else {
                    convertFractional();        //convert number with fraction part
                }

            } else {
                convertNotFractional();         //convert number without fraction part
            }

        } else {
            System.out.println("error");
        }
    }

    private static void getInput () {
        Scanner sc = new Scanner(System.in);
        String[] data = new String[3];
        for (int i = 0; i < 3; i++) {
            String input = sc.next();
            if (i != 1){

                checkInputRadix(input);
            } else {
                checkInputNumber(input, Integer.parseInt(data[0]));
            }
            if (!correctness) {
                break;
            }
            data[i] = input;
        }
        sc.close();
        if (correctness) {
            sourceRadix = Integer.parseInt(data[0]);
            targetRadix = Integer.parseInt(data[2]);
            number = data[1];
        }
    }

    private static void checkInputNumber (String str, int radix) {
        String symbols = "1023456789abcdefghijklmnopqrstuvwxyz";    //all symbols in number with radix 36
        String range = symbols.substring(0, radix);  //all symbols in number with specific radix
        for (char ch :            // check if input number contain correct symbols
                str.toCharArray()) {
            if (range.indexOf(ch) == -1 && ch != '.') {
                correctness = false;
                break;
            }
        }

    }

    private static void checkInputRadix (String str) {      //check radix
        for (char ch : str.toCharArray()){      //check that all chars is digit
            if (!Character.isDigit(ch)) {
                correctness = false;
                break;
            }
        }
        if (correctness) {
            int a = Integer.parseInt(str);
            if (a > 36 || a < 1) {      //check that radix from 1 to 36
                correctness = false;
            }
        }
    }

    private static void convertFractional() {

        String integerPart = number.substring(0, number.indexOf("."));
        String fractionPart = number.substring(number.indexOf(".") + 1);
        StringBuilder num = new StringBuilder();
        num.append(Long.toString(Long.parseLong(integerPart, sourceRadix), targetRadix)); //convert integer part
        num.append(".");
        if (sourceRadix != 10) {    //check and convert fraction part of number to radix 10
            fractionPart = convertToDecimal(fractionPart);
        }
        //to convert fraction part of number to type "0.###...#"
        double numInDouble = Double.parseDouble(fractionPart) / Math.pow(10, fractionPart.length());
        for (int i = 0; i < 5 ; i++) {
            //convert each digit of fraction part to number in specific radix
            numInDouble = numInDouble * targetRadix;
            int currentNum = (int)numInDouble;
            num.append(Long.toString(currentNum, targetRadix));
            numInDouble = numInDouble - (int)numInDouble;
        }

        System.out.println(num);
    }

    //convert the fraction part of number from source radix to radix = 10
    private static String convertToDecimal (String str) {

        double n = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            n += Character.digit(ch, sourceRadix) / Math.pow(sourceRadix, i + 1);
        }
        String num = Double.toString(n);
        num = num.substring(num.indexOf(".") + 1);
        return num;
    }

    private static void convertNotFractional() {

        long num;
        if (sourceRadix == 1) {
            num = number.length();
        } else {
            num = Long.parseLong(number, sourceRadix);
        }

        if (targetRadix == 1) {
            while(num > 0) {
                System.out.print("1");
                num--;
            }
        } else {
            System.out.println(Long.toString(num, targetRadix));
        }
    }
}
