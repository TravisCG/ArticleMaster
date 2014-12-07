package com.cybernetic.genetics.misc.ArticleMaster;

/**
 * Read command line parameters
 * @author travis
 *
 */
public class Params {

	private String indexdir;
	private String pdfdir;
	private String query;
	private TaskType type;
	
	public Params(String[] args) {
		// main task
		switch(args[0]){
			case "index":
				type=TaskType.INDEX;
				break;
			case "search":
				type = TaskType.SEARCH;
				break;
			default:
				type = TaskType.NONE;
		}
		
		// parameters
		for(int i = 1; i < args.length; i++){
			if(args[i].equals("-idir")){
				indexdir = args[i+1];
			}
			if(args[i].equals("-pdfdir")){
				pdfdir = args[i+1];
			}
			if(args[i].equals("-query")){
				query = args[i+1];
			}
		}
	}

	public TaskType getType(){
		return type;
	}
	
	public String getIndexDir(){
		return indexdir;
	}
	
	public String getPDFDir(){
		return pdfdir;
	}

	public String getQuery() {
		return query;
	}
}
