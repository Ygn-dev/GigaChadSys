using GigaChadSysModel;

namespace GigaChadSysBL;

public interface MembresiaBlackIBL
{
    List<MembresiaBlack> ListAll();
    MembresiaBlack? Load(int id);
    MembresiaBlack Save(MembresiaBlack membresiaBlack);
    MembresiaBlack? Update(MembresiaBlack membresiaBlack);
    void Remove(MembresiaBlack membresiaBlack);
}