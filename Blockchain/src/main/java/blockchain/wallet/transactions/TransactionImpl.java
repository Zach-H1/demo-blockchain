package blockchain.wallet.transactions;

import blockchain.Transaction;
import blockchain.Util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class TransactionImpl implements Transaction {
    String transactionId;
    PublicKey sender;
    PublicKey recipient;
    float value;
    byte[] signature; /* To make sure no one else can spend funds from this wallet */

    ArrayList<TransactionInput> inputs;
    ArrayList<TransactionOutput> outputs;

    private static int sequence = 0;

    public TransactionImpl(PublicKey sender, PublicKey recipient, float value, ArrayList<TransactionInput> inputs) {
        this.sender = sender;
        this.outputs = new ArrayList<>();
        this.recipient = recipient;
        this.value = value;
        this.inputs = inputs;
    }

    @Override
    public void createSignature(PrivateKey privateKey){
        this.signature = Util.applyECDSASig(privateKey, getTransactionSignatureData());
    }

    @Override
    public boolean verifySignature(){
        return Util.verifyECDSASig(this.sender, getTransactionSignatureData(), this.signature);
    }

    private String getTransactionSignatureData(){
        return Util.getStringFromKey(this.sender) + Util.getStringFromKey(this.recipient) + this.value;
    }



    private String getHash(){
        sequence++;
        return Util.encodeSHA256(Util.getStringFromKey(this.sender) +
                                        Util.getStringFromKey(this.recipient) +
                                        this.value + sequence
                                );
    }

}
