import java.io.*;               // import input-output

import javax.xml.XMLConstants;
import javax.xml.parsers.*;         // import parsers
import javax.xml.transform.dom.DOMSource;
import javax.xml.xpath.*;           // import XPath
import javax.xml.validation.*;      // import validators
import javax.xml.transform.*;       // import DOM source classes

//import com.sun.xml.internal.bind.marshaller.NioEscapeHandler;
import org.w3c.dom.*;               // import DOM

/**
  DOM handler to read XML information, to create this, and to print it.

  @author   CSCU9T4, University of Stirling
  @version  11/03/20
 */
public class DOMMenu {

	/** Document builder */
	private static DocumentBuilder builder = null;

	/** XML document */
	private static Document document = null;

	/** XPath expression */
	private static XPath path = null;

	/** XML Schema for validation */
	private static Schema schema = null;

	/*----------------------------- General Methods ----------------------------*/

	/**
    Main program to call DOM parser.

    @param args         command-line arguments
	 */
	public static void main(String[] args)  {
		// load XML file into "document"
		loadDocument(args[0]);

		validateDocument(args[1]); //placing validator method
		// print staff.xml using DOM methods and XPath queries
		printNodes();


	}

	/**
    Set global document by reading the given file.

    @param filename     XML file to read
	 */
	private static void loadDocument(String filename) {
		try {
			// create a document builder
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builder = builderFactory.newDocumentBuilder();

			// create an XPath expression
			XPathFactory xpathFactory = XPathFactory.newInstance();
			path = xpathFactory.newXPath();

			// parse the document for later searching
			document = builder.parse(new File(filename));

			//Normalising XML Structure
			document.getDocumentElement().normalize();

		}
		catch (Exception exception) {
			System.err.println("could not load document " + exception);
		}
	}

	/*-------------------------- DOM and XPath Methods -------------------------*/
	/**
   Validate the document given a schema file
   @param filename XSD file to read
	 */
	private static Boolean validateDocument(String filename)  {
		try {
			String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
			SchemaFactory factory = SchemaFactory.newInstance(language);
			schema = factory.newSchema(new File(filename));

			Validator validator = schema.newValidator();
			validator.validate(new DOMSource(document));
			System.out.println("Validation has passed:"); //Validation is okay
			return true;
		} catch (Exception e){
			System.err.println(e);
			System.err.println("Could not load schema or validate"); //Throws an Exception
			return false;
		}
	}
	/**
    Print nodes using DOM methods and XPath queries.
	 */
	private static void printNodes() {

		//Getting Root Node
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		//Get all food items
		NodeList nList = document.getElementsByTagName("menu");
		System.out.println("==========Today's Menu==============");

		for (int temp = 0; temp < nList.getLength(); temp++)
		{
			Node node = nList.item(temp);
			System.out.println("");  //Separator
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				//Print each Food Item
				Element eElement = (Element) node;
				System.out.println("Food Category : "    + eElement.getAttribute("category"));
				System.out.println("Name : "  + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("Price : "    + eElement.getElementsByTagName("price").item(1).getTextContent())
				System.out.println("Description : "   + eElement.getElementsByTagName("description").item(2).getTextContent());
			}
		}
	}
	/**
    Get result of XPath query.

    @param query        XPath query
    @return         result of query
	 */
	private static String query(String query) {
		String result = "";
		try {
			result = path.evaluate(query, document);
		}
		catch (Exception exception) {
			System.err.println("could not perform query - " + exception);
		}
		return(result);
	}
}
