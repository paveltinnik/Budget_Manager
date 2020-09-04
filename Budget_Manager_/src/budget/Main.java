package budget;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main extends Perform {
    public static void main(String[] args) {
        showStartMenu();
    }
}

// Class that define name and price of purchase
class Purchase {
    private String purchaseName;
    private double purchasePrice;

    public Purchase(String purchaseName, double price) {
        this.purchaseName = purchaseName;
        this.purchasePrice = price;
    }

    public String getPurchaseName() {
        return purchaseName;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
}

// Class that contains balance
class Balance {
    private static double balance = 0;

    public Balance(double balance) {
        Balance.balance = balance;
    }

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        Balance.balance = balance;
    }
}

class Perform {
    static String path = "purchases.txt";

    public static List<String> listOfPurchases = new ArrayList<>();

    public static List<Purchase> foodPurchases = new ArrayList<>();
    public static List<Purchase> clothesPurchases = new ArrayList<>();
    public static List<Purchase> entertainmentPurchases = new ArrayList<>();
    public static List<Purchase> otherPurchases = new ArrayList<>();
    public static List<Purchase> allPurchases = new ArrayList<>();

    private static boolean isExit = false;
    public static boolean exitFromChildMenu = false;

    static DecimalFormat decFormat = new DecimalFormat("#.00");

    static Scanner scanner = new Scanner(System.in);

    public static void showStartMenu() {
        System.out.println("Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit");

        while (!isExit) {
            switch (scanner.nextLine()) {
                case "1":
                    addIncome();
                    break;
                case "2":
                    Purchases.showPurchaseMenu();
                    break;
                case "3":
                    ListOfPurchases.showListOfPurchaseMenu();
                    break;
                case "4":
                    showBalance();
                    break;
                case "5":
                    Save.save();
                    break;
                case "6":
                    Load.load();
                    break;
                case "7":
                    Analyze.showAnalyzeMenu();
                    break;
                case "0":
                    exit();
                    break;
            }
        }
    }

    public static void addIncome() {
        System.out.println("\nEnter income:");
        try {
            Balance.setBalance(Double.parseDouble(scanner.next().replaceAll(",", ".")));
            System.out.println("Income was added!\n");
        } catch (Throwable e) {
            System.out.println("Incorrect input!");
        }
        showStartMenu();
    }

    public static void showBalance() {
        System.out.println("\nBalance: $" + Balance.getBalance() + "\n");
        showStartMenu();
    }

    public static void back() {
        exitFromChildMenu = true;
        System.out.println();
        showStartMenu();
    }

    public static void exit() {
        isExit = true;
        System.out.print("\nBye!\n");
    }
}

class Purchases extends Perform {

    public static void showPurchaseMenu() {
        exitFromChildMenu = false;

        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back");

        while (!exitFromChildMenu) {
            switch (scanner.next()) {
                case "1":
                    addFoodPurchase();
                    break;
                case "2":
                    addClothesPurchase();
                    break;
                case "3":
                    addEntertainmentPurchase();
                    break;
                case "4":
                    addOtherPurchase();
                    break;
                case "5":
                    back();
                    break;
            }
        }
    }

    private static void addFoodPurchase() {

        Purchase foodPurchase = addToListPurchase();
        foodPurchases.add(foodPurchase);

        showPurchaseMenu();
    }

    private static void addClothesPurchase() {

        Purchase clothesPurchase = addToListPurchase();
        clothesPurchases.add(clothesPurchase);

        showPurchaseMenu();
    }

    private static void addEntertainmentPurchase() {

        Purchase entertainmentPurchase = addToListPurchase();
        entertainmentPurchases.add(entertainmentPurchase);

        showPurchaseMenu();
    }

    private static void addOtherPurchase() {

        Purchase otherPurchase = addToListPurchase();
        otherPurchases.add(otherPurchase);
        showPurchaseMenu();
    }

    public static Purchase addToListPurchase() {
        System.out.println();
        scanner.nextLine();

        System.out.println("Enter purchase name: ");
        String purchaseName = scanner.nextLine();

        System.out.println("Enter its price: ");
        String purchasePrice = scanner.next().replaceAll(",", ".");
        System.out.println("Purchase was added!");

        Balance.setBalance(Balance.getBalance() - Double.parseDouble(purchasePrice));

        Purchase purchase = new Purchase(purchaseName, Double.parseDouble(purchasePrice));

        allPurchases.add(purchase);

        return purchase;
    }
}

