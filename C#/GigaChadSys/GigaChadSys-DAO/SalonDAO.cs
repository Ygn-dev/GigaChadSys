using GigaChadSysModel;

namespace GigaChadSysDAO;

public interface SalonDAO
{
    List<Salon> ListAll();
    Salon? Load(int id);
    Salon Save(Salon salon);
    Salon? Update(Salon salon);
    void Remove(Salon salon);
}