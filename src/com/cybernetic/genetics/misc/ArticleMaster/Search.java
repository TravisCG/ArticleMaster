package com.cybernetic.genetics.misc.ArticleMaster;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Search in an existing index
 * 
 * @author travis
 *
 */
public class Search {

	private IndexSearcher index;
	private Directory dir;
	private DirectoryReader dirread;
	private Analyzer analyzer;
	
	public Search(String indexDir) throws IOException {
		dir      = FSDirectory.open(new File(indexDir));
		dirread  = DirectoryReader.open(dir);
		index    = new IndexSearcher(dirread);
		analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
	}
	
	public void close(){
		try {
			dir.close();
			dirread.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void query(String query) throws ParseException, IOException {
		// TODO Auto-generated method stub
		QueryParser parser = new QueryParser(Version.LUCENE_4_9, "content", analyzer);
		Query q = parser.parse(query);
		ScoreDoc[] hits = index.search(q, 1000).scoreDocs;
		for(int i = 0; i < hits.length; i++){
			Document pdf = index.doc(hits[i].doc);
			System.out.println(pdf.get("url"));
		}
	}

}
