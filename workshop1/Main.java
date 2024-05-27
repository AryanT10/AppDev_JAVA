/**********************************************
 Workshop 1
 Course:CPA - 5
 Last Name: Tuwar
 First Name: Aryan
 ID: 112137229
 Section: ZAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date:<submission date>
 **********************************************/
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class InstrumentModel {
    private static MusicalInstrument[] instruments;

    public static MusicalInstrument[] getInstruments() {
        return instruments;
    }

    public static void setInstruments(MusicalInstrument[] instruments) {
        InstrumentModel.instruments = instruments;
    }
}

class InstrumentController {
    private final InstrumentModel model;

    public InstrumentController(InstrumentModel model) {
        this.model = model;
    }

    public void displayMostExpensiveInstrument() {
        MusicalInstrument[] instruments = model.getInstruments();
        MusicalInstrument mostExpensive = Arrays.stream(instruments).max(MusicalInstrument::compareTo).orElse(null);

        if (mostExpensive != null) {
            System.out.println("The most expensive instrument is: " + mostExpensive);
            System.out.println(mostExpensive + "'s cost is: $" + mostExpensive.getPrice());
            System.out.println(mostExpensive + " is played: " + mostExpensive.howToPlay());
            System.out.println(mostExpensive + " fixing: " + mostExpensive.howToFix());
            System.out.println(mostExpensive + " pitch type: " + mostExpensive.getPitchType());
        }
    }

    public void displayInstrumentsDescendingOrder() {
        MusicalInstrument[] instruments = model.getInstruments();
        Arrays.stream(instruments)
                .sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice()))
                .map(MusicalInstrument::toString)
                .forEach(System.out::println);
    }

    public void displayInstrumentFamilySound(Scanner scanner) {
        System.out.print("Enter an instrument family: ");
        String familyName = scanner.next().toLowerCase();

        Map<String, String> familySounds = new HashMap<>();
        Arrays.stream(model.getInstruments())
                .filter(instrument -> instrument.getClass().getSuperclass().getSimpleName().toLowerCase().contains(familyName))
                .forEach(instrument -> familySounds.put(instrument.toString(), instrument.MakeSound()));

        if (!familySounds.isEmpty()) {
            familySounds.forEach((instrument, sound) -> System.out.println(instrument + " makes sound " + sound));
        } else {
            System.out.println("No instruments found in the specified family.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Requirement 1: Receive the price value for each instrument
        System.out.println("--: Requirement 1 :--");
        Drum drum = new Drum(getInstrumentPrice(scanner, "Drum"));
        Flute flute = new Flute(getInstrumentPrice(scanner, "Flute"));
        Guitar guitar = new Guitar(getInstrumentPrice(scanner, "Guitar"));
        Harp harp = new Harp(getInstrumentPrice(scanner, "Harp"));
        Xylophone xylophone = new Xylophone(getInstrumentPrice(scanner, "Xylophone"));

        // Requirement 2: Display How to Play, How to Fix, Pitch type, and Price for the most expensive instrument
        System.out.println();
        System.out.println("--: Requirement 2 :--");
        displayMostExpensiveInstrument(drum, flute, guitar, harp, xylophone);

        // Requirement 3: Display the names of all instruments in descending order of price
        System.out.println();
        System.out.println("--: Requirement 3 :--");
        displayInstrumentsDescendingOrder(drum, flute, guitar, harp, xylophone);

        // Set instruments in the model
        InstrumentModel.setInstruments(new MusicalInstrument[]{drum, flute, guitar, harp, xylophone});

        // Requirement 4: Receive an instrument family name and display how each instrument makes sound
        System.out.println();
        System.out.println("--: Requirement 4 :--");
        InstrumentController controller = new InstrumentController(new InstrumentModel());
        controller.displayInstrumentFamilySound(scanner);

        scanner.close();
    }

    private static double getInstrumentPrice(Scanner scanner, String instrumentName) {
        System.out.print("Enter the price for " + instrumentName + ": ");
        return scanner.nextDouble();
    }

    private static void displayMostExpensiveInstrument(MusicalInstrument... instruments) {
        MusicalInstrument mostExpensive = Arrays.stream(instruments).max(MusicalInstrument::compareTo).orElse(null);

        if (mostExpensive != null) {
            System.out.println("The most expensive instrument is: " + mostExpensive);
            System.out.println(mostExpensive + "'s cost is: $" + mostExpensive.getPrice());
            System.out.println(mostExpensive + " is played: " + mostExpensive.howToPlay());
            System.out.println(mostExpensive + " fixing: " + mostExpensive.howToFix());
            System.out.println(mostExpensive + " pitch type: " + mostExpensive.getPitchType());
        }
    }

    private static void displayInstrumentsDescendingOrder(MusicalInstrument... instruments) {
        String result = Arrays.stream(instruments)
                .sorted((a, b) -> Double.compare(b.getPrice(), a.getPrice()))
                .map(MusicalInstrument::toString)
                .collect(Collectors.joining(", ", "[", "]"));

        System.out.println(result);
    }
}
