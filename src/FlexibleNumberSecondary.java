import components.stack.Stack;
import components.stack.Stack1L;

public abstract class FlexibleNumberSecondary implements FlexibleNumber {

    @Override
    public void add(FlexibleNumber n) {
        if (n == null) {
            throw new NullPointerException("n is null");
        }

        // Convert 'n' to the same base as this number
        FlexibleNumber1L other = new FlexibleNumber1L(n);
        other.setBase(this.base());

        FlexibleNumber1L thisCopy = new FlexibleNumber1L(this);
        FlexibleNumber1L otherCopy = new FlexibleNumber1L(other);
        FlexibleNumber1L result = new FlexibleNumber1L();
        result.setBase(this.base());

        int carry = 0;
        while (!thisCopy.isZero() || !otherCopy.isZero() || carry > 0) {
            int thisDigit = thisCopy.isZero() ? 0 : thisCopy.divideByBase();
            int otherDigit = otherCopy.isZero() ? 0 : otherCopy.divideByBase();

            int sum = thisDigit + otherDigit + carry;
            carry = sum / this.base();
            int digit = sum % this.base();

            result.multiplyByBase(digit);
        }

        // Reverse the result to correct the digit order
        FlexibleNumber1L reversedResult = new FlexibleNumber1L();
        reversedResult.setBase(this.base());
        while (!result.isZero()) {
            int digit = result.divideByBase();
            reversedResult.multiplyByBase(digit);
        }

        // Update the current number
        while (!this.isZero()) {
            this.divideByBase();
        }
        while (!reversedResult.isZero()) {
            int digit = reversedResult.divideByBase();
            this.multiplyByBase(digit);
        }
    }

    @Override
    public void subtract(FlexibleNumber n) {
        if (n == null) {
            throw new NullPointerException("n is null");
        }

        FlexibleNumber1L other = new FlexibleNumber1L(n);
        other.setBase(this.base());

        if (this.compareTo(other) < 0) {
            throw new ArithmeticException("Result would be negative");
        }

        FlexibleNumber1L thisCopy = new FlexibleNumber1L(this);
        FlexibleNumber1L otherCopy = new FlexibleNumber1L(other);
        FlexibleNumber1L result = new FlexibleNumber1L();
        result.setBase(this.base());

        int borrow = 0;
        while (!thisCopy.isZero() || !otherCopy.isZero()) {
            int thisDigit = thisCopy.isZero() ? 0 : thisCopy.divideByBase();
            int otherDigit = otherCopy.isZero() ? 0 : otherCopy.divideByBase();

            thisDigit -= borrow;
            if (thisDigit < otherDigit) {
                thisDigit += this.base();
                borrow = 1;
            } else {
                borrow = 0;
            }

            int diff = thisDigit - otherDigit;
            result.multiplyByBase(diff);
        }

        if (borrow != 0) {
            throw new ArithmeticException("Result would be negative");
        }

        // Remove trailing zeros
        FlexibleNumber1L trimmedResult = new FlexibleNumber1L();
        trimmedResult.setBase(this.base());
        boolean leadingZero = true;
        while (!result.isZero()) {
            int digit = result.divideByBase();
            if (digit != 0 || !leadingZero) {
                trimmedResult.multiplyByBase(digit);
                leadingZero = false;
            }
        }
        if (trimmedResult.isZero()) {
            trimmedResult.multiplyByBase(0);
        }

        // Reverse the trimmed result to correct the digit order
        FlexibleNumber1L reversedResult = new FlexibleNumber1L();
        reversedResult.setBase(this.base());
        while (!trimmedResult.isZero()) {
            int digit = trimmedResult.divideByBase();
            reversedResult.multiplyByBase(digit);
        }

        // Update the current number
        while (!this.isZero()) {
            this.divideByBase();
        }
        while (!reversedResult.isZero()) {
            int digit = reversedResult.divideByBase();
            this.multiplyByBase(digit);
        }
    }

    private int compareTo(FlexibleNumber other) {
        FlexibleNumber1L thisCopy = new FlexibleNumber1L(this);
        FlexibleNumber1L otherCopy = new FlexibleNumber1L(other);
        otherCopy.setBase(thisCopy.base());

        // Compare lengths first
        int thisLength = 0;
        while (!thisCopy.isZero()) {
            thisCopy.divideByBase();
            thisLength++;
        }
        int otherLength = 0;
        while (!otherCopy.isZero()) {
            otherCopy.divideByBase();
            otherLength++;
        }

        if (thisLength > otherLength) {
            return 1;
        } else if (thisLength < otherLength) {
            return -1;
        } else {
            // Compare digit by digit from MSB to LSB
            thisCopy = new FlexibleNumber1L(this);
            otherCopy = new FlexibleNumber1L(other);
            otherCopy.setBase(thisCopy.base());

            Stack<Integer> thisDigits = new Stack1L<>();
            Stack<Integer> otherDigits = new Stack1L<>();
            while (!thisCopy.isZero()) {
                thisDigits.push(thisCopy.divideByBase());
            }
            while (!otherCopy.isZero()) {
                otherDigits.push(otherCopy.divideByBase());
            }

            while (thisDigits.length() != 0) {
                int thisDigit = thisDigits.pop();
                int otherDigit = otherDigits.pop();
                if (thisDigit > otherDigit) {
                    return 1;
                } else if (thisDigit < otherDigit) {
                    return -1;
                }
            }
            return 0;
        }
    }
    

}


