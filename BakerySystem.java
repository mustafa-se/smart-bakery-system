import java.io.*;
import java.util.*;
import java.time.LocalDateTime; 

public class SmartBakerySystem  
{   
    public static void main(String[] args)  
    {          
        int lineCount = 0;         
        try 
        { 
            File file = new File("d:\\Products.txt");             
            Scanner in = new Scanner(file);             
            while(in.hasNextLine()) 
            {                 
                in.nextLine();                 
                lineCount++; 
            } 
        } 
        catch(FileNotFoundException e1) 
        { 
            System.out.println("Error loading the file: " + e1); 
        } 
         
        Scanner input = new Scanner(System.in);         
        String productName[] = new String[lineCount/3];        
        int productPrice[] = new int[lineCount/3];         
        int productstock[] = new int[lineCount/3]; 
         
        int index = 0; 
         
        //Store the data from file to arrays.         
        try 
        { 
            File file = new File("d:\\Products.txt");             
            Scanner in = new Scanner(file);             
            while(in.hasNext()) 
            { 
                productName[index] = in.next();                 
                productPrice[index] = in.nextInt();                 
                productstock[index] = in.nextInt();                 
                index++; 
            } 
        } 
        catch(FileNotFoundException e1) 
        { 
            System.out.println(e1.toString()); 
        } 
         
        //Main program.         
        boolean run = true;         
        while(run) 
        { 
            System.out.println("===Welcome to New Fresh Bake==="); 
            System.out.println("   1. Admin Portal"); 
            System.out.println("   2. Customer"); 
            System.out.println("   3. Exit"); 
            System.out.print("Select your choice: ");             
            int n = input.nextInt(); 
 
            switch(n) 
            {                 
                case 1: 
                { 
                    System.out.print("Username: ");   
                    // Check for the authentic admin 
                    String adminUsername = input.next(); 
                    System.out.print("Password: "); 
                    String adminPassword = input.next(); 
 
                    if(verifyLoginCredentials(adminUsername, adminPassword)) 
                    { 
                        System.out.println("=====Admin Portal====="); 
                        System.out.println("1. Change Credentials"); 
                        System.out.println("2. Manage Inventory");                                                 
                        System.out.print("Make your choice: ");                                                 
                        int make = input.nextInt();                                                 
                        switch(make) 
                        {                                                             
                            case 1:                                                              
                            {                                                                 
                                try 
                                { 
                                    FileOutputStream fos = new FileOutputStream("d:\\Credentials.txt");                                                                     
                                    PrintStream ps = new PrintStream(fos); 
                                     
                                    System.out.print("New Username:"); 
                                    String newUsername = input.next(); 
                                    System.out.print("New Password:"); 
                                    String newPassword = input.next(); 
 
                                    ps.println(newUsername);                                                                     
                                    ps.println(newPassword); 
                                } 
                                catch(FileNotFoundException e5) 
                                { 
                                    System.out.println("Error in loading file " + e5); 
                                }                                                                 
                                break; 
                            } 
                            case 2: 
                            { 
                                handleProductsInventory(productName, productPrice, productstock);                                                                 
                                try  
                                { 
                                    File file = new File("d:\\Products.txt"); 
                                    Scanner reload = new Scanner(file); 
                                    int count = 0;                                                                     
                                    while(reload.hasNextLine())  
                                    {                                                                         
                                        reload.nextLine();                                                                         
                                        count++; 
                                    } 
 
                                    productName = new String[count / 3];                                                                     
                                    productPrice = new int[count / 3];                                                                     
                                    productstock = new int[count / 3]; 
 
                                    Scanner reload2 = new Scanner(file);                                                                     
                                    int i = 0;                                                                     
                                    while(reload2.hasNext())  
                                    { 
                                        productName[i] = reload2.next();                                                                         
                                        productPrice[i] = reload2.nextInt();                                                                         
                                        productstock[i] = reload2.nextInt();                                                                         
                                        i++; 
                                    } 
                                }  
                                catch (FileNotFoundException e) 
                                { 
                                    System.out.println("Reload Failed: " + e); 
                                }                                                                 
                                break; 
                            }                             
                            default: 
                                System.out.println("Sorry. Invalid choice!"); 
                        }                       
                    }                     
                    else 
                        System.out.println("Invalid credentials!");                     
                    break; 
                }   
                case 2: 
                { 
                    showCustomerMenu(productName, productPrice, productstock); 
                    //Customer panel                     
                    String cartItems[] = new String[productName.length];                     
                    int itemPrices[] = new int[productName.length];                     
                    int itemQuantities[] = new int[productName.length];                     
                    int itemTotals[] = new int[productName.length]; 
                    int cartSize = cart(productName, productPrice, productstock, cartItems, itemPrices, itemQuantities, itemTotals); 
                    System.out.println();                     
                    generateReceipt(cartItems, itemPrices, itemQuantities, itemTotals, cartSize);                     
                    break; 
                }  
                case 3: 
                { 
                    System.out.println("Thanks for visiting! GoodBye.");                     
                    run = false;                     
                    break; 
                }                 
                default: 
                { 
                    System.out.println("Invalid Option! Please Try Again"); 
                } 
            } 
        } 
    } 
     
