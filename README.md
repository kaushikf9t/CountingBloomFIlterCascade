# CountingBloomFilterCascade
Counting Bloom filters with cascading to eliminate false positives.
1. Encode to these Bloom Filters in such a way that a set A is first encoded to the filter
2. Capture the false positives from the list of exclusions and encode them into another bloom filter
3. Repeat this process until there are no false positives
4. Also allow an option to remove the elements in the filter by decrementing counters in the bloom filters
