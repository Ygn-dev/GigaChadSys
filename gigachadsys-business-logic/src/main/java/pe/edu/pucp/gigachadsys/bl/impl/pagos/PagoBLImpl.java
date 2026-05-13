package pe.edu.pucp.gigachadsys.bl.impl.pagos;

import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.dao.impl.pagos.PagoDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.pagos.PagoDAO;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.util.List;

public class PagoBLImpl implements PagoBL {
    private PagoDAO socioDAO = new PagoDAOImpl();

    @Override
    public List<Pago> listarPagos() {
        return socioDAO.listAll();
    }
}
