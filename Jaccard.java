import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class Jaccard {
    
    public static double similarity(Set<String> left, Set<String> right) {
    final int sa = left.size();
    final int sb = right.size();
    if (sa == 0 | sb == 0) return 0;
    final Set<String> smaller = sa <= sb ? left : right;
    final Set<String> larger  = sa <= sb ? right : left;
    int intersection = 0;
    for (final Object element : smaller) try {
        if (larger.contains(element))
        intersection++;
    } catch (final ClassCastException | NullPointerException e) {}
    final long sum = sa + sb;
    return 1d / (sum - intersection) * intersection;
}

public static void findSimilarity(String[] files, String phrase) {
    final java.util.regex.Pattern p
    = java.util.regex.Pattern.compile("\\W+");
    int i=1;
    System.out.println("********************************************");
    System.out.println("jaccard similarity of query(" + phrase + ")with all documents:");
    for (String fileName : files) {
        try{
            Path filePath = Path.of(fileName);
            String content = Files.readString(filePath);
            content = content.toLowerCase();
            phrase = phrase.toLowerCase();
            final double similarity = similarity(
                p.splitAsStream(content).collect(java.util.stream.Collectors.toSet()),
                p.splitAsStream(phrase).collect(java.util.stream.Collectors.toSet()));
                System.out.println("jaccard similarity with doc " + i + " is: " + similarity);
                i++;
            } catch (IOException e) {
                System.out.println("File " + fileName + " not found. Skip it");
            }
        }
    }
    public static void main(String[] args) throws IOException {
        String phrase = "";
        String[] docs_paths = new String[]{
                "/C:/Users/user/Downloads/docs/100.txt", // change it to your path e.g. "c:\\tmp\\100.txt"
                "/C:/Users/user/Downloads/docs/101.txt",
                "/C:/Users/user/Downloads/docs//102.txt",
                "/C:/Users/user/Downloads/docs//103.txt",
                "/C:/Users/user/Downloads/docs//104.txt",
                "/C:/Users/user/Downloads/docs/105.txt",
                "/C:/Users/user/Downloads/docs//106.txt",
                "/C:/Users/user/Downloads/docs//107.txt",
                "/C:/Users/user/Downloads/docs//108.txt"

        };
        String query = "idea of March";
        String query2 = "computer science college";
        String query3 = "doctor ehab is working at faculty of artificial intellegence cairo university";
        findSimilarity(docs_paths, query);
        findSimilarity(docs_paths, query2);
        findSimilarity(docs_paths, query3);
        do {
            System.out.println("Enter search phrase: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            phrase = in.readLine();
            findSimilarity(docs_paths, phrase);
        } while (!phrase.isEmpty());
    
    }

}