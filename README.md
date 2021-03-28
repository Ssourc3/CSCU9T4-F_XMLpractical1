# CSCU9T4-F_XMLpractical1
This repository holds the starter code for the first XML practical exercise in CSCU9T4.

Changes and processes that have been made: 

1) Configured and placed the validator method to catch some exceptions if any.
2) Created a system.println to print whether validation has passed or not --> System.out.println("Validation has passed:");
3) Normalised the XML structure --> document.getDocumentElement().normalize();
4) Preceeded to try get the Root Node --> Element root = document.getDocumentElement(); 
5) Created code to get all the food Items by looping through the list --> NodeList nList = document.getElementsByTagName("food");
6) Was able to parse/read the food items on the small menu using --> eElement.getElementsByTagName and eElement.getAttribute. 

Also created own XML for testing.(Menu.xml)
