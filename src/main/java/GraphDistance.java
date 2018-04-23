import jdk.nashorn.internal.parser.JSONParser;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.query.Dataset;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.io.FileReader;

import org.json.simple.*;


public class GraphDistance {

    static final String NS = "http://rdf.freebase.com/ns/";

    public static List<List<String>> getDistNNeighbours(Dataset dataset, String mid, int N) {
        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();

        Resource center = model.getResource(NS + mid);
        LinkedList<RDPair> queue = new LinkedList<>();
        queue.addLast(new RDPair(center, 0));
        List<List<String>> answer = new ArrayList<>();
        for (int i = 0; i < N; ++i) {
            answer.add(new ArrayList<>());
        }
        HashSet<String> visited = new HashSet<>();

        while (queue.size() > 0) {
            RDPair curr = queue.removeFirst();
            Resource currResource = curr.resource;
            int currDistance = curr.distance;
            System.out.println(curr);

            if (currDistance < N) {
                StmtIterator it = currResource.listProperties();
                while (it.hasNext()) {
                    Statement stmt = it.nextStatement();
                    String neighbour = stmt.getObject().toString();
                    if (! visited.contains(neighbour)) {
                        Resource neighborResource = model.getResource(neighbour);
                        if (neighborResource != null) {
                            queue.addLast(new RDPair(neighborResource, currDistance + 1));
                            answer.get(currDistance).add(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
            }
        }

        dataset.end();

        return answer;
    }

    public static Dataset loadDataset(String dir) {
        return TDBFactory.createDataset(dir);
    }

    public static void main(String[] args) throws Exception {
        Dataset dataset = loadDataset("/home/xianyang/kb/d-fb/");
        List<List<String>> answer = getDistNNeighbours(dataset, "m.06w2sn5", 3);
        for (List<String> layer: answer) {
            for (String node: layer) {
                System.out.println(node);
            }
            System.out.println("--------------------");
        }
    }

}

class RDPair {
    Resource resource;
    int distance;

    RDPair(Resource resource, int distance) {
        this.resource = resource;
        this.distance = distance;
    }

    public String toString() {
        return resource.toString() + " " + distance;
    }
}