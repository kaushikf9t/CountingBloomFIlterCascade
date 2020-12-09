import java.util.Iterator;
import java.util.Set;
/**
 * Runner code to instantiate a Cascading Filter,
 */
public class CascadingFilterTest {
    public static void main(String[] args) {
        CascadingFilterWithCounters filter = new CascadingFilterWithCounters(5, 10000,1);
        Set<Integer> entries = CascadingFilterUtil.createRandomIntegerSet(3000);
        Set<Integer> exclusions = CascadingFilterUtil.createRandomIntegerSet(10000);
        int finalDepth = filter.initialize(entries, exclusions, 1);
        System.out.println("Final Depth after eliminating false positives " + finalDepth);

        Iterator<Integer> iterator = exclusions.iterator();
        int falsePositiveCount = 0;
        while(iterator.hasNext()) {
            if(filter.lookupElement(iterator.next())) {
                falsePositiveCount++;
            }
        }

        System.out.println("False Positive count "+falsePositiveCount);
    }
}
