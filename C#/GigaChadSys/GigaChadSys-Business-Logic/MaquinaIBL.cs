using GigaChadSysModel;

namespace GigaChadSysBL;

public interface MaquinaIBL
{
    List<Maquina> ListAll();
    Maquina? Load(int id);
    Maquina Save(Maquina maquina);
    Maquina? Update(Maquina maquina);
    void Remove(Maquina maquina);
}