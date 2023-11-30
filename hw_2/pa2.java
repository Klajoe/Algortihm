import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;

//set classpath=.\jackson-databind-2.11.1.jar;.\jackson-core-2.11.1.jar;.\jackson-annotations-2.11.1.jar;
@JsonIgnoreProperties(ignoreUnknown = true)
public class pa2{   
    public static void main(String[] args) {
        double arr[] = new double[50000];
        int i = 0;
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            Root root = om.readValue(new File("1.json"), Root.class); 
            Iterator<Vulnerability> it = root.vulnerabilities.iterator();
            while (it.hasNext()) {
                Vulnerability vul = it.next();
                if(vul.cve.metrics.cvssMetricV2 != null){
                    arr[i++] = vul.cve.metrics.cvssMetricV2.getFirst().cvssData.baseScore;
                    System.out.println(vul.cve.metrics.cvssMetricV2.getFirst().cvssData.baseScore);
                }
                else{
                    System.out.println(vul.cve.id);
                }
            }
            System.out.println("----------------------------------------" + i);   
            quickSort(arr,i);
            for(int a = 0;a<i;a++){
                System.out.println(arr[a]);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void quickSort(double[] arr,int i) {
        if (arr == null || i <= 1) return;
        quickSort(arr, 0, i - 1);
    }

    private static void quickSort(double[] arr, int low, int high) {
        if (low < high) {
            int partitionIndex = partition(arr, low, high);
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    private static int partition(double[] arr, int low, int high) {
        double pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        double temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
}

class Configuration{
    public ArrayList<Node> nodes;
}

class CpeMatch{
    public boolean vulnerable;
    public String criteria;
    public String matchCriteriaId;
    public String versionEndIncluding;
}

class Cve{
    public String id;
    public String sourceIdentifier;
    public Date published;
    public Date lastModified;
    public String vulnStatus;
    public ArrayList<Description> descriptions;
    public Metrics metrics;
    public ArrayList<Weakness> weaknesses;
    public ArrayList<Configuration> configurations;
    public ArrayList<Reference> references;
    public String evaluatorSolution;
    public String evaluatorImpact;
}

class CvssData{
    public String version;
    public String vectorString;
    public String accessVector;
    public String accessComplexity;
    public String authentication;
    public String confidentialityImpact;
    public String integrityImpact;
    public String availabilityImpact;
    public double baseScore;
}

class CvssMetricV2{
    public String source;
    public String type;
    public CvssData cvssData;
    public String baseSeverity;
    public double exploitabilityScore;
    public double impactScore;
    public boolean acInsufInfo;
    public boolean obtainAllPrivilege;
    public boolean obtainUserPrivilege;
    public boolean obtainOtherPrivilege;
    public boolean userInteractionRequired;
}

class Description{
    public String lang;
    public String value;
}

class Description2{
    public String lang;
    public String value;
}

class Metrics{
    public ArrayList<CvssMetricV2> cvssMetricV2;
}

class Node{
    public String operator;
    public boolean negate;
    public ArrayList<CpeMatch> cpeMatch;
}

class Reference{
    public String url;
    public String source;
    public ArrayList<String> tags;
}

class Root{
    public int resultsPerPage;
    public int startIndex;
    public int totalResults;
    public String format;
    public String version;
    public Date timestamp;
    public ArrayList<Vulnerability> vulnerabilities;
}

class Vulnerability{
    public Cve cve;
}

class Weakness{
    public String source;
    public String type;
    public ArrayList<Description> description;
}
