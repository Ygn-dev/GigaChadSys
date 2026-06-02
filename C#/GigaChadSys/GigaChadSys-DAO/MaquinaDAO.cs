using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface MaquinaDAO
{
    List<Maquina> ListAll();
    Maquina? Load(int id);
    Maquina Save(Maquina maquina);
    Maquina? Update(Maquina maquina);
    void Remove(Maquina maquina);
}