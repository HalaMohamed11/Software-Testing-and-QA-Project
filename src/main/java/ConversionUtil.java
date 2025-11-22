public class ConversionUtil {
    public double cryptoToCrypto(double amount, String from, String to) {
        if(amount < 0) throw new IllegalArgumentException("Amount must be positive");
        if(amount < 0.00000001) throw new IllegalArgumentException("Amount below minimum precision");
        return amount * 2;
    }
}
