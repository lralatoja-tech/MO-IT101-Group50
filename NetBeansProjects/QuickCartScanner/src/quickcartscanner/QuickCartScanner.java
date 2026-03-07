import java.util.Scanner;  // Import Scanner class for user input

/**
 * QuickCart Inventory & Sales System
 * QuickCartScanner.java
 *
 * This program accepts user input to calculate total cost,
 * remaining stock, and checks if a low stock alert is needed.
 */

public class QuickCartScanner {

    public static void main(String[] args) {

        // Create a Scanner object to read input from the keyboard
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("       QuickCart Inventory System       ");
        System.out.println("========================================");

        // === Collect user input ===
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();  // Reads a text value (String)

        System.out.print("Enter quantity sold today: ");
        int quantity = scanner.nextInt();  // Reads an integer value

        System.out.print("Enter price per item: ");
        double pricePerItem = scanner.nextDouble();  // Reads a decimal (double)

        System.out.print("Enter number of restocked items: ");
        int restockedItems = scanner.nextInt();  // Reads another integer

        // === Perform computations ===
        double totalCost = quantity * pricePerItem;        // Multiply quantity by price to get total cost
        int remainingStock = restockedItems - quantity;    // Subtract sold items from restocked items
        boolean lowStockAlert = remainingStock < 5;        // Check if remaining stock is below 5

        // === Display results ===
        System.out.println("\n=== QuickCart Sales Report ===");
        System.out.println("Product         : " + productName);
        System.out.println("Quantity Sold   : " + quantity);
        System.out.println("Price per Item  : ₱" + pricePerItem);
        System.out.println("Total Cost      : ₱" + totalCost);
        System.out.println("Remaining Stock : " + remainingStock);
        System.out.println("Low Stock Alert : " + lowStockAlert);

        // === Bonus: Custom warning message when stock is low ===
        if (lowStockAlert) {
            System.out.println("⚠ Restock soon! Only " + remainingStock + " item(s) left.");
        } else {
            System.out.println("✔ Stock level is sufficient.");
        }

        System.out.println("==============================");

        // Close the Scanner to prevent resource leaks
        scanner.close();
    }
}