/*
 *  (accpeted)
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=748&page=show_problem&problem=2347
 * 11362 - Phone List
 */
package tree;

import java.io.IOException;
import java.util.*;

public class Q11362Trie {

    public static void main(String[] args) {
        String input = null;
        int caseCount, phoneNumberCount;

        Q11362Trie solver = new Q11362Trie();

        input = readInput(input);
        if(input == null) return;

        caseCount = Integer.parseInt(input);
        for(int i=0; i<caseCount; i++){
            solver.clear();
            input = readInput(input);
            if(input == null) return;
            boolean hasFoundPrefix = false;
            phoneNumberCount = Integer.parseInt(input.trim());
            for(int k=0; k<phoneNumberCount; k++){
                input = readInput(input);
                if(input == null) return;
                if(hasFoundPrefix) continue;
                hasFoundPrefix = solver.addPhoneNumber(input);
                if(hasFoundPrefix){
                    System.out.println("NO");
                }
            }

            if(!hasFoundPrefix) System.out.println("YES");
        }
    }

    private static final int MAX = 1000;
    private PhoneNumTree phoneNumberTree;

    public Q11362Trie(){
        this.phoneNumberTree = new PhoneNumTree();
    }

    public boolean addPhoneNumber(String phoneNumber){
        return this.phoneNumberTree.addPhoneNumber(phoneNumber);
    }

    public void clear(){
        this.phoneNumberTree.clear();
    }



    public static interface PhoneBook {
        public boolean addPhoneNumber(String phoneNumber);
        public void clear();
    }

    public class PhoneNumTree implements PhoneBook{

        private List<PhoneNumDigit> phoneNumbers;

        public PhoneNumTree(){
            this.phoneNumbers = new ArrayList<PhoneNumDigit>();
        }

        @Override
        public void clear(){
            this.phoneNumbers.clear();
        }

        @Override
        public boolean addPhoneNumber(String phoneNumber){
            char[] digits = phoneNumber.toCharArray();
            List<PhoneNumDigit> numbers = phoneNumbers;
            for(int i=0; i<digits.length; i++){
                int newDigit = Character.getNumericValue(digits[i]);
                boolean isFound = false;
                for(PhoneNumDigit oldDigit : numbers){
                    if(oldDigit.digit == newDigit){
                        isFound = true;
                        if(i == digits.length -1) {
                            oldDigit.isEnd = true;
                        }

                        if(isPrefixFound(i, digits, oldDigit)){
                            return true;
                        }

                        numbers = oldDigit.digitsBehind;
                        break;
                    }
                }

                if(!isFound){
                    PhoneNumDigit newPhoneNumDigit = new PhoneNumDigit(newDigit);
                    numbers.add(newPhoneNumDigit);
                    numbers = newPhoneNumDigit.digitsBehind;
                    if(i == digits.length -1){
                        newPhoneNumDigit.isEnd = true;
                    }
                }
            }
            return false;
        }

        private boolean isPrefixFound(int currentIndex, char[] newPhoneNum, PhoneNumDigit oldDigit){
            if(currentIndex < newPhoneNum.length -1){
                if(oldDigit.isEnd) return true;
            }else if(currentIndex == newPhoneNum.length -1){
                if(oldDigit.digitsBehind.size() > 0) return true;
            }

            return false;
        }


        public class PhoneNumDigit implements Comparable<PhoneNumTree.PhoneNumDigit>{
            private boolean isEnd;
            private int digit;
            private List<PhoneNumDigit> digitsBehind;

            public PhoneNumDigit(int digit){
                this.isEnd = false;
                this.digit = digit;
                this.digitsBehind = new LinkedList<PhoneNumDigit>();
            }

            @Override
            public int compareTo(PhoneNumDigit o) {
                if(this.digit > o.digit) return 1;
                else if(this.digit < o.digit) return -1;
                return 0;
            }
        }
    }

    static String readInput(String input){
        while((input = ReadLn(MAX)) != null){
            break;
        }
        if(input != null) {
            input = input.trim();
        }
        return input;
    }

    static String ReadLn (int maxLg)  // utility function to read from stdin
    {
        byte lin[] = new byte [maxLg];
        int lg = 0, car = -1;
        String line = "";

        try
        {
            while (lg < maxLg)
            {
                car = System.in.read();
                if ((car < 0) || (car == '\n')) break;
                lin [lg++] += car;
            }
        }
        catch (IOException e)
        {
            return (null);
        }

        if ((car < 0) && (lg == 0)) return (null);  // eof
        return (new String (lin, 0, lg));
    }
}
