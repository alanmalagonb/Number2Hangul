import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final long MAX_NUMBER= 9999999999999999L;
    private static final String ERROR_MAX_NUMBER = "Maximum allowed value: 9999999999999999";

    private static String numberToHangul(String number){

        if(number.length()==4){
            int realNumber = Integer.parseInt(number);
            if(realNumber == 0) return "영";
            if(realNumber == 1) return "일";
        }

        StringBuilder numberFix = new StringBuilder(number);
        // Fix number length to multiple of 4
        while(numberFix.length()%4 != 0){
            numberFix.insert(0, "0");
        }
        number = numberFix.toString();

        StringBuilder hangul = new StringBuilder();

        int numberBlocks = number.length()/4;
        int block = numberBlocks;
        for (byte i = 0; i < numberBlocks ; i++){
            hangul.append(analizeBlock(number.substring(i*4,(i+1)*4), (byte) --block));
            if(i<numberBlocks-1) hangul.append(" ");
        }

        return hangul.toString().trim();
    }

    private static String analizeBlock(String number, byte ten){
        int realNumber = Integer.parseInt(number);
        if(realNumber==1 && ten==0) return "";

        // Number 0-9
        final String one = "영일이삼사오육칠팔구";
        // 1000, 100, 10, -
        final String multiplier = "천백십 ";

        StringBuilder converted = new StringBuilder();

        for (byte i = 0; i<4; i++){
            byte current_number = Byte.parseByte(number.substring(i,i+1));
            // The hangul of 1 and 0 is not shown
            if(current_number < 2)
                continue;
            else
                // Add current number hangul
                converted.append(one.charAt(current_number));
            // Add the multiplier of the current position
            converted.append(String.valueOf(multiplier.charAt(i)).trim());
        }

        // -, 10,000 , 100,000,000 , 1,000,000,000,000
        final String tens = " 만억조";
        if(realNumber > 0)
            // Add the ten hangul corresponding to the index of block
            converted.append(String.valueOf(tens.charAt(ten)).trim());

        return converted.toString();
    }

    @Test
    public void transform(){
        // Max 9,999,999,999,999
        List<String> number = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Random rand = new Random();
            long n = rand.nextLong(MAX_NUMBER + 2);
            if(n > MAX_NUMBER){
                System.out.println(ERROR_MAX_NUMBER);
                continue;
            }
            number.add(String.valueOf(n));
        }
        number.forEach(i -> {
            System.out.println(i + " : " + numberToHangul(i));
        });
    }
}
