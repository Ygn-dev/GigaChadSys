using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para MembresiaBlack.
/// Java: MembresiaBlack extends Membresia
///   Membresia: idMembresia, nombre, activa
///   MembresiaBlack: + costoMantenimientoAnual, cantidadInvitadosPorMes
/// BD: MembresiaBlack(membresia_ID, nombrePlan, costoMantenimientoAnual, cantidadInvitadosPorMes, activo)
/// Endpoint: GET/POST/PUT /MembresiaBlackRS
/// </summary>
public class MembresiaBlackDTO
{
    /// <summary>
    /// El getter en Java es getIdMembresia() → JSON key: "idMembresia"
    /// BD columna: membresia_ID
    /// </summary>
    [JsonPropertyName("idMembresia")]
    public int IdMembresia { get; set; }

    /// <summary>
    /// El getter en Java es getNombre() → JSON key: "nombre"
    /// BD columna: nombrePlan
    /// </summary>
    [JsonPropertyName("nombre")]
    public string Nombre { get; set; } = "Black";

    /// <summary>
    /// Precio anual del plan Black.
    /// BD columna: costoMantenimientoAnual
    /// </summary>
    [JsonPropertyName("costoMantenimientoAnual")]
    public double CostoMantenimientoAnual { get; set; }

    /// <summary>
    /// Cantidad de invitados permitidos por mes.
    /// BD columna: cantidadInvitadosPorMes
    /// </summary>
    [JsonPropertyName("cantidadInvitadosPorMes")]
    public int CantidadInvitadosPorMes { get; set; }

    /// <summary>
    /// El getter en Java es isActiva() → JSON key: "activa"
    /// </summary>
    [JsonPropertyName("activa")]
    public bool Activa { get; set; }
}
