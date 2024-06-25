package com.marcostonina.nonconstructiblechange.domain;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CurrencyService {

    /**
     * Given a list of coins, returns the minimum amount of change that it's impossible to create
     * @param coins: an array of coins
     * @return minimum amount of change that it's impossible to create. If coin list is 0, it returns 1
     */
    public int getNonConstructibleChange(List<Integer> coins) {
        if (coins.isEmpty()) {
            return 1;
        }
        Collections.sort(coins);
        int minChange = 0;

        for (int coin : coins) {
            if (coin > minChange + 1) {
                break;
            }
            minChange += coin;
        }
        return minChange + 1;
    }

}
