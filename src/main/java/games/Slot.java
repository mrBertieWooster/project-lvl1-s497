package games;

import org.slf4j.Logger;
import java.util.concurrent.TimeUnit;

public class Slot {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Slot.class);

    public static void main(String... __) throws InterruptedException {

        int jackpot = 1_000;
        int balance = 100;
        int bet = 10;
        int size = 7;
        int firstCounter = 0;
        int secondCounter = 0;
        int thirdCounter = 0;

        while (balance > 0){

            log.info("Рады приветствовать Вас на игре \"Однорукий бандит\"");
            log.info("На Вашем счету %s$, ставка - %s$%n", balance, bet);
            log.info("Крутим барабаны!");
            TimeUnit.SECONDS.sleep(2);
            log.info("Текущий розыгрыш принес следующие результаты:");

            firstCounter = (firstCounter + (int) Math.round(Math.random() * 100)) % size;
            secondCounter = (secondCounter + (int) Math.round(Math.random() * 100)) % size;
            thirdCounter = (thirdCounter + (int) Math.round(Math.random() * 100)) % size;

            log.info("| %s | %s | %s |%n",firstCounter, secondCounter, thirdCounter);

            if(firstCounter == secondCounter && firstCounter ==thirdCounter){
                balance = balance + jackpot;
                log.info("Поздравляем, Вы выиграли!! Ваш баланс составляет %s!%n", balance);
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }else{
                balance = balance - bet;
                log.info("Вы проиграли %s, Ваш баланс - %s. Попробуйте еще раз!%n", bet, balance);
                if (balance == 0){
                    log.info("На Вашем счете кончились средства, игра прекращена:((");
                }
                log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

        }

    }
}