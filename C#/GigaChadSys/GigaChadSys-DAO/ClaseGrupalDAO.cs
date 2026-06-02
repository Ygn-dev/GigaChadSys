using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface ClaseGrupalDAO
{
    List<ClaseGrupal> ListAll();
    ClaseGrupal? Load(int id);
    ClaseGrupal Save(ClaseGrupal claseGrupal);
    ClaseGrupal? Update(ClaseGrupal claseGrupal);
    void Remove(ClaseGrupal claseGrupal);
}