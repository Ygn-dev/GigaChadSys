package pe.edu.pucp.gigachadsys.bl.impl.pagos;

import pe.edu.pucp.gigachadsys.bl.inter.pagos.PagoBL;
import pe.edu.pucp.gigachadsys.dao.impl.pagos.PagoDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.pagos.PagoDAO;
import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.util.List;

public class PagoBLImpl implements PagoBL {
    private PagoDAO pagoDAO = new PagoDAOImpl();

    @Override
    public List<Pago> listarPagos() {
        return pagoDAO.listAll();
    }

    @Override
    public Pago obtenerPorId(int idPago) {
        return pagoDAO.load(idPago);
    }

    @Override
    public String registrar(Pago pago) {
        try {
            pagoDAO.save(pago);
            return "Pago registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar pago: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idPago, Pago pago) {
        try {
            pago.setIdPago(idPago);
            pagoDAO.update(pago);
            return "Pago actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar pago: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idPago) {
        try {
            Pago pago = new Pago();
            pago.setIdPago(idPago);
            pago.setActivo(false);
            pagoDAO.remove(pago);
            return "Pago eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar pago: " + e.getMessage();
        }
    }
}
