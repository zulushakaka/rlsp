import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.query.Dataset;

import java.util.Optional;


public class JenaTest {

    public static void main(String[] args) {
        System.out.println("Test Begin!");

        String directory = "/home/xianyang/kb/d-fb/";
        Dataset dataset = TDBFactory.createDataset(directory);

        dataset.begin(ReadWrite.READ);
        System.out.println("Empty: " + dataset.isEmpty());
        Model model = dataset.getDefaultModel();
        System.out.println("Empty: " + model.isEmpty());

        String jb = "http://rdf.freebase.com/ns/m.06w2sn5";
        Resource JB = model.getResource(jb);
        StmtIterator it = JB.listProperties();
        while (it.hasNext()) {
            System.out.println("***");
            Statement stmt = it.nextStatement();
            System.out.println(stmt.getPredicate() + "\t" + stmt.getObject());
        }

        it = model.listStatements();
        for (int i = 0; i < 100; ++i) {
            if (it.hasNext()) {
                Statement stmt = it.nextStatement();
                String subj = stmt.getSubject().getURI();
                String predicate = stmt.getPredicate().getURI();
                String obj = stmt.getObject().toString();
                System.out.println(subj + "\t" + predicate + "\t" + obj);
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
