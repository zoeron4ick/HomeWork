import java.math.BigInteger;
import java.security.SecureRandom;


public class RSA {
    private final static BigInteger one      = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;
    private int N;
    // generate an N-bit (roughly) public and private key
    RSA(int N) {
        this.N=N;
        BigInteger p = BigInteger.probablePrime(N<<1, random);
        BigInteger q = BigInteger.probablePrime(N<<1, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus    = p.multiply(q);
        publicKey  = new BigInteger("65537");     // common value in practice = 2^16 + 1
        privateKey = publicKey.modInverse(phi);
    }


    BigInteger encrypt(BigInteger message) {
        int size=message.bitLength();
        if (size>N){
            N=size+3;
            BigInteger p = BigInteger.probablePrime(N<<1, random);
            BigInteger q = BigInteger.probablePrime(N<<1, random);
            BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));
            modulus    = p.multiply(q);
            privateKey = publicKey.modInverse(phi);
        }
        return message.modPow(publicKey, modulus);
    }

    BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey  + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

}
