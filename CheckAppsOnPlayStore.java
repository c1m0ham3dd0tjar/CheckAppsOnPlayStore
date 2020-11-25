package main;  
  
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection; 
import java.net.MalformedURLException;
import java.net.URL;  
import java.util.Calendar;   
   
import org.jsoup.Jsoup; 
import org.jsoup.nodes.Document; 
import org.jsoup.nodes.Element;   
import org.jsoup.select.Elements;   
  
public class CheckPublishedApp {  
  
	public static void main(String[] args) throws MalformedURLException, IOException {
		String version="",installed="",updated="",resultFound="",resultNotFound="";
		    String jsonTxt="[\n";
		    String gnumericTxt="";
		String basePlayStore="https://play.google.com/store/apps/details?id=";
		String []packagess= { 
  
			   
		 
				 	 
		};
		for(int i=0;i < packagess.length;i++) {
			HttpURLConnection connection = (HttpURLConnection) new URL(basePlayStore+packagess[i]).openConnection();
			connection.setRequestMethod("HEAD");
			int responseCode = connection.getResponseCode();
			if (responseCode == 200) {
				try {
				Document document = Jsoup.connect(basePlayStore+packagess[i])
						  .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 2.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
						  .referrer("http://www.google.com")
						.get();
				 if (document != null) {
					 Elements element = document.getElementsContainingOwnText("Current Version");
			            for (Element ele : element) {
			                if (ele.siblingElements() != null) {
			                    Elements sibElemets = ele.siblingElements();
			                    for (Element sibElemet : sibElemets) {
			                        version = sibElemet.text();
			                    }
			                }
			            }
			            Elements elementUpdated = document.getElementsContainingOwnText("Updated");
			            for (Element ele : elementUpdated) {
			                if (ele.siblingElements() != null) {
			                    Elements sibElemets = ele.siblingElements();
			                    for (Element sibElemet : sibElemets) {
			                        updated = sibElemet.text();
			                    }
			                }
			            }
			            Elements elementInstalls = document.getElementsContainingOwnText("Installs");
			            for (Element ele : elementInstalls) {
			                if (ele.siblingElements() != null) {
			                    Elements sibElemets = ele.siblingElements();
			                    for (Element sibElemet : sibElemets) {
			                        installed = sibElemet.text();
			                    }
			                }
			            }
			        }
			  
 
			} catch (IOException e) {
				e.printStackTrace();
			}
				//json
			/*	if(i==packagess.length-1) {
					jsonTxt+="[\n\""+packagess[i]+"\",\n\""+version+"\",\n\""+updated+"\",\n\""+installed+"\"\n]";
				 System.out.println("[\n\""+packagess[i]+"\",\n\""+version+"\",\n\""+updated+"\",\n\""+installed+"\"\n]");
				}else {
					System.out.println("[\n\""+packagess[i]+"\",\n\""+version+"\",\n\""+updated+"\",\n\""+installed+"\"\n],");
					jsonTxt+="[\n\""+packagess[i]+"\",\n\""+version+"\",\n\""+updated+"\",\n\""+installed+"\"\n],";				
				}*/
				
			//	System.out.println(packagess[i]+" : found ; version : "+version+" ; updated "+updated+" installs "+installed);
				//	 resultFound+=packagess[i]+" : found ; version : "+version+" ; updated "+updated+" installs "+installed+"\n";

				 System.out.println(packagess[i]+","+version+","+updated+","+installed+"\n"); 
				 if(updated.contains(","))
					 updated="\""+updated+"\"";
				 if(installed.contains(","))
					 installed="\""+installed+"\"";
				 
				 gnumericTxt+=packagess[i]+","+version+","+updated+","+installed+"\n";

			}else if(responseCode==404){
				System.out.println(packagess[i]+" : not found");
				resultNotFound+=packagess[i]+" : not found \n";
			}else if(responseCode==403){
		System.out.println(packagess[i]+" : app not available in your region");
			}
		}
		//just txt
		//  writeFile(resultFound+"\n"+resultNotFound);

		//just json
		//writeFile(jsonTxt+"\n]");
		
		//gnumeric
		writeFile(gnumericTxt);
			
	}
	  static void writeFile(String text) {
	    	 try {
			      FileWriter myWriter = new FileWriter(""+Calendar.getInstance().getTime());
			      myWriter.write(text);
			      myWriter.close();
			   //   System.out.println("Successfully wrote to the file.");
			    } catch (IOException e) {
			      System.out.println("An error occurred. ");
			      e.printStackTrace();
			    }
	    }
}
