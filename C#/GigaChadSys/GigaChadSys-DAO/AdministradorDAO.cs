using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface AdministradorDAO
{
    List<Administrador> ListAll();
    Administrador? Load(int id);
    Administrador? LoadByEmail(string email);
    Administrador Save(Administrador administrador);
    Administrador? Update(Administrador administrador);
    void Remove(Administrador administrador);
}