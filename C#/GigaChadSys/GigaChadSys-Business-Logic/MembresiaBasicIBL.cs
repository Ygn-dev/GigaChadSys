using GigaChadSysModel;

namespace GigaChadSysBL;

public interface MembresiaBasicIBL
{
    List<MembresiaBasic> ListAll();
    MembresiaBasic? Load(int id);
    MembresiaBasic Save(MembresiaBasic membresiaBasic);
    MembresiaBasic? Update(MembresiaBasic membresiaBasic);
    void Remove(MembresiaBasic membresiaBasic);
}