import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.query.Dataset;


public class JenaTest {

    public static void main(String[] args) {
        System.out.println("Test Begin!");

        String directory = "/home/xianyang/kb/d-fb/";
        Dataset dataset = TDB2Factory.connectDataset(directory);

        dataset.begin(ReadWrite.READ);
        Model model = dataset.getDefaultModel();

        String jb = "<http://rdf.freebase.com/ns/m.06w2sn5>";
        model.getResource(jb);

        dataset.end();

        System.out.println("Test Over!");
    }

}
