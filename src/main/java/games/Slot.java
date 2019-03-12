package games;

public class Slot {

    public static void main(String... __) throws InterruptedException {

        int jackpot = 1_000;
        int balance = 100;
        int bet = 10;
        int size = 7;
        int firstCounter = 0;
        int secondCounter = 0;
        int thirdCounter = 0;

        while (balance > 0){

            System.out.println("Рады приветствовать Вас на игре \"Однорукий бандит\"");
            System.out.printf("На Вашем счету %s$, ставка - %s$%n", balance, bet);
            System.out.println("Крутим барабаны!");
            TimeUnit.SECONDS.sleep(2);
            System.out.println("Текущий розыгрыш принес следующие результаты:");

            firstCounter = (firstCounter + (int) Math.round(Math.random() * 100)) % size;
            secondCounter = (secondCounter + (int) Math.round(Math.random() * 100)) % size;
            thirdCounter = (thirdCounter + (int) Math.round(Math.random() * 100)) % size;

            System.out.printf("| %s | %s | %s |%n",firstCounter, secondCounter, thirdCounter);

            if(firstCounter == secondCounter && firstCounter ==thirdCounter){
                balance = balance + jackpot;
                System.out.printf("Поздравляем, Вы выиграли!! Ваш баланс составляет %s!%n", balance);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }else{
                balance = balance - bet;
                System.out.printf("Вы проиграли %s, Ваш баланс - %s. Попробуйте еще раз!%n", bet, balance);
                if (balance == 0){
                    System.out.println("На Вашем счете кончились средства, игра прекращена:((");
                }
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }

        }

    }
}