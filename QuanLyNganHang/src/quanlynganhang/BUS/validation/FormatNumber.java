package quanlynganhang.BUS.validation;

import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatNumber {

    public static String convertNumToVND(BigInteger number) {
        try {
            NumberFormat formatter = NumberFormat.getInstance(Locale.US);
            String formattedAmount = formatter.format(number);

            return formattedAmount;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
