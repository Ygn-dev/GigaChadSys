namespace GigaChadSysModel;

public class Salon
{
    public int IdSalon { get; set; }
    public string NombreSalon { get; set; } = "";
    public int AforoMaximo { get; set; }
    public bool Activo { get; set; }

    public Salon() { }

    public Salon(int idSalon, string nombreSalon, int aforoMaximo, bool activo)
    {
        IdSalon = idSalon;
        NombreSalon = nombreSalon;
        AforoMaximo = aforoMaximo;
        Activo = activo;
    }
}