package pe.edu.pucp.gigachadsys.bl.inter.pagos;

import pe.edu.pucp.gigachadsys.model.pagos.Pago;

import java.util.List;

public interface PagoBL {
    List<Pago> listarPagos();
    Pago obtenerPorId(int idPago);
    String registrar(Pago pago);
    String actualizar(int idPago, Pago pago);
    String eliminar(int idPago);
}
