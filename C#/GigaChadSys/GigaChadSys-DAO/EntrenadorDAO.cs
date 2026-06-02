using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface EntrenadorDAO
{
    List<Entrenador> ListAll();
    Entrenador? Load(int id);
    Entrenador? LoadByEmail(string email);
    Entrenador Save(Entrenador entrenador);
    Entrenador? Update(Entrenador entrenador);
    void Remove(Entrenador entrenador);
}