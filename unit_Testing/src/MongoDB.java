import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MongoDB {

    private String mongoPort="27017";
    private String mongoClientUri = "mongodb://localhost:"+mongoPort;
    private String dbnameMongo = "zoznamZiakov";

    private static MongoDB mdb = new MongoDB();

    private MongoDB(){

    }

    public static MongoDB getInstanceMongoDB(){
        return mdb;
    }

    public MongoDatabase getMongoConnectionDatabase(){
        try {
            // Creating a Mongo client
            MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoClientUri));

            // Accessing the database
            MongoDatabase database = mongoClient.getDatabase(dbnameMongo);

            for (String name: database.listCollectionNames()){
                System.out.println("Collection: "+name);
            }

            return database;
        }catch (Exception e){
            System.out.println(e);
        }


        return null;
    }

    public MongoCollection<Document> accessingCollectionUser_pid(){
        MongoDatabase database = getMongoConnectionDatabase();

        try {
            // Retieving a collection Bill
            MongoCollection<Document>  collection = database.getCollection("user_pid");
            System.out.println("Collection user_pid selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public MongoCollection<Document> accessingCollectionUser_dob(){
        MongoDatabase database = getMongoConnectionDatabase();

        try {
            // Retieving a collection Bill
            MongoCollection<Document>  collection = database.getCollection("user_dob");
            System.out.println("Collection user_dob selected successfully");
            return collection;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }

    public void addDoBToMongoDB(String firstName, String lastName, int day, int month, int year){
        MongoCollection<Document> collectionDoB = accessingCollectionUser_dob();

        Document documentDoB = new Document("firstName", firstName)
                .append("lastName",lastName)
                .append("day", day)
                .append("month", month)
                .append("year",year);
        collectionDoB.insertOne(documentDoB);
        System.out.println("DocumentDoB inserted succesfully ");
    }

    public String addPidToMongoDB(String firstName, String lastName, String pid){
        MongoCollection<Document> collectionPid = accessingCollectionUser_pid();

        Document documentPid = new Document("firstName", firstName)
                .append("lastName",lastName)
                .append("pid", pid);
        collectionPid.insertOne(documentPid);
        System.out.println("DocumentPid inserted succesfully "+documentPid.get("_id"));
        return documentPid.get("_id").toString();
    }

    public void removeDocFromUser_Pid(String idDoc){
        MongoCollection<Document> collectionUser_pid = accessingCollectionUser_pid();
        collectionUser_pid.deleteOne(new Document("_id", new ObjectId(idDoc)));
    }

    public void getPerson(){
        MongoCollection<Document> collectionUser_pid = accessingCollectionUser_pid();
        List<Document> documents = (List<Document>) collectionUser_pid.find().into(
                new ArrayList<Document>());

        for(Document document : documents){
            System.out.println(document);
            String pid=document.get("pid").toString();
            int day=0;
            int month=0;
            int year=0;

            addDoBToMongoDB(
                    document.get("firstName").toString(),
                    document.get("lastName").toString(),
                    day,
                    month,
                    year);
        }
    }
}
