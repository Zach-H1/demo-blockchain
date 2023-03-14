package blockchain;

public interface Block {

    /**
     * @param difficulty the amount of leading 0s that must be solved
     */
    void mineBlock(int difficulty);

    /**
     * @return current calculated hash of local block
     */
    String getCalculatedHash();

    /**
     * @return current registered hash of local block
     */
    String getRegisteredHash();

    /**
     * @return previous blocks hash
     */
    String getPreviousHash();
}
