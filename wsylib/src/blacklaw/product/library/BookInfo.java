package blacklaw.product.library;
/**********************************
 * @author	blacklaw
 * @email	2577927146@qq.com
 * @version	2.0.0 2013.2.1
 ***********************************/
public class BookInfo{
	//int img;
	String author;
	String publisher;
	String date;
	String isbn;
	String callno;
	String classno;
	String pages;
	String price;
	String title;
	public BookInfo(){
		
	}
	public BookInfo(String iTitle,String iAuthor,String iPublisher,String iDate,String iIsbn,String iCallno,String iClassno,String iPages,String iPrice){
		author=iAuthor;
		title=iTitle;
		publisher=iPublisher;
		date=iDate;
		isbn=iIsbn;
		callno=iCallno;
		classno=iClassno;
		pages=iPages;
		price=iPrice;
		
	}
/*	public BookInfo(int ad){
		title=getAttributeValue(ad,"title");
		author=getAttributeValue(ad,"author");
		publisher=getAttributeValue(ad,"publisher");//(String)data.get(groupPosition).get("publisher");
		date=getAttributeValue(ad,"date");//(String)data.get(groupPosition).get("date");
		isbn=getAttributeValue(ad,"isbn");//+(String)data.get(groupPosition).get("isbn"));
		callno=getAttributeValue(ad,"callNO");
		classno=getAttributeValue(ad,"classNO");
		pages=getAttributeValue(ad,"pages");
		price=getAttributeValue(ad,"price");
	}
	protected String getAttributeValue(int groupPosition,String attributeName){
		return "Null";//(String)data.get(groupPosition).get(attributeName);
	}*/
	
}