class ListOfPurchases extends Purchases {

    private static double sum;

    public static void showListOfPurchaseMenu() {

        exitFromChildMenu = false;

        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back");

        while (!exitFromChildMenu) {
            switch (scanner.nextLine()) {
                case "1":
                    showListOfFoodPurchases();
                    break;
                case "2":
                    showListOfClothesPurchases();
                    break;
                case "3":
                    showListOfEntertainmentPurchases();
                    break;
                case "4":
                    showListOfOtherPurchases();
                    break;
                case "5":
                    showListOfAllPurchases();
                    break;
                case "6":
                    back();
                    break;
            }
        }
    }

    private static void showListOfFoodPurchases() {
        System.out.println("\nFood:");
        printListOfPurchaseMenu(foodPurchases);
        showListOfPurchaseMenu();
    }

    private static void showListOfClothesPurchases() {
        System.out.println("\nClothes:");
        printListOfPurchaseMenu(clothesPurchases);
        showListOfPurchaseMenu();
    }

    private static void showListOfEntertainmentPurchases() {
        System.out.println("\nEntertainment:");
        printListOfPurchaseMenu(entertainmentPurchases);
        showListOfPurchaseMenu();
    }

    private static void showListOfOtherPurchases() {
        System.out.println("\nOther:");
        printListOfPurchaseMenu(otherPurchases);
        showListOfPurchaseMenu();
    }

    private static void showListOfAllPurchases() {
        System.out.println("\nAll:");
        printListOfPurchaseMenu(allPurchases);
        showListOfPurchaseMenu();
    }

    // Print all purchases of some type and total sum
    public static void printListOfPurchaseMenu(List<Purchase> list) {
        if (list.size() == 0) {

            System.out.println("\nPurchase list is empty\n");

        } else {

            for (Purchase purchase : list) {
                System.out.println(purchase.getPurchaseName() + " $" + decFormat.format(purchase.getPurchasePrice())
                        .replaceAll(",", "."));
            }
            System.out.print("Total sum: $" + decFormat.format(countTotalSum(list)).replaceAll(",", ".") + "\n");
            sum = 0;
        }
    }

    // Count total sum of purchase list
    public static double countTotalSum(List<Purchase> list) {
        sum = 0;
        for (Purchase purchase : list) {
            sum += purchase.getPurchasePrice();
        }
        return sum;
    }
}

class Save extends Perform {
    public static void save() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            String allList = saveAllList().toString();
            writer.write(allList);
            writer.close();

            allPurchases.clear();
            foodPurchases.clear();
            clothesPurchases.clear();
            entertainmentPurchases.clear();
            otherPurchases.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\nPurchases were saved!\n");
        showStartMenu();
    }

    // Define all purchases and balance as string
    private static StringBuilder saveAllList() {

        StringBuilder allList = new StringBuilder();

        allList.append(saveList(foodPurchases, "Food:")).
                append(saveList(clothesPurchases, "\nClothes:")).
                append(saveList(entertainmentPurchases, "\nEntertainment:")).
                append(saveList(otherPurchases, "\nOther:")).
                append("\nBalance:\n").append(Balance.getBalance());
        return allList;
    }

    private static String saveList(List<Purchase> list, String type) {
        StringBuilder purchaseList = new StringBuilder(type);
        for (Purchase val : list) {
            purchaseList.append("\n").append(val.getPurchaseName()).append("<>").append(val.getPurchasePrice());
        }
        return purchaseList.toString();
    }
}

class Load extends Perform {
    public static void load() {

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            Scanner loadScanner = new Scanner(reader);

            // Clear allPurchases list
            allPurchases.clear();

            while (loadScanner.hasNext()) {

                //Reading data to one list that consists all types, purchases and balance
                listOfPurchases.add(loadScanner.nextLine());
            }
            reader.close();

            // Set balance
            Balance.setBalance(Double.parseDouble(listOfPurchases.get(listOfPurchases.size() - 1)));

            // Load purchases to every list and to allPurchases
            loadToList("Food:", foodPurchases);
            loadToList("Clothes:", clothesPurchases);
            loadToList("Entertainment:", entertainmentPurchases);
            loadToList("Other:", otherPurchases);

            System.out.println("\nPurchases were loaded!\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        showStartMenu();
    }

    private static void loadToList(String type, List<Purchase> list) {
        Purchase purchase;
        String[] data;

        // Clear list when loading old purchases
        list.clear();

        for (int i = listOfPurchases.indexOf(type) + 1; i < listOfPurchases.size() - 1; i++) {

            if (listOfPurchases.get(i).contains("<>")) {

                data = listOfPurchases.get(i).split("<>");

                purchase = new Purchase(data[0], Double.parseDouble(data[1]));

                allPurchases.add(purchase);
                list.add(purchase);

            } else {
                break;
            }
        }
    }
}

class Analyze extends Perform {

