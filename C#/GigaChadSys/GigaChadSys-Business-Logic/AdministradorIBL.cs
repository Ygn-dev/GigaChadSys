using GigaChadSysModel;

namespace GigaChadSysBL;

public interface AdministradorIBL
{
    List<Administrador> ListAll();
    Administrador? Load(int id);
    Administrador Save(Administrador administrador);
    Administrador? Update(Administrador administrador);
    void Remove(Administrador administrador);
}