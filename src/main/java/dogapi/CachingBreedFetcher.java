package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private HashMap<String, List<String>> breedMap = new HashMap<>();
    private BreedFetcher fetcher;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) {
        // return statement included so that the starter code can compile and run.
        try {
            if ( this.breedMap.isEmpty() || !this.breedMap.containsKey(breed)) {
                this.callsMade++;
                List<String> subBreeds = this.fetcher.getSubBreeds(breed);
                this.breedMap.put(breed, subBreeds);
                return subBreeds;
            } else {
                return breedMap.get(breed);
            }
        }
        catch (BreedNotFoundException e) {
            throw new BreedNotFoundException("Can't find this breed!");
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}