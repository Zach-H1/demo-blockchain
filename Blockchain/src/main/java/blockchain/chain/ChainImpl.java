package blockchain.chain;

import blockchain.Transaction;
import blockchain.Util;
import blockchain.Wallet;
import blockchain.block.BlockImpl;
import blockchain.wallet.WalletImpl;
import blockchain.wallet.transactions.TransactionImpl;
import com.google.gson.GsonBuilder;

import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;

public class ChainImpl {

    public static ArrayList<BlockImpl> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static WalletImpl walletA;
    public static WalletImpl walletB;

    public static void main(String[]args){
        blockchain.add(new BlockImpl("This is the first block", "0"));
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new BlockImpl("Block 2", blockchain.get(blockchain.size()-1).getCalculatedHash()));
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new BlockImpl("Block 3", blockchain.get(blockchain.size()-1).getCalculatedHash()));
        blockchain.get(2).mineBlock(difficulty);

        System.out.println("Blockchain is valid: "+validateChain());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        System.out.println(blockchainJson);


//        //Setup Bouncy castle as a Security Provider
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//        //Create the new wallets
//        walletA = new WalletImpl();
//        walletB = new WalletImpl();
//        //Test public and private keys
//        System.out.println("Private and public keys:");
//        System.out.println(Util.getStringFromKey(walletA.getPrivateKey()));
//        System.out.println(Util.getStringFromKey(walletA.getPublicKey()));
//        //Create a test transaction from WalletA to walletB
//        Transaction transaction = new TransactionImpl(walletA.getPublicKey(), walletB.getPublicKey(), 5, null);
//        transaction.createSignature(walletA.getPrivateKey());
//        //Verify the signature works and verify it from the public key
//        System.out.println("Is signature verified");
//        System.out.println(transaction.verifySignature());
    }

    /*
     * Iterate over all the blocks in the chain and verify their current and previous hashes
     */
    public static boolean validateChain(){
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
        for(int i = 1; i < blockchain.size(); i++){
            BlockImpl prev = blockchain.get(i-1);
            BlockImpl curr = blockchain.get(i);

            System.out.println(currentHashIsNotRegistered(curr));
            System.out.println(previousHashIsNotRegistered(curr, prev));
            System.out.println(hashIsInvalid(curr.getCalculatedHash(), hashTarget));

            if(currentHashIsNotRegistered(curr) ||
                    previousHashIsNotRegistered(curr, prev) ||
                    hashIsInvalid(curr.getCalculatedHash(), hashTarget)) {
                return false;
            }
        }
        return true;
    }

    private static boolean hashIsInvalid(String hash, String target){
        return compareHashes(hash.substring(0, difficulty), target);
    }

    /*
     * Checks current registered previous hash to the current blocks hash
     */
    private static boolean previousHashIsNotRegistered(BlockImpl current, BlockImpl previous){
        System.out.println(current.getCalculatedHash()+"-------"+current.getPreviousHash());
        return !compareHashes(previous.getCalculatedHash(), current.getPreviousHash());
    }

    /*
     * Compares current hash to registered hash
     */
    private static boolean currentHashIsNotRegistered(BlockImpl current){
        System.out.println(current.getCalculatedHash()+"-------"+current.getRegisteredHash());
        return !compareHashes(current.getCalculatedHash(), current.getRegisteredHash());
    }

    /*
     * Compares two hashes represented as Strings
     */
    private static boolean compareHashes(String hashOne, String hashTwo){
        return hashOne.equals(hashTwo);
    }
}
