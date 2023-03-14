package blockchain;

import blockchain.block.BlockImpl;

import java.util.ArrayList;

public interface Chain {

    ArrayList<BlockImpl> blockchain = new ArrayList<>();
    int difficulty = 5;
}
