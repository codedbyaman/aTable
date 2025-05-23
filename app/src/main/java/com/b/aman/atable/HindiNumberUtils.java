package com.b.aman.atable;


import java.util.*;

public class HindiNumberUtils {

    private static final String[] units = {
            "", "ek", "do", "teen", "chaar", "paanch", "chhah", "saat", "aath", "nau",
            "das", "gyaarah", "baarah", "terah", "chaudah", "pandrah", "solah", "satrah", "atharah", "unnees"
    };

    private static final String[] tens = {
            "", "", "bees", "tees", "chaalees", "pachaas", "saath", "sattar", "assi", "nabbe"
    };

    private static final Map<Integer, String> special = Map.ofEntries(
            Map.entry(21, "ikkees"),
            Map.entry(22, "baees"),
            Map.entry(23, "teees"),
            Map.entry(24, "chaubees"),
            Map.entry(25, "pachchees"),
            Map.entry(26, "chhabbees"),
            Map.entry(27, "sattaees"),
            Map.entry(28, "atthaeess"),
            Map.entry(29, "untees"),
            Map.entry(31, "ikattis"),
            Map.entry(32, "battis"),
            Map.entry(33, "tentis"),
            Map.entry(34, "chauntis"),
            Map.entry(35, "paintis"),
            Map.entry(36, "chhattis"),
            Map.entry(37, "saintees"),
            Map.entry(38, "adhtis"),
            Map.entry(39, "untalis"),
            Map.entry(41, "iktalis"),
            Map.entry(42, "bayalis"),
            Map.entry(43, "taitalis"),
            Map.entry(44, "chaualis"),
            Map.entry(45, "paintalis"),
            Map.entry(46, "chhiyalis"),
            Map.entry(47, "sataalis"),
            Map.entry(48, "adhtalis"),
            Map.entry(49, "unchaas"),
            Map.entry(51, "ikyaavan"),
            Map.entry(52, "bawan"),
            Map.entry(53, "tirpan"),
            Map.entry(54, "chauvan"),
            Map.entry(55, "pachpan"),
            Map.entry(56, "chhappan"),
            Map.entry(57, "sattavan"),
            Map.entry(58, "aththavan"),
            Map.entry(59, "unsath"),
            Map.entry(61, "iksaath"),
            Map.entry(62, "basaath"),
            Map.entry(63, "tirsath"),
            Map.entry(64, "chausath"),
            Map.entry(65, "painsath"),
            Map.entry(66, "chhiyaasath"),
            Map.entry(67, "sadsath"),
            Map.entry(68, "adhsath"),
            Map.entry(69, "unhattar"),
            Map.entry(71, "ikhattar"),
            Map.entry(72, "bahattar"),
            Map.entry(73, "tihattar"),
            Map.entry(74, "chauhattar"),
            Map.entry(75, "pachhattar"),
            Map.entry(76, "chhihattar"),
            Map.entry(77, "sattattar"),
            Map.entry(78, "athhattar"),
            Map.entry(79, "unnasi"),
            Map.entry(81, "ikyaasi"),
            Map.entry(82, "bayaasi"),
            Map.entry(83, "tirasi"),
            Map.entry(84, "chauraasi"),
            Map.entry(85, "pachasi"),
            Map.entry(86, "chhiyaasi"),
            Map.entry(87, "sattasi"),
            Map.entry(88, "athasi"),
            Map.entry(89, "nawaasi"),
            Map.entry(91, "ikyaanave"),
            Map.entry(92, "baanave"),
            Map.entry(93, "tiryanave"),
            Map.entry(94, "chauranave"),
            Map.entry(95, "pachyanave"),
            Map.entry(96, "chhiyanave"),
            Map.entry(97, "sattayanave"),
            Map.entry(98, "athyanave"),
            Map.entry(99, "ninyanave")
    );

    public static String convert(long number) {
        if (number == 0) return "shoonya";

        if (number < 20) return units[(int) number];

        if (number < 100) {
            if (special.containsKey((int) number)) {
                return special.get((int) number);
            }
            int ten = (int) number / 10;
            int unit = (int) number % 10;
            return tens[ten] + (unit != 0 ? " " + units[unit] : "");
        }

        if (number < 1000) {
            long hundreds = number / 100;
            long rest = number % 100;
            return convert(hundreds) + " sau" + (rest != 0 ? " " + convert(rest) : "");
        }

        if (number < 100000) {
            long thousands = number / 1000;
            long rest = number % 1000;
            return convert(thousands) + " hazaar" + (rest != 0 ? " " + convert(rest) : "");
        }

        if (number < 10000000) {
            long lakhs = number / 100000;
            long rest = number % 100000;
            return convert(lakhs) + " lakh" + (rest != 0 ? " " + convert(rest) : "");
        }

        long crores = number / 10000000;
        long rest = number % 10000000;
        return convert(crores) + " crore" + (rest != 0 ? " " + convert(rest) : "");
    }


}