    public static void showAnalyzeMenu() {
        exitFromChildMenu = false;

        System.out.println("\nHow do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back");

        while (!exitFromChildMenu) {
            switch (scanner.next()) {
                case "1":
                    sortAllPurchases();
                    break;
                case "2":
                    sortByType();
                    break;
                case "3":
                    showSortCertainTypeMenu();
                    break;
                case "4":
                    back();
                    break;
            }
        }
    }

    private static void sortAllPurchases() {
        System.out.println("\n");
        ListOfPurchases.printListOfPurchaseMenu(bubbleSort(allPurchases));
        showAnalyzeMenu();
    }

    private static void sortByType() {

        System.out.println("\nTypes:");

        // Creating types of purchases and their total sum
        Purchase foodTotalSum = new Purchase("Food -", ListOfPurchases.countTotalSum(foodPurchases));
        Purchase clothesTotalSum = new Purchase("Clothes -", ListOfPurchases.countTotalSum(clothesPurchases));
        Purchase entertainmentTotalSum = new Purchase("Entertainment -", ListOfPurchases.countTotalSum(entertainmentPurchases));
        Purchase otherTotalSum = new Purchase("Other -", ListOfPurchases.countTotalSum(otherPurchases));

        List<Purchase> all = List.of(foodTotalSum, clothesTotalSum, entertainmentTotalSum, otherTotalSum);

        // Sort all purchase list and print it in order and print total sum
        ListOfPurchases.printListOfPurchaseMenu(bubbleSort(all));

        showAnalyzeMenu();
    }

    private static void showSortCertainTypeMenu() {
        exitFromChildMenu = false;

        System.out.println("\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");

        while (!exitFromChildMenu) {
            switch (scanner.next()) {
                case "1":
                    sortFood();
                    break;
                case "2":
                    sortClothes();
                    break;
                case "3":
                    sortEntertainment();
                    break;
                case "4":
                    sortOther();
                    break;
            }
        }
    }

    private static void sortFood() {
        if (foodPurchases.size() != 0) {
            System.out.println("\nFood:");
            ListOfPurchases.printListOfPurchaseMenu(bubbleSort(foodPurchases));
        } else {
            System.out.println("\nPurchase list is empty\n");
        }

        showAnalyzeMenu();
    }

    private static void sortClothes() {
        if (clothesPurchases.size() != 0) {
            System.out.println("\nClothes:");
            ListOfPurchases.printListOfPurchaseMenu(bubbleSort(clothesPurchases));
        } else {
            System.out.println("\nPurchase list is empty\n");
        }

        showAnalyzeMenu();
    }

    private static void sortEntertainment() {
        if (entertainmentPurchases.size() != 0) {
            System.out.println("\nEntertainment:");
            ListOfPurchases.printListOfPurchaseMenu(bubbleSort(entertainmentPurchases));
        } else {
            System.out.println("\nPurchase list is empty\n");
        }

        showAnalyzeMenu();
    }

    private static void sortOther() {
        if (otherPurchases.size() != 0) {
            System.out.println("\nOther:");
            ListOfPurchases.printListOfPurchaseMenu(bubbleSort(otherPurchases));
        } else {
            System.out.println("\nPurchase list is empty\n");
        }

        showAnalyzeMenu();
    }

    // Do bubble sort of some purchase list
    private static List<Purchase> bubbleSort(List<Purchase> list) {

        List<Purchase> sortedList = new ArrayList<>(list);

        if (sortedList.size() != 0) {

            for (int i = 0; i < sortedList.size() - 1; i++) {
                for (int j = 0; j < sortedList.size() - i - 1; j++) {

                    if (sortedList.get(j).getPurchasePrice() < sortedList.get(j + 1).getPurchasePrice()) {
                        Purchase temp = sortedList.get(j);
                        sortedList.set(j, sortedList.get(j + 1));
                        sortedList.set(j + 1, temp);
                    }
                }
            }
        }
        return sortedList;
    }
}
