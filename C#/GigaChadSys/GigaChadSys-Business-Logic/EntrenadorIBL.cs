using GigaChadSysModel;

namespace GigaChadSysBL;

public interface EntrenadorIBL
{
    List<Entrenador> ListAll();
    Entrenador? Load(int id);
    Entrenador Save(Entrenador entrenador);
    Entrenador? Update(Entrenador entrenador);
    void Remove(Entrenador entrenador);
}