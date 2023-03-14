package blockchain;

import java.security.PrivateKey;

public interface Transaction {

    /**
     * Signs all the data we dont wish to be tampered with.
     * @param privateKey of current transaction
     */
    void createSignature(PrivateKey privateKey);

    /**
     * Verifies the data
     * @return true if the data hasn't been tampered with
     */
    boolean verifySignature();
}
