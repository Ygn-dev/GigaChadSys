using GigaChadSysModel;

namespace GigaChadSysBL;

public interface ClaseGrupalIBL
{
    List<ClaseGrupal> ListAll();
    ClaseGrupal? Load(int id);
    ClaseGrupal Save(ClaseGrupal claseGrupal);
    ClaseGrupal? Update(ClaseGrupal claseGrupal);
    void Remove(ClaseGrupal claseGrupal);
}