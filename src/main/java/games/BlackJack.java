package games;

import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BlackJack {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BlackJack.class);

    private static int[] cards;
    private static int cursor;

    private static int[][] playersCards;
    private static int[] playersCursors;

    private static final int MAX_VALUE = 21;
    private static final int MAX_CARDS_COUNT = 8;
    private static final int BET = 10;

    private static int[] playersMoney = {100, 100};

    public static void main(String... __) throws IOException, InterruptedException {
        while (playersMoney[0] > 0 && playersMoney[1] > 0) {
            initRound();
            addCard2Player(0);
            addCard2Player(0);
            if (sum(0) < MAX_VALUE - 1) {
                while (confirm("Еще карту?")) {
                    if (!checkCardCount(0)) {
                        break;
                    }
                    addCard2Player(0);
                }
            }

            log.info("Игрок набрал {}", getFinalSum(0));

            addCard2Player(1);
            addCard2Player(1);
            while (sum(1) < MAX_VALUE - 4) {
                addCard2Player(1);
            }
            log.info("Казино набрало {}", getFinalSum(1));
            log.info("Победитель - {}", checkWinner());
            TimeUnit.SECONDS.sleep(2);

        }

        if (playersMoney[0] > 0)
            log.info("Вы выиграли! Поздравляем!");
        else
            log.info("Вы проиграли. Соболезнуем...");

    }

    private static void initRound() {
        log.info("\nУ Вас {}$, у Казино - {}$. Начинаем новый раунд!", playersMoney[0], playersMoney[1]);
        cards = CardUtils.getShaffledCards();
        playersCards = new int[2][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        cursor = 0;
    }

    private static int value(int card) {
        switch (CardUtils.getPar(card)) {
            case JACK:
                return 2;
            case QUEEN:
                return 3;
            case KING:
                return 4;
            case SIX:
                return 6;
            case SEVEN:
                return 7;
            case EIGHT:
                return 8;
            case NINE:
                return 9;
            case TEN:
                return 10;
            case ACE:
            default:
                return 11;
        }
    }

    static boolean confirm(String message) throws IOException {
        log.info("{} \"Y\" - Да, {любой другой символ} - нет (Чтобы выйти из игры, нажмите Ctrl + C)", message);
        switch (Choice.getCharacterFromUser()) {
            case 'Y':
            case 'y':
                return true;
            default:
                return false;
        }
    }

    private static void addCard2Player(int player) {
        log.info("Выпала карта - {}", CardUtils.toString(cards[cursor]));
        playersCards[player][playersCursors[player]] = cards[cursor];
        playersCursors[player] = playersCursors[player] + 1;
        cursor += 1;
    }

    static int sum(int player) {
        int result = 0;
        for (int i = 0; i < playersCursors[player]; i++) {
            result += value(playersCards[player][i]);
        }
        return result;
    }

    static int getFinalSum(int player) {
        if (sum(player) <= 21) {
            return sum(player);
        } else {
            return 0;
        }
    }

    static boolean checkCardCount(int player) {
        if (playersCursors[player] < MAX_CARDS_COUNT) {
            return true;
        }
        return false;
    }

    static String checkWinner() {
        if (getFinalSum(0) > MAX_VALUE && getFinalSum(1) > MAX_VALUE) {
            return "Отсутствует";
        }
        if (getFinalSum(0) > MAX_VALUE) {
            playersMoney[0] = playersMoney[0] - BET;
            playersMoney[1] = playersMoney[1] + BET;
            return "Казино";
        }
        if (getFinalSum(1) > MAX_VALUE) {
            playersMoney[1] = playersMoney[1] - BET;
            playersMoney[0] = playersMoney[0] + BET;
            return "Игрок";
        }
        if (getFinalSum(0) > getFinalSum(1)) {
            playersMoney[1] = playersMoney[1] - BET;
            playersMoney[0] = playersMoney[0] + BET;
            return "Игрок";
        } else {
            playersMoney[0] = playersMoney[0] - BET;
            playersMoney[1] = playersMoney[1] + BET;
            return "Казино";
        }
    }


}
