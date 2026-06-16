package pe.edu.pucp.gigachadsys.bl.impl.pagos;

import pe.edu.pucp.gigachadsys.bl.inter.pagos.MetodoPagoBL;
import pe.edu.pucp.gigachadsys.dao.impl.pagos.MetodoPagoDAOImpl;
import pe.edu.pucp.gigachadsys.dao.inter.pagos.MetodoPagoDAO;
import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;

import java.util.List;

public class MetodoPagoBLImpl implements MetodoPagoBL {
    private MetodoPagoDAO metodoPagoDAO = new MetodoPagoDAOImpl();

    @Override
    public List<MetodoPago> listarMetodosDePago() {
        return metodoPagoDAO.listAll();
    }

    @Override
    public MetodoPago obtenerPorId(int idMetodoPago) {
        return metodoPagoDAO.load(idMetodoPago);
    }

    @Override
    public String registrar(MetodoPago metodoPago) {
        try {
            metodoPagoDAO.save(metodoPago);
            return "Método de pago registrado correctamente.";
        } catch (Exception e) {
            return "Error al registrar método de pago: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(int idMetodoPago, MetodoPago metodoPago) {
        try {
            metodoPago.setIdMetodoPago(idMetodoPago);
            metodoPagoDAO.update(metodoPago);
            return "Método de pago actualizado correctamente.";
        } catch (Exception e) {
            return "Error al actualizar método de pago: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(int idMetodoPago) {
        try {
            MetodoPago metodoPago = new MetodoPago();
            metodoPago.setIdMetodoPago(idMetodoPago);
            metodoPago.setActivo(false);
            metodoPagoDAO.remove(metodoPago);
            return "Método de pago eliminado correctamente.";
        } catch (Exception e) {
            return "Error al eliminar método de pago: " + e.getMessage();
        }
    }
}
