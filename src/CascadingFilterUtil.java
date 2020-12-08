import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class CascadingFilterUtil {
    public static int[] initHashFunctionsArray(int numHashFunctions) {
        int [] hashFunctionsArray = new int[numHashFunctions];
        Random rd = new Random();
        for (int i = 0; i < hashFunctionsArray.length; i++) {
            hashFunctionsArray[i] = rd.nextInt(Integer.MAX_VALUE);// storing random integers in an array
            //System.out.println(hashFunctionsArray[i]); // printing each array element
        }
        return hashFunctionsArray;
    }

    public static int getHashCode(int hashInput, int numTableEntries) {
        return hashInput%numTableEntries;
    }

    public static Set<Integer> createRandomIntegerSet(int numElementsInSet) {
        Set<Integer> integerSet = new TreeSet<>();
        while(integerSet.size() < numElementsInSet) {
            Random rd = new Random();
            integerSet.add(rd.nextInt(Integer.MAX_VALUE));
        }
        return integerSet;
    }
}
