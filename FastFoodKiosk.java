 import java.util.*;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;



public class FastFoodKiosk {



static Scanner sc = new Scanner(System.in);

static int queueNumber = 1;



public static void main(String[] args) {

while (true) {

System.out.println("=========== SELF-SERVICE KIOSK ===========");

System.out.println("1. Start Order");

System.out.println("2. Exit");

System.out.print("Choose option: ");



int mainChoice = sc.nextInt();



if (mainChoice == 1) {

orderFood();



System.out.print("Next customer? (Y/N): ");

char next = sc.next().charAt(0);



if (next == 'N' || next == 'n') {

System.out.println("System closed. Thank you, ENJOY YOUR MEAL!");

break;

}



} else if (mainChoice == 2) {

System.out.println("System closed. Thank you, COME AGAIN!");

break;

} else {

System.out.println("Invalid choice!");

}

}

}



static void orderFood() {

Map<String, Integer> menu = new LinkedHashMap<>();

Map<String, String> menuNames = new LinkedHashMap<>();



// DRINKS

menu.put("D1", 20);



menuNames.put("D1", "Soft Drink");



menu.put("D2", 25);

menuNames.put("D2", "Iced Tea");



menu.put("D3", 40);

menuNames.put("D3", "Milkshake");



// COMBO MEALS

menu.put("M1", 120);

menuNames.put("M1", "Burger Combo");



menu.put("M2", 150);

menuNames.put("M2", "Cheeseburger Combo");



menu.put("M3", 180);

menuNames.put("M3", "Double Burger Combo");



Map<String, Integer> orderItems = new LinkedHashMap<>();

boolean finished = false;



while (!finished) {

displayMenu();



System.out.print("Select option: ");

String choice = sc.next().toUpperCase();



if (menu.containsKey(choice)) {

System.out.print("Enter quantity: ");

int qty = sc.nextInt();



if (qty > 0) {

orderItems.put(choice, orderItems.getOrDefault(choice, 0) + qty);

System.out.println(qty + " " + menuNames.get(choice) + "(s) added.");

} else {

System.out.println("Invalid quantity!");

}



} else if (choice.equals("T")) {

System.out.println("Current Total: ₱" + calculateTotal(orderItems, menu));



} else if (choice.equals("F")) {

if (orderItems.isEmpty()) {

System.out.println("You have not ordered anything yet!");

} else {

finished = true;

}



} else {

System.out.println("Invalid choice!");

}

}



boolean confirmed = false;

while (!confirmed) {

System.out.println("\n=========== ORDER REVIEW ===========");

displayOrder(orderItems, menu, menuNames);

System.out.println("TOTAL: ₱" + calculateTotal(orderItems, menu));


System.out.println("\n1. Add More Items");

System.out.println("2. Remove Item");

System.out.println("3. Confirm Order");

System.out.print("Choose: ");



int reviewChoice = sc.nextInt();



switch (reviewChoice) {

case 1:

addMoreItems(orderItems, menu, menuNames);

break;

case 2:

removeItem(orderItems, menuNames);

break;

case 3:

if (!orderItems.isEmpty()) {

confirmed = true;

} else {

System.out.println("You have no items in your order.");

}

break;

default:

System.out.println("Invalid choice!");

}

}



System.out.println("ORDER CONFIRMED!");

printReceipt(orderItems, menu, menuNames);



System.out.println("Please wait for your order...");

queueNumber++;

}



static void displayMenu() {

System.out.println("===== MENU =====");



System.out.println("--- DRINKS ---");

System.out.println("D1 - Soft Drink - 20");

System.out.println("D2 - Iced Tea - 25");

System.out.println("D3 - Milkshake - 40");



System.out.println("--- COMBO MEALS ---");

System.out.println("M1 - Burger Combo - 120");

System.out.println("M2 - Cheeseburger Combo - 150");

System.out.println("M3 - Double Burger Combo - 180");



System.out.println("T - View Total");

System.out.println("F - Finish Order");

}



static void displayMenuNoExtras() {

System.out.println("===== MENU =====");



System.out.println("--- DRINKS ---");

System.out.println("D1 - Soft Drink - 20");

System.out.println("D2 - Iced Tea - 25");

System.out.println("D3 - Milkshake - 40");



System.out.println("--- COMBO MEALS ---");

System.out.println("M1 - Burger Combo - 120");

System.out.println("M2 - Cheeseburger Combo - 150");

System.out.println("M3 - Double Burger Combo - 180");

}



static void addMoreItems(Map<String, Integer> orderItems, Map<String, Integer> menu, Map<String, String> names) {

boolean adding = true;



while (adding) {

displayMenuNoExtras();

System.out.println("X - Back");

System.out.print("Select option: ");



String choice = sc.next().toUpperCase();



if (choice.equals("X")) {

adding = false;

} else if (menu.containsKey(choice)) {

System.out.print("Enter quantity: ");

int qty = sc.nextInt();



if (qty > 0) {

orderItems.put(choice, orderItems.getOrDefault(choice, 0) + qty);

System.out.println(qty + " " + names.get(choice) + "(s) added.");

} else {

System.out.println("Invalid quantity!");

}

} else {

System.out.println("Invalid choice!");

}

}

}



static int calculateTotal(Map<String, Integer> order, Map<String, Integer> menu) {

int total = 0;

for (Map.Entry<String, Integer> entry : order.entrySet()) {

total += entry.getValue() * menu.get(entry.getKey());

}

return total;

}



static void displayOrder(Map<String, Integer> order, Map<String, Integer> menu, Map<String, String> names) {

if (order.isEmpty()) {

System.out.println("No items ordered.");

return;

}



for (Map.Entry<String, Integer> entry : order.entrySet()) {

String code = entry.getKey();

int qty = entry.getValue();

int subtotal = qty * menu.get(code);

System.out.println(names.get(code) + " x" + qty + " = ₱" + subtotal);

}

}



static void removeItem(Map<String, Integer> order, Map<String, String> names) {

if (order.isEmpty()) {

System.out.println("No items to remove.");

return;

}



List<String> keys = new ArrayList<>(order.keySet());



System.out.println("\nSelect item to remove:");

for (int i = 0; i < keys.size(); i++) {

String key = keys.get(i);

System.out.println((i + 1) + ". " + names.get(key) + " x" + order.get(key));

}



System.out.print("Choice: ");

int choice = sc.nextInt();



if (choice >= 1 && choice <= keys.size()) {

String selectedKey = keys.get(choice - 1);

int currentQty = order.get(selectedKey);



System.out.println("Current quantity: " + currentQty);

System.out.print("Enter quantity to remove: ");

int removeQty = sc.nextInt();



if (removeQty <= 0) {

System.out.println("Invalid quantity.");

} else if (removeQty >= currentQty) {

order.remove(selectedKey);

System.out.println(names.get(selectedKey) + " completely removed.");

} else {

order.put(selectedKey, currentQty - removeQty);

System.out.println(removeQty + " " + names.get(selectedKey) + "(s) removed.");

}

} else {

System.out.println("Invalid choice.");

}

}



static void printReceipt(Map<String, Integer> order, Map<String, Integer> menu, Map<String, String> names) {

LocalDateTime now = LocalDateTime.now();

DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



int total = calculateTotal(order, menu);



System.out.println("=================================");

System.out.println(" RECEIPT ");

System.out.println("=================================");

System.out.println("Queue No. : #" + queueNumber);

System.out.println("Date/Time : " + now.format(formatter));

System.out.println("---------------------------------");



for (Map.Entry<String, Integer> entry : order.entrySet()) {

String code = entry.getKey();

int qty = entry.getValue();

int subtotal = qty * menu.get(code);

System.out.println(names.get(code) + " x" + qty + " = ₱" + subtotal);

}



System.out.println("---------------------------------");

System.out.println("TOTAL AMOUNT: ₱" + total);

System.out.println("=================================");

}



}