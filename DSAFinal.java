import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class DSAFinal{
 
   public static void main(String[] args) throws IOException {
   
      ArrayList<String> dict = dictionary();
      
      //checkMain(dict);
      for(int z=0; z<5;z++){
         ArrayList<String> rlb = new ArrayList<String>(); //grey letter bank, contains all tried grey letters
         ArrayList<String> glb = new ArrayList<String>(); //green letter bank, contains all valid green letters
            for(int i=0;i<5;i++) glb.add("*");
         ArrayList<ArrayList<String>> ylb = new ArrayList<ArrayList<String>>(); //yellow letter bank, contains all possible areas for yellow letters
            ArrayList<String> ylbInner = new ArrayList<String>();
            ylbInner.add(null);
            for(int i=0;i<5;i++) ylb.add(ylbInner);
            
         boolean tf = false;   
         ArrayList<String> cWord = getWord();
         ArrayList<String> cColor = getColor();
         
         if((cColor.get(0).equals("g"))&&(cColor.get(1).equals("g"))&&(cColor.get(2).equals("g"))&&(cColor.get(3).equals("g"))&&(cColor.get(4).equals("g"))){
            StdOut.print("congratulations! You beat the wordle puzzle!");
            return;
         }
         checkMain(dict);
         dict = possibleMatches(dict,cWord,cColor,glb,ylb,rlb);
         printList(dict);
      }
   }
   public static void printList(ArrayList<String> dict){
      //for(int i=0; i<dict.size();i++) StdOut.println(dict.get(i));
      StdOut.println("there are " + dict.size() + " remaining viable words. Try: " + dict.get(0));
   }
   
   public static ArrayList<String> dictionary() throws IOException { //puts dictionary of possible words into a more readable ArrayList
      BufferedReader readText = new BufferedReader(new FileReader("C:\\Users\\ethan\\Desktop\\DSA\\FinalProject\\someWords.txt"));
      ArrayList<String> dictionary = new ArrayList<String>();
      for(String currentWord = readText.readLine(); currentWord!=null; currentWord=readText.readLine()){
         dictionary.add(currentWord);
      }
      return dictionary;
   }
   
   public static ArrayList<String> getWord(){ //gets the inputed word from the user
      StdOut.println("please input what word you guessed");
      String currWord = StdIn.readString();
      boolean tf = false;
      while(tf == false){
         tf = true;
         if(currWord.length()!=5){
            StdOut.println("please type a 5 letter word");
            currWord = StdIn.readString();
            tf = false;
         }
      }
      String[] wordSplit = currWord.split("");
      ArrayList<String> charWordList = new ArrayList<>(Arrays.asList(wordSplit));
      return charWordList;
   }
   public static ArrayList<String> getColor(){ //gets the inputed colors from the user
      StdOut.println("please type the colors of each letter (green = g, yellow = y, and grey = r)");
      String currColor = StdIn.readString();
      boolean tf = false;
      while(tf == false){
         tf = true;
         if(currColor.length()!=5){
            StdOut.println("your input should be 5 characters long!");
            currColor = StdIn.readString();
            tf = false;
         }
      }
      String[] colorSplit = currColor.split("");
      ArrayList<String> charColorList = new ArrayList<>(Arrays.asList(colorSplit));
      return charColorList;

   }
   public static ArrayList<String> updateGrey(ArrayList<String> rlb, String val){
      ArrayList<String> grey = rlb;
      grey.add(val);
      return grey;
   }
   public static ArrayList<String> checkGrey(ArrayList<String> dict, ArrayList<String> rlb){
      /*
      returns remaining viable words after removing a letter
      */
      ArrayList<String> dictTemp = dict;
      for(int currentWord = dictTemp.size()-1; currentWord>=0; currentWord--){
         String currWord = dictTemp.get(currentWord);
         String[] charWord = currWord.split("(?!^)");
         ArrayList<String> cWord = new ArrayList<>(Arrays.asList(charWord)); //converts current word to arraylist
         for(int i=0; i<5; i++){
            for(int c=0;c<rlb.size();c++){
               if(cWord.get(i) == rlb.get(c)) dictTemp.remove(currentWord); //compares inputed word char to current word char
            }
         }
      }
      return dictTemp;
   }
   public static ArrayList<ArrayList<String>> updateYellow(ArrayList<ArrayList<String>> ylb, String val, int num){
      ArrayList<ArrayList<String>> yellow = ylb;
      for(int i=0; i<5;i++){
         if(i==num) continue;
         if(yellow.get(num).get(0)==null) yellow.get(num).set(0,val);
         yellow.get(num).add(val);
      }
      return yellow;
   }
   public static ArrayList<String> checkYellow(ArrayList<String> dict, int charPlace, ArrayList<ArrayList<String>> ylb){
      ArrayList<String> tempDictionary = dict;
      for(int currentWord = dict.size()-1; currentWord >= 0; currentWord--){
      String currWord = tempDictionary.get(currentWord);
         String[] charWord = currWord.split("(?!^)");
         for(int c=0;c<ylb.size();c++){
            ArrayList<String> innerYlb = ylb.get(c);
            for(int a=0;a<innerYlb.size();a++){
               if(charWord[charPlace] == innerYlb.get(a)) tempDictionary.remove(currentWord);
               if((charWord[0] != innerYlb.get(a))&&(charWord[1] != innerYlb.get(a))&&(charWord[2] != innerYlb.get(a))&&(charWord[3] != innerYlb.get(a))&&(charWord[4] != innerYlb.get(a))){
               tempDictionary.remove(currentWord);
               }
            }
         }
      }
      return tempDictionary;
   }
   public static ArrayList<String> updateGreen(ArrayList<String> glb, String val, int num){
      ArrayList<String> green = glb;
      green.set(num,val);
      return green;
   }
   public static ArrayList<String> checkGreen(ArrayList<String> dict, ArrayList<String> glb){
      ArrayList<String> tempDictionary = dict;
      for(int currentWord = dict.size()-1; currentWord >= 0; currentWord--){
      
         String currWord = tempDictionary.get(currentWord);
         String[] charWord = currWord.split("(?!^)");
         ArrayList<String> cWord = new ArrayList<>(Arrays.asList(charWord));
         
         for(int i=5; i<5; i++){
            for(int a=0;a<5;a++){
               if(cWord.get(i) != glb.get(a)) tempDictionary.remove(currentWord);
            }
         }
      }
      return tempDictionary;
   }
   public static void checkMain(ArrayList<String> dict){
      
      
      int wordCount = dict.size();
      for(int currentWord = dict.size()-1; currentWord >= 0; currentWord--) dict.remove(currentWord);
      //for(int currentWord = 0; currentWord<wordCount; currentWord++) dict.remove(currentWord);
      
   }
   public static ArrayList<String> possibleMatches(ArrayList<String> dict, ArrayList<String> cWord, ArrayList<String> cColor, ArrayList<String> glb, ArrayList<ArrayList<String>> ylb, ArrayList<String> rlb){
      /*
      given info about greens, yellows, and greys, return all of the possible word matches
      inputs:
         green: String containing known green characters(ex: "*b***")
         yellow: A 5 boxed array representing the known yellows for each letter(example below)
               [ ][ ][ ][ ][ ]
                c     z
                d
         grey: List containing known grey characters(ex:["q","r","s","t"])
      */
      ArrayList<String> dictionary = dict;
      ArrayList<String> green = glb;
      ArrayList<ArrayList<String>> yellow = ylb;
      ArrayList<String> grey = rlb;
      
      for(int i=0;i<5;i++){
         if(cColor.get(i) == "g") green = updateGreen(glb, cWord.get(i),i);
         if(cColor.get(i) == "y") yellow = updateYellow(ylb, cWord.get(i),i);
         if(cColor.get(i) == "r") grey = updateGrey(rlb, cWord.get(i));
      }
      for(int i=0;i<5;i++){
         if(cColor.get(i) == "g") dictionary=checkGreen(dictionary, green);
         if(cColor.get(i) == "y") dictionary=checkYellow(dictionary, i, yellow);
         if(cColor.get(i) == "r") dictionary=checkGrey(dictionary, grey);
      }
      return dictionary;
   }
}
