package app.pojos;
// Generated Feb 4, 2023 12:45:08 AM by Hibernate Tools 4.3.1



/**
 * Files generated by hbm2java
 */
public class Files  implements java.io.Serializable {


     private Integer id;
     private int chatId;
     private String link;
     private int isDeleted;

    public Files() {
    }

    public Files(int chatId, String link, int isDeleted) {
       this.chatId = chatId;
       this.link = link;
       this.isDeleted = isDeleted;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getChatId() {
        return this.chatId;
    }
    
    public void setChatId(int chatId) {
        this.chatId = chatId;
    }
    public String getLink() {
        return this.link;
    }
    
    public void setLink(String link) {
        this.link = link;
    }
    public int getIsDeleted() {
        return this.isDeleted;
    }
    
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }




}


