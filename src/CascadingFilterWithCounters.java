import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This Cascading Bloom filter will be initialized with a set of entries and exclusions
 * This will create multiple layers of Cascading Filters to eliminate false positives
 */
public class CascadingFilterWithCounters {
    private int numHashes;

    private int numCounters;

    private int[] countingBloomFilter;

    private int[] hashFunctionsArray;

    private CascadingFilterWithCounters childFilter;

    public CascadingFilterWithCounters(int numHashes, int numCounters) {
        this.numHashes = numHashes;
        this.numCounters = numCounters;
        this.hashFunctionsArray = CascadingFilterUtil.initHashFunctionsArray(numHashes);

    }

    public int initialize(Set<Integer> entries, Set<Integer> exclusions, int finalDepth) {
        this.countingBloomFilter = new int[numCounters];
        //First encode the entries to the filter
        Set<Integer> falsePositives = new TreeSet<>();
        Iterator<Integer> iterator = entries.iterator();
        while(iterator.hasNext()) {
            int currentElement = iterator.next();
            for(int k = 0; k < numHashes; k++) {
                int hashValue = CascadingFilterUtil.getHashCode(currentElement^hashFunctionsArray[k], numCounters);
                countingBloomFilter[hashValue]++;
            }
        }

        /**
         * Loop through the exclusions and then add the ones that were not supposed to exist
         * in the bloom filter to the black list
         */
        Iterator<Integer> exclIterator = exclusions.iterator();
        while(exclIterator.hasNext()) {
            int element = exclIterator.next();
            if(checkExistence(element, this.countingBloomFilter, numCounters)) {
                falsePositives.add(element);
            }
        }

        System.out.println(falsePositives.size());

        if(!falsePositives.isEmpty()) {
            childFilter = new CascadingFilterWithCounters(numHashes, (this.numCounters-1000));
            childFilter.countingBloomFilter = new int[this.numCounters - 1000];
            finalDepth = childFilter.initialize(falsePositives, entries, ++finalDepth);

        }

        return finalDepth;

    }

    //private boolean checkExistence(int element) {
    private boolean checkExistence(int element, int [] bloomFilter, int currentCounters) {
        for(int k = 0; k < numHashes; k++) {
            int hashValue = CascadingFilterUtil.getHashCode(element^hashFunctionsArray[k], currentCounters);
            if(bloomFilter[hashValue] <= 0) {
                return false;
            }
        }
        return true;
    }

    private boolean performLookup(int element, int [] countingBloomFilter, int numCounters) {
        if(!checkExistence(element, countingBloomFilter, numCounters)) {
            //System.out.println("Returning false" + element);
            return false;

        } else {
            //System.out.println("Element found continue looking " + element);
            return performLookup(element, this.childFilter.countingBloomFilter, this.childFilter.numCounters);
        }
    }

    public boolean lookupElement(int element) {
       return performLookup(element, this.countingBloomFilter, this.numCounters);
    }

}
