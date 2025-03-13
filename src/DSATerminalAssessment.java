import java.util.*;
import java.text.*;
import java.io.*;

public class DSATerminalAssessment {

    // Class to represent a stock item
    static class Stock {
        String dateEntered;
        String status;
        String brand;
        String engineNumber;
        String purchaseStatus;

        public Stock(String dateEntered, String status, String brand, String engineNumber, String purchaseStatus) {
            this.dateEntered = dateEntered;
            this.status = status;
            this.brand = brand;
            this.engineNumber = engineNumber;
            this.purchaseStatus = purchaseStatus;
        }

        @Override
        public String toString() {
            return "Date: " + dateEntered + ", Status: " + status + ", Brand: " + brand + ", Engine Number: " + engineNumber + ", Purchase Status: " + purchaseStatus;
        }
    }

    // AVL Tree Node (modified BST node with height attribute)
    static class BSTNode {
        Stock data;
        BSTNode left;
        BSTNode right;
        int height; // New field for AVL balancing

        public BSTNode(Stock data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1; // Leaf nodes have height 1
        }
    }

    // AVL Tree implementation for inventory
    static class InventoryBST {
        BSTNode root;

        public InventoryBST() {
            this.root = null;
        }

        // Get height of a node (handles null nodes)
        private int height(BSTNode node) {
            if (node == null) return 0;
            return node.height;
        }

        // Get balance factor of a node
        private int getBalance(BSTNode node) {
            if (node == null) return 0;
            return height(node.left) - height(node.right);
        }

        // Right rotate subtree rooted with y
        private BSTNode rightRotate(BSTNode y) {
            BSTNode x = y.left;
            BSTNode T2 = x.right;

            // Perform rotation
            x.right = y;
            y.left = T2;

            // Update heights
            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            // Return new root
            return x;
        }

        // Left rotate subtree rooted with x
        private BSTNode leftRotate(BSTNode x) {
            BSTNode y = x.right;
            BSTNode T2 = y.left;

            // Perform rotation
            y.left = x;
            x.right = T2;

            // Update heights
            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            // Return new root
            return y;
        }

        // Insert method for AVL Tree based on engineNumber
        public void insert(Stock stock) {
            root = insertRec(root, stock);
        }

