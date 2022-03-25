package serviceLayer;

import buisnessLayer.Student;
import com.mongodb.*;

public class DBHandler {
	public static void addAccount(String name,String DOB,String username,String password) {
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

		DB db = mongoClient.getDB( "SportEquipment" );
		DBCollection coll = db.getCollection("Person");
		// Adding Data
		BasicDBObject doc = new BasicDBObject("name", name).
				append("DOB", DOB).
				append("username", username).
				append("password", password).append("Type", "student");

		coll.insert(doc);
		System.out.println("Done");
		mongoClient.close();
	}
	public static void updatePersonalInfo(String name, String DOB, String username) {
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

		DB db = mongoClient.getDB( "SportEquipment" );
		DBCollection coll = db.getCollection("Person");

		BasicDBObject query = new BasicDBObject();
		query.put("username", username);

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("name", name);
		newDocument.put("DOB", DOB);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		coll.update(query, updateObj);

		mongoClient.close();
	}
	public static void updateCredentials(String username,String password,String previousUsername) {
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

		DB db = mongoClient.getDB( "SportEquipment" );
		DBCollection coll = db.getCollection("Person");

		BasicDBObject query = new BasicDBObject();
		query.put("username", previousUsername);

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("username", username);
		newDocument.put("password", password);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		coll.update(query, updateObj);

		mongoClient.close();
	}
	public static Student verifyLogin(String username,String password) {
		//boolean verification = false;
		Student student = null;
		MongoClient mongoClient = new MongoClient( "localhost" , 27017 );

		DB db = mongoClient.getDB( "SportEquipment" );
		DBCollection coll = db.getCollection("Person");

		//DBCollection col = db.getCollection("mycol");

		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("username", username);
		searchQuery.put("password", password);

		DBCursor cursor = coll.find(searchQuery);

//		while (cursor.hasNext()) {
//			System.out.println("===========");
//			System.out.println(cursor.next());
//			System.out.println("===========");
//			//verification = true;
//		}
		if (cursor.hasNext()) {
			DBObject obj = cursor.next();
			student = new Student(obj.get("name").toString(),obj.get("DOB").toString(),obj.get("username").toString(),obj.get("password").toString());
		}

		mongoClient.close();
		return student;
	}
	public static void main(String[] args) {
		//addAccount("Husnain","12-11-1999","husnainqadri","1234");
//		Student answer = verifyLogin("husnainqadri92","1234");
//		if (answer == null) {
//			System.out.println("NULL");
//		}
//		else {
//			System.out.println("Not NULL");
//		}
		//updateCredentials("husnainqadri92","1234","husnainqadri");
		//updatePersonalInfo("Husnain Qadri","12-11-1999","husnainqadri92");
	}
}
