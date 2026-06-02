using GigaChadSysModel;

namespace GigaChadSysBL;

public interface SesionClaseIBL
{
    List<SesionClase> ListAll();
    List<SesionClase> ListByEntrenador(int idEntrenador);
    SesionClase? Load(int id);
    SesionClase Save(SesionClase sesionClase);
    SesionClase? Update(SesionClase sesionClase);
    void Remove(SesionClase sesionClase);
}