package com.cybernetic.genetics.misc.ArticleMaster;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

/**
 * Main entry point
 * @author travis
 *
 */
public class Main {

	public static void main(String[] args) {
		Params params = new Params(args);
		
		switch(params.getType()){
		case INDEX:
			Index index = new Index(params.getIndexDir());
			index.addDir(params.getPDFDir());
			index.close();
			break;
		case SEARCH:
			// Search document
			Search search;
			try {
				search = new Search(params.getIndexDir());
				search.query(params.getQuery());
				search.close();
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			//TODO error
			break;	
		}
	}

}
