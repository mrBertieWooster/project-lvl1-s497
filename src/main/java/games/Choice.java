package games;

public class Slot {

System.out.println("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\"");
        switch(System.in.read()){
        case '1':
            Slot.main();
            break;
        case '2':
            Drunkard.main();
            break;
        default:
            System.out.println("Игры с таким номером нет!");
    }
}