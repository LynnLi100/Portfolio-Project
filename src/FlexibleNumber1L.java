public class FlexibleNumber1L extends FlexibleNumberSecondary{
    /**
     * Representation of {@code this.rep}.
     */
    private String rep;
    /**
     * Representation of {@code this.base}.
     */
    private int base;

    /**
     * Minimum base.
     */
    static final int MIN_BASE = 2;
    /**
     * Maximum base.
     */
    static final int MAX_BASE = 36;
    /**
     * Default base.
     */
    private static final int DEFAULT_BASE = 10;

    // Conversion helpers

    /**
     * convert int digit to char.
     * @param digit
     *
     * @return
     *          char digit
     */
    private char toChar(int digit) {
        if (digit < DEFAULT_BASE) {
            return (char) ('0' + digit);
        } else {
            return (char) ('A' + (digit - DEFAULT_BASE));
        }
    }

    /**
     * convert char digit to int.
     * @param c
     *
     * @return
     *          int c
     */
    private int fromChar(char c) {
        if (Character.isDigit(c)) {
            return c - '0';
        } else {
            return Character.toUpperCase(c) - 'A' + DEFAULT_BASE;
        }
    }

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = "";
        this.base = DEFAULT_BASE;
    }


//constructors

    /**
     * No-argument constructor.
     */
    public FlexibleNumber1L() {
        this.createNewRep();
    }

    /**
     * Constructor from {@code int} (base 10).
     *
     * @param number (base 10)
     *          {@code int} to initialize from
     * @param  base
     *          base
     */
    public FlexibleNumber1L(int number, int base) {
        assert base >= this.MIN_BASE : "base is less than 2";
        assert base <= MAX_BASE : "base is larger than 36";
        assert number >= 0 : "number is less than 0";

        this.base = base;
        this.rep = "";
        int temp = number;
        while (temp > 0) {
            this.multiplyByBase(temp % base);
            temp /= base;
        }
    }


    /**
     *
     * @param number （base 'base')
     *              {@code String} to initialize from
     * @param base
     */
    public FlexibleNumber1L(String number, int base) {
        assert base >= this.MIN_BASE : "base is less than 2";
        assert base <= MAX_BASE : "base is larger than 36";
        this.base = base;
        this.rep = "";
        String temp = number.replaceAll("^0+", "");
        if (temp.isEmpty()) {
            this.rep = "";
        } else {
            // Validate all characters
            String reversed = new StringBuilder(temp).reverse().toString();
            for (char c : reversed.toCharArray()) {
                int digit = this.fromChar(c);
                if (digit >= base) {
                    throw new IllegalArgumentException(
                        "Invalid digit '" + c + "' for base " + base
                    );
                }
                this.multiplyByBase(digit);
            }
        }
    }


    /**
     * Constructor from {@code FlexibleNumber}.
     *
     * @param n
     *            {@code FlexibleNumber} to initialize from
     */
    public FlexibleNumber1L(FlexibleNumber n) {
        assert n != null : "Violation of: n is not null";

        FlexibleNumber1L temp = (FlexibleNumber1L) n;
        this.base = temp.base;
        this.rep = temp.rep;
    }

//kernel method

    @Override
    public void setBase(int newBase) {
        if (newBase == this.base()) {
            return;
        }
        if (newBase < FlexibleNumber1L.MIN_BASE || newBase > FlexibleNumber1L.MAX_BASE) {
            throw new IllegalArgumentException("Invalid base");
        }

        // Create a copy of the current number to process
        FlexibleNumber1L current = new FlexibleNumber1L(this);
        FlexibleNumber1L temp = new FlexibleNumber1L();
        temp.setBase(newBase);

        // Process each digit of 'current' in its base to build 'temp' in newBase
        while (!current.isZero()) {
            int rem = current.divideByBase();
            temp.multiplyByBase(rem);
        }

        // Now, temp's rep is reversed; need to reverse it to get the correct order
        FlexibleNumber1L reversedTemp = new FlexibleNumber1L();
        reversedTemp.setBase(newBase);
        while (!temp.isZero()) {
            int digit = temp.divideByBase();
            reversedTemp.multiplyByBase(digit);
        }

        // Clear the current number and rebuild with new base and digits
        while (!this.isZero()) {
            this.divideByBase();
        }
        ((FlexibleNumber1L) this).base = newBase;
        while (!reversedTemp.isZero()) {
            int digit = reversedTemp.divideByBase();
            this.multiplyByBase(digit);
        }
    }

    /**
     *
     * @param k
     */
    @Override
    public void multiplyByBase(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < this.base : "Violation of: k < base";

        if (this.isZero() && k != 0) {
            this.rep += this.toChar(k);
        } else if (!this.isZero()) {
            this.rep = this.rep + this.toChar(k);
        }
    }

    /**
     *
     * @return
     *          the last digit
     */
    @Override
    public int divideByBase() {
        int result = 0;
        if (!this.isZero()) {
            int lastIdx = this.rep.length() - 1;
            char c = this.rep.charAt(lastIdx);
            this.rep = this.rep.substring(0, lastIdx);
            result =  this.fromChar(c);
        }
        return result;
    }

    /**
     *
     * @return
     *          the last digit
     */
    @Override
    public int base() {
        return this.base;
    }

    /**
     * check whether this is zero.
     * @return
     *          the result of ckecking is zero
     */
    @Override
    public final boolean isZero() {
        boolean result = this.rep.length() == 0;
        return result;
    }

//
    @Override
    public String toString() {
        if (this.isZero()) {
            return "0";
        }
        return new StringBuilder(this.rep).reverse().toString();
    }

}
