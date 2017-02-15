package pagina;

/**
 * Created by Luciano on 07/02/2017.
 */
import java.sql.SQLException;

public interface IPagina {
    void getHead() throws SQLException;
    void getBody() throws SQLException;
}
