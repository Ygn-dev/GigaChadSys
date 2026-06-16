package pe.edu.pucp.gigachadsys.bl.inter.pagos;

import pe.edu.pucp.gigachadsys.model.pagos.MetodoPago;

import java.util.List;

public interface MetodoPagoBL {
    List<MetodoPago> listarMetodosDePago();
    MetodoPago obtenerPorId(int idMetodoPago);
    String registrar(MetodoPago metodoPago);
    String actualizar(int idMetodoPago, MetodoPago metodoPago);
    String eliminar(int idMetodoPago);
}