    public static void showCustomerMenu(String name[], int price[], int stocks[]) 
    { 
        System.out.println("\n----------------Products Menu------------------"); 
        System.out.printf("%-5s %-15s %-10s %-10s\n", "No.", "Name", "Price(Rs)", "Quantity");         
        System.out.println("-------------------------------------------");         
        for(int i = 0; i < name.length; i++) 
        { 
            System.out.printf("%-5d %-15s %-10d %-10d%n", (i + 1), name[i], price[i], stocks[i]); 
        }            
    } 
     
    public static boolean verifyLoginCredentials(String adminUsername, String adminPassword) 
    { 
        String fixedUsername = "";         
        String fixedPassword = "";         
        try 
        { 
            File file = new File("d:\\Credentials.txt");             
            Scanner read = new Scanner(file);             
            while(read.hasNext()) 
            { 
                fixedUsername = read.next();                 
                fixedPassword = read.next(); 
            }     
        } 
        catch(FileNotFoundException e4) 
        { 
            System.out.println("Error in loading file " + e4); 
        } 
        return fixedUsername.equals(adminUsername) && fixedPassword.equals(adminPassword); 
    } 
     
    public static void handleProductsInventory(String name[], int price[], int quantity[]) 
    { 
        //Main Panel 
        Scanner input = new Scanner(System.in); 
        System.out.println("\n---Inventory Management---"); 
        System.out.println("1. Update Prices."); 
        System.out.println("2. Update Stock."); 
        System.out.println("3. Add new product.");         
        System.out.print("Your Choice: ");         
        int c = input.nextInt(); 
         
        switch(c) 
        { 
            case 1: 
            { 
                showCustomerMenu(name, price, quantity);                
                while(true) 
                { 
                    //Update only selected product price 
                    System.out.print("Enter the index of product you want to update(0 to stop): ");                     
                    int n = input.nextInt();                     
                    if(n == 0)                         
                        break; 
                    if(n >= 1 && n <= name.length) 
                    { 
                        System.out.print("New price for " + name[n - 1] + ": ");                         
                        price[n - 1] = input.nextInt(); 
                    }                     
                    else 
                    { 
                        System.out.println("Invalid choice. try Again!"); 
                    }     
                } 
                System.out.println("Prices updated Successfully!");                 
                break; 
            }             
            case 2: 
            { 
                //Update only selected product stock                 
                showCustomerMenu(name, price, quantity);                 
                while(true) 
                { 
                    System.out.print("Enter the product index to add stock(0 to stop): ");                     
                    int n = input.nextInt();                     
                    if(n == 0)                         
                        break; 
                    if(n >= 1 && n <= name.length) 
                    { 
                        System.out.print("Quantity of " + name[n - 1] + " to add: ");                         
                        int qty = input.nextInt();                         
                        if(qty >= 0) 
                        { 
                            quantity[n - 1] += qty; 
                            System.out.println("Stock of " + name[n - 1] + " updated!"); 
                        }                         
                        else 
                        { 
                            System.out.println("Cannot add negative quantity. Skipping the stock of " + quantity[n - 1]); 
                        }                    
                    } 
                    else 
                    { 
                        System.out.println("Invalid index!"); 
                    } 
                     
                } 
                System.out.println("Stock updated Successfully!");                 
                break; 
            }                                      
            case 3:             
            { 
                //Adding new product  by creating new arrays and saving them into file. 
                System.out.print("Product name: "); 
                String newProduct = input.next();                 
                System.out.print("Price: ");                 
                int newPrice = input.nextInt();                 
                System.out.print("Stock: ");                 
                int newStock = input.nextInt(); 
                 
                String[] newNames = new String[name.length + 1];                 
                int[] newPrices = new int[price.length + 1];                 
                int[] newQuantities = new int[quantity.length + 1]; 
                 
                for (int i = 0; i < name.length; i++) 
                { 
                    newNames[i] = name[i];                     
                    newPrices[i] = price[i];                     
                    newQuantities[i] = quantity[i]; 
                } 
                 
                newNames[name.length] = newProduct;                 
                newPrices[price.length] = newPrice;                 
                newQuantities[quantity.length] = newStock; 
                 
                name = newNames;                 
                price = newPrices;                 
                quantity = newQuantities;                
                System.out.println("New product added successfully!"); 
                break; 
            }             
            default: 
                System.out.println("Invalid choice!"); 
        }         
        try 
        { 
            FileOutputStream fos = new FileOutputStream("d:\\Products.txt");             
            PrintStream ps = new PrintStream(fos); 
             
            for(int j = 0; j < name.length; j++) 
            {                 
                ps.println(name[j]);                 
                ps.println(price[j]);                 
                ps.println(quantity[j]); 
            } 
        } 
        catch(FileNotFoundException e2) 
        { 
            System.out.println(e2.toString()); 
        } 
    } 
     
