package helps;

import java.util.Scanner;

public class Console {

    public static Scanner console = new Scanner(System.in);

    public static String askString(String aPrompt) // method for string input
    {
        System.out.print(aPrompt); // aPrompt is a request for string input
        String reply = console.nextLine(); // input held in a variable called reply
        return reply;
    }

    public static int askInt(String aPrompt) // method for integer input
    {
        String reply = askString(aPrompt);
        return Integer.parseInt(reply); // parseInt converts string to integer
    }

    public static double askDouble(String aPrompt) // method for double input
    {
        String reply = askString(aPrompt);
        return Double.parseDouble(reply); // parseDouble converts string to double
    }
// method for one character input for menu options

    public static char askOption(String aMenu) {
        System.out.println(aMenu);
        String reply = askString("Enter option: ");
        if (reply.trim().length() == 0) // checks for null input
        {
            return '\0';
        } else // Take first character of string and convert to Uppercase
        {
            return Character.toUpperCase(reply.trim().charAt(0));
        }
    }
// method for one character input

    public static char askChar(String aChar) {
        String reply = askString(aChar);
        if (reply.trim().isEmpty()) {
            return (char) 0;
        }
// Take first character of string and convert to Uppercase
        return Character.toUpperCase(reply.trim().charAt(0));
    }

    public static void WriteLn(String msg) {
        System.out.println(msg);
    }

    public static void Write(String msg) {
        System.out.print(msg);
    }

    public static void clear() {
        System.out.print("\f");
    }

    public static void exit(int code) {
        System.exit(code);
    }
}
