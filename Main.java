/**
 *  This class will load a dictionary and translate english words to pirate speak
 *
 *  @author Nicholas Zidel
 *  CS1121, Fall 2017
 *
 *  Date Last Modified: 09/17/2017
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PirateTalk {

    // You must use these two arrays to store your pirate dictionary data.
    private String [ ] englishWords = null; // VARIABLE DESCRIPTION
    private String [ ] pirateWords  = null; // VARIABLE DESCRIPTION

    /**
     * This method will load the dictionary file and put the english word and translated word into a dictionary
     *
     * @param The parameters for this method is a string for the file name
     *
     * @return This will return a boolean, true if the file loaded and false if it did not.
     */
    public boolean loadDictionary( String filename ) {
        boolean loaded = false;
        try(Scanner scan = new Scanner(new File(filename))) {
            if (scan.hasNextInt()) {
                int dictLength = scan.nextInt();
                englishWords = new String[dictLength + 1];
                pirateWords = new String[dictLength + 1];
            }
            int wordNum = 0;
            while (scan.hasNextLine()) {
                String translation = scan.nextLine();
                int colon = translation.indexOf(":");
                if (colon != -1) {
                    englishWords[wordNum] = translation.substring(0, colon);
                    pirateWords[wordNum] = translation.substring(colon + 1);
                }

                wordNum++;
            }
            loaded = true;
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return loaded;
    } // END OF METHOD

    /**
     * This is the method that will translate english words to pirate speak
     *
     * @param This will take in a word
     *
     * @return It will return a translated word
     */
    public String translateWord( String word ) {
        String translation = word;
        boolean translated = false;

        if (word == null) {
            return "Splice the mainbrace!";
        }

        if (word.isEmpty()) {
            return "Blimey!";
        }

        for (int i = 0; i < englishWords.length; i++) {
            if (englishWords[i] != null) {
                if (englishWords[i].equalsIgnoreCase(word)) {
                    translation = pirateWords[i];
                    translated = true;
                }
            }
        }

        if (word.substring(0, 1).equals(word.substring(0, 1).toUpperCase())) {
            translation = translation.substring(0, 1).toUpperCase() + translation.substring(1);
        }

        if (!translated) {
            for (int i = 0; i < word.length(); i++) {
                if (word.substring(i, i + 1).equalsIgnoreCase("v")) {
                    translation = word.substring(0, i) + "'" + word.substring(i + 1);
                }
            }
            if (word.length() >= 3) {
                if (word.contains("ing")) {
                    translation = (word.substring(0, word.length() - 1) + "'");
                }
            }
        }
        return translation;

    } // END OF METHOD

    /**
     * Translate passage will take an passage and translate any words that need translating
     *
     * @param It will take in a passage
     *
     * @return It will output a translation of the passage
     */
    public String translatePassage( String passage ) {
        if (passage == null) {
            return "Splice the mainbrace!";
        }
        else if (passage.isEmpty()) {
            return "Blimey!";
        }

        Scanner scan = new Scanner(passage);
        String translatedPassage = "";

        while (scan.hasNext()) {
            translatedPassage += translateWord(scan.findInLine("(\\w+)|([\\p{P}]+)|(\\s+)"));
        }
        return translatedPassage;

    } // END OF METHOD

    /**
     * This is the main method which use the other methods in the PirateTalk class
     *
     * @param it takes in args
     *
     * @return there is no output
     */

    public static void main( String [ ] args ) {
        PirateTalk yeOldeObject = new PirateTalk( );

        // Test loadDictionary
        // NOTE: to use the assert keyword for testing,
        //       you must use the -ea compiler command line option.
        yeOldeObject.loadDictionary( "pirate_dictionary.txt" );

        // Test translateWord( )
        assert yeOldeObject.translateWord( "hello" ).equals( "ahoy" );
        assert yeOldeObject.translateWord( "Hello" ).equals( "Ahoy" );
        assert yeOldeObject.translateWord( "coins" ).equals( "pieces of eight" );

        // Test translatePassage( )
        assert yeOldeObject.translatePassage( "Hello! I am Professor Leo." ).equals( "Ahoy! Shiver me timbers! I be Cap'n Leo. Arrr." );
        assert yeOldeObject.translatePassage( "The student spent a lot of money of books!" ).equals( "Th' swab spent a briny lot o' booty o' books! Shiver me timbers!" );
        assert yeOldeObject.translatePassage( "You can call me anything you want. Just do not call me late for dinner!" ).equals( "Ye can call me anythin' ye be needin'. Arrr. Jus' do nah call me late for grub! Shiver me timbers!" );

        //System.out.print(yeOldeObject.translatePassage(null));

    } // END OF METHOD

} // END OF CLASS: PirateTalk
