package com.codingcompetition.statefarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.codingcompetition.statefarm.model.PointOfInterest;

public class SearchProgram {

	private SearchProgram() {}
	
	public static void main(String[] args) {
		Interpreter interp = new StreetMapDataInterpreter("/small-metro.xml");
		
		
		SearchPredicate pred = null;
		
		System.out.print("Enter Query: ");
		String query = new Scanner(System.in).nextLine();
		
		for(String q : splitAroundParen(query)) {

			if(pred==null) {
				pred = parse(q);
			} else {
				pred = pred.or(parse(q));
			}
			
		}
		
		List<PointOfInterest> l = interp.interpret(pred);
		for(PointOfInterest p : l) {
			System.out.println(p);
		}
		
	}
	
	private static String[] splitAroundParen(String query) {
		//split around parentheses
		List<String> parts = new ArrayList<String>();
		String current = "";
		int paren = 0;

		for(int i=0; i<query.length(); i++) {
			if(paren==0 && Character.isWhitespace(query.charAt(i))) {
				parts.add(current);
				current = "";
			}else{
				if(query.charAt(i)=='('){
					paren++;
				} else if(query.charAt(i)==')'){
					paren--;
				}
				current+=query.charAt(i);
			}
		}
		if(current.length()>0) parts.add(current);
		
		return parts.toArray(new String[parts.size()]);
	}

	private static SearchPredicate parse(String q) {
		if(q.startsWith("and(") && q.endsWith(")")) {
			String[] qs = splitAroundParen(q.substring(4, q.length()-1));

			SearchPredicate pred = null;
			for(String q2 : qs) {
				if(pred==null) {
					pred = parse(q2);
				} else {
					pred = pred.and(parse(q2));
				}				
			}
			
			return pred;
		} else if(q.startsWith("or(") && q.endsWith(")")) {
			String[] qs = splitAroundParen(q.substring(4, q.length()-1));

			SearchPredicate pred = null;
			for(String q2 : qs) {
				if(pred==null) {
					pred = parse(q2);
				} else {
					pred = pred.or(parse(q2));
				}				
			}
			
			return pred;
		} else {
			String[] a = q.split("=!");
			if(a.length==1) {
				a = q.split("=");
				Category c = Category.getCategory(a[0].replaceAll("\"", ""));
				if(c==null) throw new RuntimeException("no category called "+a[0]);
				return new SearchCriteria(c, a[1].replaceAll("\"", ""));
			} else {
				Category c = Category.getCategory(a[0].replaceAll("\"", ""));
				if(c==null) throw new RuntimeException("no category called "+a[0]);
				return new SearchCriteria(c, a[1].replaceAll("\"", "")).not();
			}
		}
	}
	
}
