import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.query.Dataset;

import java.util.Optional;


public class JenaTest {

    public static void main(String[] args) {
        System.out.println("Test Begin!");

        String directory = "/home/xianyang/kb/d-fb/";
        Dataset dataset = TDB2Factory.connectDataset(directory);

        dataset.begin(ReadWrite.READ);
        System.out.println("Empty: " + dataset.isEmpty());
        Model model = dataset.getDefaultModel();

        String jb = "<http://rdf.freebase.com/ns/m.06w2sn5>";
        Resource JB = model.getResource(jb);
        StmtIterator it = JB.listProperties();
        while (it.hasNext()) {
            System.out.println("***");
            System.out.println(it.next().getString());
        }

        it = model.listStatements();
        for (int i = 0; i < 100; ++i) {
            if (it.hasNext()) {
                Statement stmt = it.nextStatement();
                String subj = stmt.getSubject().getURI();
                System.out.println(subj);
            }
            else {
                System.out.println("no stmt");
                break;
            }
        }

        /*
        Optional<String> nullStr = Optional.empty();
        Optional<Property> nullProp = Optional.empty();

        it = model.listStatements(JB, nullProp.get(), nullStr.get());
        while (it.hasNext()) {
            System.out.println(it.next().getString());
        }
        */
        dataset.end();

        System.out.println("Test Over!");
    }

}
