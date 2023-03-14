package blockchain;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Util {

    public static int generateNonce(){
        SecureRandom rand = new SecureRandom();
        return rand.nextInt(Integer.MAX_VALUE);
    }

    public static String encodeSHA256(String data){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder encoded = new StringBuilder();
            for(Byte datum : hash){
                String hex = Integer.toHexString(0xff & datum);
                if(hex.length() == 1) encoded.append('0');
                encoded.append(hex);
            }
            return encoded.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }


    public static byte[] applyECDSASig(PrivateKey privateKey, String input){
        Signature dsa;
        byte[] output;
        try{
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte[] strByte = input.getBytes();
            dsa.update(strByte);
            byte[] realSig = dsa.sign();
            output = realSig;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
        return output;
    }

    public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature){
        try{
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
