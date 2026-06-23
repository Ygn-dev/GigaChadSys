import java.sql.Connection;
import pe.edu.pucp.gigachadsys.dao.manager.DBManager;
public class TestDB {
    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        Connection c1 = DBManager.getInstance().getConnection();
        long t2 = System.currentTimeMillis();
        System.out.println("Conn 1 took: " + (t2-t1) + "ms");
        Connection c2 = DBManager.getInstance().getConnection();
        long t3 = System.currentTimeMillis();
        System.out.println("Conn 2 took: " + (t3-t2) + "ms");
        Connection c3 = DBManager.getInstance().getConnection();
        long t4 = System.currentTimeMillis();
        System.out.println("Conn 3 took: " + (t4-t3) + "ms");
    }
}
