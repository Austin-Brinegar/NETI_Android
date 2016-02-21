package netidevs.neti_android.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Austin on 2/20/2016.
 */
public class DrugModel {
   public static class subjects{
       public static class Drugs{
           private static String name;
           public static List<tags> tagList;
           public static List<topics> topicList;
           public static List<Drugs> drugList;

           public static List<Drugs> getDrugList() {
               return drugList;
           }

           public static void setDrugList(List<Drugs> drugList) {
               Drugs.drugList = new ArrayList<>(drugList);
           }

           public static List<tags> getTagList() {
               return tagList;
           }

           public static void setTagList(List<tags> tagList) {
               Drugs.tagList = new ArrayList<tags>(tagList);
           }

           public static List<topics> getTopicList() {
               return topicList;
           }

           public static void setTopicList(List<topics> topicList) {
               Drugs.topicList = new ArrayList<topics>(topicList);
           }

           public static String getName() {
               return name;
           }

           public static void setName(String Sname) {
               name = Sname;
           }

           public static class tags{
               private static String tagName;

               public String getTagName() {
                   return tagName;
               }

               public static void setTagName(String StagName) {
                   tagName = StagName;
               }
           }

           public static class topics{
               private static String description;
               private static String title;

               public String getDescription() {
                   return description;
               }

               public static void setDescription(String Sdescription) {
                   description = Sdescription;
               }

               public String getTitle() {
                   return title;
               }

               public static void setTitle(String Stitle) {
                   title = title;
               }
           }

           private static String toxicity;

           public String getToxicity() {
               return toxicity;
           }

           public static void setToxicity(String Stoxicity) {
               toxicity = Stoxicity;
           }
       }
   }
}
