package com.cybernetic.genetics.misc.ArticleMaster;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Create text index for search
 * If the index exists, append new elements
 * 
 * @author travis
 *
 */
public class Index {
	private IndexWriter index;
	
	Index(String idir){
		Directory dir;
		
		try {
			dir = FSDirectory.open(new File(idir));
		} catch (IOException e) {
			// If we can not open a file, create index into memory
			dir = new RAMDirectory();
		}
		
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);
		conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		
		try {
			index = new IndexWriter(dir, conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addDir(String pdfdir){
		File dir = new File(pdfdir);
		File[] pdffiles = dir.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith("pdf");
			}});
		
		for(File f : pdffiles){
			try {
				Document pdf = getDocument(f);
				index.addDocument(pdf);//TODO: updateDocument ?
			} catch (IOException e) {
				System.err.println("Adding document: " + f.getAbsolutePath() + " failed");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Extract text from PDF and create Lucene document
	 * @param f
	 * @return
	 */
	private Document getDocument(File f) throws IOException{
		PDDocument pdf = PDDocument.load(f);
		PDFTextStripper totext = new PDFTextStripper();
		
		Document doc = new Document();
		doc.add(new StringField("url", f.getAbsolutePath(), Field.Store.YES));
		doc.add(new TextField("content", totext.getText(pdf), Field.Store.YES));
		
		pdf.close();
		return doc;
	}

	public void close(){
		try {
			index.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
