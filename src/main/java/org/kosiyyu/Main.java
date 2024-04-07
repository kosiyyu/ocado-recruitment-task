package org.kosiyyu;

public class Main {
    public static void main(String[] args) {
        String configPath = "src/main/resources/configuration/config.json";
        String basket1 = "src/main/resources/baskets/basket-1.json";
        String basket2 = "src/main/resources/baskets/basket-2.json";

        BasketSplitter bs = new BasketSplitter(configPath);

        var basketList1 = Utils.loadBasket(basket1);
        var results1 = bs.split(basketList1);
        Utils.printMap(results1);

//        var basketList2 = Utils.loadBasket(basket2);
//        var results2 = bs.split(basketList2);
//        Utils.printMap(results2);
    }
}