
package Assignment8;
 
import java.io.IOException;
import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
 
public class Assignment8{
 
	public static void main(String[] args) throws IOException {
 
		IRiakClient client = null;
		try {
			client = RiakFactory.pbcClient("127.0.0.1", 8087);
		} catch (RiakException e) {
			e.printStackTrace();
		}
		
		Bucket myBucket = null;
		String bucketName = "s22217";
		try {
			myBucket = client.fetchBucket(bucketName).execute();
			if (myBucket == null) {
				myBucket = client.createBucket(bucketName).execute();
			}
		} catch (RiakRetryFailedException e) {
			e.printStackTrace();
		}
		
		String document= "hello Riak";
		String key= "one";
		try {
			myBucket.store(key, document).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		
		String fetchedDocument = "";
		try {
			fetchedDocument = myBucket.fetch(key, String.class).execute();
			System.out.println("retrieved document content: "+ fetchedDocument );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			fetchedDocument = myBucket.fetch(key, String.class).execute();
			fetchedDocument +=" some";
			myBucket.store(key, fetchedDocument).execute();
			
			fetchedDocument = myBucket.fetch(key, String.class).execute();
			System.out.println("modified document content: " + fetchedDocument );
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		try {
			myBucket.delete(key).execute();
			fetchedDocument = myBucket.fetch(key, String.class).execute();
			System.out.println("deleted document content: " + fetchedDocument );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		client.shutdown();
 
	}
 
}