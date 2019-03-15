package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.concurrent.TimeUnit;

public class Drunkard {
    private static final int PARS_TOTAL_COUNT = Par.values().length;

    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    private static final int PLAYERS_COUNT = 2;
    private static final String[] players = {"Игрок №1", "Игрок №2"};
    private static int[][] playersCards = new int[PLAYERS_COUNT][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];
    private static int[] playersCardHeads = {CARDS_TOTAL_COUNT / 2, CARDS_TOTAL_COUNT / 2};

    public static void main(String... __) throws InterruptedException {
        System.out.println("Сдаем карты");

        dealCards(playersCards);

        int turnCount = 1;
        int winner = 0;

        while (!hasWinner(winner)) {

            System.out.printf("Ход №%s, %s карта: %s; %s карта: %s%n", turnCount,
                                                                        players[0],
                                    toString(playersCards[0][playersCardTails[0]]),
                                                                        players[1],
                                    toString(playersCards[1][playersCardTails[1]]));

            int curCardP1 = getCard(0);
            int curCardP2 = getCard(1);
            winner = 2;
            if (curCardP1 > curCardP2 || curCardP1 == 0 && curCardP2 == 8) {
                playersCardHeads[0] = incrementIndex(playersCardHeads[0]);
                playersCards[0][playersCardHeads[0]] = playersCards[1][playersCardTails[1]];
                playersCardHeads[0] = incrementIndex(playersCardHeads[0]);
                playersCards[0][playersCardHeads[0]] = playersCards[0][playersCardTails[0]];

                winner = 0;
                System.out.println("Кон за первым игроком!");
            } else if (curCardP1 < curCardP2 || curCardP2 == 0 && curCardP1 == 8) {
                playersCardHeads[1] = incrementIndex(playersCardHeads[1]);
                playersCards[1][playersCardHeads[1]] = playersCards[0][playersCardTails[0]];
                playersCardHeads[1] = incrementIndex(playersCardHeads[1]);
                playersCards[1][playersCardHeads[1]] = playersCards[0][playersCardTails[0]];

                winner = 1;
                System.out.println("Кон за вторым игроком!");
            } else {
                System.out.println("Ничья!");
                playersCardHeads[1] = incrementIndex(playersCardHeads[1]);
                playersCardHeads[0] = incrementIndex(playersCardHeads[0]);
                playersCards[1][playersCardHeads[1]] = playersCards[1][playersCardTails[1]];
                playersCards[0][playersCardHeads[0]] = playersCards[0][playersCardTails[0]];

            }
            System.out.printf("У игрока №1 %s карт, у игрока №2 %s карт%n", countCards(playersCardTails[0], playersCardHeads[0]), countCards(playersCardTails[1], playersCardHeads[1]));

            turnCount += 1;
            TimeUnit.SECONDS.sleep(2);
        }
    }

    enum Suit {
        SPADES, // пики
        HEARTS, // червы
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK, // Валет
        QUEEN, // Дама
        KING, // Король
        ACE // Туз
    }

    private static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    private static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    private static int countCards(int tail, int head) {
        if (tail > head) {
            return head + 35 - tail;
        } else {
            return head - tail;
        }
    }

    private static int getCard(int index){
        playersCardTails[index] = incrementIndex(playersCardTails[index]);
        return getPar(playersCards[index][playersCardTails[index-1]]).ordinal();
    }

    private static boolean hasWinner(int winner){
        if (winner < 2 && playerCardsIsEmpty(winner)) {
            System.out.println("У нас победитель! Выиграл " + players[winner]);
            return true;
        }
        return false;
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playersCardTails[playerIndex];
        int head = playersCardHeads[playerIndex];

        return tail == head;
    }

    private static void dealCards(int[][] playersCards) {
        int[] deck = new int[CARDS_TOTAL_COUNT];
        for (int i = 0; i < deck.length; i++) {
            deck[i] = i;
        }

        MathArrays.shuffle(deck);

        for (int i = 0, j = 0; i < deck.length; j++, i += 2) {
            playersCards[0][j] = deck[i];
            playersCards[1][j] = deck[i + 1];
        }
    }


}