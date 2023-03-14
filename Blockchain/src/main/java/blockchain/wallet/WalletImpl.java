package blockchain.wallet;

import blockchain.Wallet;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class WalletImpl implements Wallet {

    PrivateKey privateKey;
    PublicKey publicKey;

    public WalletImpl() { createKeys(); }


    /*
     * This is beyond the scope of this current project, I am currently taking a course in cybersecurity
     * when I learn more about different encryption algorithms I will revise this
     */
    @Override
    public void createKeys(){
        try{

           KeyPairGenerator generator = KeyPairGenerator.getInstance("ECDSA", "BC");
           SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
           ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

           generator.initialize(ecSpec, random);
           KeyPair pair = generator.generateKeyPair();

           this.privateKey = pair.getPrivate();
           this.publicKey = pair.getPublic();

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
