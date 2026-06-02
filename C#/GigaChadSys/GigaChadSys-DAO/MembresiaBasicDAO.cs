using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface MembresiaBasicDAO
{
    List<MembresiaBasic> ListAll();
    MembresiaBasic? Load(int id);
    MembresiaBasic Save(MembresiaBasic membresiaBasic);
    MembresiaBasic? Update(MembresiaBasic membresiaBasic);
    void Remove(MembresiaBasic membresiaBasic);
}