package games;

import org.slf4j.Logger;

import java.util.concurrent.TimeUnit;

import static games.CardUtils.CARDS_TOTAL_COUNT;
import static games.CardUtils.getShaffledCards;


public class Drunkard {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Drunkard.class);

    private static final int PLAYERS_COUNT = 2;
    private static final String[] players = {"Игрок №1", "Игрок №2"};

    private static int[][] playersCards = new int[PLAYERS_COUNT][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[PLAYERS_COUNT];
    private static int[] playersCardHeads = {CARDS_TOTAL_COUNT / PLAYERS_COUNT, CARDS_TOTAL_COUNT / PLAYERS_COUNT};

    public static void main(String... __) throws InterruptedException {
        log.info("Сдаем карты");

        dealCards(playersCards);

        int turnCount = 1;
        int winner = 0;

        while (!hasWinner(winner)) {

            log.info("Ход №{}, {} карта: {}; {} карта: {}%n", turnCount,
                    players[0],
                    CardUtils.toString(playersCards[0][playersCardTails[0]]),
                    players[1],
                    CardUtils.toString(playersCards[1][playersCardTails[1]]));

            int curCardP1 = getCard(0);
            int curCardP2 = getCard(1);
            winner = PLAYERS_COUNT;
            if (curCardP1 > curCardP2 || curCardP1 == 0 && curCardP2 == 8) {
                addCard2Player(0, curCardP1, curCardP2);
                winner = 0;
                log.info("Кон за первым игроком!");
            } else if (curCardP1 < curCardP2 || curCardP2 == 0 && curCardP1 == 8) {
                addCard2Player(1, curCardP1, curCardP2);
                winner = 1;
                log.info("Кон за вторым игроком!");
            } else {
                int[] arrCards = {curCardP1};
                addCard2Player(0, arrCards);
                arrCards[0] = curCardP2;
                addCard2Player(1, arrCards);
                log.info("Ничья!");
            }
            log.info("У игрока №1 {} карт, у игрока №2 {} карт%n", countCards(playersCardTails[0],
                    playersCardHeads[0]),
                    countCards(playersCardTails[1],
                            playersCardHeads[1]));

            turnCount += 1;
            TimeUnit.SECONDS.sleep(2);
        }
    }


    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    private static int countCards(int tail, int head) {
        return head - tail + tail > head ? CARDS_TOTAL_COUNT - 1 : 0;
    }

    private static int getCard(int index) {
        playersCardTails[index] = incrementIndex(playersCardTails[index]);
        return playersCards[index][playersCardTails[index] - 1];
    }

    private static void addCard2Player(int index, int... cards) {
        for (int card : cards) {
            playersCardHeads[index] = incrementIndex(playersCardHeads[index]);
            playersCards[index][playersCardHeads[index]] = cards[card];
        }
    }

    private static boolean hasWinner(int winner) {
        if (winner < PLAYERS_COUNT && playerCardsIsEmpty(winner)) {
            log.info("У нас победитель! Выиграл " + players[winner]);
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
        int[] deck = getShaffledCards();

        for (int i = 0, j = 0; i < deck.length; j++, i += 2) {
            playersCards[0][j] = deck[i];
            playersCards[1][j] = deck[i + 1];
        }
    }


}