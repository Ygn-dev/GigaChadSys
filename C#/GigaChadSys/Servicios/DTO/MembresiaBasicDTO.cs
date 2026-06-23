using System.Text.Json.Serialization;

namespace GigaChadSys.Servicios.DTO;

/// <summary>
/// DTO que mapea al JSON de Java para MembresiaBasic.
/// Java: MembresiaBasic extends Membresia
///   Membresia: idMembresia, nombre, activa
///   MembresiaBasic: + costoMantenimientoMensual
/// BD: MembresiaBasic(membresia_ID, nombrePlan, costoMantenimientoMensual, activo)
/// Endpoint: GET/POST/PUT /MembresiaBasicRS
/// </summary>
public class MembresiaBasicDTO
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
    public string Nombre { get; set; } = "Basic";

    /// <summary>
    /// Precio mensual del plan Basic.
    /// BD columna: costoMantenimientoMensual
    /// </summary>
    [JsonPropertyName("costoMantenimientoMensual")]
    public double CostoMantenimientoMensual { get; set; }

    /// <summary>
    /// El getter en Java es isActiva() → JSON key: "activa"
    /// </summary>
    [JsonPropertyName("activa")]
    public bool Activa { get; set; }
}
