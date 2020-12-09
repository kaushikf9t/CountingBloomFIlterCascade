# CountingBloomFilterCascade
Counting Bloom filters with cascading to eliminate false positives.

Encode to these Bloom Filters in such a way that a set A is first encoded to the filter
Capture the false positives from the list of exclusions and encode them into another bloom filter
Repeat this process until there are no false positives
Also allow an option to remove the elements in the filter by decrementing counters in the bloom filters