        private BSTNode insertRec(BSTNode node, Stock stock) {
            // 1. Perform standard BST insertion
            if (node == null) {
                return new BSTNode(stock);
            }

            if (stock.engineNumber.compareTo(node.data.engineNumber) < 0) {
                node.left = insertRec(node.left, stock);
            } else if (stock.engineNumber.compareTo(node.data.engineNumber) > 0) {
                node.right = insertRec(node.right, stock);
            } else {
                // Duplicate keys not allowed
                return node;
            }

            // 2. Update height of this ancestor node
            node.height = Math.max(height(node.left), height(node.right)) + 1;

            // 3. Get the balance factor to check if this node became unbalanced
            int balance = getBalance(node);

            // Left Left Case
            if (balance > 1 && stock.engineNumber.compareTo(node.left.data.engineNumber) < 0) {
                return rightRotate(node);
            }

            // Right Right Case
            if (balance < -1 && stock.engineNumber.compareTo(node.right.data.engineNumber) > 0) {
                return leftRotate(node);
            }

            // Left Right Case
            if (balance > 1 && stock.engineNumber.compareTo(node.left.data.engineNumber) > 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && stock.engineNumber.compareTo(node.right.data.engineNumber) < 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            // Return the unchanged node pointer
            return node;
        }

        // Search method remains the same
        public BSTNode search(String engineNumber) {
            return searchRec(root, engineNumber);
        }

        private BSTNode searchRec(BSTNode root, String engineNumber) {
            // Base case: root is null or key is present at root
            if (root == null || root.data.engineNumber.equals(engineNumber)) {
                return root;
            }

            // Key is greater than root's key
            if (root.data.engineNumber.compareTo(engineNumber) < 0) {
                return searchRec(root.right, engineNumber);
            }

            // Key is less than root's key
            return searchRec(root.left, engineNumber);
        }

        // Get node with minimum value
        private BSTNode minValueNode(BSTNode node) {
            BSTNode current = node;

            // Find the leftmost leaf
            while (current.left != null) {
                current = current.left;
            }

            return current;
        }

        // Remove a node from AVL Tree
        public void remove(Stock stock) {
            root = removeRec(root, stock.engineNumber);
        }

        private BSTNode removeRec(BSTNode root, String engineNumber) {
            // 1. Perform standard BST delete
            if (root == null) {
                return root;
            }

            // Traverse the tree
            if (engineNumber.compareTo(root.data.engineNumber) < 0) {
                root.left = removeRec(root.left, engineNumber);
            } else if (engineNumber.compareTo(root.data.engineNumber) > 0) {
                root.right = removeRec(root.right, engineNumber);
            } else {
                // Node with only one child or no child
                if (root.left == null || root.right == null) {
                    BSTNode temp = (root.left != null) ? root.left : root.right;

                    // No child case
                    if (temp == null) {
                        temp = root;
                        root = null;
                    } else { // One child case
                        root = temp; // Copy the contents of non-empty child
                    }
                } else {
                    // Node with two children: Get the inorder successor
                    BSTNode temp = minValueNode(root.right);

                    // Copy the inorder successor's data to this node
                    root.data = temp.data;

                    // Delete the inorder successor
                    root.right = removeRec(root.right, temp.data.engineNumber);
                }
            }

            // If the tree had only one node then return
            if (root == null) {
                return root;
            }

            // 2. Update height of the current node
            root.height = Math.max(height(root.left), height(root.right)) + 1;

            // 3. Get the balance factor
            int balance = getBalance(root);

            // Left Left Case
            if (balance > 1 && getBalance(root.left) >= 0) {
                return rightRotate(root);
            }

            // Left Right Case
            if (balance > 1 && getBalance(root.left) < 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }

            // Right Right Case
            if (balance < -1 && getBalance(root.right) <= 0) {
                return leftRotate(root);
            }

            // Right Left Case
            if (balance < -1 && getBalance(root.right) > 0) {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }

            return root;
        }

        // In-order traversal to collect all stocks
        public List<Stock> getAllStocks() {
            List<Stock> stocks = new ArrayList<>();
            inOrderTraversal(root, stocks);
            return stocks;
        }

        private void inOrderTraversal(BSTNode node, List<Stock> stocks) {
            if (node != null) {
                inOrderTraversal(node.left, stocks);
                stocks.add(node.data);
                inOrderTraversal(node.right, stocks);
            }
        }

        // Check if engine number exists
        public boolean containsEngineNumber(String engineNumber) {
            return search(engineNumber) != null;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // AVL Tree for inventory (non-linear data structure with improved efficiency)
        InventoryBST inventoryBST = new InventoryBST();

        // HashMap for brand-based filtering (non-linear data structure)
        HashMap<String, List<Stock>> brandMap = new HashMap<>();

        // Stack for undo functionality
        Stack<Stock> undoStack = new Stack<>();

        // Load initial data from the embedded CSV data using the InventoryData class
        loadInventoryFromString(inventoryBST, brandMap);

        while (true) {
            System.out.println("\nMotorPH Inventory System");
            System.out.println("1. Add New Stock");
            System.out.println("2. Manage Stocks (Delete/Undo)");
            System.out.println("3. Sort, Search, and Count by Brand");
            System.out.println("4. Display Inventory and Summary");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Add new stock with system date as default
                    String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date()); // Get system date
                    String engineNumber;
                    boolean exists;

                    // Validate engine number first
                    do {
                        System.out.print("Enter Engine Number: ");
                        engineNumber = scanner.nextLine();

                        // Check if engine number already exists using AVL Tree
                        exists = inventoryBST.containsEngineNumber(engineNumber);

                        if (exists) {
                            System.out.println("Engine Number already exists. Try again or return to menu.");
                            System.out.print("Do you want to enter a new Engine Number? (yes/no): ");
                            String response = scanner.nextLine().trim().toLowerCase();
                            if (!response.equals("yes")) {
                                break; // Break out of the do-while loop
                            }
                        }
                    } while (exists);

                    if (exists) break; // Return to menu if user doesn't want to re-enter

                    // Proceed to enter brand after validation
                    System.out.print("Enter Brand: ");
                    String brand = scanner.nextLine().toLowerCase(); // Convert to lowercase

                    // Assign default values
                    String status = "New";
                    String purchaseStatus = "On-Hand";

                    // Create new stock entry
                    Stock newStock = new Stock(date, status, brand, engineNumber, purchaseStatus);

                    // Add to inventory AVL Tree
                    inventoryBST.insert(newStock);

                    // Add to brand map
                    if (!brandMap.containsKey(brand)) {
                        brandMap.put(brand, new ArrayList<>());
                    }
                    brandMap.get(brand).add(newStock);

                    // Push to undo stack
                    undoStack.push(newStock);

                    System.out.println("Stock added successfully! Details: " + newStock);
                    break;

                case 2:
                    // Manage stocks (delete or undo last added stock)
                    while (true) {
                        System.out.println("1. Delete Stock");
                        System.out.println("2. Undo Last Added Stock");
                        System.out.print("Choose an option: ");
                        int manageChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (manageChoice == 1) {
                            // Delete stock by engine number with validation
                            while (true) {
                                System.out.print("Enter Engine Number to Delete: ");
                                String engineToDelete = scanner.nextLine();

                                // Find the stock using AVL Tree
                                BSTNode stockNode = inventoryBST.search(engineToDelete);

                                if (stockNode != null) {
                                    Stock stockToDelete = stockNode.data;

                                    if (stockToDelete.status.equals("Old") && stockToDelete.purchaseStatus.equals("Sold")) {
                                        System.out.println("Are you sure you want to delete this stock? " + stockToDelete + " (yes/no): ");
                                        String confirmation = scanner.nextLine().trim().toLowerCase();
                                        if (confirmation.equals("yes")) {
                                            // Remove from AVL Tree
                                            inventoryBST.remove(stockToDelete);

                                            // Remove from brand map
                                            if (brandMap.containsKey(stockToDelete.brand)) {
                                                brandMap.get(stockToDelete.brand).remove(stockToDelete);

                                                // If the brand has no more stocks, remove it from the brand map
                                                if (brandMap.get(stockToDelete.brand).isEmpty()) {
                                                    brandMap.remove(stockToDelete.brand);
                                                    System.out.println("Brand '" + stockToDelete.brand + "' has been removed from the inventory as it has no more stocks.");
                                                }
                                            }

                                            System.out.println("Stock deleted successfully.");
                                        } else {
                                            System.out.println("Stock deletion canceled.");
                                        }
                                    } else {
                                        System.out.println("Only 'Old' and 'Sold' stocks can be deleted.");
                                    }
                                } else {
                                    System.out.println("No stock found with the given engine number. Please enter a valid engine number.\n");
                                }

                                // Ask if the user wants to delete another stock
                                System.out.print("Do you want to delete another stock? (yes/no): ");
                                String deleteAnother = scanner.nextLine().trim().toLowerCase();
                                if (!deleteAnother.equals("yes")) {
                                    break; // Exit delete loop
                                }
                            }
                        } else if (manageChoice == 2) {
                            // Undo last added stock
                            if (!undoStack.isEmpty()) {
                                Stock lastAdded = undoStack.pop();

                                // Remove from AVL Tree
                                inventoryBST.remove(lastAdded);

                                // Remove from brand map
                                if (brandMap.containsKey(lastAdded.brand)) {
                                    brandMap.get(lastAdded.brand).remove(lastAdded);

                                    // If the brand has no more stocks, remove it from the brand map
                                    if (brandMap.get(lastAdded.brand).isEmpty()) {
                                        brandMap.remove(lastAdded.brand);
                                        System.out.println("Brand '" + lastAdded.brand + "' has been removed from the inventory as it has no more stocks.");
                                    }
                                }

                                System.out.println("Last added stock has been undone:");
                                System.out.println(lastAdded);
                            } else {
                                System.out.println("No actions to undo.");
                            }
                        } else {
                            System.out.println("Invalid option. Returning to main menu.");
                        }
                        break;
                    }
                    break;

                case 3:
                    handleBrandSearchAndCount(scanner, brandMap, inventoryBST);
                    break;

                case 4:
                    // Display inventory and summary
                    List<Stock> allStocks = inventoryBST.getAllStocks();
                    if (allStocks.isEmpty()) {
                        System.out.println("Inventory is empty.");
                    } else {
                        // Sort by date before displaying
                        sortByDate(allStocks);
                        displayInventory(allStocks);
                        displaySummary(allStocks);
                    }
                    break;

                case 5:
                    // Exit program
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void handleBrandSearchAndCount(Scanner scanner, HashMap<String, List<Stock>> brandMap, InventoryBST inventoryBST) {
        boolean continueSearching = true;

        while (continueSearching) {
            // Sort by Brand using HashMap (non-linear DS)
            System.out.println("\nSort by Brand:");

            // Using TreeSet to maintain sorted order of brands (non-linear DS)
            TreeSet<String> brands = new TreeSet<>(brandMap.keySet());

            if (brands.isEmpty()) {
                System.out.println("No brands available in the inventory.");
                return;
            }

            int i = 1;
            // Convert TreeSet to Array for indexed access
            String[] brandArray = brands.toArray(new String[0]);
            for (String brandName : brandArray) {
                // Capitalize the first letter for display
                String displayBrand = brandName.substring(0, 1).toUpperCase() + brandName.substring(1);
                System.out.println(i + ". " + displayBrand);
                i++;
            }

            // Prompt for brand number until a valid one is entered
            int brandIndex;
            while (true) {
                System.out.print("Enter Brand Number: ");
                brandIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (brandIndex > 0 && brandIndex <= brandArray.length) {
                    break; // Valid brand number entered
                } else {
                    System.out.println("Invalid selection. Please enter a valid brand number.");
                }
            }

            String filterBrand = brandArray[brandIndex - 1].toLowerCase(); // Convert to lowercase

            // Get filtered stocks using HashMap (O(1) access)
            List<Stock> filtered = brandMap.getOrDefault(filterBrand, new ArrayList<>());

            // Sort the filtered list by date before displaying
            sortByDate(filtered);

            System.out.println("\nStocks for brand: " + filterBrand);
            displayInventory(filtered);
            System.out.println("Total: " + filtered.size());

            int onHandCount = 0, soldCount = 0;
            for (Stock stock : filtered) {
                if (stock.purchaseStatus.equalsIgnoreCase("On-hand")) {
                    onHandCount++;
                } else if (stock.purchaseStatus.equalsIgnoreCase("Sold")) {
                    soldCount++;
                }
            }
            System.out.println("On-hand: " + onHandCount);
            System.out.println("Sold: " + soldCount);

            // Search by Engine Number within the selected brand
            System.out.print("\nDo you want to search for a specific engine number? (yes/no): ");
            String searchChoice = scanner.nextLine().trim().toLowerCase();

            if (searchChoice.equals("yes")) {
                searchByEngineNumber(scanner, filtered); // Pass the filtered list
            }

            // Ask if the user wants to search another brand
            System.out.print("Do you want to search another brand? (yes/no): ");
            String continueChoice = scanner.nextLine().trim().toLowerCase();
            continueSearching = continueChoice.equals("yes");
        }
    }

    public static void searchByEngineNumber(Scanner scanner, List<Stock> filtered) {
        boolean continueSearch = true;

        while (continueSearch) {
            System.out.print("Enter Engine Number to Search: ");
            String searchEngine = scanner.nextLine();

            // Search within the filtered list (current brand)
            boolean found = false;
            for (Stock stock : filtered) {
                if (stock.engineNumber.equals(searchEngine)) {
                    System.out.println("Stock found: " + stock);
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Engine number does not exist.");
            }

            System.out.print("Do you want to search another engine number? (yes/no): ");
            String tryAgain = scanner.nextLine().trim().toLowerCase();
            continueSearch = tryAgain.equals("yes");
        }
    }

    // Load inventory from embedded CSV data
    public static void loadInventoryFromString(InventoryBST inventoryBST, HashMap<String, List<Stock>> brandMap) {
        try (BufferedReader br = new BufferedReader(InventoryData.getCSVAsReader())) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) { // Ensure the row has exactly 5 columns
                    Stock stock = new Stock(data[0], data[1], data[2].toLowerCase(), data[3], data[4]); // Convert brand to lowercase

                    // Add to AVL Tree
                    inventoryBST.insert(stock);

                    // Add to brand map
                    if (!brandMap.containsKey(stock.brand)) {
                        brandMap.put(stock.brand, new ArrayList<>());
                    }
                    brandMap.get(stock.brand).add(stock);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV data: " + e.getMessage());
        }
    }

    public static void sortByDate(List<Stock> stocks) {
        Collections.sort(stocks, new Comparator<Stock>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

            @Override
            public int compare(Stock s1, Stock s2) {
                try {
                    Date date1 = dateFormat.parse(s1.dateEntered);
                    Date date2 = dateFormat.parse(s2.dateEntered);
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    // In case of parsing error, fall back to string comparison
                    return s1.dateEntered.compareTo(s2.dateEntered);
                }
            }
        });
    }

    // Display inventory with numbering
    public static void displayInventory(List<Stock> inventory) {
        int index = 1;
        for (Stock stock : inventory) {
            System.out.println(index + ". " + stock);
            index++;
        }
    }

    // Display inventory summary
    public static void displaySummary(List<Stock> inventory) {
        int onHandCount = 0, soldCount = 0;

        // Count On-hand and Sold stocks
        for (Stock stock : inventory) {
            if (stock.purchaseStatus.equalsIgnoreCase("On-hand")) {
                onHandCount++;
            } else if (stock.purchaseStatus.equalsIgnoreCase("Sold")) {
                soldCount++;
            }
        }

        System.out.println(" Summary:");
        System.out.println("Total Stocks: " + inventory.size());
        System.out.println("On-hand: " + onHandCount);
        System.out.println("Sold: " + soldCount);
    }
}
