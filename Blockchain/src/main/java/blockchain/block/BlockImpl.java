package blockchain.block;
import blockchain.Block;
import blockchain.Util;

import java.util.Date;

public class BlockImpl implements Block {
    String hash; /* Hashcode for the current block */
    String previousHash; /* Hashcode for the previous block */
    private String data; /* Data stored in this block */
    private long timestamp; /* Timestamp of the block at point of initialization */

    // TODO create wallets
    private int nonce; /* Stands for "number once", an arbitrary number that is used once in a cryptographic communication,
                          should be random to mitigate replay attacks */

    public BlockImpl(String data, String previousHash){
        this.data = data;
        this.previousHash = previousHash;
        this.timestamp = new Date().getTime();
        this.hash = this.getRegisteredHash();
    }

    @Override
    public void mineBlock(int difficulty){
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target)){
            // TODO create wallets
//            this.nonce = blockchain.Util.generateNonce();
            this.nonce++;
            this.hash = getRegisteredHash();
        }
        System.out.println("Block is mined: "+this.hash);
    }

    @Override
    public String getCalculatedHash(){
        return this.hash;
    }

    @Override
    public String getRegisteredHash(){
        return Util.encodeSHA256(previousHash + timestamp + nonce + data);
    }

    @Override
    public String getPreviousHash(){
        return this.previousHash;
    }

    @Override
    public String toString() {
        return "BlockImpl{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
