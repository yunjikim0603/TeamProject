package job_community.svc;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import job_community.vo.JobBoardBean;

import org.w3c.dom.Element;



public class OpenApi {
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	public static void main(String[] args) throws Exception{
		
		OpenApi http = new OpenApi();
		http.sendGet();
		
	}
	
	public String getTagValue(String string, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(string).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}
	
	
	
	public ArrayList<JobBoardBean> sendGet() throws Exception {
		String url = "http://api.career.co.kr/open?id=cfz/KrO7Dz8=&uc=C1&kw=개발자&gubun=0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.getResponseCode();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null){
//			System.out.println(inputLine);
			response.append(inputLine);
		}
		in.close();
		
		ArrayList<JobBoardBean> jobList = new ArrayList<JobBoardBean>();
		
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			
			FileOutputStream output = new FileOutputStream("./JobData"); 
			output.write(response.toString().getBytes());
			output.close();
			
			Document doc = dBuilder.parse("./JobData");
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("jobs");

			for(int i = 0; i < 8; i++) {
				Node nNode = nList.item(i);
				if(nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
//					System.out.println("###########################" + i + "바퀴");
//					System.out.println("회사명 : " + getTagValue("회사명", eElement));
//					System.out.println("title : " + getTagValue("title", eElement));
//					System.out.println("worktype : " + getTagValue("worktype", eElement));
//					System.out.println("pay : " + getTagValue("pay", eElement));
//					System.out.println("opendate : " + getTagValue("opendate", eElement));
//					System.out.println("closedate : " + getTagValue("closedate", eElement));
//					System.out.println("area : " + getTagValue("area", eElement));
//					System.out.println("url : " + getTagValue("url", eElement));
					JobBoardBean boardBean = new JobBoardBean();

					boardBean.setName(getTagValue("회사명", eElement));
					boardBean.setTitle(getTagValue("title", eElement));
					boardBean.setWorktype(getTagValue("worktype", eElement));
					boardBean.setPay(getTagValue("pay", eElement));
					boardBean.setOpendate(getTagValue("opendate", eElement));
					String str = getTagValue("closedate", eElement);
					String closedate = str.substring(0 , 10);
					//System.out.println(closedate);
					boardBean.setClosedate(closedate);
					boardBean.setArea(getTagValue("area", eElement));
					boardBean.setUrl(getTagValue("url", eElement));
					
//					list.add(getTagValue("회사명", eElement));
//					list.add(getTagValue("title", eElement));
//					list.add(getTagValue("worktype", eElement));
//					list.add(getTagValue("pay", eElement));
//					list.add(getTagValue("opendate", eElement));
//					list.add(getTagValue("closedate", eElement));
//					list.add(getTagValue("area", eElement));
//					list.add(getTagValue("url", eElement));

					jobList.add(boardBean);
				}
				
			}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println(jobList);
		
		return jobList;
		
	}

	
}