    public static int cart(String name[], int price[], int stock[], String cartItems[], int itemPrices[], int itemQuantities[], int itemTotals[]) 
    { 
        //Adding customer choice into cart Arrays and reducing stock in file.         
        Scanner input = new Scanner(System.in);         
        int cartIndex = 0; 
         
        while(true) 
        { 
            System.out.print("Select Product to add(0 for checkout): ");             
            int option = input.nextInt();             
            if(option == 0)                 
                break;             
            int index = option - 1;             
            if(option >= 1 && option <= name.length) 
            { 
                System.out.print("Quantity you want to buy: ");                 
                int qty = input.nextInt();                 
                if(qty > 0) 
                { 
                    if(cartIndex >= name.length) 
                    { 
                        System.out.println("Maximum cart limit reached!");                         
                        break; 
                    }                     
                    if(qty <= stock[index]) 
                    { 
                        cartItems[cartIndex] = name[index];                           
                        itemPrices[cartIndex] = price[index];                         
                        itemQuantities[cartIndex] = qty;                         
                        stock[index] -= qty;                         
                        itemTotals[cartIndex] = qty * price[index];                        
                        cartIndex++; 
                    }                     
                    else 
                    { 
                        System.out.println("Insufficient quantity only " + stock[index] + " items are remaining!"); 
                    }                 
                }                     
                else 
                { 
                    System.out.println("Quantity must be greater than zero!"); 
                }             
            }             
            else 
            { 
                System.out.println("Invalid product choice!"); 
            }     
        }                    
        try 
        { 
            FileOutputStream fos = new FileOutputStream("d:\\Products.txt");             
            PrintStream ps = new PrintStream(fos); 
             
            for(int i = 0; i < name.length; i++) 
            {                 
                ps.println(name[i]);                 
                ps.println(price[i]);                 
                ps.println(stock[i]); 
            } 
        } 
        catch(FileNotFoundException e3) 
        { 
            System.out.println(e3.toString()); 
        } 
         
        return cartIndex; 
    } 
     
    public static void generateReceipt(String cartIems[], int itemPrices[], int itemQunatities[], int itemTotals[], int cartSize) 
    { 
        int total = 0; 
        System.out.println("===================Customer     Receipt:    New Bake=========="); 
        System.out.println(); 
        System.out.println("Date & Time: " + LocalDateTime.now()); 
        System.out.printf("%-20s %-10s %-10s %-10s\n", "items", "price", "qty", "SubTotal");        
        System.out.println("----------------------------------------------------");         
        for(int i = 0; i < cartSize; i++) 
        {   
            System.out.printf("%-20s    Rs.%-8d     %-10d %8d%n", cartIems[i], itemPrices[i], itemQunatities[i], itemTotals[i]);            
            total += itemTotals[i]; 
        }   
        System.out.println("===================================================="); 
        System.out.println("                     Total Payable Amount = " + total); 
        System.out.println("--------------Thanks For Shopping-------------------"); 
        System.out.println("===================================================="); 
        System.out.println("====================================================");      
    }   
}