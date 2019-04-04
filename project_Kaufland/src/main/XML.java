package main;

import interfaces.DraftInterface;
import interfaces.Pce;
import items.Item;
import items.food.Fruit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Date;

public class XML {

    private static XML xml = new XML();
    private XML(){}
    public static XML getInstanceXML(){
        return xml;
    }

    public void CreateXML(Bill bill) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuildFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        Element rootElement = doc.createElement("Bill");
        doc.appendChild(rootElement);

        Date date = new java.sql.Date(bill.getDate().getTime());
        Date time = new java.sql.Time(bill.getDate().getTime());

        Element dateEl = doc.createElement("DateOfPurchase");
        rootElement.appendChild(dateEl);

        Element dateOfPurch = doc.createElement("Date");
        dateOfPurch.appendChild(doc.createTextNode(String.valueOf(date)));
        dateEl.appendChild(dateOfPurch);

        Element timeOfPurch = doc.createElement("Time");
        timeOfPurch.appendChild(doc.createTextNode(String.valueOf(time)));
        dateEl.appendChild(timeOfPurch);

        for (Item item : bill.getList()){
            Element itemOfBill = doc.createElement("Item");
            rootElement.appendChild(itemOfBill);

            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(String.valueOf(item.getName())));
            itemOfBill.appendChild(name);

            Element price = doc.createElement("PriceOfOneUnit");
            price.appendChild(doc.createTextNode(String.valueOf(item.getPrice())));
            itemOfBill.appendChild(price);

            Element sumPrice = doc.createElement("TotalPrice");
            sumPrice.appendChild(doc.createTextNode(String.valueOf(item.getTotalPrice())));
            itemOfBill.appendChild(sumPrice);

            Element count = doc.createElement("Count");

            String countValue="";
            if (item instanceof DraftInterface){
                countValue = String.valueOf(((DraftInterface) item).getVolume());
            }else if(item instanceof Fruit){
                countValue = String.valueOf(((Fruit) item).getWeight());
            }else if(item instanceof Pce){
                countValue = String.valueOf(((Pce) item).getAmount());
            }

            count.appendChild(doc.createTextNode(countValue));
            itemOfBill.appendChild(count);

            Element unitName = doc.createElement("Unit");
            unitName.appendChild(doc.createTextNode(item.getUnit()));
            itemOfBill.appendChild(unitName);


        }

        Element finalPrice = doc.createElement("FinalPriceOfBill");
        finalPrice.appendChild(doc.createTextNode(String.valueOf(bill.getFinalPriceOfBill())));
        rootElement.appendChild(finalPrice);

        Element idBill = doc.createElement("IdOfBill");
        idBill.appendChild(doc.createTextNode(String.valueOf(bill.getId())));
        rootElement.appendChild(idBill);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //aby to vyzeral jak format
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:\\Users\\Tomas\\Desktop\\gitfrompctoGITHUB\\project_Kaufland\\src\\textOutput\\BillXML.xml"));
        transformer.transform(source,result);

    }
}
