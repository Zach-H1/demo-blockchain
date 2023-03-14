package blockchain.wallet.transactions;

public class TransactionInput {

    String transactionOutputId;
    TransactionOutput UTXO; /* Unspent Transaction Output (Bitcoin Convention) */

    public TransactionInput(String transactionOutputId){
        this.transactionOutputId = transactionOutputId;
    }
}
