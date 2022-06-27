import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem","Lucky","Berserk","Thor"};
    public static int[] heroesHealth = {250, 220, 230, 260, 500, 180, 150, 140};
    public static int[] heroesDamage = {25, 20, 30, 0, 120, 45, 35, 15 };
    public static String bossDefenceType = "";
    public static int golem;
    public static int medic;
    public static int berserk;
    public static boolean missRound = false;

    public static void main(String[] args) {
        printStatistics();
        findHeroes();
        findHeroes();
        while (!isGameFinish()) {
            round();
        }
    }

    public static void printStatistics() {

        System.out.println("________________________________\n"
                + "Boss health- " + bossHealth + " Damage-" + " [" + bossDamage + "] ");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println("Heroes health " + heroesAttackType[i] +
                    " " + heroesHealth[i] +
                    " damage -" + " [" + heroesDamage[i] + "]");
        }
        System.out.println("________________________________");
    }

    public static boolean isGameFinish() {
        if (bossHealth <= 0) {
            System.out.println("HEROES WON");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("BOSS WON");
        }
        return allHeroesDead;
    }

    public static void round() {
        chooseBosseDefence();
        bossHits();
        heroesHits();
        printStatistics();
        healthTeam();

    }

    public static void bossHits() {
        for (int i = 0; i < heroesAttackType.length; i++) {
            //Thor skill
            if(!missRound) {

                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] < bossDamage) {
                        heroesHealth[i] = 0;
                    } else {
                        //Lucky skill
                        if (heroesAttackType[i] == "Lucky" && skillLucky()) {
                            System.out.println("SKILL LUCKY WAS ACTIVATED!");
                            continue;

                        }
                        //Golem skill
                        if (heroesHealth[golem] > 0) {
                            // Berserk skill
                            if (i == berserk) {
                                skillBerserk();
                                continue;

                            }
                            heroesHealth[i] -= bossDamage - (bossDamage / 5);
                            heroesHealth[golem] -= bossDamage / 5;
                            continue;
                        }
                        if (i == berserk) {
                            skillBerserk();
                            continue;

                        }
                        heroesHealth[i] -= bossDamage;
                    }
                }

            }else {
                missRound = false;
                break;
            }

        }
    }
    public static void heroesHits() {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0 && bossHealth > 0) {
                    if (bossDefenceType == heroesAttackType[i]) {
                        bossHealth += heroesDamage[i];
                    }
                }
                if (bossHealth < heroesDamage[i]) {
                    bossHealth = 0;
                } else {
                    //Thor skill
                    if (heroesAttackType[i] == "Thor" && skillThor()) {
                        missRound = true;
                        System.out.println("SKILL THOR WAS ACTIVATED!");

                    }
                    bossHealth -= heroesDamage[i];
                }
            }
        }
    public static void chooseBosseDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefenceType);
    }
// Medic
    public static void healthTeam (){
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] < 100 && heroesHealth[i] !=0){
                if ( heroesHealth[medic] > 0) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(100);
                    heroesHealth[i] += randomIndex;
                    System.out.println("Medic health - [" + heroesAttackType[i] + "] on [" + randomIndex + "] HP");
                    break;
                }
            }
        }
    }

    public static boolean skillLucky(){
        Random random = new Random();
        return random.nextBoolean();

    }
    public static void  skillBerserk(){
        heroesHealth[berserk] -= (bossDamage - (bossDamage / 5)) / 2 ;
        heroesHealth[golem] -= bossDamage / 5;
        bossHealth -= bossDamage / 2;

    }
    public static boolean skillThor(){
        Random random = new Random();
        return random.nextBoolean();

    }
    public static void findHeroes(){
        for (int i = 0; i < heroesAttackType.length ; i++) {
            if (heroesAttackType[i] == "Golem"){
                golem = i;
            }
            if (heroesAttackType[i] == "Medic"){
                medic = i;
            }
            if(heroesAttackType[i] == "Berserk"){
                berserk = i;

            }
        }
    }
}