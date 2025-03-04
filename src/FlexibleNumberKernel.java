/**
 * Kernel interface for base-flexible numeric representation.
 */
public interface FlexibleNumberKernel {

    // Base Constraints
    /** Minimum allowed base */
    int MIN_BASE = 2;
    /** Maximum allowed base */
    int MAX_BASE = 36;
    /** Default base for initialization */
    int DEFAULT_BASE = 10;

    /**
     * Multiplies value by current base and adds digit.
     *
     * @param digit Digit to append (0 ≤ digit < getBase())
     * @requires 0 ≤ digit < getBase()
     * @ensures this = base * #this + digit
     */
    void multiplyByBase(int digit);

    /**
     * Divides value by current base, returns remainder.
     *
     * @return Remainder digit
     * @ensures this = #this / base
     * @ensures 0 ≤ result < getBase()
     */
    int divideByBase();

    /**
     * Reports current numeric base.
     *
     * @return Current base value
     * @ensures MIN_BASE ≤ result ≤ MAX_BASE
     */
    int getBase();

    /**
     * Checks for zero value.
     *
     * @return true iff this represents 0
     * @ensures result = (this == 0)
     */
    boolean isZero();
}