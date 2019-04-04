package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import interfaces.DraftInterface;
import interfaces.Pce;
import items.Item;
import items.food.Fruit;
import main.Bill;
import main.Globals;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;


public class MongoDB {

    private static MongoDB mdb = new MongoDB();

    private MongoDB(){

    }

    public static MongoDB getInstanceMongoDB(){
        return mdb;
    }

    public MongoDatabase getMongoConnectionDatabase(){
        try {
            // Creating a Mongo client
            MongoClient mongoClient = new MongoClient(new MongoClientURI(Globals.mongoClientUri));

            // Creating Credentials
            MongoCredential credential;
            credential = MongoCredential.createCredential(Globals.userMongo, Globals.dbnameMongo,
                    Globals.passwMongo.toCharArray());
            System.out.println("Connected to the database successfully");

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase(Globals.dbnameMongo);
            System.out.println("Credentials ::"+ credential);

            for (String name: database.listCollectionNames()){
                System.out.println("Collection: "+name);
            }

            return database;
        }catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public MongoCollection<Document> accessingCollectionBill(){
        MongoDatabase database = getMongoConnectionDatabase();

        try {
            // Retieving a collection Bill
            MongoCollection<Document>  collection = database.getCollection("bill");
            System.out.println("Collection bill selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public MongoCollection<Document> accessingCollectionItems(){
        MongoDatabase database = getMongoConnectionDatabase();

        try {
            // Retieving a collection Items
            MongoCollection<Document>  collection = database.getCollection("items");
            System.out.println("Collection items selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    public void addBillToMongoDB(Bill bill){
        MongoCollection<Document> collectionBill = accessingCollectionBill();
        MongoCollection<Document> collectionItem = accessingCollectionItems();


        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm:ss");
        String time = formattime.format(bill.getDate());

        SimpleDateFormat formatdate = new SimpleDateFormat("dd:MM:YYYY");
        String date = formatdate.format(bill.getDate());

        Document documentBill = new Document("Date", date)
             .append("Time", time)
             .append("TotalPrice", bill.getFinalPriceOfBill());
        collectionBill.insertOne(documentBill);
        System.out.println("Document Bill inserted succesfully");
        ObjectId id = documentBill.getObjectId("_id");

        for (Item item:bill.getList()) {
            Document documentItem = new Document("name",item.getName())
                    .append("idBill",id)
                    .append("price",item.getPrice());
                    if (item instanceof DraftInterface){
                        documentItem.append("count",((DraftInterface) item).getVolume());
                    }else if(item instanceof Fruit){
                        documentItem.append("count",((Fruit) item).getWeight());
                    }else if(item instanceof Pce){
                        documentItem.append("count",((Pce) item).getAmount());
                    }
                        documentItem.append("unit",item.getUnit());
            collectionItem.insertOne(documentItem);
            System.out.println("Document bill inserted succesfully");
        }
    }



}
