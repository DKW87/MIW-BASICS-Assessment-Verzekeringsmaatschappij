package controller;

import java.util.Scanner;

/* Dit is een applicatie waar de gebruiker autopolissen kan bijhouden. Denk aan het invullen van
 * naam polishouder, waarde auto en aantal schadevrije jaren. Op basis daarvan kan een premie berekend
 * worden en wordt al deze informatie netjes afgedrukt in een overzicht. Tot slot wordt er gecontroleerd
 * hoeveel lage premies er aanwezig zijn in het overzicht van polissen.
 * Danny Kwant */
public class VerzekeringsmaatschappijMainController {

    public static void main(String[] args) {

        // lokale variabelen
        int aantalPolissenRegistreren;
        String naamPolisHouder;
        int waardeAuto;
        int schadevrijeJaren;
        int aantalLagePremies;
        double jaarPremie;
        final double MIN_WAARDE_AUTO = 1000.00;
        final int MIN_SCHADEVRIJE_JAREN = 0;
        final int MAX_SCHADEVRIJE_JAREN = 40;
        Scanner userInput;

        // Scanner init
        userInput = new Scanner(System.in);

        System.out.println("Dit is de uitwerking van Danny Kwant, 500955184.\n");

        System.out.print("Hoeveel autopolissen wil je registreren? ");
        aantalPolissenRegistreren = userInput.nextInt();
        userInput.nextLine(); // clear buffer

        // init rijen
        String[] rijNaamPolisHouder = new String[aantalPolissenRegistreren];
        int[] rijWaardeAuto = new int[aantalPolissenRegistreren];
        int[] rijSchadeVrijeJaren = new int[aantalPolissenRegistreren];
        double[] rijJaarPremie = new double[aantalPolissenRegistreren];

        // herhaaldelijk vragen naar naam polishouder, waarde auto en aantal schadevrije jaren
        // totdat alle indexen in de gemaakte rijen gevuld zijn met data
        for (int i = 0; i < aantalPolissenRegistreren; i++) {

            System.out.printf("%s %d%n", "Autopolis", i + 1);
            System.out.printf("%23s", "Naam polishouder: ");
            naamPolisHouder = userInput.nextLine();
            rijNaamPolisHouder[i] = naamPolisHouder;

            // do while met een if statement om een error te tonen als de gebruiker
            // niet de gewenste gegevens invult
            do {

                System.out.printf("%35s", "Waarde van de auto in euro's: ");
                waardeAuto = userInput.nextInt();

                if (waardeAuto < MIN_WAARDE_AUTO) {
                    System.out.printf("%44s%n", "De waarde moet minimaal 1000 euro zijn!");
                }

            } while (waardeAuto < MIN_WAARDE_AUTO);

            rijWaardeAuto[i] = waardeAuto;

            // do while met een if statement om een error te tonen als de gebruiker
            // niet de gewenste gegevens invult
            do {

                System.out.printf("%31s", "Aantal schadevrije jaren: ");
                schadevrijeJaren = userInput.nextInt();
                userInput.nextLine(); // clear buffer

                if (schadevrijeJaren < MIN_SCHADEVRIJE_JAREN) {
                    System.out.printf("%55s%n", "Het aantal schadevrije jaren moet minimaal 0 zijn!");
                } else if (schadevrijeJaren > MAX_SCHADEVRIJE_JAREN) {
                    System.out.printf("%55s%n", "Het aantal schadevrije jaren mag maximaal 40 zijn!");
                }

            } while (schadevrijeJaren < MIN_SCHADEVRIJE_JAREN || schadevrijeJaren > MAX_SCHADEVRIJE_JAREN);

            rijSchadeVrijeJaren[i] = schadevrijeJaren;

            // bereken jaarpremies en sla dit op in de rij
            jaarPremie = berekenJaarpremie(rijWaardeAuto[i], rijSchadeVrijeJaren[i]);
            rijJaarPremie[i] = jaarPremie;

        } // for

        System.out.println("\nOverzicht van de autopolissen");

        // Print headers voor de gegevens uit de rijen die daaronder komen
        System.out.printf("%-13s%10s%20s%30s%n", "Polishouder", "Waarde", "Aantal jaren", "Jaarpremie");

        // print overzicht van alle gegevens in de rijen
        for (int i = 0; i < aantalPolissenRegistreren; i++) {

            System.out.printf("%-13s%10d%20d%30.02f%n", rijNaamPolisHouder[i], rijWaardeAuto[i], rijSchadeVrijeJaren[i], rijJaarPremie[i]);

        }

        // controleer dmv methode hoeveel premies onder de 1000 euroo aanwezig zijn in de
        // door de gebruikers opgegeven informatie en print het resultaat uit
        aantalLagePremies = bepaalLageJaarPremies(rijJaarPremie);
        System.out.println("\nAantal lage premies: " + aantalLagePremies);


    } // main

    // public methods

    // Bereken per autopolis de jaarpremie op basis van de ingevoerde gegevens
    public static double berekenJaarpremie(int mpWaarde, int mpSchadevrijeJaren) {
        double basisPremie = 0;
        double totaleJaarpremie = 0;
        final double BASIS_PREMIE = 0.10;
        final double KORTINGSPERCENTAGE_PER_JAAR = 0.05;
        final double MIN_PERCENTAGE_TE_BETALEN_PREMIE = 0.30;
        final int MAX_SCHADEVRIJE_JAREN_KORTING = 14;
        final int MIN_SCHADEVRIJE_JAREN_KORTING = 0;

        basisPremie = mpWaarde * BASIS_PREMIE;

        if (mpSchadevrijeJaren <= MAX_SCHADEVRIJE_JAREN_KORTING) {

            totaleJaarpremie = basisPremie - (basisPremie * (mpSchadevrijeJaren * KORTINGSPERCENTAGE_PER_JAAR));

        } else {

            totaleJaarpremie = basisPremie * MIN_PERCENTAGE_TE_BETALEN_PREMIE;

        }

        if (mpSchadevrijeJaren == 0) {
            basisPremie = mpWaarde * BASIS_PREMIE;
        }


        return totaleJaarpremie;
    }

    // lees gegevens uit een rijWaarde en bepaal hoeveel van de indexen minder dan 1000 zijn
    public static int bepaalLageJaarPremies(double[] mpJaarPremies) {
        int telAantalLagePremies = 0;
        final int MIN_WAARDE_PREMIE = 1000;

        for (int i = 0; i < mpJaarPremies.length; i++) {

            if (mpJaarPremies[i] < MIN_WAARDE_PREMIE) {

                telAantalLagePremies++;

            }

        }

        return telAantalLagePremies;
    }

} // class
