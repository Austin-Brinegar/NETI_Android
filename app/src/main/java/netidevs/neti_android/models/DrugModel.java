package netidevs.neti_android.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 2/20/2016.
 */
public class DrugModel {
   public static class subjects{
       public static class Drugs{
           private String name;
           public List<tags> tagList;
           public List<topics> topicList;
           public List<Drugs> drugList;

           public List<Drugs> getDrugList() {
               return drugList;
           }

           public void setDrugList(List<Drugs> DrugList) {
               drugList = new ArrayList<>(DrugList);
           }

           public List<tags> getTagList() {
               return tagList;
           }

           public void setTagList(List<tags> TagList) {
               tagList = new ArrayList<tags>(TagList);
           }

           public List<topics> getTopicList() {
               return topicList;
           }

           public void setTopicList(List<topics> TopicList) {
               topicList = new ArrayList<topics>(TopicList);
           }

           public String getName() {
               return name;
           }

           public void setName(String Sname) {
               name = Sname;
           }

           public static class tags{
               private String tagName;

               public String getTagName() {
                   return tagName;
               }

               public void setTagName(String StagName) {
                   tagName = StagName;
               }
           }

           public static class topics{
               private String description;
               private String title;

               public String getDescription() {
                   return description;
               }

               public void setDescription(String Sdescription) {
                   description = Sdescription;
               }

               public String getTitle() {
                   return title;
               }

               public void setTitle(String Stitle) {
                   title = Stitle;
               }
           }

           private String toxicity;

           public String getToxicity() {
               return toxicity;
           }

           public void setToxicity(String Stoxicity) {
               toxicity = Stoxicity;
           }
       }
   }
}
