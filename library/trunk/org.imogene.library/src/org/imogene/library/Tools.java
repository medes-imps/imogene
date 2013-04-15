package org.imogene.library;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Tools {

	public static LibraryDesc getLibraryDesc(URL url){
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = factory.newDocumentBuilder();
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");			
			Document dom = domBuilder.parse(url.openStream());			
			NodeList list = dom.getElementsByTagName("library");
			if(list.getLength()>0){
				Node descNode = list.item(0);
				NamedNodeMap attrs = descNode.getAttributes();
				LibraryDesc libDesc = new LibraryDesc();
				libDesc.setUrl(url);
				libDesc.setId(getNodeItemValue(attrs, "id"));
				libDesc.setName(getNodeItemValue(attrs, "name"));
				libDesc.setDescription(getNodeItemValue(attrs, "desc"));
				return libDesc;
			}		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getNodeItemValue(NamedNodeMap  nodes, String name){
		Node item = nodes.getNamedItem(name);
		if(item!=null)
			return item.getNodeValue();
		return "";
	}
	
	/*
	 * Retrieve the referenced jar from the xml description
	 * @param is the XML file inputstream
	 * @return the list of jar names to include
	 */
	public static List<String> getJarNames(InputStream is){
		List<String> jarFiles = new ArrayList<String>();
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder domBuilder = factory.newDocumentBuilder();
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer trans = transFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");			
			Document dom = domBuilder.parse(is);
			NodeList list = dom.getElementsByTagName("jar");
			for(int i=0; i<list.getLength(); i++){
				String jar = getJarName(list.item(i));
				if(jar != null)
					jarFiles.add(jar);
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}catch(TransformerConfigurationException tce){
			tce.printStackTrace();
		}catch(SAXException se){
			se.printStackTrace();			
		}
		return jarFiles;		
	}
	
	/*
	 * Get the jar name from the specified node attributes
	 */
	public static String getJarName(Node node){
		if(node != null && node.hasAttributes()){
			Node attrNode = node.getAttributes().getNamedItem("file");
			if(attrNode !=null){
				return attrNode.getNodeValue();
			}
		}
		return null;
	}
}
