/**
 * Enhanced interface for base-flexible numeric operations.
 */
public interface FlexibleNumber extends FlexibleNumberKernel {

    // Secondary Operations

    

    /**
     * Adds another number to this value.
     *
     * @param n Number to add (auto-converted to this.base)
     * @requires n ≠ null
     * @ensures this.value = #this.value + n.value
     * @throws ArithmeticException if bases mismatch and conversion fails
     */
    void add(FlexibleNumber n);

    /**
     * Subtracts another number from this value.
     *
     * @param n Number to subtract (auto-converted to this.base)
     * @requires n ≠ null ∧ #this.value ≥ n.value
     * @ensures this.value = #this.value - n.value
     * @throws ArithmeticException if result would be negative or base conversion fails
     */
    void subtract(FlexibleNumber n);

    /**
     * String representation in current base.
     *
     * @return Digit string (0-9, A-Z)
     * @ensures result = this.toString()
     */
    @Override
    String toString();
}