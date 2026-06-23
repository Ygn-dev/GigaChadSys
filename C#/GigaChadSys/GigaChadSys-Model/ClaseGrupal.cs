namespace GigaChadSysModel;

public class ClaseGrupal
{
    public int IdClase { get; set; }
    public string NombreDisciplina { get; set; } = "";
    public string Descripcion { get; set; } = "";
    public int DuracionMinutos { get; set; }
    public string Nivel { get; set; } = "";
    public bool Activo { get; set; }

    public ClaseGrupal() { }

    public ClaseGrupal(int idClase, string nombreDisciplina, string descripcion,
                       int duracionMinutos, string nivel, bool activo)
    {
        IdClase = idClase;
        NombreDisciplina = nombreDisciplina;
        Descripcion = descripcion;
        DuracionMinutos = duracionMinutos;
        Nivel = nivel;
        Activo = activo;
    }
}