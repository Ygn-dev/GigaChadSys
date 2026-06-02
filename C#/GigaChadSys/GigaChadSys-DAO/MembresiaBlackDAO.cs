using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface MembresiaBlackDAO
{
    List<MembresiaBlack> ListAll();
    MembresiaBlack? Load(int id);
    MembresiaBlack Save(MembresiaBlack membresiaBlack);
    MembresiaBlack? Update(MembresiaBlack membresiaBlack);
    void Remove(MembresiaBlack membresiaBlack);
}