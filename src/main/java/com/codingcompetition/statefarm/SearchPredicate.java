package com.codingcompetition.statefarm;

public interface SearchPredicate {

	public boolean fits(String key, String val);
	
	public default SearchPredicate not() {
		return (String k, String v) -> !fits(k, v);
	}
	
	public default SearchPredicate and(SearchPredicate query) {
		return (String k, String v) -> fits(k, v) && query.fits(k, v);
	}
	
	public default SearchPredicate or(SearchPredicate query) {
		return (String k, String v) -> fits(k, v) || query.fits(k, v);
	}
	
	public default SearchPredicate xor(SearchPredicate query) {
		return (String k, String v) -> fits(k, v) ^ query.fits(k, v);
	}
	
}
