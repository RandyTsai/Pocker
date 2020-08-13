import java.util.Arrays;

public class Poker {


    private int ranks[] = new int[5];
    private String suits[] = new String[5];
    /**
     * holds the quantity of each rank and suit.
     * rankCount: index0-12 represents number of 2-A
     * suitCount: index0-3 represents number of C D H S
     */
    private int rankCount[] = new int[13];
    private int suitCount[] = new int[4];//0-4 = "C","D","H","S"


    public static void main(String[] args) {

        Poker pk = new Poker();

        if(args.length>5){
            System.out.println("NOT UNDERTAKEN");
            System.exit(0);
        }else if(args.length<5){
            System.out.println("Error: wrong number of arguments; must be a multiple of 5");
            System.exit(0);
        }


        /**to begin to insert values**/
        String hands[] = args;
        pk.insertValue(hands);



        /**to begin judgement hands**/
        pk.judge();
    }



    /**insertion function
     * to deal with ranks and suits and count.
     * insert values into each variables(array) above
     */
    private void insertValue(String s[]){

        for (int i=0; i<s.length; i++) {
            String card = s[i].toUpperCase();
            String firstDigit = card.substring(0,1);
            String secondDigit = card.substring(1,2);


            //error length of input check
            if(card.length()!=2){
                System.out.printf("Error: invalid card name '%s'", card);
                System.exit(0);}


            /**deal with ranks & turns T-A to integer for sorting later**/
            String validRank[] = {"2", "3", "4", "5", "6", "7", "8", "9"};
            for (int j=0; j<validRank.length;j++) {

                if (firstDigit.equals("T")){
                    ranks[i] = 10;
                    rankCount[8] += 1;
                    break;
                }else if(firstDigit.equals("J")){
                    ranks[i] = 11;
                    rankCount[9] +=1;
                    break;
                }else if(firstDigit.equals("Q")){
                    ranks[i] = 12;
                    rankCount[10] +=1;
                    break;
                }else if(firstDigit.equals("K")){
                    ranks[i] = 13;
                    rankCount[11] +=1;
                    break;
                }else if(firstDigit.equals("A")) {
                    ranks[i] = 14;
                    rankCount[12] +=1;
                    break;
                }else if(!validRank[j].equals(firstDigit)){
                    continue;   //if user input an "I", it will jump out of iteration after run out of the validRank[], due to no match
                }else{          //would go this condition only have a match before run out of the validRank[]
                    ranks[i] = Integer.parseInt(firstDigit);
                    rankCount[Integer.parseInt(firstDigit)-2] +=1;
                    //System.out.println("founded & inserted");
                    break;
                }
            }


            /**check ranks inserted correct, else error**/
            if(ranks[i]==0){
                //System.out.println("NOW check"+firstDigit);
                System.out.printf("Error: invalid card name '%s'%n", card);
                System.exit(0);
            }


            /**deal with suits**/
            switch(secondDigit) {
                case "C":
                    suitCount[0] += 1;
                    suits[i] = secondDigit;
                    break;
                case "D":
                    suitCount[1] += 1;
                    suits[i] = secondDigit;
                    break;
                case "H":
                    suitCount[2] += 1;
                    suits[i] = secondDigit;
                    break;
                case "S":
                    suitCount[3] += 1;
                    suits[i] = secondDigit;
                    break;
                default:
                    System.out.printf("Error: invalid card name '%s'%n", card);
                    System.exit(0);
            }
            //System.out.println("r="+ranks[i]+"s="+suits[i]);
        }

    }



    /**judgement function**/
    private void judge(){

        Arrays.sort(ranks);//sorting ranks of a hand
        Poker pk = new Poker();

        /**Flush & Straight**/
        if(pk.isFlush(suitCount)&&pk.isStraight(ranks)){
            System.out.printf("Player 1: %s-high straight flush%n", pk.convertToLingo(ranks[4]));
            System.exit(0);
        }else if(pk.isFlush(suitCount)){
            System.out.printf("Player 1: %s-high flush%n", pk.convertToLingo(ranks[4]));
            System.exit(0);
        }else if(pk.isStraight(ranks)){
            System.out.printf("Player 1: %s-high straight%n", pk.convertToLingo(ranks[4]));
            System.exit(0);
        }else{


            /**Four of a kind**/
            for(int i=0; i<rankCount.length;i++){
                if(rankCount[i]==4){
                    System.out.printf("Player 1: Four %ss%n", pk.convertToLingo(i+2));
                    System.exit(0);
                }
            }


            /**Full house & Three of a kind**/
            for(int i=0; i<rankCount.length;i++) {
                if (rankCount[i]!=3) {continue;
                }else{

                    for(int j=0; j<rankCount.length;j++){
                        if(rankCount[j]!=2){continue;
                        }else{
                            System.out.printf("Player 1: %ss full of %ss%n", pk.convertToLingo(i+2), pk.convertToLingo(j+2));
                            System.exit(0);
                        }
                    }
                    System.out.printf("Player 1: Three %ss%n", pk.convertToLingo(i+2));
                    System.exit(0);
                }
            }


            /**Two pair & One pair**/
            for(int i=0; i<rankCount.length; i++){
                if(rankCount[i]!=2){continue;
                }else{

                    for(int j=0; j<rankCount.length; j++){
                        if(j != i && rankCount[j]==2 ){
                            System.out.printf("Player 1: %ss over %ss%n", pk.convertToLingo(j+2), pk.convertToLingo(i+2));
                            System.exit(0);
                        }
                    }
                }
                System.out.printf("Player 1: Pair of %ss%n", pk.convertToLingo(i+2));//1PAIR
                System.exit(0);
            }


            /**High card**/
            System.out.printf("Player 1: %s-high%n", pk.convertToLingo(ranks[4]));
        }

    }



    private boolean isFlush(int a[]){
        for(int element:a){
            if(element==5){return true;}
        }
        return false;
    }



    private boolean isStraight(int a[]){
        int count = 0;
        for(int i=0; i<a.length-1; i++){
            if(a[i]+1 == a[i+1]){count+=1;} //if the former +1 = the latter
        }
        return count==4? true: false;
    }



    private String convertToLingo(int a){
        switch (a){
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            case 14:
                return "Ace";
        }
        return Integer.toString(a);

    }
}
