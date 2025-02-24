import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Card {
    private String symbol;
    private String value;

    public Card(String symbol, String value) {
        this.symbol = symbol;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " of " + symbol;
    }
}

public class CardCollection {
    private Map<String, List<Card>> cardMap;

    public CardCollection() {
        cardMap = new HashMap<>();
    }

    public void addCard(Card card) {
        cardMap.computeIfAbsent(card.getSymbol(), k -> new ArrayList<>()).add(card);
    }

    public List<Card> findCardsBySymbol(String symbol) {
        return cardMap.getOrDefault(symbol, new ArrayList<>());
    }

    public void displayAllCards() {
        for (Map.Entry<String, List<Card>> entry : cardMap.entrySet()) {
            System.out.println("Symbol: " + entry.getKey());
            for (Card card : entry.getValue()) {
                System.out.println("  " + card);
            }
        }
    }

    public static void main(String[] args) {
        CardCollection collection = new CardCollection();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Symbol");
            System.out.println("3. Display All Cards");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Card Symbol: ");
                    String symbol = scanner.nextLine();
                    System.out.print("Enter Card Value: ");
                    String value = scanner.nextLine();
                    collection.addCard(new Card(symbol, value));
                    break;
                case 2:
                    System.out.print("Enter Symbol to search: ");
                    String searchSymbol = scanner.nextLine();
                    List<Card> cards = collection.findCardsBySymbol(searchSymbol);
                    if (cards.isEmpty()) {
                        System.out.println("No cards found for symbol: " + searchSymbol);
                    } else {
                        System.out.println("Cards found:");
                        for (Card card : cards) {
                            System.out.println("  " + card);
                        }
                    }
                    break;
                case 3:
                    collection.displayAllCards();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
