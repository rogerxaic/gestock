package gestock.entity;

import gestock.util.Tools;

/**
 * Created by Roger on 1/10/2016.
 */
public class Quantity {

    private static String[] liquidUnits = {"mL", "cL", "dL", "L", "daL", "hL", "kL"};
    private static String[] weigthUnits = {"mg", "cg", "dg", "g", "dag", "hg", "kg"};

    protected double quantity;
    protected double smallestUnitQuantity; //in mg, ml
    protected String unit;
    protected int type = 3; //1 L, 2 g, 3 other

    public Quantity(String text) {
        if (text.contains(" ") && text.split(" ").length == 2) {
            String[] toParse = text.split(" ");
            this.quantity = Double.parseDouble(toParse[0]);
            this.unit = toParse[1];
        } else if (!text.contains(" ")) {
            int lastIndex = 0;
            for (int i = 0; i < text.length(); i++) {
                if (Tools.isNumeric(text.substring(0, i))) {
                    lastIndex = i;
                }
            }
            this.quantity = 0;
            this.unit = "u";
            this.quantity = Double.parseDouble(text.substring(0, lastIndex));
            this.unit = (text.substring(lastIndex));
        } else {
            String[] toParse = text.split(" ");
            StringBuilder sbNumbers = new StringBuilder();
            StringBuilder sbUnits = new StringBuilder();
            for (String s : toParse) {
                if (Tools.isNumeric(s)) {
                    sbNumbers.append(s);
                } else {
                    sbUnits.append(s);
                }
            }
            this.quantity = 0;
            this.unit = "u";
            this.quantity = Double.parseDouble(sbNumbers.toString());
            this.unit = sbUnits.toString();
        }

        //process type
        smallestUnitQuantity = quantity;

        for (String unit : liquidUnits) {
            if (unit.toLowerCase().equals(this.unit.toLowerCase())) {
                this.unit = unit;
                this.type = 1;
                Integer diff = difference(liquidUnits, liquidUnits[0], unit);
                if (diff != null) {
                    double factor = Math.pow(10, diff);
                    smallestUnitQuantity = quantity * factor;
                }
            }
        }
        for (String unit : weigthUnits) {
            if (unit.toLowerCase().equals(this.unit.toLowerCase())) {
                this.unit = unit;
                this.type = 2;
                Integer diff = difference(weigthUnits, weigthUnits[0], unit);
                if (diff != null) {
                    double factor = Math.pow(10, diff);
                    smallestUnitQuantity = quantity * factor;
                }
            }
        }
    }

    public static void main(String[] args) {
        Quantity q = new Quantity("2 4 5 m l 6");
        System.out.println(q);
    }

    private Integer difference(String[] array, String in, String out) {
        Integer first = getIndex(array, in);
        Integer last = getIndex(array, out);

        if (first == null || last == null) {
            return null;
        }
        return last - first;
    }

    private Integer getIndex(String[] array, String element) {
        Integer result = null;
        for (int i = 0; i < array.length; i++) {
            if (array[i].toLowerCase().equals(element.toLowerCase())) {
                result = i;
            }
        }
        return result;
    }

    public double getQuantity(String unit) {
        Integer index;
        double quantity;
        switch (type) {
            case 1: //liquids
                index = getIndex(liquidUnits, unit);
                if (index != null) {
                    double factor = Math.pow(10, -index);
                    quantity = smallestUnitQuantity * factor;
                } else {
                    quantity = 0;
                }
                break;
            case 2: //weight
                index = getIndex(weigthUnits, unit);
                if (index != null) {
                    double factor = Math.pow(10, -index);
                    quantity = smallestUnitQuantity * factor;
                } else {
                    quantity = 0;
                }
                break;
            default:
                quantity = this.quantity;
                break;
        }
        return quantity;
    }

    public String toString() {
        return quantity + " " + unit;
    }

    public String getSaveString() {
        return quantity + unit;
    }
}
